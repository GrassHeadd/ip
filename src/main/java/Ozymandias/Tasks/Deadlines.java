package Ozymandias.Tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadlines extends Task {
    private final LocalDate endDate;

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Deadlines(String taskDetails, String dueDateString) {
        super(taskDetails);
        this.endDate = LocalDate.parse(dueDateString, FORMAT);
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public String getTaskType() {return "[D]";}

    @Override
    public String toString() {
        // Example: "do homework (by: Oct 15 2025)"
        return super.toString() + " (by: " + endDate.format(FORMAT) + ")";
    }
}

