package activities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import users.CasualUser;
import users.User;

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
        User user = new CasualUser(1);
        DistanceAndAltimetryAct activity1 = new DistanceAndAltimetryAct(1, user, 0, 0);
        assertNotNull(activity1);

        DistanceAndAltimetryAct activity2 = new DistanceAndAltimetryAct(activity1);
        assertNotNull(activity2);
    }

    @Test
    public void testGetDistance() {
        User user = new CasualUser(1);
        DistanceAndAltimetryAct activity1 = new DistanceAndAltimetryAct(1, user, 0, 0);
        assertEquals(0, activity1.getDistance());

        DistanceAndAltimetryAct activity2 = new DistanceAndAltimetryAct(2, user, 581, 0);
        assertEquals(581, activity2.getDistance());

        DistanceAndAltimetryAct activity3 = new DistanceAndAltimetryAct(3, user, -335, 0);
        assertEquals(0, activity3.getDistance());
    }

    @Test
    public void testGetAltimetry() {
        User user = new CasualUser(1);
        DistanceAndAltimetryAct activity1 = new DistanceAndAltimetryAct(1, user, 0, 0);
        assertEquals(0, activity1.getAltimetry());

        DistanceAndAltimetryAct activity2 = new DistanceAndAltimetryAct(2, user, 0, 123);
        assertEquals(123, activity2.getAltimetry());

        DistanceAndAltimetryAct activity3 = new DistanceAndAltimetryAct(3, user, 0, -67);
        assertEquals(0, activity3.getAltimetry());
    }

    @Test
    public void testSetDistance() {
        User user = new CasualUser(1);
        DistanceAndAltimetryAct activity = new DistanceAndAltimetryAct(1, user, 0, 0);

        activity.setDistance(500);
        assertEquals(500, activity.getDistance());

        activity.setDistance(0);
        assertEquals(0, activity.getDistance());

        activity.setDistance(-67);
        assertEquals(0, activity.getDistance());
    }

    @Test
    public void testSetAltimetry() {
        User user = new CasualUser(1);
        DistanceAndAltimetryAct activity = new DistanceAndAltimetryAct(1, user, 0,0);

        activity.setAltimetry(120);
        assertEquals(120, activity.getAltimetry());

        activity.setAltimetry(0);
        assertEquals(0, activity.getAltimetry());

        activity.setAltimetry(-912);
        assertEquals(0, activity.getAltimetry());
    }

    // TODO: testCalculateCalories
}