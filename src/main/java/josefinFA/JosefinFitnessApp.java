package josefinFA;

import users.User;
import users.UserController;
import utils.IDManager;

import java.util.Date;
import java.util.Objects;

public class JosefinFitnessApp {
    private UserController userController;
    private IDManager idManager;
    private Stats stats;

    public JosefinFitnessApp(){
        this.userController = new UserController();
        this.idManager = new IDManager();
        this.stats = new Stats();
    }

    public JosefinFitnessApp(UserController userController, IDManager idManager, Stats stats) {
        this.userController = userController;
        this.idManager = idManager;
        this.stats = stats;
    }

    public String addUser(String name,
                   String username,
                   Date birthdate,
                   String address,
                   String email,
                   boolean sex,
                   double height,
                   double weight,
                   int heartFreq){
        if(userController.isUsernameAvailable(username)){
            int id = idManager.generateUniqueUserID();
            userController.addUser(id,name,username,birthdate,address,email,sex,height,weight,heartFreq);
            return "User added successfully!";
        } else {
            return "That username is not available!";
        }
    }
}
