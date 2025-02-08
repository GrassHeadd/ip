package Ozymandias.ui;

import java.time.format.DateTimeParseException;

import Ozymandias.Task.Deadlines;
import Ozymandias.Task.Events;
import Ozymandias.Task.Task;
import Ozymandias.Task.ToDos;

public class Parser {

    /**
     * Takes in user input and call corresponding functions base on the input by the user
     *
     * @param input Input by the user
     * @param oz The main chatbot object
     */
    public static String handleCommand(String input, Ozymandias oz) {
        try{
            if (input.startsWith("find")) {
                try {
                    String[] parts = input.split("find ", 2); // Split only once
                    if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        return ("    Enter a keyword to search for!\n");
                    } else {
                        return oz.findTask(parts[1].trim());
                    }
                } catch (Exception e) {
                    return ("    Error finding task: " + e.getMessage() + "\n");
                }
            } else if (input.equalsIgnoreCase("bye")) {
                oz.setExit(true);
                return oz.getUi().greetGoodbye();

            } else if (input.trim().equalsIgnoreCase("list")) {
                return oz.printTasks();
            } else if (input.startsWith("mark") || input.startsWith("unmark")) {
                String[] tokens = input.split(" ");
                int taskId = Integer.parseInt(tokens[1]);
                if (!oz.getTasks().hasTask(taskId)) {
                    return ("    THERE IS NO TASK WITH ID " + taskId + "\n");
                }
                return oz.markTask(taskId, input.startsWith("mark"));
            } else if (input.startsWith("delete")) {
                    String[] tokens = input.split(" ");
                    int taskId = Integer.parseInt(tokens[1]);
                    return oz.deleteTask(taskId);
            } else {
                    return createDifferentTask(input, oz);
            }
        } catch (DateTimeParseException e) {
            return ("    Invalid date you entered\n");
        } catch (IndexOutOfBoundsException e) {
            return ("    Missing task details or invalid format you banana pants");
        } catch (NumberFormatException e) {
            return ("    Invalid task id you entered\n");
        } catch (NullPointerException e) {
            return ("    You didn't put anything!\n");
        } catch (Exception e) {
            return ("    You did some stupid stuff, find out yourself!\n");
        }
    }


    /**
     * Method responsible for creating the different task base on the user input,
     * gets called by handleCommand when a new task needs to be created
     *
     * @param input Details of task to be created from the user
     * @return The corresponding tasks requested to be created
     */
    public static String createDifferentTask(String input, Ozymandias oz) {
        try {
            Task newTask = null;

            if (input.startsWith("todo")) {
                String description = input.substring(4).trim();
                if (description.isEmpty()) {return "no description!!";}
                newTask = new ToDos(description);
                return oz.addTask(newTask);

            } else if (input.startsWith("deadline")) {
                //"deadline return book /by 2025-01-28"
                String[] parts = input.substring(8).split("/by");
                String description = parts[0].trim();
                if (description.isEmpty()) {return "no description!!";}
                if (parts.length < 2) {
                    return ("Provide a valid date in yyyy-MM-dd");
                }
                String dueDate = parts[1].trim();
                newTask = new Deadlines(description, dueDate);
                return oz.addTask(newTask);

            } else if (input.startsWith("event")) {
                //"event project presentation /from 2025-03-10 /to 2025-03-11"
                String[] parts = input.substring(5).split("/from|/to");
                String description = parts[0].trim();
                if (description.isEmpty()) {return "no description!!";}
                if (parts.length < 3) {
                    return ("Provide an actual valid date in yyyy-MM-dd, e.g.:\n")
                    + ("event project presentation /from 2025-03-10 /to 2025-03-11\n idiot \n");
                }
                String startDate = parts[1].trim();
                String endDate = parts[2].trim();
                newTask = new Events(description, startDate, endDate);
                return oz.addTask(newTask);
            }
        } catch (DateTimeParseException e) {
            return ("    Invalid date you entered\n");
        } catch (IndexOutOfBoundsException e) {
            return ("    Missing task details or invalid format you banana pants");
        } catch (NullPointerException e) {
            return ("    You didn't put anything!\n");
        } catch (Exception e) {
            return ("    idk man think yourself what you did wrong!\n");
        }
        return "what are you even asking me to do???";
    }
}
