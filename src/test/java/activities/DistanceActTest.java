package activities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import users.CasualUser;
import users.User;

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
        User user = new CasualUser(1);
        DistanceAct activity1 = new DistanceAct(1, user, 0);
        assertNotNull(activity1);

        DistanceAct activity2 = new DistanceAct(activity1);
        assertNotNull(activity2);
    }

    @Test
    public void testGetDistance() {
        User user = new CasualUser(1);
        DistanceAct activity1 = new DistanceAct(1, user, 0);
        assertEquals(0, activity1.getDistance());

        DistanceAct activity2 = new DistanceAct(2, user, 581);
        assertEquals(581, activity2.getDistance());

        DistanceAct activity3 = new DistanceAct(3, user, -335);
        assertEquals(0, activity3.getDistance());
    }

    @Test
    public void testSetDistance() {
        User user = new CasualUser(1);
        DistanceAct activity = new DistanceAct(1, user, 0);

        activity.setDistance(500);
        assertEquals(500, activity.getDistance());

        activity.setDistance(0);
        assertEquals(0, activity.getDistance());

        activity.setDistance(-67);
        assertEquals(0, activity.getDistance());
    }

    // TODO: testCalculateCalories
}