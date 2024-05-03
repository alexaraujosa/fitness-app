package activities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistanceActTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        DistanceAct activity1 = new DistanceAct(1, 123456, 0);
        assertNotNull(activity1);

        DistanceAct activity2 = new DistanceAct(activity1);
        assertNotNull(activity2);
    }

    @Test
    public void testGetDistance() {
        DistanceAct activity1 = new DistanceAct(1, 123456, 0);
        assertEquals(0, activity1.getDistance());

        DistanceAct activity2 = new DistanceAct(2, 123456, 581);
        assertEquals(581, activity2.getDistance());

        DistanceAct activity3 = new DistanceAct(3, 123456, -335);
        assertEquals(0, activity3.getDistance());
    }

    @Test
    public void testSetDistance() {
        DistanceAct activity = new DistanceAct(1, 123456, 0);

        activity.setDistance(500);
        assertEquals(500, activity.getDistance());

        activity.setDistance(0);
        assertEquals(0, activity.getDistance());

        activity.setDistance(-67);
        assertEquals(0, activity.getDistance());
    }

    // TODO: testCalculateCalories
}