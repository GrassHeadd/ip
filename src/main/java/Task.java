public abstract class Task {
    private boolean isDone = false;
    private final String taskDetails;

    public Task(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public void toggleIsDone() {
        isDone = !isDone;
        if (isDone) {
            System.out.println("Nice! I've marked this task as done: ");
            System.out.println("  [X] " + taskDetails);
        } else {
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  [ ] " + taskDetails);
        }
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public abstract String getTaskType();

    @Override
    public String toString() {
        return taskDetails;
    }
}
