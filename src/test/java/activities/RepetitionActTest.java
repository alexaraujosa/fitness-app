package activities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import users.CasualUser;
import users.User;

import static org.junit.jupiter.api.Assertions.*;

class RepetitionActTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        User user = new CasualUser(1);
        RepetitionAct activity1 = new RepetitionAct(1, user, 0);
        assertNotNull(activity1);

        RepetitionAct activity2 = new RepetitionAct(activity1);
        assertNotNull(activity2);
    }

    @Test
    public void testGetNRepetitions() {
        User user = new CasualUser(1);
        RepetitionAct activity1 = new RepetitionAct(1, user, 0);
        assertEquals(0, activity1.getNRepetitions());

        RepetitionAct activity2 = new RepetitionAct(2, user, 581);
        assertEquals(581, activity2.getNRepetitions());

        RepetitionAct activity3 = new RepetitionAct(3, user, -335);
        assertEquals(0, activity3.getNRepetitions());
    }

    @Test
    public void testSetNRepetitions() {
        User user = new CasualUser(1);
        RepetitionAct activity = new RepetitionAct(1, user, 0);

        activity.setNRepetitions(500);
        assertEquals(500, activity.getNRepetitions());

        activity.setNRepetitions(0);
        assertEquals(0, activity.getNRepetitions());

        activity.setNRepetitions(-67);
        assertEquals(0, activity.getNRepetitions());
    }

    // TODO: testCalculateCalories
}