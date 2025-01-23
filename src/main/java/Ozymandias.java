import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.LinkedHashMap;

public class Ozymandias {
    private static LinkedHashMap<Integer, Task> taskList = new LinkedHashMap<>();
    private static int idCounter = 1;

    public static String greetHello() {
        return """
                Hello! I'm Ozymandias
                What can I do for you?\s
               """;
    }

    public static String greetGoodbye() {
        return "    Bye. Hope to see you again soon!\n";
    }

    public static void addTask(String input) {
        Task newTask = null;

        if (input.startsWith("todo")) {
            String description = input.substring(5).trim();
            newTask = new ToDos(description);
        } else if (input.startsWith("deadline")) {
            String[] parts = input.substring(9).split("/by");
            String description = parts[0].trim();
            String dueDate = parts.length > 1 ? parts[1].trim() : "unspecified";
            newTask = new Deadlines(description, dueDate);
        } else if (input.startsWith("event")) {
            String[] parts = input.substring(6).split("/from|/to");
            String description = parts[0].trim();
            String startDate = parts.length > 1 ? parts[1].trim() : "unspecified";
            String endDate = parts.length > 2 ? parts[2].trim() : "unspecified";
            newTask = new Events(description, startDate, endDate);
        }

        if (newTask != null) {
            taskList.put(idCounter++, newTask);
            System.out.println("     Got it. I've added this task:");
            System.out.println("       " + newTask.getTaskType() + "[" + newTask.getStatusIcon() + "] " + newTask);
            System.out.println("     Now you have " + taskList.size() + " tasks in the list.");
            System.out.println();
        }
    }

    public static void printTasks() {
        System.out.println("     Here are the tasks in your list:");
        for (Integer id : taskList.keySet()) {
            Task task = taskList.get(id);
            System.out.println("     " + id + "." + task.getTaskType() + "[" + task.getStatusIcon() + "] " + task);
        }
        System.out.println();
    }


    public static void main(String[] args) {
        System.out.println(greetHello());
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            //CASE OF EXIT
            if (input.equalsIgnoreCase("bye")) {
                System.out.println(greetGoodbye());
                return;
            //CASE OF LISTING OUT THE TASKS
            } else if (input.equalsIgnoreCase("list")) {
                printTasks();
            //CASE OF MARKING AND UNMARKING TASK
            } else if (input.startsWith("mark") || input.startsWith("unmark")) {
                int taskId = 0;
                if (input.startsWith("mark")) {
                    taskId = Integer.parseInt(input.replace("mark ", "").trim());
                } else if (input.startsWith("unmark")) {
                    taskId = Integer.parseInt(input.replace("unmark ", "").trim());
                }

                if (taskList.containsKey(taskId)) {
                    taskList.get(taskId).toggleIsDone();
                } else {
                    System.out.println("No task found with id " + taskId);
                }
                System.out.println();
            //CASE OF ADDING INPUT
            } else {
                addTask(input);
            }
        }
    }
}
