package josefinFA;

import activities.Activity;
import activities.DistanceAct;
import activities.DistanceAndAltimetryAct;
import users.CasualUser;
import users.User;
import users.UserController;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Stats implements Serializable {
    private UserController userController;
    private LocalDateTime systemDate;
    private User allTimeUserWithMostCaloriesBurned;
    private User allTimeUserWithMostActivitiesCompleted;


    //region Constructors
    public Stats(){
        this.userController = new UserController();
        this.systemDate = LocalDateTime.now();
        this.allTimeUserWithMostActivitiesCompleted = new CasualUser(1);
        this.allTimeUserWithMostCaloriesBurned = new CasualUser(1);
    }

    public Stats(UserController userController, LocalDateTime systemDate, User allTimeUserWithMostActivitiesCompleted, User allTimeUserWithMostCaloriesBurned){
        this.userController = userController.clone();
        this.systemDate = systemDate;
        this.allTimeUserWithMostCaloriesBurned = allTimeUserWithMostCaloriesBurned.clone();
        this.allTimeUserWithMostActivitiesCompleted = allTimeUserWithMostActivitiesCompleted.clone();
    }

    public Stats(Stats st){
        this.userController = st.getUserController();
        this.systemDate = st.systemDate;
        this.allTimeUserWithMostActivitiesCompleted = st.getAllTimeUserWithMostActivitiesCompleted();
        this.allTimeUserWithMostCaloriesBurned = st.getAllTimeUserWithMostCaloriesBurned();
    }
    //endregion

    //region Getters and Setters
    public UserController getUserController() {
        return this.userController.clone();
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public LocalDateTime getSystemDate() {
        return this.systemDate;
    }

    public void setSystemDate(LocalDateTime systemDate) {
        this.systemDate = systemDate;
    }

    public User getAllTimeUserWithMostCaloriesBurned() {
        return this.allTimeUserWithMostCaloriesBurned.clone();
    }

    public void setAllTimeUserWithMostCaloriesBurned(User user) {
        this.allTimeUserWithMostCaloriesBurned = user;
    }

    public User getAllTimeUserWithMostActivitiesCompleted() {
        return this.allTimeUserWithMostActivitiesCompleted.clone();
    }

    public void setAllTimeUserWithMostActivitiesCompleted(User user) {
        this.allTimeUserWithMostActivitiesCompleted = user;
    }
    //endregion

    //region Calculate Stats
    public void updateAllTimeValues(){
        this.userWithMostCaloriesBurned();
        this.userWithMostActivitiesCompleted();
    }

    public User userWithMostCaloriesBurned(LocalDateTime from){
        int burnedCalories = -1;
        int finalUserID = -1;

        List<User> users = this.userController.getUsers().getUsersList();
        for(User user : users){
            int newBurnedCalories = 0;
            for(Activity act : user.getActivityController().getActivities().getActivities().values()){
                if(act.getBegin().isAfter(from) && act.getEnd().isBefore(this.systemDate)){
                    newBurnedCalories += user.calculateBurnedCalories(act.getId());
                }
            }
            if(newBurnedCalories > burnedCalories){
                burnedCalories = newBurnedCalories;
                finalUserID = user.getId();
            }
        }
        return this.userController.getUsers().getUserWithId(finalUserID);
    }

    public void userWithMostCaloriesBurned(){
        int burnedCalories = -1;
        int finalUserID = -1;

        List<User> users = this.userController.getUsers().getUsersList();
        for(User user : users){
            int newBurnedCalories = 0;
            for(Activity act : user.getActivityController().getActivities().getActivities().values()){
                if(act.getEnd().isBefore(this.systemDate)){
                    newBurnedCalories += user.calculateBurnedCalories(act.getId());
                }
            }
            if(newBurnedCalories > burnedCalories){
                burnedCalories = newBurnedCalories;
                finalUserID = user.getId();
            }
        }
        this.allTimeUserWithMostCaloriesBurned = this.userController.getUsers().getUserWithId(finalUserID);
    }

    public User userWithMostActivitiesCompleted(LocalDateTime from){
        int nActivities = -1;
        int finalUserID = -1;
        List<User> users = this.userController.getUsers().getUsersList();
        for(User user : users){
            int newNActivities = 0;
            for(Activity act : user.getActivityController().getActivities().getActivities().values()){
                if(act.getBegin().isAfter(from) && act.getEnd().isBefore(this.systemDate)){
                    newNActivities++;
                }
            }
            if(newNActivities > nActivities){
                nActivities = newNActivities;
                finalUserID = user.getId();
            }
        }
        return this.userController.getUsers().getUserWithId(finalUserID);
    }

    public void userWithMostActivitiesCompleted(){
        int nActivities = -1;
        int finalUserID = -1;
        List<User> users = this.userController.getUsers().getUsersList();
        for(User user : users){
            int newNActivities = 0;
            for(Activity act : user.getActivityController().getActivities().getActivities().values()){
                if(act.getEnd().isBefore(this.systemDate)){
                    newNActivities++;
                }
            }
            if(newNActivities > nActivities){
                nActivities = newNActivities;
                finalUserID = user.getId();
            }
        }
        this.allTimeUserWithMostActivitiesCompleted = this.userController.getUsers().getUserWithId(finalUserID);
    }


    public String mostCommunActivity() {
        Map<String, Integer> nActivitiesByType = new HashMap<>();
        List<User> users = this.userController.getUsers().getUsersList();

        for (User user : users) {
            for (Activity act : user.getActivityController().getActivities().getActivities().values().stream().toList()) {
                String activityType = act.getClass().getSimpleName();
                nActivitiesByType.put(activityType, nActivitiesByType.getOrDefault(activityType, 0) + 1);
            }
        }

        String mostCommonActivityType = "";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : nActivitiesByType.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommonActivityType = entry.getKey();
            }
        }

        return mostCommonActivityType;
    }

    public int distanceDoneByUser(int userID, LocalDateTime from){
        int distance = 0;
        User user = this.userController.getUsers().getUserWithId(userID);
        for(Activity act : user.getActivityController().getActivities().getActivities().values()){
            if(act.getBegin().isAfter(from) &&
                    act.getEnd().isBefore(this.systemDate) &&
                    act.getClass().getSuperclass().getSimpleName().equals("DistanceAct")){
                DistanceAct myAct = (DistanceAct) act;
                distance += myAct.getDistance();
            } else if(act.getBegin().isAfter(from) &&
                    act.getEnd().isBefore(this.systemDate) &&
                    act.getClass().getSuperclass().getSimpleName().equals("DistanceAndAltimetryAct")) {
                DistanceAndAltimetryAct myAct = (DistanceAndAltimetryAct) act;
                distance += myAct.getDistance();
            }
        }
        return distance;
    }

    public int altimetryDoneByUser(int userID, LocalDateTime from){
        int altimetry = 0;
        User user = this.userController.getUsers().getUserWithId(userID);
        for(Activity act : user.getActivityController().getActivities().getActivities().values()){
            if(act.getBegin().isAfter(from) &&
                    act.getEnd().isBefore(this.systemDate) &&
                    act.getClass().getSuperclass().getSimpleName().equals("DistanceAndAltimetryAct")){
                DistanceAndAltimetryAct myAct = (DistanceAndAltimetryAct) act;
                altimetry += myAct.getAltimetry();
            }
        }
        return altimetry;
    }

    public List<Activity> getUsersActivities(int userID) {
        return this.userController.getUsers().getUserWithId(userID).getActivityController().getActivities().getActivities().values().stream().toList();
    }
    //endregion


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stats stats = (Stats) o;
        return Objects.equals(getUserController(), stats.getUserController()) &&
                Objects.equals(getSystemDate(), stats.getSystemDate()) &&
                Objects.equals(getAllTimeUserWithMostCaloriesBurned(), stats.getAllTimeUserWithMostCaloriesBurned()) &&
                Objects.equals(getAllTimeUserWithMostActivitiesCompleted(), stats.getAllTimeUserWithMostActivitiesCompleted());
    }

    public Stats clone(){
        return new Stats(this);
    }
}
