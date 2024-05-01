package users;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    CasualUser user = new CasualUser(1);
    CasualUser user2 = new CasualUser(2);

    @Test
    void testToString() {
        System.out.println(user.toString());
    }

    @Test
    void addActivity() {
    }

    @Test
    void removeActivity() {
    }

    @Test
    void addTrainingPlan() {
    }

    @Test
    void removeTrainingPlan() {
    }

    @Test
    void updateActivity() {
    }

    @Test
    void updateTrainingPlan() {
    }

    @Test
    void testEquals() {
        assertNotEquals(user, user2);
        assertEquals(user, user.clone());
    }
}