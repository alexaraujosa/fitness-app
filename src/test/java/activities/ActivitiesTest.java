package activities;

import activities.distance.Skating;
import activities.repetitions.AbdominalExercises;
import activities.repetitions.PushUps;
import activities.repetitions.Stretching;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Activities activities1 = new Activities();
        assertNotNull(activities1);
        Activities activities2 = new Activities(activities1);
        assertNotNull(activities2);
    }

    @Test
    public void testAddActivity() {
        Activities activities = new Activities();
        Skating activity1 = new Skating(1, 123456, 0, 3, false);
        PushUps activity2 = new PushUps(2, 123456, 3, true);

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
        Stretching activity = new Stretching(1, 123456, 1, true);
        activities.add(activity);
        assertEquals(activities.get(1), activity);
        assertEquals(activities.get(activity), activity);
    }

    @Test
    public void testRemoveActivity() {
        Activities activities = new Activities();
        PushUps activity1 = new PushUps(1, 123456, 0, true);
        AbdominalExercises activity2 = new AbdominalExercises(2, 123456, 0, true);

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
        PushUps activity1 = new PushUps(1, 123456, 0, false);
        PushUps activity2 = new PushUps(1, 123456, 5, true);
        activities.add(activity1);
        assertEquals(activities.get(1), activity1);
        assertNotEquals(activities.get(1), activity2);
        activities.update(activity2);
        assertNotEquals(activities.get(1), activity1);
        assertEquals(activities.get(1), activity2);
    }

}