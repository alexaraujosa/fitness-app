package activities;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Activity implements Serializable {
    private final int id;
    private String name;
    private LocalDateTime begin;
    private LocalDateTime end;
    private final int idUser;
    private int burnedCalories;
    private int heartRate;

    //region Constructors
    public Activity(
            int id,
            int idUser
        ) {
        this.id = id;
        this.name = "N/a";
        this.begin = LocalDateTime.now();
        this.end = LocalDateTime.now();
        this.idUser = idUser;
        this.burnedCalories = 0;
        this.heartRate = 0;
    }

    public Activity(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate
        ) {
        this.id = id;
        this.name = name;
        this.begin = begin;
        this.end = end;
        this.idUser = idUser;
        this.burnedCalories = burnedCalories;
        this.heartRate = heartRate;
    }

    public Activity(
            Activity activity
        ) {
        this.id = activity.getId();
        this.name = activity.getName();
        this.begin = activity.getBegin();
        this.end = activity.getEnd();
        this.idUser = activity.getIdUser();
        this.burnedCalories = activity.getBurnedCalories();
        this.heartRate = activity.getHeartRate();
    }
    //endregion

    //region Getters And Setters
    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public LocalDateTime getBegin() { return this.begin; }
    public LocalDateTime getEnd() { return this.end; }
    public int getIdUser() { return this.idUser; }
    public int getBurnedCalories() { return this.burnedCalories; }
    public int getHeartRate() { return this.heartRate; }

    public void setName(String name) { this.name = name; }
    public void setBegin(LocalDateTime begin) { this.begin = begin; }
    public void setEnd(LocalDateTime end) { this.end = end; }
    public void setBurnedCalories(int calories) { this.burnedCalories = calories; }
    public void setHeartRate(int heartRate) { this.heartRate = heartRate; }
    //endregion

    public abstract void calculateCalories();

    @Override
    public String toString() {
        return  "Activity -{ ID: " + this.id +
                " | NAME: " + this.name +
                " | BEGIN: " + this.begin.toString() +
                " | END: " + this.end.toString() +
                " | BURNED CALORIES: " + this.burnedCalories +
                " | ID_USER: " + this.idUser +
                " | HEART_RATE: " + this.heartRate +
                " }-\n";
    }

    public abstract Activity clone();

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof Activity)) return false;
        Activity activity = (Activity)o;
        return  this.id == activity.id &&
                this.name.equals(activity.name) &&
                this.begin == activity.begin &&
                this.end == activity.end &&
                this.idUser == activity.idUser &&
                this.burnedCalories == activity.burnedCalories &&
                this.heartRate == activity.heartRate;
    }

}
