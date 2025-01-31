package Ozymandias.ui;

import java.util.Scanner;
import java.util.Map;

/**
 * The main class for the Ozymandias task manager.
 * It handles user interactions, command processing, and task storage.
 */
public class Ozymandias {
    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;
    private boolean isExit = false;

    public Ozymandias(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = storage.load();  // load any existing tasks from file
    }

    /**
     * Runs the Ozymandias application, continuously accept user input
     * til user exits the program.
     */
    public void run() {
        ui.greetHello();
        Scanner scanner = new Scanner(System.in);

        while (!isExit) {
            String input = scanner.nextLine();
            Parser.handleCommand(input, this);
            storage.save(tasks);
        }
    }

    public void setExit(boolean exit) {this.isExit = exit;}

    public Ui getUi() {return ui;}

    public TaskList getTasks() {return tasks;}

    /**
     * Adds a task to the task list
     *
     * @param t Task to be added
     */
    public void addTask(Task t) {
        tasks.addTask(t);
        System.out.println("     Got it. I've added this task:\n"
                + "       " + t.getTaskType() + "[" + t.getStatusIcon() + "] " + t);
        System.out.println("     Now you have " + tasks.size() + " tasks in the list.\n");
    }

    /**
     * Remove a task to the task list
     *
     * @param id task id to be removed
     */
    public void deleteTask(int id) {
        if (tasks.hasTask(id)) {
            Task removedTask = tasks.removeTask(id);
            System.out.println("     Noted. I've removed this task:");
            System.out.println("       " + removedTask.getTaskType()
                    + "[" + removedTask.getStatusIcon() + "] " + removedTask);
            System.out.println("     Now you have " + tasks.size() + " tasks in the list.\n");
        } else {
            System.out.println("     Error: No task found with ID " + id);
        }
    }

    /**
     * Marks or unmark a task in the tasklist
     *
     * @param id Id of task to be marked
     * @param isMark Whether task is marked currently
     */
    public void markTask(int id, boolean isMark) {
        Task t = tasks.getTask(id);
        if (t == null) {
            System.out.println("    No task with ID " + id + " found!");
            return;
        }
        if (t.getStatusIcon().equals("X") && isMark) {
            System.out.println("    Ozymandias.ui.Task is already marked done!\n");
        } else if (t.getStatusIcon().equals(" ") && !isMark) {
            System.out.println("    Ozymandias.ui.Task is already not done!\n");
        } else {
            t.toggleIsDone();
        }
    }

    /**
     * Prints out the tasks in the tasklist
     */
    public void printTasks() {
        if (tasks.size() == 0) {
            System.out.println("    Your task list is empty.");
        } else {
            System.out.println("     Here are the tasks in your list:");
            for (Map.Entry<Integer, Task> entry : tasks.getAllTasks().entrySet()) {
                int id = entry.getKey();
                Task tk = entry.getValue();
                System.out.println("     " + id + "." + tk.getTaskType()
                                    + "[" + tk.getStatusIcon() + "] " + tk);
            }
        }
        System.out.println();
    }

    public String findTask(String input) {
        input = input.toLowerCase().trim();
        int count = 0;
        String output = "    Here are the matching tasks in your list:\n";

        for (Map.Entry<Integer, Task> entry : tasks.getAllTasks().entrySet()) {
            Task tk = entry.getValue();
            String tkString = tk.toString().toLowerCase().trim();

            if (tkString.contains(input)) {
                count++;
                output += "     " + count + "." + tk.getTaskType()
                        + "[" + tk.getStatusIcon() + "] " + tk + "\n";
            }
        }

        if (count == 0) {
            return "       There is no matching task!\n";
        }
        return output;
    }

    public static void main(String[] args) {
        Ozymandias oz = new Ozymandias("./data/Ozymandias.txt");
        oz.run();
    }
}
