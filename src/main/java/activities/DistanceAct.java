package activities;

public abstract class DistanceAct extends Activity{
    private int distance;

    public DistanceAct(int id, int idUser, int distance) {
        super(id, idUser);
        this.distance = distance;
    }

    public DistanceAct(DistanceAct activity) {
        super(activity);
        this.distance = activity.getDistance();
    }

    public int getDistance() { return this.distance; }

    public void setDistance(int distance) { this.distance = distance; }

    public abstract void calculateCalories();

}
