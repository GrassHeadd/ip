package Ozymandias.ui;

import Ozymandias.Tasks.Deadlines;
import Ozymandias.Tasks.Events;
import Ozymandias.Tasks.Task;
import Ozymandias.Tasks.ToDos;

/**
 * Utility class providing static methods to handle the creation of different
 * task types based on user input.
 */
public class HandleTaskCreate {

    /**
     * Creates a new todos task from the user input.
     *
     * @param input The raw user input string starting with "todos".
     * @param oz    The main chatbot object that manages the task list and provides
     *              methods to add tasks.
     * @return A string indicating success or an error message if the description
     *         is invalid or empty.
     */
    public static String handleTodoCreation(String input, Ozymandias oz) {
        // e.g., "todo buy groceries"
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            return "no description!!";
        }
        Task newTask = new ToDos(description);
        return oz.addTask(newTask);
    }

    /**
     * Creates a new Deadlines task from the user input.
     *
     * @param input The raw user input string starting with "deadline".
     * @param oz    The main chatbot object that manages the task list and provides
     *              methods to add tasks.
     * @return A string indicating success or an error message if the format is
     *         invalid or the date is missing.
     */
    public static String handleDeadlineCreation(String input, Ozymandias oz) {
        // e.g., "deadline return book /by 2025-01-28"
        String[] parts = input.substring(8).split("/by");
        String description = parts[0].trim();
        if (description.isEmpty()) {
            return "no description!!";
        }
        if (parts.length < 2) {
            if(parts[0].contains("/")){
                return "wrong command format";
            }
            return "Provide a valid date in yyyy-MM-dd e.g.\n"
                    + "deadline return book /by 2025-01-28";
        }
        String dueDate = parts[1].trim();
        Task newTask = new Deadlines(description, dueDate);
        return oz.addTask(newTask);
    }

    /**
     * Creates a new Events task from the user input.
     *
     * @param input The raw user input string starting with "events".
     * @param oz    The main chatbot object that manages the task list and provides
     *              methods to add tasks.
     * @return A string indicating success or an error message if the format is
     *         invalid or the dates are missing.
     */
    public static String handleEventCreation(String input, Ozymandias oz) {
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
