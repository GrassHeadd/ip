public class ToDos extends Task {
    public ToDos(String taskDetails) {
        super(taskDetails);
    }

    @Override
    public String getTaskType() {
        return "[T]";
    }
}