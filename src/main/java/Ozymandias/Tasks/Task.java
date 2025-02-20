package Ozymandias.Tasks;

import java.time.LocalDate;

public abstract class Task {
    private static int nextId = 1;          // Auto-increment ID if you want unique IDs
    private int id;
    private boolean isDone = false;
    private final String taskDetails;

    public Task(String taskDetails) {
        this.taskDetails = taskDetails;
        this.id = nextId++;
    }

    public void toggleIsDone() {
        isDone = !isDone;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public abstract String getTaskType();

    public LocalDate getEndDate() {
        return null;
    }
    
    public void setId(int newId) {
        this.id = newId;
    }

    @Override
    public String toString() {
        return taskDetails;
    }
}
