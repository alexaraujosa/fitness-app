package users;

import exceptions.UsernameAlreadyExistsException;

import java.util.Date;

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

    public void isUsernameAvailable(String username) throws UsernameAlreadyExistsException {
        this.users.isUserNameAvailable(username);
    }

    public boolean userWithIdExits(int id) {
        return this.users.containsUser(id);
    }

    public void addCasualUser(
            int id,
            String name,
            String username,
            Date birthdate,
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
            Date birthdate,
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
            Date birthdate,
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
