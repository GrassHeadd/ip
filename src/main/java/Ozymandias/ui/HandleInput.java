package Ozymandias.ui;

import Ozymandias.Tasks.Task;
import java.time.LocalDate;

/**
 * Provides static utility methods to parse and handle user input commands
 * such as find, bye, list, mark/unmark, delete, and remind.
 */
public class HandleInput {

    /**
     * Checks if the user input starts with the "find" command.
     *
     * @param input The raw user input.
     * @return True if the input starts with "find", else false.
     */
    public static boolean isFindCommand(String input) {
        return input.startsWith("find");
    }

    /**
     * Checks if the user input is a "bye" command.
     *
     * @param input The raw user input.
     * @return True if the input is "bye" (case-insensitive), else false.
     */
    public static boolean isByeCommand(String input) {
        return input.equalsIgnoreCase("bye");
    }

    /**
     * Checks if the user input is a "list" command.
     *
     * @param input The raw user input.
     * @return True if the input is "list" (case-insensitive), else false.
     */
    public static boolean isListCommand(String input) {
        return input.equalsIgnoreCase("list");
    }

    /**
     * Checks if the user input starts with "mark" or "unmark" command.
     *
     * @param input The raw user input.
     * @return True if the input starts with "mark" or "unmark", else false.
     */
    public static boolean isMarkOrUnmarkCommand(String input) {
        return input.startsWith("mark") || input.startsWith("unmark");
    }

    /**
     * Checks if the user input starts with "delete" command.
     *
     * @param input The raw user input.
     * @return True if the input starts with "delete", else false.
     */
    public static boolean isDeleteCommand(String input) {
        return input.startsWith("delete");
    }

    /**
     * Checks if the user input is a "remind" command.
     *
     * @param input The raw user input.
     * @return True if the input equals "remind" (case-insensitive), else false.
     */
    public static boolean isRemindCommand(String input) {
        return input.equalsIgnoreCase("remind");
    }

    /**
     * Handles the "find" command by extracting the keyword and delegating
     * the search to the chatbot's findTask method.
     *
     * @param input The raw user input that starts with "find".
     * @param oz    The main chatbot object to perform the search.
     * @return A string containing the search results or an error message if no keyword is provided.
     */
    public static String handleFindCommand(String input, Ozymandias oz) {
        String[] parts = input.split("find ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            return "    Enter a keyword to search for!\n";
        }
        String keyword = parts[1].trim();
        return oz.findTask(keyword);
    }

    /**
     * Handles "mark" or "unmark" commands, parsing the task ID from the input
     * and marking or unmarking the specified task.
     *
     * @param input The raw user input starting with "mark" or "unmark".
     * @param oz    The main chatbot object that manages tasks.
     * @return A string indicating the result of the mark/unmark operation,
     *         or an error message if the task does not exist.
     */
    public static String handleMarkOrUnmark(String input, Ozymandias oz) {
        String[] tokens = input.split(" ");
        int taskId = Integer.parseInt(tokens[1]);
        if (!oz.getTasks().hasTask(taskId)) {
            return "    THERE IS NO TASK WITH ID " + taskId + "\n";
        }
        boolean isMark = input.startsWith("mark");
        return oz.markTask(taskId, isMark);
    }

    /**
     * Handles the "delete" command by parsing the task ID and removing the task
     * from the chatbot's task list.
     *
     * @param input The raw user input starting with "delete".
     * @param oz    The main chatbot object that manages tasks.
     * @return A string indicating the result of the deletion,
     *         or an error message if the task does not exist.
     */
    public static String handleDeleteCommand(String input, Ozymandias oz) {
        String[] tokens = input.split(" ");
        int taskId = Integer.parseInt(tokens[1]);
        return oz.deleteTask(taskId);
    }

    /**
     * Handles the "remind" command to display upcoming tasks that are either
     * Deadlines or Events, and have an end date later than the current date.
     *
     * @param input The raw user input "remind".
     * @param oz    The main chatbot object that manages tasks.
     * @return A string listing upcoming tasks, or a message if no tasks exist.
     */
    public static String handleRemindCommand(String input, Ozymandias oz) {
        TaskList tasks = oz.getTasks();
        String output = "Here are the upcoming stuff, get prepared loser!\n";

        for (int i = 0; i < tasks.size(); i++) {
            Task currentTask = tasks.getTask(i + 1);
            // Check if it's a deadline or event
            if (currentTask.getTaskType().equals("[E]") || currentTask.getTaskType().equals("[D]")) {
                LocalDate endDate = currentTask.getEndDate();
                // If endDate is after today's date
                if (checkDate(endDate, LocalDate.now())) {
                    output += (i + 1) + "." + currentTask.toString() + "\n";
                }
            }
        }

        if (tasks.size() == 0) {
            return "You surprisingly don't have any upcoming tasks! " +
                    "You are either very boring or your entire life is too much a mess!";
        }

        return output;
    }

    /**
     * Checks if the first date is after the second date.
     *
     * @param firstDate  The first LocalDate to compare.
     * @param secondDate The second LocalDate to compare.
     * @return True if {@code firstDate} is strictly after {@code secondDate},
     *         otherwise false.
     */
    private static Boolean checkDate(LocalDate firstDate, LocalDate secondDate) {
        return firstDate.isAfter(secondDate);
    }
}
