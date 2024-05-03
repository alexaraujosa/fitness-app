package activities;

import java.util.HashMap;
import java.util.Map;

public class Activities {
    private Map<Integer,Activity> activities;


    public Activities() {
        this.activities = new HashMap<>();
    }

    public Activities(Activities activities) {
        this.activities = new HashMap<>(activities.getActivities());
    }

    public Map<Integer,Activity> getActivities() { return new HashMap<>(this.activities); }

    public boolean exists(int id) {
        return this.activities.containsKey(id);
    }

    public void add(Activity activity) {
        this.activities.put(activity.getId(), activity.clone());
    }

    public Activity get(Activity activity) {
        return this.activities.get(activity.getId()).clone();
    }

    public Activity get(int id) {
        return this.activities.get(id).clone();
    }

    public void remove(Activity activity) {
        if(this.activities.containsKey(activity.getId())) {
            this.activities.remove(activity.getId());
            return;
        }
        // TODO: Else, error handler
    }

    public void remove(int id) {
        if(this.activities.containsKey(id)) {
            this.activities.remove(id);
            return;
        }
        // TODO: Else, error handler
    }

    public void update(Activity activity) {
        if(this.activities.containsKey(activity.getId())) {
            this.activities.put(activity.getId(), activity.clone());
            return;
        }
        // TODO: Else, error handler
    }

    public Activities clone() { return new Activities(this); }

}
