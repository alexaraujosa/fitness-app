package users;

import exceptions.ErrorRemovingUserException;
import exceptions.ErrorUpdatingUserException;
import exceptions.UsernameAlreadyExistsException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * Represents a collection of users with unique usernames and IDs. Provides methods for managing users such as adding,
 * updating, and removing, as well as checking for user information, existence, and username availability.
 */
public class Users implements Serializable {
    private final List<User> usersList;
    private final Map<Integer,User> usersById;
    private final Map<String,User> usersByUsername;

    //region Constructors
    /**
     * Constructs an empty Users object.
     */
    public Users(){
        this.usersList = new ArrayList<>();
        this.usersById = new HashMap<>();
        this.usersByUsername = new HashMap<>();
    }

    /**
     * Constructs a Users object with the specified list of users.
     *
     * @param users The list of users.
     */
    public Users(List<User> users){
        this.usersList = new ArrayList<>(users);
        this.usersById = this.generateUsersById();
        this.usersByUsername = this.generateUsersByUsername();
    }

    /**
     * Constructs a clone of the Users object.
     *
     * @param users The Users object to be cloned.
     */
    public Users(Users users){
        this.usersList = new ArrayList<>(users.getUsersList());
        this.usersById = this.generateUsersById();
        this.usersByUsername = this.generateUsersByUsername();
    }
    //endregion

    //region Getters
    /**
     * Retrieves a copy of the list of users.
     *
     * @return A new list containing copies of all users in the collection.
     */
    public List<User> getUsersList() {
        return new ArrayList<>(this.usersList);
    }

    /**
     * Retrieves a copy of the mapping of usernames to users.
     *
     * @return A new map containing copies of all username-user mappings in the collection.
     */
    public Map<String, User> getUsersByUsername() {
        return new HashMap<>(this.usersByUsername);
    }

    /**
     * Retrieves a copy of the mapping of user IDs to users.
     *
     * @return A new map containing copies of all user ID-user mappings in the collection.
     */
    public Map<Integer, User> getUsersById() {
        return new HashMap<>(this.usersById);
    }
    //endregion

    //region Verifiers
    /**
     * Retrieves a user with the specified ID.
     *
     * @param id The ID of the user.
     * @return The user with the specified ID.
     */
    public User getUserWithId(int id){
        return this.usersById.get(id).clone();
    }

    /**
     * Retrieves a user with the specified username.
     *
     * @param username The username of the user.
     * @return The user with the specified username.
     */
    public User getUserWithUsername(String username){
        return this.usersByUsername.get(username).clone();
    }

    /**
     * Checks if the given username is available (not already used by another user).
     *
     * @param username The username to check
     * @return true if the username is available, false otherwise
     */
    public boolean isUsernameAvailable(String username) {
        return this.usersByUsername.keySet().stream().noneMatch(u -> u.equals(username));
    }

    /**
     * Checks if a user with the given ID exists in the collection.
     *
     * @param id The ID of the user to check
     * @return true if a user with the given ID exists, otherwise false
     */
    public boolean containsUser(int id){
        return this.usersById.containsKey(id);
    }

    /**
     * Checks if a user with the given username exists in the collection.
     *
     * @param username The username of the user to check
     * @return true if a user with the given username exists, otherwise false
     */
    public boolean containsUser(String username){
        return this.usersByUsername.containsKey(username);
    }
    //endregion

    //region UpdateUserInfo
    /**
     * Updates information of an existing user with the provided User object.
     * This method removes the existing user from the collection, adds the updated user, and maintains the integrity of internal mappings.
     *
     * @param user The User object containing updated information
     */
    public void updateUser(User user) {
        User temp = this.getUserWithId(user.getId());
        this.usersList.remove(temp);

        this.addUser(user);
    }

    /**
     * Updates the name of the user with the given ID.
     *
     * @param id The ID of the user whose name is to be updated
     * @param name The new name to set
     */
    public void updateUserName(int id, String name){
        this.usersById.get(id).setName(name);
    }

    /**
     * Updates the username of the user with the given ID.
     *
     * @param id The ID of the user whose username is to be updated
     * @param username The new username to set
     * @throws UsernameAlreadyExistsException if the new username is already in use by another user
     */
    public void updateUserUsername(int id, String username) throws UsernameAlreadyExistsException {
        if(!isUsernameAvailable(username)){
            throw new UsernameAlreadyExistsException("A User with that username already Exists!");
        }
        User user = this.usersById.get(id);
        this.usersByUsername.remove(user.getUsername());
        user.setUsername(username);
        this.usersByUsername.put(username, user);
    }

    /**
     * Updates the username of the user with the given current username.
     *
     * @param currentUsername The current username of the user
     * @param newUsername The new username to set
     * @throws UsernameAlreadyExistsException if the new username is already in use by another user
     */
    public void updateUserUsername(String currentUsername, String newUsername) throws UsernameAlreadyExistsException {
        if(!isUsernameAvailable(newUsername)){
            throw new UsernameAlreadyExistsException("A User with that username already Exists!");
        }
        User user = this.usersByUsername.get(currentUsername);
        this.usersByUsername.remove(user.getUsername());
        user.setUsername(newUsername);
        this.usersByUsername.put(newUsername, user);
    }

    /**
     * Updates the birthdate of the user with the given ID.
     *
     * @param id The ID of the user whose birthdate is to be updated
     * @param birthdate The new birthdate to set
     */
    public void updateUserBirthdate(int id, LocalDate birthdate){
        this.usersById.get(id).setBirthdate(birthdate);
    }

    /**
     * Updates the address of the user with the given ID.
     *
     * @param id The ID of the user whose address is to be updated
     * @param address The new address to set
     */
    public void updateUserAddress(int id, String address){
        this.usersById.get(id).setAddress(address);
    }

    /**
     * Updates the email of the user with the given ID.
     *
     * @param id The ID of the user whose email is to be updated
     * @param email The new email to set
     */
    public void updateUserEmail(int id, String email) {
        this.usersById.get(id).setEmail(email);
    }

    /**
     * Updates the height of the user with the given ID.
     *
     * @param id The ID of the user whose height is to be updated
     * @param height The new height to set
     */
    public void updateUserHeight(int id, double height){
        this.usersById.get(id).setHeight(height);
    }

    /**
     * Updates the weight of the user with the given ID.
     *
     * @param id The ID of the user whose weight is to be updated
     * @param weight The new weight to set
     */
    public void updateUserWeight(int id, double weight){
        this.usersById.get(id).setWeight(weight);
    }

    /**
     * Updates the heart frequency of the user with the given ID.
     *
     * @param id The ID of the user whose heart frequency is to be updated
     * @param heartFrequency The new heart frequency to set
     */
    public void updateUserHeartFrequency(int id, int heartFrequency){
        this.usersById.get(id).setHeartFreq(heartFrequency);
    }
    //endregion

    /**
     * Adds a new user to the collection.
     *
     * @param user The User object to add
     */
    public void addUser(User user) {
        User newUser = user.clone();
        this.usersList.add(newUser);
        this.usersByUsername.put(newUser.getUsername(), newUser);
        this.usersById.put(newUser.getId(), newUser);
    }

    /**
     * Removes a user from the collection by ID.
     *
     * @param id The ID of the user to remove
     * @throws ErrorRemovingUserException if an error occurs while removing the user
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

    /**
     * Generates a map of users indexed by their IDs.
     *
     * @return A map containing users indexed by their IDs
     */
    private Map<Integer,User> generateUsersById(){
        Map<Integer,User> usersById = new HashMap<>();
        for(User user : this.usersList){
            usersById.put(user.getId(), user);
        }
        return usersById;
    };

    /**
     * Generates a map of users indexed by their usernames.
     *
     * @return A map containing users indexed by their usernames
     */
    private Map<String, User> generateUsersByUsername(){
        Map<String, User> usersByUsername = new HashMap<>();
        for(User user : this.usersList){
            usersByUsername.put(user.getUsername(), user);
        }
        return usersByUsername;
    }

    /**
     * Provides a string representation of the Users object.
     *
     * @return A string representation of the Users object
     */
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
     *
     * @param o The object to compare
     * @return true if the objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return this.getUsersByUsername().equals(users.getUsersByUsername()) &&
                this.getUsersById().equals(users.getUsersById()) &&
                this.getUsersList().equals(users.getUsersList());
    }

    /**
     * Creates a clone of the Users object.
     *
     * @return A cloned Users object
     */
    public Users clone(){
        return new Users(this);
    }
}
