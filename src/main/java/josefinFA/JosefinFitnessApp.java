package josefinFA;

import exceptions.ErrorUpdatingUserException;
import exceptions.UserDoesNotExistsException;
import exceptions.UsernameAlreadyExistsException;
import users.CasualUser;
import users.User;
import users.UserController;
import utils.IDManager;

import java.io.IOException;
import java.time.LocalDate;

public class JosefinFitnessApp {
    private int userID;
    private UserController userController;
    private IDManager idManager;
    private Stats stats;

    //region constructors
    public JosefinFitnessApp(){
        this.userID = -1;
        this.userController = new UserController();
        this.idManager = new IDManager();
        this.stats = new Stats();
    }

    public JosefinFitnessApp(int userID, UserController userController, IDManager idManager, Stats stats) {
        this.userID = userID;
        this.userController = userController.clone();
        this.idManager = idManager.clone();
        this.stats = stats.clone();
    }

    public JosefinFitnessApp(JosefinFitnessApp app){
        this.userID = app.getUserID();
        this.userController = app.getUserController();
        this.idManager = app.getIdManager();
        this.stats = app.getStats();
    }
    //endregion

    //region getters&Setters
    private int getUserID() {
        return this.userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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
    public void login(String username) {
        if(this.userController.userWithUsernameExists(username)) {
            this.userID = this.userController.getUsernameID(username);
        } else {
            System.err.println("User " + username + " does not exist");
        }
    }

    public void logout(){
        this.userID = -1;
    }

    public String getLoggedUserInfo(){
        return this.userController.getUsers().getUserWithId(this.userID).toString();
    }

    public void updateLoggedUserName(String name) throws ErrorUpdatingUserException {
        try{
            this.userController.updateUserName(this.userID, name);
        } catch (ErrorUpdatingUserException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateLoggedUserUsername(String username) throws UsernameAlreadyExistsException, ErrorUpdatingUserException {
        try{
            this.userController.updateUserUsername(this.userID, username);
        } catch (ErrorUpdatingUserException | UsernameAlreadyExistsException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateLoggedUserBirtdate(LocalDate birtdate) throws ErrorUpdatingUserException {
        try {
            this.userController.updateUserBirthdate(this.userID, birtdate);
        } catch (ErrorUpdatingUserException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateLoggedUserAddress(String address) throws ErrorUpdatingUserException {
        try {
            this.userController.updateUserAddress(this.userID, address);
        } catch (ErrorUpdatingUserException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateLoggedUserEmail(String email) throws ErrorUpdatingUserException {
        try {
            this.userController.updateUserEmail(this.userID, email);
        } catch (ErrorUpdatingUserException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateLoggedUserHeight(int height) throws ErrorUpdatingUserException {
        try {
            this.userController.updateUserHeight(this.userID, height);
        } catch (ErrorUpdatingUserException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateLoggedUserWeight(int weight) throws ErrorUpdatingUserException {
        try {
            this.userController.updateUserWeight(this.userID, weight);
        } catch (ErrorUpdatingUserException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateLoggedUserHearFreq(int hearFreq) throws ErrorUpdatingUserException {
        try {
            this.userController.updateUserHeartFrequency(this.userID, hearFreq);
        } catch (ErrorUpdatingUserException e) {
            System.err.println(e.getMessage());
        }
    }

    //NOTE: Ver melhor como vai ser a criação de atividades
    public void addRowingToLoggedUser(int id, String name, LocalDate begin, LocalDate end, int heartRate){
        this.userController.addRowing();
    }

    public void addSkatingToLoggedUser(){

    }

    public void addTrackRunningToLoggedUser(){

    }

    public void addMountainBikingToLoggedUser(){

    }

    public void addRoadCyclingToLoggedUser(){

    }

    public void addTrailRunningToLoggedUser(){

    }

    public void addAbdominalExercisesToLoggedUser(){

    }

    public void addPushUpsToLoggedUser(){

    }

    public void addStretchingToLoggedUser(){

    }

    public void addLegExtensionToLoggedUser(){

    }

    public void addWeightLiftingToLoggedUser(){

    }

    //TODO: Adicionar planos de treino
    //TODO: Delete Account

    //NOTE: Esta função seria necessária caso não guarda-se o UserID mas sim o user... nesse caso
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

    public boolean removeUser(int id){
        if(this.userController.userWithIdExits(id)){
            userController.removeUser(id);
            idManager.removeUserIdEntry(id);
            return true;
        } else {
            System.out.println("O user com esse id não existe");;
            return false;
        }
    }
    //endregion

    //TODO: Add stats functions

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
