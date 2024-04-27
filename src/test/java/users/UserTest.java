package users;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testToString() {
        User user = new User(1);
        System.out.println(user.toString());
    }
}