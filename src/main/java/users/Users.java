package users;

import exceptions.ErrorRemovingUserException;
import exceptions.ErrorUpdatingUserException;
import exceptions.UsernameAlreadyExistsException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 *  The Users class represents a collection of users with unique usernames and IDs.
 *  It provides methods to manage users such as adding, updating and removing as well as checking
 *  for users information, users existence and username availability.
 */
public class Users implements Serializable {
    private final List<User> usersList;
    private final Map<Integer,User> usersById;
    private final Map<String,User> usersByUsername;

    //region Constructors
    public Users(){
        this.usersList = new ArrayList<>();
        this.usersById = new HashMap<>();
        this.usersByUsername = new HashMap<>();
    }

    public Users(List<User> users){
        this.usersList = new ArrayList<>(users);
        this.usersById = this.generateUsersById();
        this.usersByUsername = this.generateUsersByUsername();
    }

    /**
     * Constructs a clone of the Users object.
     * @param users Users object to be cloned
     */
    public Users(Users users){
        this.usersList = new ArrayList<>(users.getUsersList());
        this.usersById = this.generateUsersById();
        this.usersByUsername = this.generateUsersByUsername();
    }
    //endregion

    //region Getters
    public List<User> getUsersList() {
        return new ArrayList<>(this.usersList);
    }

    /**
     * Returns a copy of the usernames list.
     * @return Map of usernames
     */
    public Map<String, User> getUsersByUsername() {
        return new HashMap<>(this.usersByUsername);
    }

    /**
     * Returns a copy of the usersById map.
     * @return Map of users ny their ID
     */
    public Map<Integer, User> getUsersById() {
        return new HashMap<>(this.usersById);
    }
    //endregion

    public User getUserWithId(int id){
        return this.usersById.get(id).clone();
    }

    public User getUserWithUsername(String username){
        return this.usersByUsername.get(username).clone();
    }

    public boolean isUsernameAvailable(String username) {
        return this.usersByUsername.keySet().stream().noneMatch(u -> u.equals(username));
    }

    /**
     * Checks if a user with the given ID exists.
     * @param id User ID
     * @return true if a user with the given ID exists, otherwise false
     */
    public boolean containsUser(int id){
        return this.usersById.containsKey(id);
    }

    public boolean containsUser(String username){
        return this.usersByUsername.containsKey(username);
    }

    //region UpdateUserInfo
    public void updateUser(User user) {
        User temp = this.getUserWithId(user.getId());
        this.usersList.remove(temp);

        this.addUser(user);
    }

    public void updateUserName(int id, String name){
        this.usersById.get(id).setName(name);
    }

    public void updateUserUsername(int id, String username) throws UsernameAlreadyExistsException {
        if(!isUsernameAvailable(username)){
            throw new UsernameAlreadyExistsException("A User with that username already Exists!");
        }
        User user = this.usersById.get(id);
        this.usersByUsername.remove(user.getUsername());
        user.setUsername(username);
        this.usersByUsername.put(username, user);
    }

    public void updateUserUsername(String currentUsername, String newUsername) throws UsernameAlreadyExistsException {
        if(!isUsernameAvailable(newUsername)){
            throw new UsernameAlreadyExistsException("A User with that username already Exists!");
        }
        User user = this.usersByUsername.get(currentUsername);
        this.usersByUsername.remove(user.getUsername());
        user.setUsername(newUsername);
        this.usersByUsername.put(newUsername, user);
    }

    public void updateUserBirthdate(int id, LocalDate birthdate){
        this.usersById.get(id).setBirthdate(birthdate);
    }

    public void updateUserAddress(int id, String address){
        this.usersById.get(id).setAddress(address);
    }

    public void updateUserEmail(int id, String email) {
        this.usersById.get(id).setEmail(email);
    }

    public void updateUserHeight(int id, double height){
        this.usersById.get(id).setHeight(height);
    }

    public void updateUserWeight(int id, double weight){
        this.usersById.get(id).setWeight(weight);
    }

    public void updateUserHeartFrequency(int id, int heartFrequency){
        this.usersById.get(id).setHeartFreq(heartFrequency);
    }
    //endregion

    /**
     * Adds a new user to the collection.
     * @param user User object to add
     */
    public void addUser(User user) {
        User newUser = user.clone();
        this.usersList.add(newUser);
        this.usersByUsername.put(newUser.getUsername(), newUser);
        this.usersById.put(newUser.getId(), newUser);
    }

    /**
     * Removes a user from the collection by ID.
     * @param id ID of the user to remove
     */
    public void removeUser(int id) throws ErrorRemovingUserException {
        User user = usersById.get(id);
        usersByUsername.remove(user.getUsername());
        usersById.remove(id);
        usersList.remove(user);

        if(this.usersList.contains(user)){
            throw new ErrorRemovingUserException("The user was not removed correctly!");
        }
    }

    private Map<Integer,User> generateUsersById(){
        Map<Integer,User> usersById = new HashMap<>();
        for(User user : this.usersList){
            usersById.put(user.getId(), user);
        }
        return usersById;
    };

    private Map<String, User> generateUsersByUsername(){
        Map<String, User> usersByUsername = new HashMap<>();
        for(User user : this.usersList){
            usersByUsername.put(user.getUsername(), user);
        }
        return usersByUsername;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--{ USERS LIST }--\n");
        this.usersList.forEach(user -> sb.append(user.toString()));
        sb.append("--{ END USER LIST }--\n");
        return sb.toString();
    }

    /**
     * Overrides the equals method to compare Users objects for equality.
     * @param o Object to compare
     * @return true if the objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return this.getUsersByUsername().equals(users.getUsersByUsername()) &&
                this.getUsersById().equals(users.getUsersById());
    }

    /**
     * Creates a clone of the Users object.
     * @return Cloned Users object
     */
    public Users clone(){
        return new Users(this);
    }
}
