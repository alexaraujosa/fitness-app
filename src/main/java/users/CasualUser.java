package users;

import activities.Activity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/**
 * Represents a casual user, extending the functionality of the User class.
 */
public class CasualUser extends User implements Serializable {

    //region Constructors
    /**
     * Constructs a casual user with the specified ID.
     *
     * @param id The ID of the casual user
     */
    public CasualUser(
            int id
    ) {
        super(id);
    }

    /**
     * Constructs a casual user with the specified details.
     *
     * @param id         The ID of the casual user
     * @param name       The name of the casual user
     * @param username   The username of the casual user
     * @param birthdate  The birthdate of the casual user
     * @param address    The address of the casual user
     * @param email      The email of the casual user
     * @param sex        The gender of the casual user (true for male, false for female)
     * @param height     The height of the casual user
     * @param weight     The weight of the casual user
     * @param heartFreq  The heart frequency of the casual user
     */
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

    /**
     * Constructs a casual user as a copy of another user.
     *
     * @param u The user object to copy
     */
    public CasualUser(
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

        double typeBasedBmr = ((bmr * this.getHeartFreq())/75);

        double percentage = 1 - (1 * typeBasedBmr) / bmr;

        userValue = (double) (this.getActivityController().get(activityID).getBurnedCalories() * this.getActivityController().get(activityID).getHeartRate()) /75;

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
     * Provides a string representation of the casual user.
     *
     * @return A string representation of the casual user
     */
    @Override
    public String toString() {
        return "{CASUAL USER}--{" +
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
     * Creates a clone of the casual user.
     *
     * @return A cloned casual user object
     */
    public CasualUser clone() {
        return new CasualUser(this);
    }

    /**
     * Overrides the equals method to compare casual user objects for equality.
     *
     * @param o The object to compare
     * @return true if the objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
