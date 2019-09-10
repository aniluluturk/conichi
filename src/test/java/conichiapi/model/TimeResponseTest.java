package conichiapi.model;

import conichiapi.model.TimeResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeResponseTest {

    @Test
    void defaultConstructorShouldSetNull() {
        TimeResponse t = new TimeResponse();

        assertNull(t.getDefaultStr());
        assertNull(t.getHumanStr());
    }

    @Test
    void parametrizedConstructorShouldSetFields() {
        TimeResponse t = new TimeResponse("10/10/10 at 12:12:12", "10/10/10T12:12:12Z");

        assertEquals(t.getHumanStr(), "10/10/10 at 12:12:12");
        assertEquals(t.getDefaultStr(), "10/10/10T12:12:12Z");
    }

}