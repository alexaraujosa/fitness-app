package activities;

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
        Activity activity = new DistanceAct(1, 1, 0);
        activityController.add(activity);
        assertEquals(activity, activityController.get(1));
        assertEquals(activity, activityController.get(activity));
    }

    @Test
    public void testAdd() {
        ActivityController activityController = new ActivityController();
        Activity activity1 = new DistanceAct(1, 1, 0);
        Activity activity2 = new RepetitionWithWeightsAct(2, 1, 0, 1);
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
        Activity activity1 = new DistanceAct(1, 1, 0);
        Activity activity2 = new RepetitionWithWeightsAct(2, 1, 0, 1);
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
        Activity activity1 = new DistanceAct(1, 1, 0);
        Activity activity2 = new RepetitionWithWeightsAct(1, 1, 0, 1);
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
        Activity activity1 = new DistanceAct(1, 1, 0);
        Activity activity2 = new RepetitionWithWeightsAct(2, 1, 0, 1);
        activityController.add(activity1);
        activityController.add(activity2);
        Activities activities = activityController.generateLink();
        assertEquals(activities, activityController.getActivities());
    }


}