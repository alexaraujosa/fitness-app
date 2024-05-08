package activities;

import activities.distance.Rowing;
import activities.distanceAltimetry.RoadCycling;
import activities.distanceAltimetry.RoadRunning;
import activities.distanceAltimetry.TrailRunning;
import activities.repetitions.PushUps;
import activities.repetitions.Stretching;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityControllerTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        ActivityController activityController1 = new ActivityController();
        assertNotNull(activityController1);
        ActivityController activityController2 = new ActivityController(activityController1);
        assertNotNull(activityController2);
    }

    @Test
    public void testGet() {
        ActivityController activityController = new ActivityController();
        PushUps activity = new PushUps(1, 1, 3, false);
        activityController.add(activity);
        assertEquals(activity, activityController.get(1));
        assertEquals(activity, activityController.get(activity));
    }

    @Test
    public void testAdd() {
        ActivityController activityController = new ActivityController();
        Stretching activity1 = new Stretching(1, 1, 0, false);
        TrailRunning activity2 = new TrailRunning(2, 1, 0, 1, true);
        assertFalse(activityController.exists(activity1));
        assertFalse(activityController.exists(activity2));
        activityController.add(activity1);
        assertTrue(activityController.exists(activity1));
        activityController.add(activity2);
        assertTrue(activityController.exists(activity1));
        assertTrue(activityController.exists(activity2));
    }

    @Test
    public void testRemove() {
        ActivityController activityController = new ActivityController();
        PushUps activity1 = new PushUps(1, 1, 0, true);
        Rowing activity2 = new Rowing(2, 1, 0, 1, true);
        activityController.add(activity1);
        activityController.add(activity2);
        assertTrue(activityController.exists(activity1));
        assertTrue(activityController.exists(activity2));
        activityController.remove(activity1);
        assertFalse(activityController.exists(activity1));
        assertTrue(activityController.exists(activity2));
        activityController.remove(2);
        assertFalse(activityController.exists(activity1));
        assertFalse(activityController.exists(activity2));
    }

    @Test
    public void testUpdate() {
        ActivityController activityController = new ActivityController();
        RoadCycling activity1 = new RoadCycling(1, 1, 3, 5, false);
        RoadCycling activity2 = new RoadCycling(1, 1, 500, 120, true);
        activityController.add(activity1);
        assertEquals(activity1, activityController.get(1));
        assertNotEquals(activity2, activityController.get(1));
        activityController.update(activity2);
        assertNotEquals(activity1, activityController.get(1));
        assertEquals(activity2, activityController.get(1));
    }

    @Test
    public void testGenerateLink() {
        ActivityController activityController = new ActivityController();
        RoadRunning activity1 = new RoadRunning(1, 1, 0, 0, false);
        PushUps activity2 = new PushUps(2, 1, 0, false);
        activityController.add(activity1);
        activityController.add(activity2);
        Activities activities = activityController.generateLink();
        assertEquals(activities, activityController.getActivities());
    }


}