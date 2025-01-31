package Ozymandias.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadlines extends Task {
    private final LocalDate dueDate;

    private static final DateTimeFormatter INPUT_FORMAT  = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Deadlines(String taskDetails, String dueDateString) {
        super(taskDetails);
        this.dueDate = LocalDate.parse(dueDateString, INPUT_FORMAT);
    }

    @Override
    public String getTaskType() {return "[D]";}

    @Override
    public String toString() {
        // Example: "do homework (by: Oct 15 2025)"
        return super.toString() + " (by: " + dueDate.format(OUTPUT_FORMAT) + ")";
    }
}

