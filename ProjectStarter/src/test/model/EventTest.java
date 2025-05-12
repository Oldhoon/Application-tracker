package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;

    @BeforeEach
    public void setUp() {
        e = new Event("Test event");
    }

    @Test
    public void testConstructor() {
        assertEquals("Test event", e.getDescription());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Test event", e.getDescription());
    }

}
