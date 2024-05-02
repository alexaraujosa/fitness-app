package activities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import users.CasualUser;
import users.User;

import static org.junit.jupiter.api.Assertions.*;

class RepetitionWithWeightsActTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        User user = new CasualUser(1);
        RepetitionWithWeightsAct activity1 = new RepetitionWithWeightsAct(1, user, 0, 0);
        assertNotNull(activity1);

        RepetitionWithWeightsAct activity2 = new RepetitionWithWeightsAct(activity1);
        assertNotNull(activity2);
    }

    @Test
    public void testGetNRepetitions() {
        User user = new CasualUser(1);
        RepetitionWithWeightsAct activity1 = new RepetitionWithWeightsAct(1, user, 0, 0);
        assertEquals(0, activity1.getNRepetitions());

        RepetitionWithWeightsAct activity2 = new RepetitionWithWeightsAct(2, user, 581, 0);
        assertEquals(581, activity2.getNRepetitions());

        RepetitionWithWeightsAct activity3 = new RepetitionWithWeightsAct(3, user, -335, 0);
        assertEquals(0, activity3.getNRepetitions());
    }

    @Test
    public void testGetWeight() {
        User user = new CasualUser(1);
        RepetitionWithWeightsAct activity1 = new RepetitionWithWeightsAct(1, user, 0, 0);
        assertEquals(0, activity1.getWeight());

        RepetitionWithWeightsAct activity2 = new RepetitionWithWeightsAct(2, user, 0, 123);
        assertEquals(123, activity2.getWeight());

        RepetitionWithWeightsAct activity3 = new RepetitionWithWeightsAct(3, user, 0, -67);
        assertEquals(0, activity3.getWeight());
    }

    @Test
    public void testSetNRepetitions() {
        User user = new CasualUser(1);
        RepetitionWithWeightsAct activity = new RepetitionWithWeightsAct(1, user, 0, 0);

        activity.setNRepetitions(500);
        assertEquals(500, activity.getNRepetitions());

        activity.setNRepetitions(0);
        assertEquals(0, activity.getNRepetitions());

        activity.setNRepetitions(-67);
        assertEquals(0, activity.getNRepetitions());
    }

    @Test
    public void testSetWeight() {
        User user = new CasualUser(1);
        RepetitionWithWeightsAct activity = new RepetitionWithWeightsAct(1, user, 0,0);

        activity.setWeight(120);
        assertEquals(120, activity.getWeight());

        activity.setWeight(0);
        assertEquals(0, activity.getWeight());

        activity.setWeight(-912);
        assertEquals(0, activity.getWeight());
    }

    // TODO: testCalculateCalories
}