package users;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class CasualUser extends User{
    public CasualUser(int id) {
        super(id);
    }

    public CasualUser(
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

    public CasualUser(User u) {
        super(u);
    }

    @Override
    public double calculateCaloriesBurnMultiplier() {
        double multiplier = 0;
        double bmr = this.calculateBMR();
        multiplier = (bmr * this.getHeartFreq())/75; // Assuming heartFreq is in beats per minute

        return multiplier;
    }

    public CasualUser clone() {
        return new CasualUser(this);
    }
}
