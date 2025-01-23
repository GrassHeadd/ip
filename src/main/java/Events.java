public class Events extends Task {
    private String startDate;
    private String endDate;
    public Events(String taskDetails, String startDate, String endDate) {
        super(taskDetails);
        this.startDate = startDate;
        this.endDate = endDate;
    }


    @Override
    public String getTaskType() {
        return "[E]";
    }

    @Override
    public String toString() {
        return super.toString() + " (from: "+ startDate + " to: " + endDate + ")";
    }

}
