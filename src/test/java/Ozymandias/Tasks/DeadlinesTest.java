package Ozymandias.Tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlinesTest {

    @Test
    public void testDeadlineCreation() {
        Deadlines deadlines = new Deadlines("Submit Assignment", "2025-10-15");
        assertEquals("[D]", deadlines.getTaskType());
    }

    @Test
    public void testDeadlineToString() {
        Deadlines deadlines = new Deadlines("Submit Assignment", "2025-10-15");
        assertEquals("Submit Assignment (by: Oct 15 2025)", deadlines.toString());
    }
}
