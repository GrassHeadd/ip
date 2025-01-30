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
        tasks.put(currentId++, t);
    }

    public Task removeTask(int id) {
        Task removedTask = tasks.remove(id);
        if (removedTask == null) {return null;}
        for (int i = id + 1; i < Task.getNextId(); i++) {
            Task t = tasks.remove(i);
            if (t != null) {
                tasks.put(i - 1, t);
            }
        }
        Task.reduceNextId();
        return removedTask;
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public boolean hasTask(int id) {
        return tasks.containsKey(id);
    }

    public LinkedHashMap<Integer, Task> getAllTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }
}
