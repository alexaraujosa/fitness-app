package activities;

public abstract class DistanceAndAltimetryAct extends Activity{
    private int distance;
    private int altimetry;


    public DistanceAndAltimetryAct(int id, int idUser, int distance, int altimetry) {
        super(id, idUser);
        this.distance = distance;
        this.altimetry = altimetry;
    }

    public DistanceAndAltimetryAct(DistanceAndAltimetryAct activity) {
        super(activity);
        this.distance = activity.getDistance();
        this.altimetry = activity.getAltimetry();
    }

    public int getDistance() { return this.distance; }
    public int getAltimetry() { return this.altimetry; }

    public void setDistance(int distance) { this.distance = distance; }
    public void setAltimetry(int altimetry) { this.altimetry = altimetry; }

    public abstract void calculateCalories();

}
