package TrainingPlan;

import activities.Activity;
import activities.Hard;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainingPlan implements Serializable {
    private int id;
    private List<Activity> activities;
    private LocalDate doDate;
    private boolean[] repeat;
    private int calories;

    //region Constructors
    public TrainingPlan(int id, List<Activity> activities, LocalDate doDate, boolean[] repeat) {
        this.id = id;
        this.activities = new ArrayList<>(activities);
        this.doDate = doDate;
        this.repeat = Arrays.copyOf(repeat, repeat.length);
        this.calories = activities.stream().mapToInt(Activity::getBurnedCalories).sum();
    }
    //endregion

    //region Getters And Setters
    public int getId() {
        return this.id;
    }
    public List<Activity> getActivities() { return new ArrayList<>(this.activities); }
    public LocalDate getDoDate() { return this.doDate; }
    public boolean[] getRepeat() { return Arrays.copyOf(this.repeat, this.repeat.length); }
    public int getCalories() { return this.calories; }
    public boolean containsHardActivity() {
        return this.getActivities().stream().anyMatch(a -> a instanceof Hard);
    }
    //endregion

}
