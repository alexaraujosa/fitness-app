package users;

import java.util.*;

public class Users {
    private final List<String> usernames;
    private final Map<Integer,User> usersById;

    public Users(){
        this.usernames = new ArrayList<>();
        this.usersById = new HashMap<>();
    }

    public boolean isUserNameAvailable(String username){
        return usernames.stream().noneMatch(u -> u.equals(username));
    }

    public void addUser(User newUser) {
        usernames.add(newUser.getUsername());
        usersById.put(newUser.getId(), newUser);
    }
}
