package josefinFA;

import activities.Activity;
import activities.DistanceAct;
import activities.DistanceAndAltimetryAct;
import exceptions.*;
import users.User;
import users.UserController;
import utils.IDManager;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JosefinFitnessApp {
    private int userID;
    private LocalDate systemDate;
    private UserController userController;
    private IDManager idManager;
    private Stats stats;

    //region constructors
    public JosefinFitnessApp(){
        this.userID = -1;
        this.systemDate = LocalDate.now();
        this.userController = new UserController();
        this.idManager = new IDManager();
        this.stats = new Stats();
    }

    public JosefinFitnessApp(int userID, LocalDate date, UserController userController, IDManager idManager, Stats stats) {
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

    public LocalDate getSystemDate() {
        return this.systemDate;
    }

    public void setSystemDate(LocalDate systemDate) {
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

    public void addRowingToLoggedUser(String name, LocalDate begin, LocalDate end, int heartRate){
        int activityID = this.idManager.generateUniqueActivityID();
        this.userController.addRowing();
    }

    public void addSkatingToLoggedUser(){
        int activityID = this.idManager.generateUniqueActivityID();
    }

    public void addTrackRunningToLoggedUser(){
        int activityID = this.idManager.generateUniqueActivityID();
    }

    public void addMountainBikingToLoggedUser(){
        int activityID = this.idManager.generateUniqueActivityID();
    }

    public void addRoadCyclingToLoggedUser(){
        int activityID = this.idManager.generateUniqueActivityID();
    }

    public void addTrailRunningToLoggedUser(){
        int activityID = this.idManager.generateUniqueActivityID();
    }

    public void addAbdominalExercisesToLoggedUser(){
        int activityID = this.idManager.generateUniqueActivityID();
    }

    public void addPushUpsToLoggedUser(){
        int activityID = this.idManager.generateUniqueActivityID();
    }

    public void addStretchingToLoggedUser(){
        int activityID = this.idManager.generateUniqueActivityID();
    }

    public void addLegExtensionToLoggedUser(){
        int activityID = this.idManager.generateUniqueActivityID();
    }

    public void addWeightLiftingToLoggedUser(){
        int activityID = this.idManager.generateUniqueActivityID();
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
    public int UserWithMostCaloriesBurned(LocalDate from){
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

    public int UserWithMostActivitiesCompleted(LocalDate from){
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

    public int distanceDoneByUser(int userID, LocalDate from){
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

    public int altimetryDoneByUser(int userID, LocalDate from){
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
