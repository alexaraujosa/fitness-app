package users;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class AmateurUser extends User implements Serializable {
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
    public int calculateBurnedCalories(int activityID) {
        double userValue = 0;
        double bmr = this.calculateBMR();

        double typeBasedBmr = ((bmr * this.getHeartFreq())/65);

        double percentage = 1 - (1 * typeBasedBmr) / bmr;

        userValue = (double) (this.getActivityController().get(activityID).getBurnedCalories() * this.getActivityController().get(activityID).getHeartRate()) /65;

        return (int) (userValue + userValue * (percentage));
    }

    public AmateurUser clone(){
        return new AmateurUser(this);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
