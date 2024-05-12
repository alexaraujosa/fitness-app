package activities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Activities implements Serializable {
    private Map<Integer,Activity> activities;

    //region Constructors
    public Activities() {
        this.activities = new HashMap<>();
    }

    public Activities(
            Activities activities
    ) {
        this.activities = new HashMap<>(activities.getActivities());
    }
    //endregion

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--{ ACTIVITIES LIST }--\n");
        this.getActivities().values().forEach(activity -> sb.append(activity.toString()));
        sb.append("--{ END ACTIVITIES LIST }--\n");
        return sb.toString();
    }

    public Activities clone() { return new Activities(this); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activities that = (Activities) o;
        return this.activities.equals(that.activities);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getActivities());
    }
}
