package activities;

import users.User;

public class DistanceAct extends Activity{
    private int distance;

    public DistanceAct(int id, User user, int distance) {
        super(id, user);
        this.distance = Math.max(distance, 0);
    }

    public DistanceAct(DistanceAct activity) {
        super(activity);
        this.distance = activity.getDistance();
    }

    public int getDistance() { return this.distance; }

    public void setDistance(int distance) { this.distance = Math.max(distance, 0); }

    public int calculateCalories(User user) {
        return 0; // TODO
    }

    public DistanceAct clone() { return new DistanceAct(this); }
}
