package users;

import exceptions.ErrorRemovingUserException;
import exceptions.ErrorUpdatingUserException;
import exceptions.UsernameAlreadyExistsException;

import java.time.LocalDate;

public class UserController {
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
    public void addRowing(){

    }

    public void addSkating(){

    }

    public void addTrackRunning(){

    }

    public void addMountainBiking(){

    }

    public void addRoadCycling(){

    }

    public void addTrailRunning(){

    }

    public void addAbdominalExercises(){

    }

    public void addPushUps(){

    }

    public void addStretching(){

    }

    public void addLegExtension(){

    }

    public void addWeightLifting(){

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
