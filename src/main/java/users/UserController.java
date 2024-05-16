package users;

import TrainingPlan.TrainingPlan;
import activities.*;
import activities.distance.Rowing;
import activities.distance.Skating;
import activities.distance.TrackRunning;
import activities.distanceAltimetry.MountainBiking;
import activities.distanceAltimetry.RoadCycling;
import activities.distanceAltimetry.RoadRunning;
import activities.distanceAltimetry.TrailRunning;
import activities.repetitions.AbdominalExercises;
import activities.repetitions.PushUps;
import activities.repetitions.Stretching;
import activities.repetitionsWeight.LegExtension;
import activities.repetitionsWeight.WeightLifting;
import exceptions.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The UserController class manages users and provides methods to interact with user data.
 */
public class UserController implements Serializable {
    private Users users;

    //region Constructors
    /**
     * Constructs an empty UserController object.
     */
    public UserController() {
        this.users = new Users();
    }

    /**
     * Constructs a UserController object using a Users object.
     * @param users users object to use.
     */
    public UserController(
            Users users
    ) {
        this.users = users.clone();
    }

    /**
     * Constructs a UserController object by copying another UserController's users object.
     *
     * @param controller The UserController from which to copy the users object.
     */
    public UserController(
            UserController controller
    ) {
        this.users = controller.getUsers();
    }
    //endregion

    //region Getters And Setters
    /**
     * Retrieves a clone of the users managed by this controller.
     *
     * @return A clone of the users managed by this controller.
     */
    public Users getUsers() {
        return this.users.clone();
    }

    /**
     * Sets the users managed by this controller.
     *
     * @param users The users to set.
     */
    public void setUsers(Users users) {
        this.users = users;
    }
    //endregion

    //region utils
    /**
     * Checks if a username is available.
     *
     * @param username The username to check.
     * @return true if the username is available, otherwise false.
     */
    public boolean isUsernameAvailable(String username) {
        return this.users.isUsernameAvailable(username);
    }

    /**
     * Checks if a user with a given ID exists.
     *
     * @param id The ID of the user to check.
     * @return true if a user with the given ID exists, otherwise false.
     */
    public boolean userWithIdExits(int id) {
        return this.users.containsUser(id);
    }

    /**
     * Retrieves the ID of a user based on their username.
     *
     * @param username The username of the user.
     * @return The ID of the user with the given username.
     */
    public int getUsernameID(String username) {
        return this.users.getUserWithUsername(username).getId();
    }

    /**
     * Removes a user with the given ID.
     *
     * @param id The ID of the user to remove.
     * @throws ErrorRemovingUserException If an error occurs while removing the user.
     */
    public void removeUser(int id) throws ErrorRemovingUserException {
        this.users.removeUser(id);
    }

    /**
     * Checks if a user with a given username exists.
     *
     * @param username The username of the user to check.
     * @return true if a user with the given username exists, otherwise false.
     */
    public boolean userWithUsernameExists(String username) {
        return this.users.containsUser(username);
    }
    //endregion

    //region Add Methods
    /**
     * Adds a casual user with the given information.
     *
     * @param id         The ID of the user.
     * @param name       The name of the user.
     * @param username   The username of the user.
     * @param birthdate  The birthdate of the user.
     * @param address    The address of the user.
     * @param email      The email address of the user.
     * @param sex        The gender of the user (true for male, false for female).
     * @param height     The height of the user (in meters).
     * @param weight     The weight of the user (in kilograms).
     * @param heartFreq  The heart frequency of the user.
     */
    public void addCasualUser(
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
        CasualUser casualUser = new CasualUser(id,name,username,birthdate,address,email,sex,height,weight,heartFreq);
        this.users.addUser(casualUser);
    }

    /**
     * Adds an amateur user with the given information.
     *
     * @param id         The ID of the user.
     * @param name       The name of the user.
     * @param username   The username of the user.
     * @param birthdate  The birthdate of the user.
     * @param address    The address of the user.
     * @param email      The email address of the user.
     * @param sex        The gender of the user (true for male, false for female).
     * @param height     The height of the user (in meters).
     * @param weight     The weight of the user (in kilograms).
     * @param heartFreq  The heart frequency of the user.
     */
    public void addAmateurUser(
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
        AmateurUser amateurUser = new AmateurUser(id,name,username,birthdate,address,email,sex,height,weight,heartFreq);
        this.users.addUser(amateurUser);
    }

    /**
     * Adds a professional user with the given information.
     *
     * @param id         The ID of the user.
     * @param name       The name of the user.
     * @param username   The username of the user.
     * @param birthdate  The birthdate of the user.
     * @param address    The address of the user.
     * @param email      The email address of the user.
     * @param sex        The gender of the user (true for male, false for female).
     * @param height     The height of the user (in meters).
     * @param weight     The weight of the user (in kilograms).
     * @param heartFreq  The heart frequency of the user.
     */
    public void addProfessionalUser(
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
        ProfessionalUser professionalUser = new ProfessionalUser(id,name,username,birthdate,address,email,sex,height,weight,heartFreq);
        this.users.addUser(professionalUser);
    }
    //endregion

    //region Update Methods
    /**
     * Updates the name of a user with the specified ID.
     *
     * @param id   The ID of the user.
     * @param name The new name to set for the user.
     * @throws ErrorUpdatingUserException If an error occurs while updating the user's name.
     */
    public void updateUserName(int id, String name) throws ErrorUpdatingUserException {
        this.users.updateUserName(id,name);
        if(!this.users.getUserWithId(id).getName().equals(name)) {
            throw new ErrorUpdatingUserException("Error updating user name!");
        }
    }

    /**
     * Updates the username of a user with the specified ID.
     *
     * @param id       The ID of the user.
     * @param username The new username to set for the user.
     * @throws ErrorUpdatingUserException      If an error occurs while updating the user's username.
     * @throws UsernameAlreadyExistsException If the new username already exists.
     */
    public void updateUserUsername(int id, String username) throws ErrorUpdatingUserException, UsernameAlreadyExistsException {
        this.users.updateUserUsername(id,username);
        if(!this.users.getUserWithId(id).getUsername().equals(username)) {
            throw new ErrorUpdatingUserException("Error updating user username!");
        }
    }

    /**
     * Updates the username of a user with the specified username.
     *
     * @param currentUsername The current username of the user.
     * @param newUsername     The new username to set for the user.
     * @throws ErrorUpdatingUserException      If an error occurs while updating the user's username.
     * @throws UsernameAlreadyExistsException If the new username already exists.
     */
    public void updateUserUsername(String currentUsername, String newUsername) throws ErrorUpdatingUserException, UsernameAlreadyExistsException {
        this.users.updateUserUsername(currentUsername,newUsername);
        if(this.users.containsUser(currentUsername)) {
            throw new ErrorUpdatingUserException("Error updating user username!");
        }
    }

    /**
     * Updates the birthdate of a user with the specified ID.
     *
     * @param id       The ID of the user.
     * @param birthdate The new birthdate to set for the user.
     * @throws ErrorUpdatingUserException If an error occurs while updating the user's username.
     */
    public void updateUserBirthdate(int id, LocalDate birthdate) throws ErrorUpdatingUserException {
        this.users.updateUserBirthdate(id,birthdate);

        if(!this.users.getUserWithId(id).getBirthdate().isEqual(birthdate)) {
            throw new ErrorUpdatingUserException("Error updating user birthdate!");
        }
    }

    /**
     * Updates the address of a user with the specified ID.
     *
     * @param id       The ID of the user.
     * @param address The new address to set for the user.
     * @throws ErrorUpdatingUserException If an error occurs while updating the user's username.
     */
    public void updateUserAddress(int id, String address) throws ErrorUpdatingUserException {
        this.users.updateUserAddress(id,address);
        if(!this.users.getUserWithId(id).getAddress().equals(address)) {
            throw new ErrorUpdatingUserException("Error updating user address!");
        }
    }

    /**
     * Updates the email of a user with the specified ID.
     *
     * @param id       The ID of the user.
     * @param email The new email to set for the user.
     * @throws ErrorUpdatingUserException If an error occurs while updating the user's username.
     */
    public void updateUserEmail(int id, String email) throws ErrorUpdatingUserException {
        this.users.updateUserEmail(id,email);
        if(!this.users.getUserWithId(id).getEmail().equals(email)) {
            throw new ErrorUpdatingUserException("Error updating user email!");
        }
    }

    /**
     * Updates the height of a user with the specified ID.
     *
     * @param id       The ID of the user.
     * @param height The new height to set for the user.
     * @throws ErrorUpdatingUserException If an error occurs while updating the user's username.
     */
    public void updateUserHeight(int id, double height)throws ErrorUpdatingUserException {
        this.users.updateUserHeight(id,height);
        if(this.users.getUserWithId(id).getHeight() != height) {
            throw new ErrorUpdatingUserException("Error updating user height!");
        }
    }

    /**
     * Updates the weight of a user with the specified ID.
     *
     * @param id       The ID of the user.
     * @param weight The new weight to set for the user.
     * @throws ErrorUpdatingUserException If an error occurs while updating the user's username.
     */
    public void updateUserWeight(int id, double weight) throws ErrorUpdatingUserException{
        this.users.updateUserWeight(id,weight);
        if(this.users.getUserWithId(id).getWeight() != weight) {
            throw new ErrorUpdatingUserException("Error updating user weight!");
        }
    }

    /**
     * Updates the heartFrequency of a user with the specified ID.
     *
     * @param id       The ID of the user.
     * @param heartFrequency The new heartFrequency to set for the user.
     * @throws ErrorUpdatingUserException If an error occurs while updating the user's username.
     */
    public void updateUserHeartFrequency(int id, int heartFrequency) throws ErrorUpdatingUserException {
        this.users.updateUserHeartFrequency(id,heartFrequency);
        if(this.users.getUserWithId(id).getHeartFreq() != heartFrequency) {
            throw new ErrorUpdatingUserException("Error updating user heartFreq!");
        }
    }
    //endregion

    //region AddingActivities
    /**
     * Adds a rowing activity for a user.
     *
     * @param id              The ID of the activity.
     * @param name            The name of the activity.
     * @param begin           The start date and time of the activity.
     * @param end             The end date and time of the activity.
     * @param idUser          The ID of the user performing the activity.
     * @param burnedCalories  The number of calories burned during the activity.
     * @param heartRate       The heart rate during the activity.
     * @param distance        The distance covered during the activity.
     * @param personsOnBoard  The number of persons on board during rowing.
     * @param rowAgainstTide  Whether rowing was against the tide.
     * @throws ErrorAddingActivityException If an error occurs while adding the activity.
     */
    public void addRowing(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            int personsOnBoard,
            boolean rowAgainstTide
    ) throws ErrorAddingActivityException {
        Rowing activity = new Rowing(id, name, begin, end, idUser, burnedCalories, heartRate, distance, personsOnBoard, rowAgainstTide);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    /**
     * Adds a skating activity for a user.
     *
     * @param id            The ID of the activity.
     * @param name          The name of the activity.
     * @param begin         The start date and time of the activity.
     * @param end           The end date and time of the activity.
     * @param idUser        The ID of the user performing the activity.
     * @param burnedCalories The number of calories burned during the activity.
     * @param heartRate     The heart rate during the activity.
     * @param distance      The distance covered during the activity.
     * @param skateWeight   The weight of the skates used.
     * @param freestyle     Whether the skating was freestyle.
     * @throws ErrorAddingActivityException If an error occurs while adding the activity.
     */
    public void addSkating(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            double skateWeight,
            boolean freestyle
    ) throws ErrorAddingActivityException {
        Skating activity = new Skating(id, name, begin, end, idUser, burnedCalories, heartRate, distance, skateWeight, freestyle);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    /**
     * Adds a track running activity for a user.
     *
     * @param id            The ID of the activity.
     * @param name          The name of the activity.
     * @param begin         The start date and time of the activity.
     * @param end           The end date and time of the activity.
     * @param idUser        The ID of the user performing the activity.
     * @param burnedCalories The number of calories burned during the activity.
     * @param heartRate     The heart rate during the activity.
     * @param distance      The distance covered during the activity.
     * @param hurdleJump    Whether hurdles were jumped during the activity.
     * @throws ErrorAddingActivityException If an error occurs while adding the activity.
     */
    public void addTrackRunning(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            boolean hurdleJump
    ) throws ErrorAddingActivityException {
        TrackRunning activity = new TrackRunning(id, name, begin, end, idUser, burnedCalories, heartRate, distance, hurdleJump);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    /**
     * Adds a mountain biking activity for a user.
     *
     * @param id            The ID of the activity.
     * @param name          The name of the activity.
     * @param begin         The start date and time of the activity.
     * @param end           The end date and time of the activity.
     * @param idUser        The ID of the user performing the activity.
     * @param burnedCalories The number of calories burned during the activity.
     * @param heartRate     The heart rate during the activity.
     * @param distance      The distance covered during the activity.
     * @param altimetry     The altimetry gained during the activity.
     * @param bigTires      Whether big tires were used during the activity.
     * @throws ErrorAddingActivityException If an error occurs while adding the activity.
     */
    public void addMountainBiking(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            int altimetry,
            boolean bigTires
    ) throws ErrorAddingActivityException {
        MountainBiking activity = new MountainBiking(id, name, begin, end, idUser, burnedCalories, heartRate, distance, altimetry, bigTires);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    /**
     * Adds a road cycling activity for a user.
     *
     * @param id            The ID of the activity.
     * @param name          The name of the activity.
     * @param begin         The start date and time of the activity.
     * @param end           The end date and time of the activity.
     * @param idUser        The ID of the user performing the activity.
     * @param burnedCalories The number of calories burned during the activity.
     * @param heartRate     The heart rate during the activity.
     * @param distance      The distance covered during the activity.
     * @param altimetry     The altimetry gained during the activity.
     * @param windAgainst   Whether the cyclist faced wind against them during the activity.
     * @throws ErrorAddingActivityException If an error occurs while adding the activity.
     */
    public void addRoadCycling(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            int altimetry,
            boolean windAgainst
    ) throws ErrorAddingActivityException {
        RoadCycling activity = new RoadCycling(id, name, begin, end, idUser, burnedCalories, heartRate, distance, altimetry, windAgainst);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    /**
     * Adds a road running activity for a user.
     *
     * @param id            The ID of the activity.
     * @param name          The name of the activity.
     * @param begin         The start date and time of the activity.
     * @param end           The end date and time of the activity.
     * @param idUser        The ID of the user performing the activity.
     * @param burnedCalories The number of calories burned during the activity.
     * @param heartRate     The heart rate during the activity.
     * @param distance      The distance covered during the activity.
     * @param altimetry     The altimetry gained during the activity.
     * @param windAgainst   Whether the runner faced wind against them during the activity.
     * @throws ErrorAddingActivityException If an error occurs while adding the activity.
     */
    public void addRoadRunning(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            int altimetry,
            boolean windAgainst
    ) throws ErrorAddingActivityException {
        RoadRunning activity = new RoadRunning(id, name, begin, end, idUser, burnedCalories, heartRate, distance, altimetry, windAgainst);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    /**
     * Adds a trail running activity for a user.
     *
     * @param id            The ID of the activity.
     * @param name          The name of the activity.
     * @param begin         The start date and time of the activity.
     * @param end           The end date and time of the activity.
     * @param idUser        The ID of the user performing the activity.
     * @param burnedCalories The number of calories burned during the activity.
     * @param heartRate     The heart rate during the activity.
     * @param distance      The distance covered during the activity.
     * @param altimetry     The altimetry gained during the activity.
     * @param wetFloor      Whether the floor was wet during the activity.
     * @throws ErrorAddingActivityException If an error occurs while adding the activity.
     */
    public void addTrailRunning(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            int altimetry,
            boolean wetFloor
    ) throws ErrorAddingActivityException {
        TrailRunning activity = new TrailRunning(id, name, begin, end, idUser, burnedCalories, heartRate, distance, altimetry, wetFloor);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    /**
     * Adds an abdominal exercises activity for a user.
     *
     * @param id             The ID of the activity.
     * @param name           The name of the activity.
     * @param begin          The start date and time of the activity.
     * @param end            The end date and time of the activity.
     * @param idUser         The ID of the user performing the activity.
     * @param burnedCalories The number of calories burned during the activity.
     * @param heartRate      The heart rate during the activity.
     * @param nRepetitions   The number of repetitions performed.
     * @param helped         Whether the user was assisted during the exercise.
     * @throws ErrorAddingActivityException If an error occurs while adding the activity.
     */
    public void addAbdominalExercises(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int nRepetitions,
            boolean helped
    ) throws ErrorAddingActivityException {
        AbdominalExercises activity = new AbdominalExercises(id, name, begin, end, idUser, burnedCalories, heartRate, nRepetitions, helped);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    /**
     * Adds a push-ups activity for a user.
     *
     * @param id                   The ID of the activity.
     * @param name                 The name of the activity.
     * @param begin                The start date and time of the activity.
     * @param end                  The end date and time of the activity.
     * @param idUser               The ID of the user performing the activity.
     * @param burnedCalories       The number of calories burned during the activity.
     * @param heartRate            The heart rate during the activity.
     * @param nRepetitions         The number of repetitions performed.
     * @param diamondIntercalated  Whether diamond push-ups were performed.
     * @throws ErrorAddingActivityException If an error occurs while adding the activity.
     */
    public void addPushUps(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int nRepetitions,
            boolean diamondIntercalated
    ) throws ErrorAddingActivityException {
        PushUps activity = new PushUps(id, name, begin, end, idUser, burnedCalories, heartRate, nRepetitions, diamondIntercalated);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    /**
     * Adds a stretching activity for a user.
     *
     * @param id                   The ID of the activity.
     * @param name                 The name of the activity.
     * @param begin                The start date and time of the activity.
     * @param end                  The end date and time of the activity.
     * @param idUser               The ID of the user performing the activity.
     * @param burnedCalories       The number of calories burned during the activity.
     * @param heartRate            The heart rate during the activity.
     * @param nRepetitions         The number of repetitions performed.
     * @param helped               Whether the user was assisted during stretching.
     * @throws ErrorAddingActivityException If an error occurs while adding the activity.
     */
    public void addStretching(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int nRepetitions,
            boolean helped
    ) throws ErrorAddingActivityException {
        Stretching activity = new Stretching(id, name, begin, end, idUser, burnedCalories, heartRate, nRepetitions, helped);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    /**
     * Adds a leg extension activity for a user.
     *
     * @param id            The ID of the activity.
     * @param name          The name of the activity.
     * @param begin         The start date and time of the activity.
     * @param end           The end date and time of the activity.
     * @param idUser        The ID of the user performing the activity.
     * @param burnedCalories The number of calories burned during the activity.
     * @param heartRate     The heart rate during the activity.
     * @param nRepetitions  The number of repetitions performed.
     * @param weight        The weight lifted during the activity.
     * @param chairAngle    The angle of the chair during the activity.
     * @throws ErrorAddingActivityException If an error occurs while adding the activity.
     */
    public void addLegExtension(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int nRepetitions,
            int weight,
            int chairAngle
    ) throws ErrorAddingActivityException {
        LegExtension activity = new LegExtension(id, name, begin, end, idUser, burnedCalories, heartRate, nRepetitions, weight, chairAngle);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    /**
     * Adds a weight lifting activity for a user.
     *
     * @param id            The ID of the activity.
     * @param name          The name of the activity.
     * @param begin         The start date and time of the activity.
     * @param end           The end date and time of the activity.
     * @param idUser        The ID of the user performing the activity.
     * @param burnedCalories The number of calories burned during the activity.
     * @param heartRate     The heart rate during the activity.
     * @param nRepetitions  The number of repetitions performed.
     * @param weight        The weight lifted during the activity.
     * @param helped        Whether the user was assisted during weight lifting.
     * @throws ErrorAddingActivityException If an error occurs while adding the activity.
     */
    public void addWeightLifting(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int nRepetitions,
            int weight,
            boolean helped
    ) throws ErrorAddingActivityException {
        WeightLifting activity = new WeightLifting(id, name, begin, end, idUser, burnedCalories, heartRate, nRepetitions, weight, helped);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }
    //endregion

    //region Training Plan Methods
    /**
     * Verifies if there is a hard activity too close to the given date in the user's training schedule.
     *
     * @param u      The user for whom the verification is being done.
     * @param doDate The date for which the verification is being done.
     * @throws ErrorHardActivityCloseException If a hard activity is found to be too close to the given date.
     */
    private void verifyCloseHardActivity(User u, LocalDate doDate) throws ErrorHardActivityCloseException {
        List<TrainingPlan> userTps = u.getTrainingSchedule().stream()
                .filter(tp -> (tp.getDoDate().isAfter(doDate.minusDays(2)) && tp.getDoDate().isBefore(doDate.plusDays(2))))
                .toList();
        for(TrainingPlan tp : userTps) {
            if(tp.containsHardActivity())
                throw new ErrorHardActivityCloseException("A close training plan already has an hard activity.");
        }
    }

    /**
     * Verifies if there are consecutive repeated days with hard activities.
     *
     * @param repeat The array indicating which days the training plan should repeat.
     * @throws ErrorHardActivityCloseException If consecutive repeated days with hard activities are found.
     */
    private void verifyCloseHardRepeatActivity(boolean[] repeat) throws ErrorHardActivityCloseException {
        for(int i = 0 ; i < repeat.length - 1 ; i++) {
            if(repeat[i] && repeat[i + 1])
                throw new ErrorHardActivityCloseException("You can't repeat the same training plan with hard activities on consecutive days.");
        }
    }

    /**
     * Verifies if there is already a training plan on the given date for the user.
     *
     * @param u      The user for whom the verification is being done.
     * @param doDate The date for which the verification is being done.
     * @throws ErrorSameDayTrainingPlanException If there is already a training plan on the given date.
     */
    private void verifySameDayTrainingPlan(User u, LocalDate doDate) throws ErrorSameDayTrainingPlanException {
        if(u.getTrainingSchedule().stream().anyMatch(tp -> tp.getDoDate().equals(doDate)))
            throw new ErrorSameDayTrainingPlanException("It already exists a training plan on the same day.");
    }

    /**
     * Verifies if there are already repeated training plans on the same day for the user.
     *
     * @param u      The user for whom the verification is being done.
     * @param repeat The array indicating which days the training plan should repeat.
     * @throws ErrorSameDayTrainingPlanException If there are repeated training plans on the same day.
     */
    private void verifyRepeatTrainingPlan(User u, boolean[] repeat) throws ErrorSameDayTrainingPlanException {
        for(TrainingPlan tp : u.getTrainingSchedule()) {
            boolean[] tpRepeat = tp.getRepeat();
            for(int i = 0 ; i < repeat.length ; i++) {
                if(repeat[i] && tpRepeat[i]) {
                    throw new ErrorSameDayTrainingPlanException("You can't repeat the training plan due to a training plan being repeated already on the same day.");
                }
            }
        }
    }

    /**
     * Adds a manual training plan for a user.
     *
     * @param id                 The ID of the training plan.
     * @param idUser             The ID of the user.
     * @param activities         The list of activities in the training plan.
     * @param doDate             The date of the training plan.
     * @param repeat             An array indicating which days the training plan should repeat.
     * @throws InvalidValueException           If the number of activities exceeds 3 or the number of hard activities exceeds 1.
     * @throws ErrorHardActivityCloseException If a hard activity is too close to the training plan.
     * @throws ErrorSameDayTrainingPlanException If there is already a training plan on the same day.
     */
    public void addManualTrainingPlan(
            int id,
            int idUser,
            List<Activity> activities,
            LocalDate doDate,
            boolean[] repeat
    ) throws InvalidValueException, ErrorHardActivityCloseException, ErrorSameDayTrainingPlanException {
        if(activities.size() > 3) throw new InvalidValueException("Number of activities must be less or equals to 3.");
        int hardActivities = (int) activities.stream().filter(a -> a instanceof Hard).count();
        if(hardActivities > 1) throw new InvalidValueException("Number of hard activities must be less or equals to 1.");

        User u = this.users.getUserWithId(idUser);
        if(hardActivities != 0) verifyCloseHardRepeatActivity(repeat);
        verifySameDayTrainingPlan(u, doDate);
        verifyCloseHardActivity(u, doDate);
        verifyRepeatTrainingPlan(u, repeat);

        TrainingPlan tp = new TrainingPlan(id, activities, doDate, repeat);
        u.addTrainingPlan(tp);
        this.users.updateUser(u);
    }

    /**
     * Adds an automatic training plan for a user.
     *
     * @param id                         The ID of the training plan.
     * @param idUser                     The ID of the user.
     * @param idsActivity                The IDs of the activities in the training plan.
     * @param wantsHard                  Indicates if the user wants hard activities in the plan.
     * @param maximumActivitiesPerDay    The maximum number of activities per day in the plan.
     * @param doDate                     The date of the training plan.
     * @param repeat                     An array indicating which days the training plan should repeat.
     * @param minimumCaloriesConsumption The minimum calories consumption for the plan.
     * @param planType                   The type of the plan (1 for Equilibrate, 2 for Strength, 3 for Cardio).
     * @throws ErrorHardActivityCloseException If a hard activity is too close to the training plan.
     * @throws InvalidValueException          If the maximum activities per day is invalid.
     */
    public void addAutomaticTrainingPlan(
            int id,
            int idUser,
            int[] idsActivity,
            boolean wantsHard,
            int maximumActivitiesPerDay,
            LocalDate doDate,
            boolean[] repeat,
            int minimumCaloriesConsumption,
            int planType
    ) throws ErrorHardActivityCloseException, InvalidValueException {
        if(maximumActivitiesPerDay <= 0 || maximumActivitiesPerDay > 3)
            throw new InvalidValueException("The system only supports a number of activities per day between 1 and 3.");

        User u = this.users.getUserWithId(idUser);
        if(wantsHard) {
            verifyCloseHardActivity(u, doDate);
        }

        List<Activity> activities = new ArrayList<>();
        switch(planType) {
            case 1 : {
                generateEquilibrateActivities(
                        activities,
                        idsActivity,
                        idUser,
                        minimumCaloriesConsumption,
                        maximumActivitiesPerDay,
                        wantsHard,
                        doDate,
                        planType
                );
                break;
            }
            case 2 : {
                generateStrengthActivities(
                        activities,
                        idsActivity,
                        idUser,
                        minimumCaloriesConsumption,
                        maximumActivitiesPerDay,
                        wantsHard,
                        doDate,
                        planType
                );
                break;
            }
            case 3 : {
                generateCardioActivities(
                        activities,
                        idsActivity,
                        idUser,
                        minimumCaloriesConsumption,
                        maximumActivitiesPerDay,
                        wantsHard,
                        doDate,
                        planType
                );
                break;
            }
        }

        TrainingPlan tp = new TrainingPlan(id, activities, doDate, repeat);
        u.addTrainingPlan(tp);
        this.users.updateUser(u);
    }

    //region Automatic Generators
    /**
     * Randomly selects a set of unique categories for activities.
     *
     * @param maximumActivitiesPerDay The maximum number of activities allowed per day.
     * @param numCategories           The total number of categories available for selection.
     * @return A set of unique category IDs.
     */
    private Set<Integer> randomCategorySelector(int maximumActivitiesPerDay, int numCategories) {
        Set<Integer> categories = new HashSet<>();
        Random random = new Random();
        int category = random.nextInt(2) + 1;
        categories.add(category);
        while(categories.size() < maximumActivitiesPerDay) {
            category = random.nextInt(numCategories) + 1;
            categories.add(category);
        }

        return categories;
    }

    /**
     * Generates a distance-based activity with random attributes.
     *
     * @param id                       The ID of the activity.
     * @param idUser                   The ID of the user.
     * @param activities               The list of activities for the user.
     * @param minimumCaloriesConsumption The minimum calories consumption required.
     * @param maximumActivitiesPerDay The maximum number of activities allowed per day.
     * @param doDate                   The date for which the activity is generated.
     * @param hasHard                  Indicates if the user has hard activities in the plan.
     * @param wantsHard                Indicates if the user wants hard activities in the plan.
     * @return The generated distance-based activity.
     */
    private Activity generateDistanceActivity(int id, int idUser, List<Activity> activities, int minimumCaloriesConsumption, int maximumActivitiesPerDay, LocalDate doDate, boolean hasHard, boolean wantsHard) {
        Random random = new Random();
        int type = random.nextInt(3) + 1; // Quantidade de Atividades

        // Se o tipo for de uma atividade hard, gerar outro tipo
        if(hasHard && type == 1) {
            while(type == 1) {
                type = random.nextInt(3) + 1;   // Quantidade de Atividades
            }
        }

        if(!hasHard && wantsHard) {
            type = 1;
        }

        DistanceAct activity;
        int startDistance = 500;
        switch(type) {
            case 1 : {
                    activity = new Rowing(id, idUser, random.nextInt(startDistance), random.nextInt(6) + 1, random.nextBoolean());
                    break;
            }
            case 2: {
                    activity = new Skating(id, idUser, random.nextInt(startDistance), random.nextDouble(13) + 3, random.nextBoolean());
                    break;
            }
            case 3: {
                    activity = new TrackRunning(id, idUser, random.nextInt(startDistance), random.nextBoolean());
                    break;
            }
            default : {
                    activity = new Rowing(id, idUser, random.nextInt(startDistance), random.nextInt(6) + 1, random.nextBoolean());
            }
        }
        activity.setBegin(doDate.atTime(random.nextInt(24), random.nextInt(60)));
        activity.setEnd(activity.getBegin().plusMinutes(30));
        if(!activities.isEmpty()) {
            Activity a = activities.getLast();
            while (!(activity.getBegin().isAfter(a.getEnd()) || activity.getEnd().isBefore(a.getBegin()))) {
                activity.setBegin(doDate.atTime(random.nextInt(24), random.nextInt(60)));
            }
            activity.setEnd(activity.getBegin().plusMinutes(30));
        }

        User u = this.users.getUserWithId(idUser);
        activity.setHeartRate((int) (u.getHeartFreq()*1.2));
        int burnedCalories = u.speculateBurnedCalories(activity);

        while(burnedCalories < minimumCaloriesConsumption) {
            int select = random.nextInt(2) + 1;
            switch(select) {
                case 1 : {
                    startDistance += random.nextInt(200);
                    activity.setDistance(startDistance);
                    break;
                }
                case 2 : {
                    activity.setEnd(activity.getEnd().plusMinutes(random.nextInt(5)));
                    break;
                }
            }
            activity.calculateCalories();
            burnedCalories = u.speculateBurnedCalories(activity);
        }

        return activity;
    }

    /**
     * Generates a distance and altimetry-based activity with random attributes.
     *
     * @param id                       The ID of the activity.
     * @param idUser                   The ID of the user.
     * @param activities               The list of activities for the user.
     * @param minimumCaloriesConsumption The minimum calories consumption required.
     * @param maximumActivitiesPerDay The maximum number of activities allowed per day.
     * @param doDate                   The date for which the activity is generated.
     * @param hasHard                  Indicates if the user has hard activities in the plan.
     * @param wantsHard                Indicates if the user wants hard activities in the plan.
     * @return The generated distance and altimetry-based activity.
     */
    private Activity generateDistanceAndAltimetryActivity(int id, int idUser, List<Activity> activities, int minimumCaloriesConsumption, int maximumActivitiesPerDay, LocalDate doDate, boolean hasHard, boolean wantsHard) {
        Random random = new Random();
        int type = random.nextInt(4) + 1;

        // Se o tipo for de uma atividade hard, gerar outro tipo
        if(hasHard && (type == 1 || type == 4)) {
            while(type == 1) {
                type = random.nextInt(4) + 1;   // Quantidade de atividades
            }
        }

        if(!hasHard && wantsHard) {
            switch(random.nextInt(2)) {
                case 0 : {
                    type = 1;
                    break;
                }
                case 1 : {
                    type = 4;
                    break;
                }
            }
        }

        DistanceAndAltimetryAct activity;
        int startDistance = 400;
        int startAltimetry = 150;
        switch(type) {
            case 1 : {
                activity = new MountainBiking(id, idUser, random.nextInt(startDistance), random.nextInt(startAltimetry), random.nextBoolean());
                break;
            }
            case 2: {
                activity = new RoadCycling(id, idUser, random.nextInt(startDistance), random.nextInt(startAltimetry), random.nextBoolean());
                break;
            }
            case 3: {
                activity = new RoadRunning(id, idUser, random.nextInt(startDistance), random.nextInt(startAltimetry), random.nextBoolean());
                break;
            }
            default : {
                activity = new TrailRunning(id, idUser, random.nextInt(startDistance), random.nextInt(startAltimetry), random.nextBoolean());
            }
        }
        activity.setBegin(doDate.atTime(random.nextInt(24), random.nextInt(60)));
        activity.setEnd(activity.getBegin().plusMinutes(30));
        if(!activities.isEmpty()) {
            Activity a = activities.getLast();
            while (!(activity.getBegin().isAfter(a.getEnd()) || activity.getEnd().isBefore(a.getBegin()))) {
                activity.setBegin(doDate.atTime(random.nextInt(24), random.nextInt(60)));
            }
            activity.setEnd(activity.getBegin().plusMinutes(30));
        }

        User u = this.users.getUserWithId(idUser);
        activity.setHeartRate((int) (u.getHeartFreq()*1.2));
        int burnedCalories = u.speculateBurnedCalories(activity);
        while(burnedCalories < minimumCaloriesConsumption) {
            int select = random.nextInt(3) + 1;
            switch(select) {
                case 1 : {
                    startDistance += random.nextInt(200);
                    activity.setDistance(startDistance);
                    break;
                }
                case 2 : {
                    startAltimetry += random.nextInt(65);
                    activity.setAltimetry(startAltimetry);
                    break;
                }
                case 3 : {
                    activity.setEnd(activity.getEnd().plusMinutes(random.nextInt(5)));
                    break;
                }
            }
            activity.calculateCalories();
            burnedCalories = u.speculateBurnedCalories(activity);
        }

        return activity;
    }

    /**
     * Generates a repetitions-based activity with random attributes.
     *
     * @param id                       The ID of the activity.
     * @param idUser                   The ID of the user.
     * @param activities               The list of activities for the user.
     * @param minimumCaloriesConsumption The minimum calories consumption required.
     * @param maximumActivitiesPerDay The maximum number of activities allowed per day.
     * @param doDate                   The date for which the activity is generated.
     * @param hasHard                  Indicates if the user has hard activities in the plan.
     * @param wantsHard                Indicates if the user wants hard activities in the plan.
     * @param planType                 The type of training plan.
     * @return The generated repetitions-based activity.
     */
    private Activity generateRepetitionsActivity(
            int id,
            int idUser,
            List<Activity> activities,
            int minimumCaloriesConsumption,
            int maximumActivitiesPerDay,
            LocalDate doDate,
            boolean hasHard,
            boolean wantsHard,
            int planType
    ) {
        Random random = new Random();
        int type = random.nextInt(3) + 1; // Quantidade de Atividades

        // Se o tipo for de uma atividade hard, gerar outro tipo
        if(hasHard && type == 2) {
            while(type == 2) {
                type = random.nextInt(3) + 1;   // Quantidade de Atividades
            }
        }

        if(!hasHard && wantsHard) {
            type = 2;
        }

        RepetitionAct activity;
        int startRepetitions = 10;
        switch(type) {
            case 1 : {
                activity = new AbdominalExercises(id, idUser, random.nextInt(startRepetitions) + 1, random.nextBoolean());
                break;
            }
            case 2: {
                activity = new PushUps(id, idUser, random.nextInt(startRepetitions) + 1, random.nextBoolean());
                break;
            }
            case 3: {
                activity = new Stretching(id, idUser, random.nextInt(startRepetitions) + 1, random.nextBoolean());
                break;
            }
            default : {
                activity = new AbdominalExercises(id, idUser, random.nextInt(startRepetitions) + 1, random.nextBoolean());
            }
        }
        activity.setBegin(doDate.atTime(random.nextInt(24), random.nextInt(60)));
        activity.setEnd(activity.getBegin().plusMinutes(30));
        if(!activities.isEmpty()) {
            Activity a = activities.getLast();
            while (!(activity.getBegin().isAfter(a.getEnd()) || activity.getEnd().isBefore(a.getBegin()))) {
                activity.setBegin(doDate.atTime(random.nextInt(24), random.nextInt(60)));
            }
            activity.setEnd(activity.getBegin().plusMinutes(4));
        }

        User u = this.users.getUserWithId(idUser);
        activity.setHeartRate((int) (u.getHeartFreq()*1.2));
        int burnedCalories = u.speculateBurnedCalories(activity);
        int decider = minimumCaloriesConsumption/10;
        if(planType == 2) decider = (int) (minimumCaloriesConsumption*0.03);    // TODO CHANGE
        while(burnedCalories < decider) {
            startRepetitions += random.nextInt(5);
            activity.setNRepetitions(startRepetitions);
            activity.setEnd(activity.getBegin().plusMinutes((long) (startRepetitions*0.2)));
            activity.calculateCalories();
            burnedCalories = u.speculateBurnedCalories(activity);
        }

        return activity;
    }

    /**
     * Generates a repetitions-based activity with weightlifting and random attributes.
     *
     * @param id                       The ID of the activity.
     * @param idUser                   The ID of the user.
     * @param activities               The list of activities for the user.
     * @param minimumCaloriesConsumption The minimum calories consumption required.
     * @param maximumActivitiesPerDay The maximum number of activities allowed per day.
     * @param doDate                   The date for which the activity is generated.
     * @param hasHard                  Indicates if the user has hard activities in the plan.
     * @param wantsHard                Indicates if the user wants hard activities in the plan.
     * @param planType                 The type of training plan.
     * @return The generated repetitions-based activity with weightlifting.
     */
    private Activity generateRepetitionsWeightActivity(
            int id,
            int idUser,
            List<Activity> activities,
            int minimumCaloriesConsumption,
            int maximumActivitiesPerDay,
            LocalDate doDate,
            boolean hasHard,
            boolean wantsHard,
            int planType
    ) {
        Random random = new Random();
        int type = random.nextInt(2) + 1;

        // Se o tipo for de uma atividade hard, gerar outro tipo
        if(hasHard && type == 2) {
            while(type == 2) {
                type = random.nextInt(2) + 1;   // Quantidade de atividades
            }
        }

        if(!hasHard && wantsHard) {
            type = 2;
        }

        RepetitionWithWeightsAct activity;
        int startRepetitions = 4;
        int startWeight = 50;
        switch(type) {
            case 1 : {
                activity = new LegExtension(id, idUser, random.nextInt(startRepetitions) + 1, random.nextInt(startWeight) + 1, random.nextInt(50) + 30);
                break;
            }
            case 2: {
                activity = new WeightLifting(id, idUser, random.nextInt(startRepetitions) + 1, random.nextInt(startWeight) + 1, random.nextBoolean());
                break;
            }
            default : {
                activity = new LegExtension(id, idUser, random.nextInt(startRepetitions) + 1, random.nextInt(startWeight) + 1, random.nextInt(50) + 30);
            }
        }
        activity.setBegin(doDate.atTime(random.nextInt(24), random.nextInt(60)));
        activity.setEnd(activity.getBegin().plusMinutes(30));
        if(!activities.isEmpty()) {
            Activity a = activities.getLast();
            while (!(activity.getBegin().isAfter(a.getEnd()) || activity.getEnd().isBefore(a.getBegin()))) {
                activity.setBegin(doDate.atTime(random.nextInt(24), random.nextInt(60)));
            }
            activity.setEnd(activity.getBegin().plusMinutes(30));
        }


        User u = this.users.getUserWithId(idUser);
        activity.setHeartRate((int) (u.getHeartFreq()*1.2));
        int burnedCalories = u.speculateBurnedCalories(activity);

        int decider = minimumCaloriesConsumption/100;
        if(planType == 2) decider = (int) (minimumCaloriesConsumption*0.03);    // TODO CHANGE

        while(burnedCalories < decider) {
            int select = random.nextInt(2) + 1;
            switch(select) {
                case 1 : {
                    startRepetitions += random.nextInt(5);
                    activity.setNRepetitions(startRepetitions);
                    break;
                }
                case 2 : {
                    startWeight += random.nextInt(3);
                    activity.setWeight(startWeight);
                    break;
                }
            }
            activity.setEnd(activity.getBegin().plusMinutes((long) (startRepetitions*0.2)));
            activity.calculateCalories();
            burnedCalories = u.speculateBurnedCalories(activity);
        }

        return activity;
    }

    /**
     * Generates equilibrated activities based on user preferences.
     *
     * @param activities               The list of activities for the user.
     * @param idsActivity              The IDs of activities to be generated.
     * @param idUser                   The ID of the user.
     * @param minimumCaloriesConsumption The minimum calories consumption required.
     * @param maximumActivitiesPerDay The maximum number of activities allowed per day.
     * @param wantsHard                Indicates if the user wants hard activities in the plan.
     * @param doDate                   The date for which the activities are generated.
     * @param planType                 The type of training plan.
     */
    private void generateEquilibrateActivities(
            List<Activity> activities,
            int[] idsActivity,
            int idUser,
            int minimumCaloriesConsumption,
            int maximumActivitiesPerDay,
            boolean wantsHard,
            LocalDate doDate,
            int planType
    ) {
        Set<Integer> categories = randomCategorySelector(maximumActivitiesPerDay, 4);
        boolean hasHard = false;
        int idIndex = -1;
        for(int category : categories) {
            Activity activity;
            if(idIndex < idsActivity.length) idIndex++;
            switch(category) {
                case 2 : {
                    activity = generateDistanceAndAltimetryActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard);
                    break;
                }
                case 3 : {
                    activity = generateRepetitionsActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard, planType);
                    break;
                }
                case 4 : {
                    activity = generateRepetitionsWeightActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard, planType);
                    break;
                }
                default: {
                    activity = generateDistanceActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard);
                    break;
                }
            }
            activities.add(activity);
            hasHard = activities.stream().anyMatch(a -> a instanceof Hard);
        }
    }

    /**
     * Generates strength-based activities based on user preferences.
     *
     * @param activities               The list of activities for the user.
     * @param idsActivity              The IDs of activities to be generated.
     * @param idUser                   The ID of the user.
     * @param minimumCaloriesConsumption The minimum calories consumption required.
     * @param maximumActivitiesPerDay The maximum number of activities allowed per day.
     * @param wantsHard                Indicates if the user wants hard activities in the plan.
     * @param doDate                   The date for which the activities are generated.
     * @param planType                 The type of training plan.
     */
    private void generateStrengthActivities(
            List<Activity> activities,
            int[] idsActivity,
            int idUser,
            int minimumCaloriesConsumption,
            int maximumActivitiesPerDay,
            boolean wantsHard,
            LocalDate doDate,
            int planType
    ) {
        Set<Integer> categories = randomCategorySelector(maximumActivitiesPerDay, 3);
        boolean hasHard = false;
        int idIndex = -1;
        for(int category : categories) {
            Activity activity;
            if(idIndex < idsActivity.length) idIndex++;
            switch(category) {
                case 1 : {
                    activity = generateRepetitionsActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard, planType);
                    break;
                }
                case 2 : {
                    activity = generateDistanceAndAltimetryActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard);
                }
                default: {
                    activity = generateRepetitionsWeightActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard, planType);
                    break;
                }
            }
            activities.add(activity);
            hasHard = activities.stream().anyMatch(a -> a instanceof Hard);
        }
    }

    /**
     * Generates cardio-based activities based on user preferences.
     *
     * @param activities               The list of activities for the user.
     * @param idsActivity              The IDs of activities to be generated.
     * @param idUser                   The ID of the user.
     * @param minimumCaloriesConsumption The minimum calories consumption required.
     * @param maximumActivitiesPerDay The maximum number of activities allowed per day.
     * @param wantsHard                Indicates if the user wants hard activities in the plan.
     * @param doDate                   The date for which the activities are generated.
     * @param planType                 The type of training plan.
     */
    private void generateCardioActivities(
            List<Activity> activities,
            int[] idsActivity,
            int idUser,
            int minimumCaloriesConsumption,
            int maximumActivitiesPerDay,
            boolean wantsHard,
            LocalDate doDate,
            int planType
    ) {
        Set<Integer> categories = randomCategorySelector(maximumActivitiesPerDay, 3);
        boolean hasHard = false;
        int idIndex = -1;
        for(int category : categories) {
            Activity activity;
            if(idIndex < idsActivity.length) idIndex++;
            switch(category) {
                case 1 : {
                    activity = generateDistanceActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard);
                    break;
                }
                case 2 : {
                    activity = generateDistanceAndAltimetryActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard);
                    break;
                }
                case 3 : {
                    activity = generateRepetitionsActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard, planType);
                }
                default: {
                    activity = generateDistanceActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard);
                    break;
                }
            }
            activities.add(activity);
            hasHard = activities.stream().anyMatch(a -> a instanceof Hard);
        }
    }
    //endregion
    //endregion

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserController that = (UserController) o;
        return this.getUsers().equals(that.getUsers());
    }

    public UserController clone(){
        return new UserController(this);
    }
}
