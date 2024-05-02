package josefinFA;

import exceptions.UsernameAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JosefinFitnessAppTest {
    JosefinFitnessApp app = new JosefinFitnessApp();

    @Test
    void addUser() throws UsernameAlreadyExistsException {
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
        app.addCasualUser(name, username, birthdate, address, email, sex, height, weight, heartFreq);

        // Test when username is not available
        app.addCasualUser("Joaquim", username, birthdate, address, email, sex, height, weight, heartFreq);

        // Test when username is not available
        app.addCasualUser("Joaquim", "joca", birthdate, address, email, sex, height, weight, heartFreq);
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