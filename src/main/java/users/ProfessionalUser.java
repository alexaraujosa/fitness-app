package users;

import java.io.Serializable;
import java.time.LocalDate;

public class ProfessionalUser extends User implements Serializable {

    //region Constructors
    public ProfessionalUser(
            int id
    ) {
        super(id);
    }

    public ProfessionalUser(
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

    public ProfessionalUser(
            User u
    ) {
        super(u);
    }
    //endregion

    @Override
    public int calculateBurnedCalories(int activityID) {
        double userValue = 0;
        double bmr = this.calculateBMR();

        double typeBasedBmr = ((bmr * this.getHeartFreq())/50);

        double percentage = 1 - (1 * typeBasedBmr) / bmr;

        userValue = (double) (this.getActivityController().get(activityID).getBurnedCalories() * this.getActivityController().get(activityID).getHeartRate()) /50;

        return (int) (userValue + userValue * (percentage));
    }

    @Override
    public String toString() {
        return "{PROFESSIONAL USER}--{" +
                " | ID: " + super.getId() +
                " | NAME: " + super.getName() +
                " | USERNAME: " + super.getUsername() +
                " | BIRTHDATE: " + super.getBirthdate() +
                " | ADDRESS: " + super.getAddress() +
                " | EMAIL: " + super.getEmail() +
                " | SEX: " + (super.getSex() ? "Male" : "Female") +
                " | HEIGHT: " + super.getHeight() +
                " | WEIGHT: " + super.getWeight() +
                " | HEART FREQ: " + super.getHeartFreq() +
                "}";
    }

    public ProfessionalUser clone() {
        return new ProfessionalUser(this);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
