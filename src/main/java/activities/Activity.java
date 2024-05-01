package activities;

import users.User;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class Activity {
    private final int id;
    private String name;
    private Date begin;
    private Date end;
    private final User user;
    private int burnedCalories;

    public Activity(
            int id,
            User user
        ) {
        this.id = id;
        this.name = "N/a";
        this.begin = new GregorianCalendar(1900, Calendar.JANUARY, 1).getTime();
        this.end = new GregorianCalendar(1900, Calendar.JANUARY, 1).getTime();
        this.user = user.clone();
        this.burnedCalories = 0;
    }

    public Activity(
            int id,
            String name,
            Date begin,
            Date end,
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
    public Date getBegin() { return this.begin; }
    public Date getEnd() { return this.end; }
    public User getUser() { return this.user.clone(); }
    public int getBurnedCalories() { return this.burnedCalories; }

    public void setName(String name) { this.name = name; }
    public void setBegin(Date begin) { this.begin = begin; }
    public void setEnd(Date end) { this.end = end; }
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
