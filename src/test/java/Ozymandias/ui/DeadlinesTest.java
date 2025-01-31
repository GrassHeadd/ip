package Ozymandias.ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeadlinesTest {

    @Test
    public void testDeadlineCreation() {
        Deadlines deadline = new Deadlines("Submit Assignment", "2025-10-15");
        assertEquals("[D]", deadline.getTaskType());
    }

    @Test
    public void testDeadlineToString() {
        Deadlines deadline = new Deadlines("Submit Assignment", "2025-10-15");
        assertEquals("Submit Assignment (by: Oct 15 2025)", deadline.toString());
    }
}
