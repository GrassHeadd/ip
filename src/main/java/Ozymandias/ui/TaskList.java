package Ozymandias.ui;

import java.util.LinkedHashMap;

public class TaskList {
    private LinkedHashMap<Integer, Task> tasks;
    private int currentId;

    public TaskList() {
        tasks = new LinkedHashMap<>();
        currentId = 1;
    }

    public void addTask(Task t) {
        tasks.put(currentId, t);
        t.setId(currentId);
        currentId++;
    }

    public Task removeTask(int id) {
        if (!tasks.containsKey(id)) {return null;}
        Task removedTask = tasks.remove(id);
        reassignTaskIds();
        return removedTask;
    }

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

    public boolean hasTask(int id) {return tasks.containsKey(id);}

    public Task getTask(int id) {return tasks.get(id);}

    public int size() {return tasks.size();}

    public LinkedHashMap<Integer, Task> getAllTasks() {return tasks;}
}
