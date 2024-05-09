package activities;

import java.io.Serializable;
import java.util.Objects;

public class ActivityController implements Serializable {
    private Activities activities;

    public ActivityController() {
        this.activities = new Activities();
    }

    public ActivityController(ActivityController activityController) {
        this.activities = new Activities(activityController.getActivities());
    }

    public Activities getActivities() { return this.activities.clone(); }

    public boolean exists(Activity activity) {
        return this.activities.exists(activity.getId());
    }

    public void add(Activity activity) {
        this.activities.add(activity);
    }

    public void remove(Activity activity) {
        this.activities.remove(activity);
    }

    public void remove(int id) {
        this.activities.remove(id);
    }

    public Activity get(Activity activity) {
        return this.activities.get(activity);
    }

    public Activity get(int id) {
        return this.activities.get(id);
    }

    public void update(Activity activity) {
        this.activities.update(activity);
    }

    public Activities generateLink() {
        Activities res = new Activities();
        for(Activity a : this.activities.getActivities().values()) {
            res.add(a);
        }

        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityController that = (ActivityController) o;
        return Objects.equals(getActivities(), that.getActivities());
    }
}
