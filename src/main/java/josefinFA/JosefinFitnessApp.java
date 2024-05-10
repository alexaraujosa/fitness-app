package josefinFA;

import activities.Activity;
import exceptions.*;
import users.User;
import users.UserController;
import utils.IDManager;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class JosefinFitnessApp implements Serializable {
    private int userID;
    private LocalDateTime systemDate;
    private UserController userController;
    private IDManager idManager;
    private Stats stats;

    //region Constructors
    public JosefinFitnessApp(){
        this.userID = -1;
        this.systemDate = LocalDateTime.now();
        this.userController = new UserController();
        this.idManager = new IDManager();
        this.stats = new Stats();
    }

    public JosefinFitnessApp(
            int userID,
            LocalDateTime date,
            UserController userController,
            IDManager idManager,
            Stats stats
    ) {
        this.userID = userID;
        this.systemDate = date;
        this.userController = userController.clone();
        this.idManager = idManager.clone();
        this.stats = stats.clone();
    }

    public JosefinFitnessApp(
            JosefinFitnessApp app
    ){
        this.userID = app.getUserID();
        this.systemDate = app.getSystemDate();
        this.userController = app.getUserController();
        this.idManager = app.getIdManager();
        this.stats = app.getStats();
    }
    //endregion

    //region Getters And Setters
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

    //region Logged User Methods
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

    //TODO: Fazer testes para isto tb
    //TODO: Tratar erros nos testes
    public void signupCasualUser(
            String name,
            String username,
            LocalDate birthdate,
            String address,
            String email,
            boolean sex,
            double height,
            double weight,
            int heartFreq
    ) throws UsernameAlreadyExistsException {
        if(userController.isUsernameAvailable(username)){
            int id = idManager.generateUniqueUserID();
            userController.addCasualUser(id, name, username, birthdate, address, email, sex, height, weight, heartFreq);
            this.userID = id;
        } else {
            throw new UsernameAlreadyExistsException("Username " + username + " already exists");
        }
    }

    public void signupAmateurUser(
            String name,
            String username,
            LocalDate birthdate,
            String address,
            String email,
            boolean sex,
            double height,
            double weight,
            int heartFreq
    ) throws UsernameAlreadyExistsException {
        if(userController.isUsernameAvailable(username)){
            int id = idManager.generateUniqueUserID();
            userController.addAmateurUser(id, name, username, birthdate, address, email, sex, height, weight, heartFreq);
            this.userID = id;
        } else {
            throw new UsernameAlreadyExistsException("Username " + username + " already exists");
        }
    }

    public void signupProfessionalUser(
            String name,
            String username,
            LocalDate birthdate,
            String address,
            String email,
            boolean sex,
            double height,
            double weight,
            int heartFreq
    ) throws UsernameAlreadyExistsException {
        if(userController.isUsernameAvailable(username)){
            int id = idManager.generateUniqueUserID();
            userController.addProfessionalUser(id, name, username, birthdate, address, email, sex, height, weight, heartFreq);
            this.userID = id;
        } else {
            throw new UsernameAlreadyExistsException("Username " + username + " already exists");
        }
    }

    public void logout(){
        this.userID = -1;
    }

    public void deleteAccount() throws ErrorRemovingUserException {
        this.userController.removeUser(this.userID);
        this.setUserID(-1);
    }

    public String getLoggedUserInfo(){
        return this.userController.getUsers().getUserWithId(this.userID).toString();
    }
    //endregion

    //region User Methods
    public void updateUserName(
            int id,
            String name
    ) throws ErrorUpdatingUserException {
        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        this.userController.updateUserName(_id, name);
    }

    public void updateUserUsername(
            int id,
            String username
    ) throws UsernameAlreadyExistsException, ErrorUpdatingUserException {
        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        this.userController.updateUserUsername(_id, username);
    }

    public void updateUserBirthdate(
            int id,
            LocalDate birthdate) throws ErrorUpdatingUserException {
        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        this.userController.updateUserBirthdate(_id, birthdate);
    }

    public void updateUserAddress(
            int id,
            String address
    ) throws ErrorUpdatingUserException {
        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        this.userController.updateUserAddress(_id, address);
    }

    public void updateUserEmail(
            int id,
            String email
    ) throws ErrorUpdatingUserException {
        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        this.userController.updateUserEmail(_id, email);
    }

    public void updateUserHeight(
            int id,
            double height
    ) throws ErrorUpdatingUserException {
        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        this.userController.updateUserHeight(_id, height);
    }

    public void updateUserWeight(
            int id,
            double weight
    ) throws ErrorUpdatingUserException {
        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        this.userController.updateUserWeight(_id, weight);
    }

    public void updateUserHearFreq(
            int id,
            int hearFreq
    ) throws ErrorUpdatingUserException {
        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        this.userController.updateUserHeartFrequency(_id, hearFreq);
    }
    //endregion

    //region Activity Methods
    public void addRowingToUser(
            int id,
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

        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addRowing(activityID, name, begin, end, _id, 0, heartRate, distance, personsOnBoard, rowAgainstTide);
    }

    public void addSkatingToUser(
            int id,
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

        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addSkating(activityID, name, begin, end, _id, 0, heartRate, distance, skateWeight, freestyle);
    }

    public void addTrackRunningToUser(
            int id,
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

        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addTrackRunning(activityID, name, begin, end, _id, 0, heartRate, distance, hurdleJump);
    }

    public void addMountainBikingToUser(
            int id,
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

        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addMountainBiking(activityID, name, begin, end, _id, 0, heartRate, distance, altimetry, bigTires);
    }

    public void addRoadCyclingToUser(
            int id,
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

        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addRoadCycling(activityID, name, begin, end, _id, 0, heartRate, distance, altimetry, windAgainst);
    }

    public void addRoadRunningToUser(
            int id,
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

        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addRoadRunning(activityID, name, begin, end, _id, 0, heartRate, distance, altimetry, windAgainst);
    }

    public void addTrailRunningToUser(
            int id,
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

        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addTrailRunning(activityID, name, begin, end, _id, 0, heartRate, distance, altimetry, wetFloor);
    }

    public void addAbdominalExercisesToUser(
            int id,
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

        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addAbdominalExercises(activityID, name, begin, end, _id, 0, heartRate, nRepetitions, helped);
    }

    public void addPushUpsToUser(
            int id,
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

        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addPushUps(activityID, name, begin, end, _id, 0, heartRate, nRepetitions, diamondIntercalated);
    }

    public void addStretchingToUser(
            int id,
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

        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addStretching(activityID, name, begin, end, _id, 0, heartRate, nRepetitions, helped);
    }

    public void addLegExtensionToUser(
            int id,
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

        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addLegExtension(activityID, name, begin, end, _id, 0, heartRate, nRepetitions, weight, chairAngle);
    }

    public void addWeightLiftingToUser(
            int id,
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

        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addWeightLifting(activityID, name, begin, end, _id, 0, heartRate, nRepetitions, weight, helped);
    }
    //endregion

    //region Training Plan Methods
    public void addManualTrainingPlan(
            int id,
            List<Activity> activities,
            LocalDate doDate,
            boolean[] repeat
    ) throws InvalidValueException, ErrorHardActivityCloseException {
        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        int idTrainingPlan = this.idManager.generateUniqueTrainingPlanID();
        this.userController.addManualTrainingPlan(idTrainingPlan, _id, activities, doDate, repeat);
    }

    public void addAutomaticTrainingPlan(
            int id,
            boolean wantsHard,
            int maximumActivityPerDay,
            LocalDate doDate,
            boolean[] repeat,
            int minimumCaloriesConsumption,
            int planType
    ) throws ErrorHardActivityCloseException, InvalidValueException {
        // Admin verifier
        int _id = (id == -1) ? this.userID : id;

        int idTrainingPlan = this.idManager.generateUniqueTrainingPlanID();
        int[] idsActivity = new int[maximumActivityPerDay];
        for(int i = 0; i < maximumActivityPerDay; i++) {
            idsActivity[i] = this.idManager.generateUniqueActivityID();
        }
        this.userController.addAutomaticTrainingPlan(idTrainingPlan, _id, idsActivity, wantsHard, maximumActivityPerDay, doDate, repeat, minimumCaloriesConsumption, planType);
    }
    //endregion Methods

    //region Administrator Methods
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
    ) throws UsernameAlreadyExistsException {
        if(userController.isUsernameAvailable(username)){
            int id = idManager.generateUniqueUserID();
            userController.addCasualUser(id, name, username, birthdate, address, email, sex, height, weight, heartFreq);
        } else {
            throw new UsernameAlreadyExistsException("Username " + username + " already exists");
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
    ) throws UsernameAlreadyExistsException {
        if(userController.isUsernameAvailable(username)){
            int id = idManager.generateUniqueUserID();
            userController.addAmateurUser(id, name, username, birthdate, address, email, sex, height, weight, heartFreq);
        } else {
            throw new UsernameAlreadyExistsException("Username " + username + " already exists");
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
    ) throws UsernameAlreadyExistsException {
        if(userController.isUsernameAvailable(username)){
            int id = idManager.generateUniqueUserID();
            userController.addProfessionalUser(id, name, username, birthdate, address, email, sex, height, weight, heartFreq);
        } else {
            throw new UsernameAlreadyExistsException("Username " + username + " already exists");
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

    //region Stats
    //Chamar sempre esta função quando entras na view das stats... ela atualiza as fixas e clona o estado da app para lá
    public void loadStats(){
        this.stats.setUserController(this.userController.clone());
        this.stats.setSystemDate(this.systemDate);
        this.stats.updateAllTimeValues();
    }

    public User userWithMostCaloriesBurned(LocalDateTime from){
        return this.stats.userWithMostCaloriesBurned(from);
    }

    public User userWithMostActivitiesCompleted(LocalDateTime from){
        return this.stats.userWithMostActivitiesCompleted(from);
    }

    public String mostCommonActivity() {
        return this.stats.mostCommunActivity();
    }

    public int distanceDoneByUser(int userID, LocalDateTime from){
        return this.stats.distanceDoneByUser(userID, from);
    }

    public int altimetryDoneByUser(int userID, LocalDateTime from){
        return this.stats.altimetryDoneByUser(userID, from);
    }

    public List<Activity> getUsersActivities(int userID) {
        return this.userController.getUsers().getUserWithId(userID).getActivityController().getActivities().getActivities().values().stream().toList();
    }

    //endregion

    // region State Management
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
    //endregion

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
