package users;

import TrainingPlan.TrainingPlan;
import activities.ActivityController;

import java.util.Date;
import java.util.List;

public class User {
    private int id;
    private String name;
    private String username;
    private Date birthdate;
    private String address;
    private String email;
    private boolean sex;
    private double height;
    private double weight;
    private int hearFreq;
    private ActivityController activityController;
    private List<TrainingPlan> trainingSchedule;
}
