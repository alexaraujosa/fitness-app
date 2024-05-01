package users;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UsersTest {
    Users users = new Users();

    @Test
    void isUserNameAvailable() {
    }

    @Test
    void addUser() {
    }

    @Test
    void testEquals() {
        assertEquals(users, users.clone());
    }

    @Test
    void getUsernames() {
    }

    @Test
    void getUsersById() {
    }

    @Test
    void containsUser() {
    }

    @Test
    void testEquals1() {
    }

    @Test
    void testClone() {
    }

    @Test
    void removeUser() {
        String name = "John Doe";
        String username = "johndoe";
        Date birthdate = new Date();
        String address = "123 Main St";
        String email = "johndoe@example.com";
        boolean sex = true;
        double height = 180.0;
        double weight = 75.0;
        int heartFreq = 70;

        User u1 = new User(1,name, username, birthdate, address, email, sex, height, weight, heartFreq);
        User u2 = new User(2,"Paulo", "paulo123", birthdate, address, email, sex, height, weight, heartFreq);
        User u3 = new User(3,"jp", "jp123", birthdate, address, email, sex, height, weight, heartFreq);
        users.addUser(u1);
        users.addUser(u2);
        users.addUser(u3);

        users.removeUser(2);

        assertFalse(users.getUsernames().contains(u2.getUsername()));
        assertFalse(users.getUsersById().containsKey(2));
    }
}