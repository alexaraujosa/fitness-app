package activities;

import users.User;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class Activity {
    private final int id;
    private String name;
    private LocalDate begin;
    private LocalDate end;
    private final User user;
    private int burnedCalories;

    public Activity(
            int id,
            User user
        ) {
        this.id = id;
        this.name = "N/a";
        this.begin = LocalDate.now();
        this.end = LocalDate.now();
        this.user = user.clone();
        this.burnedCalories = 0;
    }

    public Activity(
            int id,
            String name,
            LocalDate begin,
            LocalDate end,
            User user,
            int burnedCalories
        ) {
        this.id = id;
        this.name = name;
        this.begin = begin;
        this.end = end;
        this.user = user.clone();
        this.burnedCalories = burnedCalories;
    }

    public Activity(
            Activity activity
        ) {
        this.id = activity.getId();
        this.name = activity.getName();
        this.begin = activity.getBegin();
        this.end = activity.getEnd();
        this.user = activity.getUser();
        this.burnedCalories = activity.getBurnedCalories();
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public LocalDate getBegin() { return this.begin; }
    public LocalDate getEnd() { return this.end; }
    public User getUser() { return this.user.clone(); }
    public int getBurnedCalories() { return this.burnedCalories; }

    public void setName(String name) { this.name = name; }
    public void setBegin(LocalDate begin) { this.begin = begin; }
    public void setEnd(LocalDate end) { this.end = end; }
    public void setBurnedCalories(int calories) { this.burnedCalories = calories; }

    public abstract int calculateCalories(User user);

    @Override
    public String toString() {
        return  "Activity -{ ID: " + this.id +
                " | NAME: " + this.name +
                " | BEGIN: " + this.begin.toString() +
                " | END: " + this.end.toString() +
                " | BURNED CALORIES: " + this.burnedCalories +
                " | USER: " + this.user.toString() +
                " }-\n";
    }

    @Override
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
                this.user.equals(activity.user) &&
                this.burnedCalories == activity.burnedCalories;
    }

}
