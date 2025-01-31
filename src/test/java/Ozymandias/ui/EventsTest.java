package Ozymandias.ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventsTest {

    @Test
    public void testEventCreation() {
        Events event = new Events("Project Meeting", "2025-03-10", "2025-03-11");
        assertEquals("[E]", event.getTaskType());
    }

    @Test
    public void testEventToString() {
        Events event = new Events("Project Meeting", "2025-03-10", "2025-03-11");
        assertEquals("Project Meeting (from: Mar 10 2025 to: Mar 11 2025)", event.toString());
    }
}
