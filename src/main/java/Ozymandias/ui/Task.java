package Ozymandias.ui;

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
        if (isDone) {
            System.out.println("    Nice! I've marked this task as done:");
            System.out.println("      [X] " + taskDetails);
            System.out.println();
        } else {
            System.out.println("    OK, I've marked this task as not done yet:");
            System.out.println("      [ ] " + taskDetails);
            System.out.println();
        }
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public abstract String getTaskType();

    public int getId() {
        return id;
    }

    public void setId(int newId) {
        this.id = newId;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void reduceNextId() {
        nextId--;
    }

    @Override
    public String toString() {
        return taskDetails;
    }
}
