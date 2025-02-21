package Ozymandias.Tasks;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Events extends Task {
    private final LocalDate startDate;
    private final LocalDate endDate;

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Events(String taskDetails, String startDateString, String endDateString) {
        super(taskDetails);
        this.startDate = LocalDate.parse(startDateString, FORMAT);
        this.endDate   = LocalDate.parse(endDateString, FORMAT);
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String getTaskType() {
        return "[E]";
    }

    @Override
    public String toString() {
        // Example: "project presentation (from: Mar 10 2025 to: Mar 11 2025)"
        return super.toString() + " (from: " + startDate.format(FORMAT)  + " to: "   + endDate.format(FORMAT)  + ")";
    }
}