package Ozymandias.ui;

import java.time.format.DateTimeParseException;

import Ozymandias.Tasks.Deadlines;
import Ozymandias.Tasks.Events;
import Ozymandias.Tasks.Task;
import Ozymandias.Tasks.ToDos;

/**
 * A parser that interprets user commands and delegates tasks
 * to the appropriate methods in the Ozymandias chatbot.
 */
public class Parser {

    /**
     * Processes the user's input and calls the corresponding methods based on the command.
     *
     * @param input The raw command entered by the user.
     * @param oz    The main chatbot object.
     * @return A response message to be displayed to the user.
     */
    public static String handleCommand(String input, Ozymandias oz) {
        try {
            String trimmedInput = input.trim();
            if (isFindCommand(trimmedInput)) {
                return handleFindCommand(trimmedInput, oz);
            } else if (isByeCommand(trimmedInput)) {
                oz.setExit(true);
                return oz.getUi().greetGoodbye();
            } else if (isListCommand(trimmedInput)) {
                return oz.printTasks();
            } else if (isMarkOrUnmarkCommand(trimmedInput)) {
                return handleMarkOrUnmark(trimmedInput, oz);
            } else if (isDeleteCommand(trimmedInput)) {
                return handleDeleteCommand(trimmedInput, oz);
            } else {
                // If none of the above commands match, assume user wants to create a new Task.
                return createDifferentTask(trimmedInput, oz);
            }
        } catch (DateTimeParseException e) {
            return "    Invalid date you entered\n";
        } catch (IndexOutOfBoundsException e) {
            return "    Missing task details or invalid format you banana pants\n";
        } catch (NumberFormatException e) {
            return "    Invalid task id you entered\n";
        } catch (NullPointerException e) {
            return "    You didn't put anything!\n";
        } catch (Exception e) {
            return "    You did some stupid stuff, find out yourself!\n";
        }
    }

    private static boolean isFindCommand(String input) {
        return input.startsWith("find");
    }

    private static boolean isByeCommand(String input) {
        return input.equalsIgnoreCase("bye");
    }

    private static boolean isListCommand(String input) {
        return input.equalsIgnoreCase("list");
    }

    private static boolean isMarkOrUnmarkCommand(String input) {
        return input.startsWith("mark") || input.startsWith("unmark");
    }

    private static boolean isDeleteCommand(String input) {
        return input.startsWith("delete");
    }

    private static String handleFindCommand(String input, Ozymandias oz) {
        String[] parts = input.split("find ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            return "    Enter a keyword to search for!\n";
        }
        String keyword = parts[1].trim();
        return oz.findTask(keyword);
    }

    private static String handleMarkOrUnmark(String input, Ozymandias oz) {
        String[] tokens = input.split(" ");
        int taskId = Integer.parseInt(tokens[1]);
        if (!oz.getTasks().hasTask(taskId)) {
            return "    THERE IS NO TASK WITH ID " + taskId + "\n";
        }
        boolean isMark = input.startsWith("mark");
        return oz.markTask(taskId, isMark);
    }

    private static String handleDeleteCommand(String input, Ozymandias oz) {
        String[] tokens = input.split(" ");
        int taskId = Integer.parseInt(tokens[1]);
        return oz.deleteTask(taskId);
    }

    /**
     * Creates different task objects (ToDos, Deadlines, Events)
     * based on the user's input string.
     */
    public static String createDifferentTask(String input, Ozymandias oz) {
        try {
            if (input.startsWith("todo")) {
                return handleTodoCreation(input, oz);

            } else if (input.startsWith("deadline")) {
                return handleDeadlineCreation(input, oz);

            } else if (input.startsWith("event")) {
                return handleEventCreation(input, oz);
            }

        } catch (DateTimeParseException e) {
            return "    Invalid date you entered\n";
        } catch (IndexOutOfBoundsException e) {
            return "    Missing task details or invalid format you banana pants\n";
        } catch (NullPointerException e) {
            return "    You didn't put anything!\n";
        } catch (Exception e) {
            return "    idk man think yourself what you did wrong!\n";
        }

        // If the command doesn’t match any known pattern
        return "what are you even asking me to do???";
    }

    /**
     * Handles creation of a "todo" task
     */
    private static String handleTodoCreation(String input, Ozymandias oz) {
        // e.g. "todo buy groceries"
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            return "no description!!";
        }
        Task newTask = new ToDos(description);
        return oz.addTask(newTask);
    }

    /**
     * Handles creation of a "deadline" task
     */
    private static String handleDeadlineCreation(String input, Ozymandias oz) {
        // e.g. "deadline return book /by 2025-01-28"
        String[] parts = input.substring(8).split("/by");
        String description = parts[0].trim();
        if (description.isEmpty()) {
            return "no description!!";
        }
        if (parts.length < 2) {
            return "Provide a valid date in yyyy-MM-dd";
        }
        String dueDate = parts[1].trim();
        Task newTask = new Deadlines(description, dueDate);
        return oz.addTask(newTask);
    }

    /**
     * Handles creation of an "event" task
     */
    private static String handleEventCreation(String input, Ozymandias oz) {
        // e.g., "event project presentation /from 2025-03-10 /to 2025-03-11"
        String[] parts = input.substring(5).split("/from|/to");
        String description = parts[0].trim();
        if (description.isEmpty()) {
            return "no description!!";
        }
        if (parts.length < 3) {
            return "Provide an actual valid date in yyyy-MM-dd, e.g.:\n"
                    + "event project presentation /from 2025-03-10 /to 2025-03-11\n idiot \n";
        }
        String startDate = parts[1].trim();
        String endDate = parts[2].trim();
        Task newTask = new Events(description, startDate, endDate);
        return oz.addTask(newTask);
    }
}
