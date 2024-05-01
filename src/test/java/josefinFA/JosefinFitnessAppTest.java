package josefinFA;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JosefinFitnessAppTest {
    JosefinFitnessApp app = new JosefinFitnessApp();

    @Test
    void addUser() {
        String name = "John Doe";
        String username = "johndoe";
        Date birthdate = new Date();
        String address = "123 Main St";
        String email = "johndoe@example.com";
        boolean sex = true;
        double height = 180.0;
        double weight = 75.0;
        int heartFreq = 70;

        // Test when username is available
        String result1 = app.addUser(name, username, birthdate, address, email, sex, height, weight, heartFreq,1);
        assertEquals("User added successfully!", result1);

        // Test when username is not available
        String result2 = app.addUser("Joaquim", username, birthdate, address, email, sex, height, weight, heartFreq,2);
        assertEquals("That username is not available!", result2);

        // Test when username is not available
        String result3 = app.addUser("Joaquim", "joca", birthdate, address, email, sex, height, weight, heartFreq,2);
        assertEquals("User added successfully!", result3);
    }

    //BUG: Algo de errado não está certo
    @Test
    void testEquals() {
        assertEquals(app, app.clone());
    }

    @Test
    void testClone() {
    }
}