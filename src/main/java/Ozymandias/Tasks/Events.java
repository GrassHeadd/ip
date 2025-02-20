package Ozymandias.Tasks;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Events extends Task {
    private final LocalDate startDate;
    private final LocalDate endDate;

    private static final DateTimeFormatter INPUT_FORMAT  = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Events(String taskDetails, String startDateString, String endDateString) {
        super(taskDetails);
        this.startDate = LocalDate.parse(startDateString, INPUT_FORMAT);
        this.endDate   = LocalDate.parse(endDateString, INPUT_FORMAT);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String getTaskType() {return "[E]";}

    @Override
    public String toString() {
        // Example: "project presentation (from: Mar 10 2025 to: Mar 11 2025)"
        return super.toString() + " (from: " + startDate.format(OUTPUT_FORMAT)  + " to: "   + endDate.format(OUTPUT_FORMAT)  + ")";
    }
}