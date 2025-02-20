package Ozymandias.ui;

import Ozymandias.Tasks.Deadlines;
import Ozymandias.Tasks.Events;
import Ozymandias.Tasks.Task;
import Ozymandias.Tasks.ToDos;

public class HandleTaskCreate {
    /**
     * Handles creation of a "todo" task
     */
    public static String handleTodoCreation(String input, Ozymandias oz) {
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
    public static String handleDeadlineCreation(String input, Ozymandias oz) {
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
