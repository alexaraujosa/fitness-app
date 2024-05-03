package activities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ActivitiesTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        Activities activities = new Activities();
        assertNotNull(activities);
    }

    @Test
    public void testAddActivity() {
        Activities activities = new Activities();
        Activity activity1 = new DistanceAndAltimetryAct(1, 123456, 0, 0);
        Activity activity2 = new RepetitionAct(2, 123456, 0);

        assertFalse(activities.exists(activity1.getId()));
        assertFalse(activities.exists(activity2.getId()));

        activities.add(activity1);
        assertTrue(activities.exists(activity1.getId()));

        activities.add(activity2);
        assertTrue(activities.exists(activity1.getId()));
        assertTrue(activities.exists(activity2.getId()));
    }

    @Test
    public void testGetActivity() {
        Activities activities = new Activities();
        RepetitionAct activity = new RepetitionAct(1, 123456, 0);
        activities.add(activity);
        assertTrue(activities.get(1).equals(activity));
        assertTrue(activities.get(activity).equals(activity));
    }

    @Test
    public void testRemoveActivity() {
        Activities activities = new Activities();
        Activity activity1 = new RepetitionWithWeightsAct(1, 123456, 0, 0);
        Activity activity2 = new DistanceAct(2, 123456, 0);

        activities.add(activity1);
        activities.add(activity2);
        activities.remove(activity1);
        assertFalse(activities.exists(activity1.getId()));
        assertTrue(activities.exists(activity2.getId()));
        activities.remove(activity2);
        assertFalse(activities.exists(activity1.getId()));
        assertFalse(activities.exists(activity2.getId()));
    }

    @Test
    public void testUpdateActivity() {
        Activities activities = new Activities();
        RepetitionAct activity1 = new RepetitionAct(1, 123456, 0);
        RepetitionAct activity2 = new RepetitionAct(1, 123456, 5);
        activities.add(activity1);
        assertTrue(activities.get(1).equals(activity1));
        assertFalse(activities.get(1).equals(activity2));
        activities.update(activity2);
        assertFalse(activities.get(1).equals(activity1));
        assertTrue(activities.get(1).equals(activity2));
    }

}