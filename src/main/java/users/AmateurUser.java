package users;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class AmateurUser extends User{
    public AmateurUser(int id) {
        super(id);
    }

    public AmateurUser(
            int id,
            String name,
            String username,
            LocalDate birthdate,
            String address,
            String email,
            boolean sex,
            double height,
            double weight,
            int heartFreq
    ) {
        super(id, name, username, birthdate, address, email, sex, height, weight, heartFreq);
    }

    public AmateurUser(User u) {
        super(u);
    }

    @Override
    public double calculateCaloriesBurnMultiplier() {
        double multiplier = 0;
        double bmr = this.calculateBMR();
        multiplier = (bmr * this.getHeartFreq())/65; // Assuming heartFreq is in beats per minute

        return multiplier;
    }

    public AmateurUser clone(){
        return new AmateurUser(this);
    }
}
