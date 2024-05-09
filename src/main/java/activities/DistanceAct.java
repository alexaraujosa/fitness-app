package activities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
            LocalDateTime begin,
            LocalDateTime end,
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

    //region Getters And Setters
    public int getDistance() { return this.distance; }

    public void setDistance(int distance) { this.distance = distance; }
    //endregion

    public abstract void calculateCalories();

}
