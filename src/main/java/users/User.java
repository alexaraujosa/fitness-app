package users;

import TrainingPlan.TrainingPlan;
import activities.Activity;
import activities.ActivityController;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

/**
 * The User class represents a User, containing information as it's name, username, birthdate
 * address, email and more. This class implements methods to set and get this values as well
 * as methods to calculate calorie expenditure and to add activities and trainingPlans.
 */
public abstract class User implements Serializable {
    private final int id;
    private String name;
    private String username;
    private LocalDate birthdate;
    private String address;
    private String email;
    private boolean sex;
    private double height;
    private double weight;
    private int heartFreq;
    private final ActivityController activityController;
    private final List<TrainingPlan> trainingSchedule;

    //region constructors
    /**
     * Constructs an empty user with the specified ID.
     * Note: The caller is responsible for ensuring the uniqueness of the ID.
     *
     * @param id Unique number used to identify the User
     */
    public User(int id){
        this.id = id;
        this.name = "N/a";
        this.username = "N/a";
        this.birthdate = LocalDate.now();
        this.address = "N/a";
        this.email = "N/a";
        this.sex = false;
        this.height = 0;
        this.weight = 0;
        this.heartFreq = 0;
        this.activityController = new ActivityController();
        this.trainingSchedule = new ArrayList<TrainingPlan>();
    }

    /**
     * Constructs a user with the specified information.
     *
     * @param id Unique number used to identify the User.
     * @param name The name of the user.
     * @param username The username of the user.
     * @param birthdate The birthdate of the user.
     * @param address The address of the user.
     * @param email The email address of the user.
     * @param sex The gender of the user (true for male, false for female).
     * @param height The height of the user (in meters).
     * @param weight The weight of the user (in kilograms).
     * @param heartFreq The heart frequency of the user.
     */
    public User(
            int id,
            String name,
            String username,
            LocalDate birthdate,
            String address,
            String email,
            boolean sex,
            double height,
            double weight,
            int heartFreq
    ){
        this.id = id;
        this.name = name;
        this.username = username;
        this.birthdate = birthdate;
        this.address = address;
        this.email = email;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.heartFreq = heartFreq;
        this.activityController = new ActivityController();
        this.trainingSchedule = new ArrayList<TrainingPlan>();
    }

    /**
     * Constructs a new user object by copying the information from another user.
     *
     * @param u The user object from which to copy the information.
     */
    public User(User u){
        this.id = u.getId();
        this.name = u.getName();
        this.username = u.getUsername();
        this.birthdate = u.getBirthdate();
        this.address = u.getAddress();
        this.email = u.getEmail();
        this.sex = u.getSex();
        this.height = u.getHeight();
        this.weight = u.getWeight();
        this.heartFreq = u.getHeartFreq();
        this.activityController = u.getActivityController();
        this.trainingSchedule = u.getTrainingSchedule();
    }
    //endregion

    //region getters&setters
    /**
     * Retrieves the unique ID of the user.
     *
     * @return The unique ID of the user.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Retrieves the name of the user.
     *
     * @return The name of the user.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The new name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The new username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the birthdate of the user.
     *
     * @return The birthdate of the user.
     */
    public LocalDate getBirthdate() {
        return this.birthdate;
    }

    /**
     * Sets the birthdate of the user.
     *
     * @param birthdate The new birthdate of the user.
     */
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * Retrieves the address of the user.
     *
     * @return The address of the user.
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Sets the address of the user.
     *
     * @param address The new address of the user.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Retrieves the email of the user.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email The new email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the sex of the user.
     *
     * @return The sex of the user (true for male, false for female).
     */
    public boolean getSex() {
        return this.sex;
    }

    /**
     * Sets the sex of the user.
     *
     * @param sex The new sex of the user (true for male, false for female).
     */
    public void setSex(boolean sex) {
        this.sex = sex;
    }

    /**
     * Retrieves the height of the user.
     *
     * @return The height of the user (in meters).
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Sets the height of the user.
     *
     * @param height The new height of the user (in meters).
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Retrieves the weight of the user.
     *
     * @return The weight of the user (in kilograms).
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * Sets the weight of the user.
     *
     * @param weight The new weight of the user (in kilograms).
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Retrieves the heart frequency of the user.
     *
     * @return The heart frequency of the user.
     */
    public int getHeartFreq() {
        return this.heartFreq;
    }

    /**
     * Sets the heart frequency of the user.
     *
     * @param heartFreq The new heart frequency of the user.
     */
    public void setHeartFreq(int heartFreq) {
        this.heartFreq = heartFreq;
    }

    /**
     * Retrieves a copy of the activity controller of the user.
     *
     * @return A copy of the activity controller of the user.
     */
    public ActivityController getActivityController() {
        return new ActivityController(this.activityController);
    }

    /**
     * Retrieves a copy of the training schedule of the user.
     *
     * @return A copy of the training schedule of the user.
     */
    public List<TrainingPlan> getTrainingSchedule() {
        return new ArrayList<>(this.trainingSchedule);
    }
    //endregion

    //region Activity Methods
    public void addActivity(Activity act){
        //NOTE: Verificar se a atividade sobrepõe outra atividade?
        act.calculateCalories();
        this.activityController.add(act);
    }

    public void updateActivity(Activity activity){
        //TODO: substitui a antiga ativade com a nova
        this.activityController.update(activity);
        //Ativadades completas não fazem sentido ser atualizadas
        //Se for uma atividade de um plano de treino ou não se deixa atualizar aqui ou fazer as verificações necessarias
    }

    public void removeActivity(int id){
        this.activityController.remove(id);
    }
    //endregion

    //region Training Plan Methods
    public void addTrainingPlan(TrainingPlan tp){
        //TODO: esta função tera que verificar as regras do plano de treino
        this.trainingSchedule.add(tp);
        // Uma plano de treino não pode ter mais de uma atividade hard por dia, nem pode ter ativadade hard em dias consecutivos
        // nunca pode ter mais de 3 atividades por dia
    }

    public void updateTrainingPlan(TrainingPlan oldTp, TrainingPlan newTp){
        //ver se o novo plano de treino pode encaixar onde é suposto
        //só pode ser encaixado no presente ou futuro
        //se tivermos um plano de treino que se repita todas as sextas, temos de alterar as sextas daqui para a frente, não as anteriores
    }

    public void removeTrainingPlan(TrainingPlan tp){
        int index = this.trainingSchedule.indexOf(tp);
        TrainingPlan plan = this.trainingSchedule.get(index);
        for(Activity a : plan.getActivities()) {
            this.activityController.remove(a.getId());
        }
        this.trainingSchedule.remove(tp);
    }
    //endregion

    protected double calculateBMR(){
        LocalDate today = LocalDate.now();
        int age = Period.between(this.getBirthdate(), today).getYears();

        //NOTES: USING Mifflin-St.Jeor Equation
        //basal metabolic rate;
        double bmr = 0;

        if (this.getSex()) { // Assuming true for male, false for female
            bmr = (10 * this.getWeight()) + (6.25 * this.getHeight()) - (5 * age) + 5;
        } else {
            bmr = (10 * this.getWeight()) + (6.25 * this.getHeight()) - (5 * age) - 161;
        }
        return bmr;
    }

    public abstract int calculateBurnedCalories(int activityId);

    public abstract User clone();

    @Override
    public String toString() {
        return "User {\n" +
                "\tid: " + id + ",\n" +
                "\tname: " + name + ",\n" +
                "\tusername: " + username + ",\n" +
                "\tbirthdate: " + birthdate.toString() + ",\n" +
                "\taddress: " + address + ",\n" +
                "\temail: " + email + ",\n" +
                "\tsex: " + (sex ? "Male" : "Female") + ",\n" +
                "\theight: " + height + ",\n" +
                "\tweight: " + weight + ",\n" +
                "\theartFreq: " + heartFreq + "\n" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.id == user.id &&
                this.name.equals(user.name) &&
                this.username.equals(user.username) &&
                this.birthdate.equals(user.birthdate) &&
                this.address.equals(user.address) &&
                this.email.equals(user.email) &&
                this.sex == user.sex &&
                Double.compare(height, user.height) == 0 &&
                Double.compare(weight, user.weight) == 0 &&
                heartFreq == user.heartFreq &&
                activityController.equals(user.activityController) &&
                trainingSchedule.equals(user.trainingSchedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, username, birthdate, address, email, sex, height, weight, heartFreq, activityController, trainingSchedule);
    }
}
