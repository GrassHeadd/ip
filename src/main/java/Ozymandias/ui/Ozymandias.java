package Ozymandias.ui;

import java.util.Map;

import Ozymandias.Storage.Storage;
import Ozymandias.Tasks.Task;

/**
 * The main class for the Ozymandias task manager.
 * It handles user interactions, command processing, and task storage.
 */
public class Ozymandias {
    private final TaskList tasks;

    public Ozymandias(String filePath) {
        this.tasks = Storage.load();
    }

    public TaskList getTasks() {
        return tasks;
    }

    /**
     * Adds a task to the task list
     *
     * @param t Task to be added
     */
    public String addTask(Task t) {
        tasks.addTask(t);
        return ("Fine, I'll add this task:\n"
                + "   " + t.getTaskType() + "[" +
                t.getStatusIcon() + "] " + t + "\n")
                + ("Now you have " + tasks.size() + " tasks in the list.\n");
    }

    /**
     * Remove a task to the task list
     *
     * @param id task id to be removed
     */
    public String deleteTask(int id) {
        if (tasks.hasTask(id)) {
            Task removedTask = tasks.removeTask(id);
            assert tasks.getTask(id) == null : tasks.getTask(id) + " was not deleted";
            return "Fine, I've removed this task:\n"
                    + removedTask.getTaskType()
                    + "[" + removedTask.getStatusIcon() + "] " + removedTask + "\n"
                    + "Now you have " + tasks.size() + " tasks in the list.\n";
        } else {
            return ("Error: No task found with ID " + id);
        }
    }

    /**
     * Marks or unmark a task in the tasklist
     *
     * @param id id of task to be marked
     * @param isMark Whether task is marked currently
     */
    public String markTask(int id, boolean isMark) {
        Task t = tasks.getTask(id);
        if (t == null) {
            return ("No task with ID " + id + " found!");
        }

        if (t.getStatusIcon().equals("X") && isMark) {
            return ("Task is already marked done!\n");
        } else if (t.getStatusIcon().equals(" ") && !isMark) {
            return ("Task is already not done!\n");
        } else {
            t.toggleIsDone();
            return ("Task toggled!\n");
        }
    }

    /**
     * Prints out the tasks in the tasklist
     */
    public String printTasks() {
        if (tasks.size() == 0) {
            return ("Your task list is empty.");
        } else {
            StringBuilder output = new StringBuilder(("Here are the tasks in your list: \n"));
            for (Map.Entry<Integer, Task> entry : tasks.getAllTasks().entrySet()) {
                int id = entry.getKey();
                Task tk = entry.getValue();
                assert tk != null : "Retrieved task should not be empty";
                output.append("     ").append(id)
                        .append(".").append(tk.getTaskType())
                        .append("[").append(tk.getStatusIcon())
                        .append("] ").append(tk).append("\n");
            }
            return output.toString();
        }

    }

    /**
     * Processes the user's input and
     * get all the tasks in the taskList
     * containing the input
     *
     * @param input The command in String to find tasks containing the input
     * @return a String of all the tasks containing the input
     */

    public String findTask(String input) {
        input = input.toLowerCase().trim();
        int count = 0;
        StringBuilder output = new StringBuilder(
                "Here are the matching tasks in your list:\n");

        for (Map.Entry<Integer, Task> entry : tasks.getAllTasks().entrySet()) {
            Task tk = entry.getValue();
            String tkString = tk.toString().toLowerCase().trim();

            if (tkString.contains(input)) {
                count++;
                output.append("     ").append(count)
                        .append(".").append(tk.getTaskType())
                        .append("[").append(tk.getStatusIcon())
                        .append("] ").append(tk).append("\n");
            }
        }

        if (count == 0) {
            return "There is no matching task!\n";
        }

        assert count > 0 : "there should be tasks in your list";

        return output.toString();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return Parser.handleCommand(input, this);
    }

}
