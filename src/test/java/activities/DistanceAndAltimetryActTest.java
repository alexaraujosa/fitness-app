package activities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistanceAndAltimetryActTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        DistanceAndAltimetryAct activity1 = new DistanceAndAltimetryAct(1, 123456, 0, 0);
        assertNotNull(activity1);

        DistanceAndAltimetryAct activity2 = new DistanceAndAltimetryAct(activity1);
        assertNotNull(activity2);
    }

    @Test
    public void testGetDistance() {
        DistanceAndAltimetryAct activity1 = new DistanceAndAltimetryAct(1, 123456, 0, 0);
        assertEquals(0, activity1.getDistance());

        DistanceAndAltimetryAct activity2 = new DistanceAndAltimetryAct(2, 123456, 581, 0);
        assertEquals(581, activity2.getDistance());

        DistanceAndAltimetryAct activity3 = new DistanceAndAltimetryAct(3, 123456, -335, 0);
        assertEquals(0, activity3.getDistance());

        DistanceAndAltimetryAct activity4 = new DistanceAndAltimetryAct(4, 123456, 0, -123);
        assertEquals(0, activity4.getAltimetry());
    }

    @Test
    public void testGetAltimetry() {
        DistanceAndAltimetryAct activity1 = new DistanceAndAltimetryAct(1, 123456, 0, 0);
        assertEquals(0, activity1.getAltimetry());

        DistanceAndAltimetryAct activity2 = new DistanceAndAltimetryAct(2, 123456, 0, 123);
        assertEquals(123, activity2.getAltimetry());

        DistanceAndAltimetryAct activity3 = new DistanceAndAltimetryAct(3, 123456, 0, -67);
        assertEquals(0, activity3.getAltimetry());
    }

    @Test
    public void testSetDistance() {
        DistanceAndAltimetryAct activity = new DistanceAndAltimetryAct(1, 123456, 0, 0);

        activity.setDistance(500);
        assertEquals(500, activity.getDistance());

        activity.setDistance(0);
        assertEquals(0, activity.getDistance());

        activity.setDistance(-67);
        assertEquals(0, activity.getDistance());
    }

    @Test
    public void testSetAltimetry() {
        DistanceAndAltimetryAct activity = new DistanceAndAltimetryAct(1, 123456, 0,0);

        activity.setAltimetry(120);
        assertEquals(120, activity.getAltimetry());

        activity.setAltimetry(0);
        assertEquals(0, activity.getAltimetry());

        activity.setAltimetry(-912);
        assertEquals(0, activity.getAltimetry());
    }

    // TODO: testCalculateCalories
}