package users;

import exceptions.UsernameAlreadyExistsException;

import java.util.*;

public class Users {
    private final List<String> usernames;
    private final Map<Integer,User> usersById;

    public Users(){
        this.usernames = new ArrayList<>();
        this.usersById = new HashMap<>();
    }

    public Users(List<String> usernames, Map<Integer, User> usersById){
        this.usernames = usernames;
        this.usersById = usersById;
    }

    public Users(Users u){
        this.usernames = u.getUsernames();
        this.usersById = u.getUsersById();
    }

    public List<String> getUsernames() {
        return new ArrayList<>(this.usernames);
    }

    public Map<Integer, User> getUsersById() {
        return new HashMap<>(this.usersById);
    }

    public boolean isUserNameAvailable(String username) throws UsernameAlreadyExistsException {
        if(usernames.stream().noneMatch(u -> u.equals(username))){
            return true;
        } else {
            throw new UsernameAlreadyExistsException("Username " + username + " already exists!");
        }
    }

    public boolean containsUser(int id){
        return usersById.containsKey(id);
    }


    public void addUser(User newUser) {
        usernames.add(newUser.getUsername());
        usersById.put(newUser.getId(), newUser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return this.getUsernames().equals(users.getUsernames()) &&
                this.getUsersById().equals(users.getUsersById());
    }

    public Users clone(){
        return new Users(this);
    }

    public void removeUser(int id) {
        User user = usersById.get(id);
        usernames.remove(user.getUsername());
        usersById.remove(id);
    }
}
