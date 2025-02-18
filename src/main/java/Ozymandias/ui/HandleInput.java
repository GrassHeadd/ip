package Ozymandias.ui;

public class HandleInput {
    public static boolean isFindCommand(String input) {
        return input.startsWith("find");
    }

    public static boolean isByeCommand(String input) {
        return input.equalsIgnoreCase("bye");
    }

    public static boolean isListCommand(String input) {
        return input.equalsIgnoreCase("list");
    }

    public static boolean isMarkOrUnmarkCommand(String input) {
        return input.startsWith("mark") || input.startsWith("unmark");
    }

    public static boolean isDeleteCommand(String input) {
        return input.startsWith("delete");
    }

    public static String handleFindCommand(String input, Ozymandias oz) {
        String[] parts = input.split("find ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            return "    Enter a keyword to search for!\n";
        }
        String keyword = parts[1].trim();
        return oz.findTask(keyword);
    }

    public static String handleMarkOrUnmark(String input, Ozymandias oz) {
        String[] tokens = input.split(" ");
        int taskId = Integer.parseInt(tokens[1]);
        if (!oz.getTasks().hasTask(taskId)) {
            return "    THERE IS NO TASK WITH ID " + taskId + "\n";
        }
        boolean isMark = input.startsWith("mark");
        return oz.markTask(taskId, isMark);
    }

    public static String handleDeleteCommand(String input, Ozymandias oz) {
        String[] tokens = input.split(" ");
        int taskId = Integer.parseInt(tokens[1]);
        return oz.deleteTask(taskId);
    }
}
