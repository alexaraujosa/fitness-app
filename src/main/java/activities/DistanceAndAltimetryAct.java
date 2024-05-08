package activities;

import java.time.LocalDate;

public abstract class DistanceAndAltimetryAct extends Activity{
    private int distance;
    private int altimetry;

    //region Constructors
    public DistanceAndAltimetryAct(
            int id,
            int idUser,
            int distance,
            int altimetry
    ) {
        super(id, idUser);
        this.distance = distance;
        this.altimetry = altimetry;
    }

    public DistanceAndAltimetryAct(
            int id,
            String name,
            LocalDate begin,
            LocalDate end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            int altimetry
    ) {
        super(id, name, begin, end, idUser, burnedCalories, heartRate);
        this.distance = distance;
        this.altimetry = altimetry;
    }

    public DistanceAndAltimetryAct(
            DistanceAndAltimetryAct activity
    ) {
        super(activity);
        this.distance = activity.getDistance();
        this.altimetry = activity.getAltimetry();
    }
    //endregion

    public int getDistance() { return this.distance; }
    public int getAltimetry() { return this.altimetry; }

    public void setDistance(int distance) { this.distance = distance; }
    public void setAltimetry(int altimetry) { this.altimetry = altimetry; }

    public abstract void calculateCalories();

}
