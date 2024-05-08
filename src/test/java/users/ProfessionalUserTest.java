package users;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

class ProfessionalUserTest {
    String name = "John Doe";
    String username = "johndoe";
    LocalDate birthdate = LocalDate.now();
    String address = "123 Main St";
    String email = "johndoe@example.com";
    boolean sex = true;
    double height = 188.0;
    double weight = 97.0;
    int heartFreq = 70;

//    ProfessionalUser user = new ProfessionalUser(1, name, username, birthdate, address, email, sex, height, weight, heartFreq);

    @Test
    void CalculateCaloriesBurnMultiplier() {
    }

    @Test
    void testClone() {
    }
}