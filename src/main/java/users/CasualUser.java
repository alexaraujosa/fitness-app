package users;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class CasualUser extends User implements Serializable {

    //region Constructors
    public CasualUser(
            int id
    ) {
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

    public CasualUser(
            User u
    ) {
        super(u);
    }
    //endregion

    @Override
    public int calculateBurnedCalories(int activityID) {
        double userValue = 0;
        double bmr = this.calculateBMR();

        double typeBasedBmr = ((bmr * this.getHeartFreq())/75);

        double percentage = 1 - (1 * typeBasedBmr) / bmr;

        userValue = (double) (this.getActivityController().get(activityID).getBurnedCalories() * this.getActivityController().get(activityID).getHeartRate()) /75;

        return (int) (userValue + userValue * (percentage));
    }

    public CasualUser clone() {
        return new CasualUser(this);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
