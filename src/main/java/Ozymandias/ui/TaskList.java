package Ozymandias.ui;

import java.util.LinkedHashMap;

import Ozymandias.Tasks.Task;

/**
 * Represents a list (or collection) of {@code Task} objects managed by an ID system.
 * Provides methods to add, remove, and retrieve tasks while keeping IDs consistent.
 */
public class TaskList {
    private LinkedHashMap<Integer, Task> tasks;

    private int currentId;

    public TaskList() {
        tasks = new LinkedHashMap<>();
        currentId = 1;
    }

    /**
     * Add a new task to the list and assign it an id.
     *
     * @param t The task to be added.
     */
    public void addTask(Task t) {
        tasks.put(currentId, t);
        t.setId(currentId);
        currentId++;
    }

    /**
     * Remove the task with the id from the list,
     * after removal, all remaining tasks reindexed.
     *
     * @param id id of the task to remove.
     * @return removed {@code Task} if found, otherwise {@code null}.
     */
    public Task removeTask(int id) {
        if (!tasks.containsKey(id)) {
            return null;
        }
        Task removedTask = tasks.remove(id);
        reassignTaskIds();
        return removedTask;
    }

    /**
     * Check if a task with the specified id exists in the list
     *
     * @param id The id to check.
     * @return {@code true} if a task with that ID exists, otherwise {@code false}.
     */
    public boolean hasTask(int id) {
        return tasks.containsKey(id);
    }

    /**
     * Retrieves the task with the given ID.
     *
     * @param id The ID of the task to retrieve.
     * @return The corresponding {@code Task} object if found, otherwise {@code null}.
     */
    public Task getTask(int id) {
        return tasks.get(id);
    }

    /**
     * Returns the total number of tasks in the list.
     *
     * @return The size of the list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying map of all tasks.
     * The map is keyed by ID and maintains insertion order.
     *
     * @return A {@code LinkedHashMap} of IDs to {@code Task} objects.
     */
    public LinkedHashMap<Integer, Task> getAllTasks() {
        return tasks;
    }

    /**
     * Reassigns IDs to all tasks in ascending order, starting from 1.
     * This is typically called after removing a task to keep IDs contiguous.
     */
    private void reassignTaskIds() {
        LinkedHashMap<Integer, Task> newTasks = new LinkedHashMap<>();
        int newId = 1;

        for (Task task : tasks.values()) {
            task.setId(newId);
            newTasks.put(newId, task);
            newId++;
        }
        tasks = newTasks;
        currentId = newId;
    }
}
