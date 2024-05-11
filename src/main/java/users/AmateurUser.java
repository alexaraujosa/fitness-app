package users;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class AmateurUser extends User implements Serializable {

    //region Constructors
    public AmateurUser(
            int id
    ) {
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

    public AmateurUser(
            User u
    ) {
        super(u);
    }
    //endregion

    @Override
    public int calculateBurnedCalories(int activityID) {
        double userValue = 0;
        double bmr = this.calculateBMR();

        double typeBasedBmr = ((bmr * this.getHeartFreq())/65);

        double percentage = 1 - (1 * typeBasedBmr) / bmr;

        userValue = (double) (this.getActivityController().get(activityID).getBurnedCalories() * this.getActivityController().get(activityID).getHeartRate()) /65;

        return (int) (userValue + userValue * (percentage));
    }

    @Override
    public String toString() {
        return "{AMATEUR USER}--{" +
                " | ID: " + super.getId() +
                " | NAME: " + super.getName() +
                " | USERNAME: " + super.getUsername() +
                " | BIRTHDATE: " + super.getBirthdate() +
                " | ADDRESS: " + super.getAddress() +
                " | EMAIL: " + super.getEmail() +
                " | SEX: " + (super.getSex() ? "Male" : "Female") +
                " | HEIGHT: " + String.format("%.2f", super.getHeight()) +
                " | WEIGHT: " + String.format("%.2f", super.getWeight()) +
                " | HEART FREQ: " + super.getHeartFreq() +
                "}\n";
    }

    public AmateurUser clone(){
        return new AmateurUser(this);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
