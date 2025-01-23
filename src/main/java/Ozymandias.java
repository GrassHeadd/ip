import java.util.Scanner;
import java.util.ArrayList;
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

    public static void addTask(String text) {
        taskList.put(idCounter++, new Task(text));
        System.out.println("    added: " + text+ "\n");
    }

    public static LinkedHashMap<Integer, Task> getTasks() {
        return new LinkedHashMap<>(taskList); // Return a copy to prevent modification
    }

    public static void printTasks() {
        for (Integer id : taskList.keySet()) {
            System.out.println("    " + id + ". " + "[" + taskList.get(id).getStatusIcon() + "] " + taskList.get(id));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println(greetHello());
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                System.out.println(greetGoodbye());
                return;
            } else if (input.equalsIgnoreCase("list")) {
                printTasks();
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
            } else {
                addTask(input);
            }
        }
    }
}
