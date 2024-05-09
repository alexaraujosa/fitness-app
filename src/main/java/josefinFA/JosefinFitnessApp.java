package josefinFA;

import activities.Activity;
import activities.DistanceAct;
import activities.DistanceAndAltimetryAct;
import activities.distanceAltimetry.RoadCycling;
import exceptions.*;
import users.User;
import users.UserController;
import utils.IDManager;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JosefinFitnessApp implements Serializable {
    private int userID;
    private LocalDateTime systemDate;
    private UserController userController;
    private IDManager idManager;
    private Stats stats;

    //region constructors
    public JosefinFitnessApp(){
        this.userID = -1;
        this.systemDate = LocalDateTime.now();
        this.userController = new UserController();
        this.idManager = new IDManager();
        this.stats = new Stats();
    }

    public JosefinFitnessApp(int userID, LocalDateTime date, UserController userController, IDManager idManager, Stats stats) {
        this.userID = userID;
        this.systemDate = date;
        this.userController = userController.clone();
        this.idManager = idManager.clone();
        this.stats = stats.clone();
    }

    public JosefinFitnessApp(JosefinFitnessApp app){
        this.userID = app.getUserID();
        this.systemDate = app.getSystemDate();
        this.userController = app.getUserController();
        this.idManager = app.getIdManager();
        this.stats = app.getStats();
    }
    //endregion

    //region getters&Setters
    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public LocalDateTime getSystemDate() {
        return this.systemDate;
    }

    public void setSystemDate(LocalDateTime systemDate) {
        this.systemDate = systemDate;
    }

    public UserController getUserController() {
        return this.userController.clone();
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public IDManager getIdManager() {
        return this.idManager.clone();
    }

    public void setIdManager(IDManager idManager) {
        this.idManager = idManager;
    }

    public Stats getStats() {
        return this.stats.clone();
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }
    //endregion

    //region LoggedUserFunctions
    public void login(String username) throws ErrorLoggingInException {
        if(this.userController.userWithUsernameExists(username)) {
            this.userID = this.userController.getUsernameID(username);
            if(this.userID == -1) {
                throw new ErrorLoggingInException("Error updating user ID");
            }
        } else {
            throw new ErrorLoggingInException("User " + username + " does not exist");
        }
    }

    public void logout(){
        this.userID = -1;
    }

    public String getLoggedUserInfo(){
        return this.userController.getUsers().getUserWithId(this.userID).toString();
    }

    public void updateLoggedUserName(String name) throws ErrorUpdatingUserException {
        this.userController.updateUserName(this.userID, name);
    }

    public void updateLoggedUserUsername(String username) throws UsernameAlreadyExistsException, ErrorUpdatingUserException {
        this.userController.updateUserUsername(this.userID, username);
    }

    public void updateLoggedUserBirthdate(LocalDate birthdate) throws ErrorUpdatingUserException {
        this.userController.updateUserBirthdate(this.userID, birthdate);
    }

    public void updateLoggedUserAddress(String address) throws ErrorUpdatingUserException {
        this.userController.updateUserAddress(this.userID, address);
    }

    public void updateLoggedUserEmail(String email) throws ErrorUpdatingUserException {
        this.userController.updateUserEmail(this.userID, email);
    }

    public void updateLoggedUserHeight(double height) throws ErrorUpdatingUserException {
        this.userController.updateUserHeight(this.userID, height);
    }

    public void updateLoggedUserWeight(double weight) throws ErrorUpdatingUserException {
        this.userController.updateUserWeight(this.userID, weight);
    }

    public void updateLoggedUserHearFreq(int hearFreq) throws ErrorUpdatingUserException {
        this.userController.updateUserHeartFrequency(this.userID, hearFreq);
    }

    public void addRowingToLoggedUser (
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int heartRate,
            int distance,
            int personsOnBoard,
            boolean rowAgainstTide
    ) throws InvalidValueException, ErrorAddingActivityException {
        if(distance <= 0 || personsOnBoard <= 0 || heartRate <= 0) {
            throw new InvalidValueException("The system doesn't support non-positive values.");
        }

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addRowing(activityID, name, begin, end, this.userID, 0, heartRate, distance, personsOnBoard, rowAgainstTide);
    }

    public void addSkatingToLoggedUser(
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int heartRate,
            int distance,
            double skateWeight,
            boolean freestyle
    ) throws InvalidValueException, ErrorAddingActivityException {
        if(distance <= 0 || skateWeight <= 0 || heartRate <= 0) {
            throw new InvalidValueException("The system doesn't support non-positive values.");
        }

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addSkating(activityID, name, begin, end, this.userID, 0, heartRate, distance, skateWeight, freestyle);
    }

    public void addTrackRunningToLoggedUser(
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int heartRate,
            int distance,
            boolean hurdleJump
    ) throws InvalidValueException, ErrorAddingActivityException {
        if(distance <= 0 || heartRate <= 0) {
            throw new InvalidValueException("The system doesn't support non-positive values.");
        }

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addTrackRunning(activityID, name, begin, end, this.userID, 0, heartRate, distance, hurdleJump);
    }

    public void addMountainBikingToLoggedUser(
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int heartRate,
            int distance,
            int altimetry,
            boolean bigTires
    ) throws InvalidValueException, ErrorAddingActivityException {
        if(distance <= 0 || altimetry <= 0 || heartRate <= 0) {
            throw new InvalidValueException("The system doesn't support non-positive values.");
        }

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addMountainBiking(activityID, name, begin, end, this.userID, 0, heartRate, distance, altimetry, bigTires);
    }

    public void addRoadCyclingToLoggedUser(
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int heartRate,
            int distance,
            int altimetry,
            boolean windAgainst
    ) throws InvalidValueException, ErrorAddingActivityException {
        if(distance <= 0 || altimetry <= 0 || heartRate <= 0) {
            throw new InvalidValueException("The system doesn't support non-positive values.");
        }

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addRoadCycling(activityID, name, begin, end, this.userID, 0, heartRate, distance, altimetry, windAgainst);
    }

    public void addRoadRunningToLoggedUser(
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int heartRate,
            int distance,
            int altimetry,
            boolean windAgainst
    ) throws InvalidValueException, ErrorAddingActivityException {
        if(distance <= 0 || altimetry <= 0 || heartRate <= 0) {
            throw new InvalidValueException("The system doesn't support non-positive values.");
        }

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addRoadRunning(activityID, name, begin, end, this.userID, 0, heartRate, distance, altimetry, windAgainst);
    }

    public void addTrailRunningToLoggedUser(
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int heartRate,
            int distance,
            int altimetry,
            boolean wetFloor
    ) throws InvalidValueException, ErrorAddingActivityException {
        if(distance <= 0 || altimetry <= 0 || heartRate <= 0) {
            throw new InvalidValueException("The system doesn't support non-positive values.");
        }

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addTrailRunning(activityID, name, begin, end, this.userID, 0, heartRate, distance, altimetry, wetFloor);
    }

    public void addAbdominalExercisesToLoggedUser(
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int heartRate,
            int nRepetitions,
            boolean helped
    ) throws InvalidValueException, ErrorAddingActivityException {
        if(nRepetitions <= 0 || heartRate <= 0) {
            throw new InvalidValueException("The system doesn't support non-positive values.");
        }

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addAbdominalExercises(activityID, name, begin, end, this.userID, 0, heartRate, nRepetitions, helped);
    }

    public void addPushUpsToLoggedUser(
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int heartRate,
            int nRepetitions,
            boolean diamondIntercalated
    ) throws InvalidValueException, ErrorAddingActivityException {
        if(nRepetitions <= 0 || heartRate <= 0) {
            throw new InvalidValueException("The system doesn't support non-positive values.");
        }

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addPushUps(activityID, name, begin, end, this.userID, 0, heartRate, nRepetitions, diamondIntercalated);
    }

    public void addStretchingToLoggedUser(
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int heartRate,
            int nRepetitions,
            boolean helped
    ) throws InvalidValueException, ErrorAddingActivityException {
        if(nRepetitions <= 0 || heartRate <= 0) {
            throw new InvalidValueException("The system doesn't support non-positive values.");
        }

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addStretching(activityID, name, begin, end, this.userID, 0, heartRate, nRepetitions, helped);
    }

    public void addLegExtensionToLoggedUser(
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int heartRate,
            int nRepetitions,
            int weight,
            int chairAngle
    ) throws InvalidValueException, ErrorAddingActivityException {
        if(nRepetitions <= 0 || weight <= 0 || heartRate <= 0 || chairAngle <= 0) {
            throw new InvalidValueException("The system doesn't support non-positive values.");
        }

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addLegExtension(activityID, name, begin, end, this.userID, 0, heartRate, nRepetitions, weight, chairAngle);
    }

    public void addWeightLiftingToLoggedUser(
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int heartRate,
            int nRepetitions,
            int weight,
            boolean helped
    ) throws InvalidValueException, ErrorAddingActivityException {
        if(nRepetitions <= 0 || weight <= 0 || heartRate <= 0) {
            throw new InvalidValueException("The system doesn't support non-positive values.");
        }

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addWeightLifting(activityID, name, begin, end, this.userID, 0, heartRate, nRepetitions, weight, helped);
    }

    //TODO: Adicionar planos de treino

    public void deleteAccount() throws ErrorRemovingUserException {
        this.userController.removeUser(this.userID);
        this.setUserID(-1);
    }

    //NOTE: Esta função seria necessária caso não se guarde o UserID mas sim o user... nesse caso
    //      seria para atualizar o user completo... não me parece que faça falta though.
    public void saveChanges() {
    }
    //endregion

    //region AdminFunctions
    public void addCasualUser(
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
        if(userController.isUsernameAvailable(username)){
            int id = idManager.generateUniqueUserID();
            userController.addCasualUser(id, name, username, birthdate, address, email, sex, height, weight, heartFreq);
        } else {
            System.err.println("Username " + username + " already exists");
        }
    }

    public void addAmateurUser(
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
        if(userController.isUsernameAvailable(username)){
            int id = idManager.generateUniqueUserID();
            userController.addAmateurUser(id, name, username, birthdate, address, email, sex, height, weight, heartFreq);
        } else {
            System.err.println("Username " + username + " already exists");
        }
    }

    public void addProfessionalUser(
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
        if(userController.isUsernameAvailable(username)){
            int id = idManager.generateUniqueUserID();
            userController.addProfessionalUser(id, name, username, birthdate, address, email, sex, height, weight, heartFreq);
        } else {
            System.err.println("Username " + username + " already exists");
        }
    }

    public void removeUser(int id) throws ErrorRemovingUserException, UserDoesNotExistsException {
        if(this.userController.userWithIdExits(id)){
            userController.removeUser(id);
            idManager.removeUserIdEntry(id);
        } else {
            throw new UserDoesNotExistsException("User with id:" + id + " does not exist");
        }
    }
    //endregion


    //region stats
    public void loadStats(){

    }

    /*This function returns the userId of the user with most calories burned*/
    public int UserWithMostCaloriesBurned(LocalDateTime from){
        int burnedCalories = -1;
        int finalUserID = -1;

        List<User> users = this.userController.getUsers().getUsersList();
        for(User user : users){
            int newBurnedCalories = 0;
            for(Activity act : user.getActivityController().getActivities().getActivities().values()){
                if(act.getBegin().isAfter(from) && act.getEnd().isBefore(this.systemDate)){
                    newBurnedCalories += user.calculateBurnedCalories(act.getId());
                }
            }
            if(newBurnedCalories > burnedCalories){
                burnedCalories = newBurnedCalories;
                finalUserID = user.getId();
            }
        }
        return finalUserID;
    }

    public int UserWithMostActivitiesCompleted(LocalDateTime from){
        int nActivities = -1;
        int finalUserID = -1;
        List<User> users = this.userController.getUsers().getUsersList();
        for(User user : users){
            int newNActivities = 0;
            for(Activity act : user.getActivityController().getActivities().getActivities().values()){
                if(act.getBegin().isAfter(from) && act.getEnd().isAfter(this.systemDate)){
                    newNActivities++;
                }
            }
            if(newNActivities > nActivities){
                nActivities = newNActivities;
                finalUserID = user.getId();
            }
        }
        return finalUserID;
    }


    public String mostCommunActivity() {
        Map<String, Integer> nActivitiesByType = new HashMap<>();
        List<User> users = this.userController.getUsers().getUsersList();

        for (User user : users) {
            for (Activity act : user.getActivityController().getActivities().getActivities().values()) {
                String activityType = act.getClass().getSimpleName();
                nActivitiesByType.put(activityType, nActivitiesByType.getOrDefault(activityType, 0) + 1);
                System.out.println(activityType);
                System.out.println(user.getUsername() + "--" + nActivitiesByType.values());
            }
        }

        String mostCommonActivityType = "";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : nActivitiesByType.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommonActivityType = entry.getKey();
            }
        }

        return mostCommonActivityType;
    }

    public int distanceDoneByUser(int userID, LocalDateTime from){
        int distance = 0;
        User user = this.userController.getUsers().getUserWithId(userID);
        for(Activity act : user.getActivityController().getActivities().getActivities().values()){
            if(act.getBegin().isAfter(from) &&
                    act.getEnd().isAfter(this.systemDate) &&
                    act.getClass().getSuperclass().getSimpleName().equals("DistanceAct")){
                DistanceAct myAct = (DistanceAct) act;
                distance += myAct.getDistance();
            } else if(act.getBegin().isAfter(from) &&
                    act.getEnd().isAfter(this.systemDate) &&
                    act.getClass().getSuperclass().getSimpleName().equals("DistanceAndAltimetryAct")) {
                DistanceAndAltimetryAct myAct = (DistanceAndAltimetryAct) act;
                distance += myAct.getDistance();
            }
        }
        return distance;
    }

    public int altimetryDoneByUser(int userID, LocalDateTime from){
        int altimetry = 0;
        User user = this.userController.getUsers().getUserWithId(userID);
        for(Activity act : user.getActivityController().getActivities().getActivities().values()){
            if(act.getBegin().isAfter(from) &&
                    act.getEnd().isAfter(this.systemDate) &&
                    act.getClass().getSuperclass().getSimpleName().equals("DistanceAndAltimetryAct")){
                DistanceAndAltimetryAct myAct = (DistanceAndAltimetryAct) act;
                altimetry += myAct.getAltimetry();
            }
        }
        return altimetry;
    }

    public List<Activity> getUsersActivities(int userID){
        return this.userController.getUsers().getUserWithId(userID).getActivityController().getActivities().getActivities().values().stream().toList();
    }
    //endregion

    public void saveState(String fileName){
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void loadState(String fileName) {
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            JosefinFitnessApp app = (JosefinFitnessApp) in.readObject();
            in.close();
            fileIn.close();
            this.userController = app.getUserController();
            this.idManager = app.getIdManager();
            this.systemDate = app.getSystemDate();
            this.userID = app.getUserID();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JosefinFitnessApp that = (JosefinFitnessApp) o;
        return this.getUserController().equals(that.getUserController()) &&
                this.getIdManager().equals(that.getIdManager()) &&
                this.getStats().equals(that.getStats());
    }

    public JosefinFitnessApp clone(){
        return new JosefinFitnessApp(this);
    }
}
