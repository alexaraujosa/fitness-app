package users;

import java.util.Date;

public class UserController {
    private Users users;

    public UserController(){
        this.users = new Users();
    }

    public boolean isUsernameAvailable(String username) {
        return users.isUserNameAvailable(username);
    }

    public void addUser(int id, String name, String username, Date birthdate, String address, String email, boolean sex, double height, double weight, int heartFreq) {
        User newUser = new User(id,name,username,birthdate,address,email,sex,height,weight,heartFreq);
        users.addUser(newUser.clone());
    }
}
