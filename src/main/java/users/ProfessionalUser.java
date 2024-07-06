package users;

import activities.Activity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a professional user, extending the functionality of the User class.
 */
public class ProfessionalUser extends User implements Serializable {

    //region Constructors
    /**
     * Constructs a professional user with the specified ID.
     *
     * @param id The ID of the professional user
     */
    public ProfessionalUser(
            int id
    ) {
        super(id);
    }

    /**
     * Constructs a professional user with the specified details.
     *
     * @param id         The ID of the professional user
     * @param name       The name of the professional user
     * @param username   The username of the professional user
     * @param birthdate  The birthdate of the professional user
     * @param address    The address of the professional user
     * @param email      The email of the professional user
     * @param sex        The gender of the professional user (true for male, false for female)
     * @param height     The height of the professional user
     * @param weight     The weight of the professional user
     * @param heartFreq  The heart frequency of the professional user
     */
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

    /**
     * Constructs a professional user as a copy of another user.
     *
     * @param u The user object to copy
     */
    public ProfessionalUser(
            User u
    ) {
        super(u);
    }
    //endregion

    /**
     * Calculates the burned calories for a given activity based on the user's heart rate and other factors.
     *
     * @param activityID The ID of the activity
     * @return The calculated burned calories
     */
    @Override
    public int calculateBurnedCalories(int activityID) {
        double userValue = 0;
        double bmr = this.calculateBMR();

        double typeBasedBmr = ((bmr * this.getHeartFreq())/50);

        double percentage = 1 - (1 * typeBasedBmr) / bmr;

        userValue = (double) (this.getActivityController().get(activityID).getBurnedCalories() * this.getActivityController().get(activityID).getHeartRate()) /50;

        return (int) (userValue + userValue * (percentage));
    }

    /**
     * Estimates the burned calories for a given activity based on the user's heart rate and other factors.
     *
     * @param a The activity for which to estimate burned calories
     * @return The estimated burned calories
     */
    @Override
    public int speculateBurnedCalories(Activity a) {
        double userValue = 0;
        double bmr = this.calculateBMR();
        a.calculateCalories();

        double typeBasedBmr = ((bmr * this.getHeartFreq())/65);

        double percentage = 1 - (1 * typeBasedBmr) / bmr;

        userValue = (double) (a.getBurnedCalories() * a.getHeartRate()) /65;

        return (int) (userValue + userValue * (percentage));
    }

    /**
     * Provides a string representation of the professional user.
     *
     * @return A string representation of the professional user
     */
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
                " | HEIGHT: " + String.format("%.2f", super.getHeight()) +
                " | WEIGHT: " + String.format("%.2f", super.getWeight()) +
                " | HEART FREQ: " + super.getHeartFreq() +
                "}\n";
    }

    /**
     * Creates a clone of the professional user.
     *
     * @return A cloned professional user object
     */
    public ProfessionalUser clone() {
        return new ProfessionalUser(this);
    }

    /**
     * Overrides the equals method to compare professional user objects for equality.
     *
     * @param o The object to compare
     * @return true if the objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
