package activities;

import users.User;

public class DistanceAndAltimetryAct extends Activity{
    private int distance;
    private int altimetry;


    public DistanceAndAltimetryAct(int id, int idUser, int distance, int altimetry) {
        super(id, idUser);
        this.distance = Math.max(distance, 0);
        this.altimetry = Math.max(altimetry, 0);
    }

    public DistanceAndAltimetryAct(DistanceAndAltimetryAct activity) {
        super(activity);
        this.distance = activity.getDistance();
        this.altimetry = activity.getAltimetry();
    }

    public int getDistance() { return this.distance; }
    public int getAltimetry() { return this.altimetry; }

    public void setDistance(int distance) { this.distance = Math.max(distance, 0); }
    public void setAltimetry(int altimetry) { this.altimetry = Math.max(altimetry, 0); }

    public int calculateCalories(User user) {
        return 1; // TODO: And does this even need the user? The activity already has the user...
    }

    public DistanceAndAltimetryAct clone() { return new DistanceAndAltimetryAct(this); }
}
