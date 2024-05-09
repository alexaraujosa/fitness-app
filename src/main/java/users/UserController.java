package users;

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
import exceptions.ErrorAddingActivityException;
import exceptions.ErrorRemovingUserException;
import exceptions.ErrorUpdatingUserException;
import exceptions.UsernameAlreadyExistsException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserController implements Serializable {
    private Users users;

    public UserController(){
        this.users = new Users();
    }

    public UserController(Users users){
        this.users = users.clone();
    }

    public UserController(UserController controller){
        this.users = controller.getUsers();
    }

    public Users getUsers() {
        return this.users.clone();
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public boolean isUsernameAvailable(String username) {
        return this.users.isUsernameAvailable(username);
    }

    public boolean userWithIdExits(int id) {
        return this.users.containsUser(id);
    }

    public int getUsernameID(String username) {
        return this.users.getUserWithUsername(username).getId();
    }

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

    public void updateUserName(int id, String name) throws ErrorUpdatingUserException {
        this.users.updateUserName(id,name);
        if(!this.users.getUserWithId(id).getName().equals(name)) {
            throw new ErrorUpdatingUserException("Error updating user name!");
        }
    }

    public void updateUserUsername(int id, String username) throws ErrorUpdatingUserException, UsernameAlreadyExistsException {
        this.users.updateUserUsername(id,username);
        if(!this.users.getUserWithId(id).getUsername().equals(username)) {
            throw new ErrorUpdatingUserException("Error updating user username!");
        }
    }

    public void updateUserUsername(String currentUsername, String newUsername) throws ErrorUpdatingUserException, UsernameAlreadyExistsException {
        this.users.updateUserUsername(currentUsername,newUsername);
        if(this.users.containsUser(currentUsername)) {
            throw new ErrorUpdatingUserException("Error updating user username!");
        }
    }

    public void updateUserBirthdate(int id, LocalDate birthdate) throws ErrorUpdatingUserException {
        this.users.updateUserBirthdate(id,birthdate);

        if(!this.users.getUserWithId(id).getBirthdate().isEqual(birthdate)) {
            throw new ErrorUpdatingUserException("Error updating user birthdate!");
        }
    }

    public void updateUserAddress(int id, String address) throws ErrorUpdatingUserException {
        this.users.updateUserAddress(id,address);
        if(!this.users.getUserWithId(id).getAddress().equals(address)) {
            throw new ErrorUpdatingUserException("Error updating user address!");
        }
    }

    public void updateUserEmail(int id, String email) throws ErrorUpdatingUserException {
        this.users.updateUserEmail(id,email);
        if(!this.users.getUserWithId(id).getEmail().equals(email)) {
            throw new ErrorUpdatingUserException("Error updating user email!");
        }
    }

    public void updateUserHeight(int id, double height)throws ErrorUpdatingUserException {
        this.users.updateUserHeight(id,height);
        if(this.users.getUserWithId(id).getHeight() != height) {
            throw new ErrorUpdatingUserException("Error updating user height!");
        }
    }

    public void updateUserWeight(int id, double weight) throws ErrorUpdatingUserException{
        this.users.updateUserWeight(id,weight);
        if(this.users.getUserWithId(id).getWeight() != weight) {
            throw new ErrorUpdatingUserException("Error updating user weight!");
        }
    }

    public void updateUserHeartFrequency(int id, int heartFrequency) throws ErrorUpdatingUserException {
        this.users.updateUserHeartFrequency(id,heartFrequency);
        if(this.users.getUserWithId(id).getHeartFreq() != heartFrequency) {
            throw new ErrorUpdatingUserException("Error updating user heartFreq!");
        }
    }

    //region AddingActivities
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

    public void removeUser(int id) throws ErrorRemovingUserException {
        this.users.removeUser(id);
    }

    public boolean userWithUsernameExists(String username) {
        return this.users.containsUser(username);
    }

//    public void updateUser(User user) {
//        this.users.removeUser(user.getId());
//        this.users.addUser(user);
//    }

}
