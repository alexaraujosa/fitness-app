package activities;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class DistanceAct extends Activity implements Serializable {
    private int distance;

    //region Constructors
    public DistanceAct(
            int id,
            int idUser,
            int distance
    ) {
        super(id, idUser);
        this.distance = distance;
    }

    public DistanceAct(
            int id,
            String name,
            LocalDate begin,
            LocalDate end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance
    ) {
        super(id, name, begin, end, idUser, burnedCalories, heartRate);
        this.distance = distance;
    }

    public DistanceAct(
            DistanceAct activity
    ) {
        super(activity);
        this.distance = activity.getDistance();
    }
    //endregion

    public int getDistance() { return this.distance; }

    public void setDistance(int distance) { this.distance = distance; }

    public abstract void calculateCalories();

}
