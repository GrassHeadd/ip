import java.util.Scanner;
import java.util.LinkedHashMap;
import java.io.File;


public class Ozymandias {
    private static LinkedHashMap<Integer, Task> taskList = new LinkedHashMap<>();
    private static int idCounter = 1;

    public static String greetHello() {
        return """
            Behold! I am Ozymandias, King of Kings.
            """;
    }

    public static String greetGoodbye() {
        return """
           Go now, traveler
           """;
    }


    public static <T extends Task> T createDifferentTask(String input) {
        Task newTask = null;

        //type: todo
        if (input.startsWith("todo")) {
            String description = input.substring(5).trim();
            if (description.isEmpty()) {
                throw new IllegalArgumentException("Task description for 'todo' cannot be empty.");
            }
            newTask = new ToDos(description);

        //type deadline
        } else if (input.startsWith("deadline")) {
            String[] parts = input.substring(9).split("/by");
            String description = parts[0].trim();
            if (description.isEmpty()) {
                throw new IllegalArgumentException("Task description for 'deadline' cannot be empty.");
            }
            String dueDate = parts.length > 1 ? parts[1].trim() : "unspecified";
            newTask = new Deadlines(description, dueDate);

        //type event
        } else if (input.startsWith("event")) {
            String[] parts = input.substring(6).split("/from|/to");
            String description = parts[0].trim();
            if (description.isEmpty()) {
                throw new IllegalArgumentException("Task description for 'event' cannot be empty.");
            }
            String startDate = parts.length > 1 ? parts[1].trim() : "unspecified";
            String endDate = parts.length > 2 ? parts[2].trim() : "unspecified";
            newTask = new Events(description, startDate, endDate);
        }

        return (T) newTask;
    }

    public static void addTask(String input) {
        Task newTask = null;
        newTask = createDifferentTask(input);

        try {
                if (newTask == null) {
                    System.out.println("    You fool! Task shall have a type!");
                    return;
                }

                taskList.put(idCounter++, newTask);
                System.out.println("     Got it. I've added this task:\n" + "       " + newTask.getTaskType() + "[" + newTask.getStatusIcon() + "] " + newTask);
                System.out.println("     Now you have " + taskList.size() + " tasks in the list.");
                System.out.println();
                
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid task type");
        } catch (NullPointerException e) {
            System.out.println("Task name cannot be empty");
        }
    }

    public static void deleteTask(int id) {
        if (taskList.containsKey(id)) {
            Task removedTask = taskList.remove(id);
            System.out.println("     Noted. I've removed this task:");
            System.out.println("       " + removedTask.getTaskType() + "[" + removedTask.getStatusIcon() + "] " + removedTask);
            System.out.println("     Now you have " + taskList.size() + " tasks in the list.");
            System.out.println();

            LinkedHashMap<Integer, Task> updatedTaskList = new LinkedHashMap<>();
            int newId = 1;
            for (Task task : taskList.values()) {
                updatedTaskList.put(newId++, task);
            }
            taskList = updatedTaskList;
            idCounter = newId;
        } else {
            System.out.println("Error: No task found with ID " + id);
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
            if (input.isBlank()) {
                System.out.println("Error: Input cannot be empty.");
                continue;
            }

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
                try {
                    if (input.startsWith("mark")) {
                        taskId = Integer.parseInt(input.replace("mark ", "").trim());
                    } else if (input.startsWith("unmark")) {
                        taskId = Integer.parseInt(input.replace("unmark ", "").trim());
                    }

                    if (taskList.containsKey(taskId)) {
                        taskList.get(taskId).toggleIsDone();
                    } else {
                        System.out.println("Error: No task found with ID " + taskId);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Task ID must be a valid integer.");
                } catch (Exception e) {
                    System.out.println("An unexpected error occurred while marking/unmarking: " + e.getMessage());
                }

            // CASE OF DELETING A TASK
            } else if (input.startsWith("delete")) {
                try {
                    int taskId = Integer.parseInt(input.replace("delete ", "").trim());
                    deleteTask(taskId);
                } catch (NumberFormatException e) {
                    System.out.println("Error: Task ID must be a valid integer.");
                }

            //CASE OF ADDING INPUT
            } else {
                try {
                    addTask(input);
                } catch (IndexOutOfBoundsException e) {
                System.out.println("Error: Missing task details or invalid format.");
                }
            }
        }
    }
}
