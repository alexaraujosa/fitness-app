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

    public JosefinFitnessApp(JosefinFitnessApp app){
        this.userController = app.getUserController();
        this.idManager = app.getIdManager();
        this.stats = app.getStats();
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
