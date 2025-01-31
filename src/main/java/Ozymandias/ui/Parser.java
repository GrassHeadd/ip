package Ozymandias.ui;

import java.time.format.DateTimeParseException;

public class Parser {

    public static void handleCommand(String input, Ozymandias oz) {
        if (input.isBlank()) {System.out.println("    You didn't put anything you baffoon!!!\n");}

        if (input.equalsIgnoreCase("bye")) {
            oz.getUi().greetGoodbye();
            oz.setExit(true);
        } else if (input.equalsIgnoreCase("list")) {
            oz.printTasks();
        } else if (input.startsWith("mark") || input.startsWith("unmark")) {
            try {
                String[] tokens = input.split(" ");
                int taskId = Integer.parseInt(tokens[1]);
                if (!oz.getTasks().hasTask(taskId)) {
                    System.out.println("    THERE IS NO TASK WITH ID " + taskId + "\n");
                    return;
                }
                oz.markTask(taskId, input.startsWith("mark"));
            } catch (NumberFormatException e) {
                System.out.println("    TASK ID SHOULD BE AN INTEGER YOU MORON\n.");
            }
        } else if (input.startsWith("delete")) {
            try {
                String[] tokens = input.split(" ");
                int taskId = Integer.parseInt(tokens[1]);
                oz.deleteTask(taskId);
            } catch (NumberFormatException e) {
                System.out.println("    TASK ID SHOULD BE AN INTEGER YOU MORON\n");
            }
        } else {
            try {
                Task newTask = createDifferentTask(input);
                if (newTask == null) {
                    System.out.println("    Invalid task or missing details you idiot\n");
                    return;
                }
                oz.addTask(newTask);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("    Missing task details or invalid format you banana pants\n");
            }
        }
    }

    public static Task createDifferentTask(String input) {
        Task newTask = null;

        if (input.startsWith("todo")) {
            String description = input.substring(4).trim();
            if (description.isEmpty()) {
                System.out.println("    You can't todo nothing you apple piano\n");
                return null;
            }
            newTask = new ToDos(description);
        } else if (input.startsWith("deadline")) {
            //"deadline return book /by 2025-01-28"
            String[] parts = input.substring(8).split("/by");
            String description = parts[0].trim();
            if (description.isEmpty()) {
                System.out.println("    You can't todo nothing you banana plane\n");
                return null;
            }
            if (parts.length < 2) {
                System.out.println("    Provide a valid date in yyyy-MM-dd you mushroom table");
                return null;
            }
            String dueDate = parts[1].trim();
            try {
                newTask = new Deadlines(description, dueDate);
            } catch (DateTimeParseException e) {
                System.out.println("    Wrong date format for deadline scotch tape face!");
                return null;
            }

        } else if (input.startsWith("event")) {
            //"event project presentation /from 2025-03-10 /to 2025-03-11"
            String[] parts = input.substring(5).split("/from|/to");
            if (parts.length < 3) {
                System.out.println("    Provide an actual valid date in yyyy-MM-dd, e.g.:");
                System.out.println("    event project presentation /from 2025-03-10 /to 2025-03-11\n idiot \n");
                return null;
            }
            String description = parts[0].trim();
            if (description.isEmpty()) {
                System.out.println("    You can't event nothing you basketball telephone\n");
                return null;
            }
            String startDate = parts[1].trim();
            String endDate   = parts[2].trim();
            try {
                newTask = new Events(description, startDate, endDate);
            } catch (DateTimeParseException e) {
                System.out.println("    Wrong date format for event bread professor!\n");
                return null;
            }
        }
        return newTask;
    }
}
