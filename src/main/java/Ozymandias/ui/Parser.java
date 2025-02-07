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
    public static void handleCommand(String input, Ozymandias oz) {
        try{
            if (input.startsWith("find")) {
                try {
                    String[] parts = input.split("find ", 2); // Split only once
                    if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        System.out.println("    Enter a keyword to search for!\n");
                    } else {
                        String result = oz.findTask(parts[1].trim());
                        System.out.println(result);
                    }
                } catch (Exception e) {
                    System.out.println("    Error finding task: " + e.getMessage() + "\n");
                }
                return;
            } else if (input.equalsIgnoreCase("bye")) {
                oz.getUi().greetGoodbye();
                oz.setExit(true);
            } else if (input.trim().equalsIgnoreCase("list")) {
                oz.printTasks();
            } else if (input.startsWith("mark") || input.startsWith("unmark")) {
                    String[] tokens = input.split(" ");
                    int taskId = Integer.parseInt(tokens[1]);
                    if (!oz.getTasks().hasTask(taskId)) {
                        System.out.println("    THERE IS NO TASK WITH ID " + taskId + "\n");
                        return;
                    }
                    oz.markTask(taskId, input.startsWith("mark"));
            } else if (input.startsWith("delete")) {
                    String[] tokens = input.split(" ");
                    int taskId = Integer.parseInt(tokens[1]);
                    oz.deleteTask(taskId);
            } else {
                    Task newTask = createDifferentTask(input);
                    if (newTask == null) {
                        System.out.println("    Reflect on your idiocy! Look at what you're asking me to do\n");
                        return;
                    }
                    oz.addTask(newTask);
            }
        } catch (DateTimeParseException e) {
            System.out.println("    Invalid date you entered\n");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("    Missing task details or invalid format you banana pants");
        } catch (NumberFormatException e) {
            System.out.println("    Invalid task id you entered\n");
        } catch (NullPointerException e) {
            System.out.println("    You didn't put anything!\n");
        } catch (Exception e) {
            System.out.println("    You did some stupid stuff, find out yourself!\n");
        }

    }


    /**
     * Method responsible for creating the different task base on the user input,
     * gets called by handleCommand when a new task needs to be created
     *
     * @param input Details of task to be created from the user
     * @return The corresponding tasks requested to be created
     */
    public static Task createDifferentTask(String input) {
        try {
            Task newTask = null;

            if (input.startsWith("todo")) {
                String description = input.substring(4).trim();
                if (description.isEmpty()) {return null;}
                newTask = new ToDos(description);
                return newTask;

            } else if (input.startsWith("deadline")) {
                //"deadline return book /by 2025-01-28"
                String[] parts = input.substring(8).split("/by");
                String description = parts[0].trim();
                if (parts.length < 2) {
                    System.out.println("    Provide a valid date in yyyy-MM-dd");
                    return null;
                }
                String dueDate = parts[1].trim();
                newTask = new Deadlines(description, dueDate);
                return newTask;

            } else if (input.startsWith("event")) {
                //"event project presentation /from 2025-03-10 /to 2025-03-11"
                String[] parts = input.substring(5).split("/from|/to");
                if (parts.length < 3) {
                    System.out.println("    Provide an actual valid date in yyyy-MM-dd, e.g.:");
                    System.out.println("    event project presentation /from 2025-03-10 /to 2025-03-11\n idiot \n");
                    return null;
                }
                String description = parts[0].trim();
                String startDate = parts[1].trim();
                String endDate = parts[2].trim();
                newTask = new Events(description, startDate, endDate);
                return newTask;
            }
        } catch (DateTimeParseException e) {
            System.out.println("    Invalid date you entered\n");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("    Missing task details or invalid format you banana pants");
        } catch (NullPointerException e) {
            System.out.println("    You didn't put anything!\n");
        } catch (Exception e) {
            System.out.println("    idk man think youself what you did wrong!\n");
        }
        return null;
    }
}
