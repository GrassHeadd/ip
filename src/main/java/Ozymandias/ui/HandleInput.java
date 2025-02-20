package Ozymandias.ui;

import Ozymandias.Tasks.Deadlines;
import Ozymandias.Tasks.Events;

import java.time.LocalDate;

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

    public static boolean isRemindCommand(String input) {
        return input.equalsIgnoreCase("remind");
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

    public static String handleRemindCommand(String input, Ozymandias oz) {
        TaskList tasks = oz.getTasks();
        String output = "Here are the upcoming stuff, get prepared loser!\n";
        for (int i = 0; i < tasks.size(); i++) {
            if((tasks.getTask(i+1).getTaskType().equals("[E]")) || (tasks.getTask(i+1).getTaskType().equals("[D]"))) {
                LocalDate endDate = tasks.getTask(i+1).getEndDate();
                if (checkDate(endDate, LocalDate.now())) {
                    System.out.println("check date");
                    System.out.println(tasks.getTask(i+1).toString());
                    System.out.println(tasks);
                    System.out.println("adding");
                    output = output + i + "." + tasks.getTask(i+1).toString() + "\n";
                }
            }
        }

        if (tasks.size() == 0) {
            return "You surprisingly don't have any upcoming tasks! " +
                    "You are either very boring or your entire life is too much a mess!";
        }

        return output;
    }

    private static Boolean checkDate(LocalDate firstDate, LocalDate secondDate) {
        return firstDate.isAfter(secondDate);
    }

}
