public class Deadlines extends Task {
    private String dueDate;

    public Deadlines(String taskDetails, String dueDate) {
        super(taskDetails);
        this.dueDate = dueDate;
    }

    @Override
    public String getTaskType() {
        return "[D]";
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + dueDate + ")";
    }
}
