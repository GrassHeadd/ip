import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Events extends Task {
    private final LocalDate startDate;
    private final LocalDate endDate;

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");


    public Events(String taskDetails, String startDate, String endDate) {
        super(taskDetails);
        this.startDate = LocalDate.parse(startDate, INPUT_FORMAT);
        this.endDate = LocalDate.parse(endDate, INPUT_FORMAT);
    }


    @Override
    public String getTaskType() {
        return "[E]";
    }

    @Override
    public String toString() {
        return super.toString() + " (from: "+ startDate.format(OUTPUT_FORMAT) + " to: " + endDate + ")";
    }

}