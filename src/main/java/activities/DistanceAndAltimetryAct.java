package activities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class DistanceAndAltimetryAct extends Activity implements Serializable {
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
            LocalDateTime begin,
            LocalDateTime end,
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

    //region Getters And Setters
    public int getDistance() { return this.distance; }
    public int getAltimetry() { return this.altimetry; }

    public void setDistance(int distance) { this.distance = distance; }
    public void setAltimetry(int altimetry) { this.altimetry = altimetry; }
    //endregion

    public abstract void calculateCalories();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DistanceAndAltimetryAct that = (DistanceAndAltimetryAct) o;
        return getDistance() == that.getDistance() &&
                getAltimetry() == that.getAltimetry();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDistance(), getAltimetry());
    }
}
