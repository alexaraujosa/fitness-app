package users;

import java.util.Date;

public class UserController {
    private Users users;

    public UserController(){
        this.users = new Users();
    }

    public UserController(Users users){
        this.users = users;
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
        return this.users.isUserNameAvailable(username);
    }

    public boolean userWithIdExits(int id) {
        return this.users.containsUser(id);
    }

    public void addUser(int id, String name, String username, Date birthdate, String address, String email, boolean sex, double height, double weight, int heartFreq) {
        User newUser = new User(id,name,username,birthdate,address,email,sex,height,weight,heartFreq);
        this.users.addUser(newUser.clone());
    }

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

    public void removeUser(int id) {
        this.users.removeUser(id);
    }
}
