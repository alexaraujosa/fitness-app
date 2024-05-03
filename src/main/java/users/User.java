package users;

import TrainingPlan.TrainingPlan;
import activities.Activity;
import activities.ActivityController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

public abstract class User {
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
     *  Construtor Parametrizado do User com todas as variaveis
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

    public int getId() {
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getSex() {
        return this.sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getHeartFreq() {
        return this.heartFreq;
    }

    public void setHeartFreq(int heartFreq) {
        this.heartFreq = heartFreq;
    }

    public ActivityController getActivityController() {
        return new ActivityController(this.activityController);
    }

    public List<TrainingPlan> getTrainingSchedule() {
        return new ArrayList<>(this.trainingSchedule);
    }

    public void addActivity(Activity act){
        this.activityController.addActivity(act);
        //this.activityController.add(act);
    }

    public void removeActivity(int id){
        //TODO: Remove a atividade, ver se fazemos isto com id ou com nome
        //this.activityController.remove(id);
    }

    public void addTrainingPlan(TrainingPlan tp){
        //TODO: esta função tera que verificar as regras do plano de treino
        this.trainingSchedule.add(tp);
        // Uma plano de treino não pode ter mais de uma atividade hard por dia, nem pode ter ativadade hard em dias consecutivos
        // nunca pode ter mais de 3 atividades por dia
    }

    public void removeTrainingPlan(TrainingPlan tp){
        //TODO: teriamos tb de remover as atividades desse tp
    }

    public void updateActivity(Activity oldAct, Activity newAct){
        //TODO: substitui a antiga ativade com a nova
        //Ativadades completas não fazem sentido ser atualizadas
        //Se for uma atividade de um plano de treino ou não se deixa atualizar aqui ou fazer as verificações necessarias
    }

    public void updateTrainingPlan(TrainingPlan oldTp, TrainingPlan newTp){
        //ver se o novo plano de treino pode encaixar onde é suposto
        //só pode ser encaixado no presente ou futuro
        //se tivermos um plano de treino que se repita todas as sextas, temos de alterar as sextas daqui para a frente, não as anteriores
    }

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

    public abstract double calculateCaloriesBurnMultiplier();

    public abstract User clone();

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return "User {\n" +
                "\tid: " + id + ",\n" +
                "\tname: " + name + ",\n" +
                "\tusername: " + username + ",\n" +
                "\tbirthdate: " + dateFormat.format(birthdate) + ",\n" +
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
