package activities.distance;

import activities.DistanceAct;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class TrackRunning extends DistanceAct implements Serializable {
    private boolean hurdleJump;

    //region Constructors
    public TrackRunning(
            int id,
            int idUser,
            int distance,
            boolean hurdleJump
    ) {
        super(id, idUser, distance);
        this.hurdleJump = hurdleJump;
    }

    public TrackRunning(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            boolean hurdleJump
    ) {
        super(id, name, begin, end, idUser, burnedCalories, heartRate, distance);
        this.hurdleJump = hurdleJump;
    }

    public TrackRunning(
            TrackRunning trackRunning
    ) {
        super(trackRunning);
        this.hurdleJump = trackRunning.getHurdleJump();
    }
    //endregion

    //region Getters And Setters
    public boolean getHurdleJump() { return this.hurdleJump; }

    public void setHurdleJump(boolean hurdleJump) { this.hurdleJump = hurdleJump; }
    //endregion

    public void calculateCalories() {
        double hurdleImpact = (this.hurdleJump) ? 1.4 : 1;
        double caloriesMultiplierPerDistance = 0.07;

        int calories = (int) (caloriesMultiplierPerDistance*this.getDistance()*hurdleImpact); // 0.07*1000*1.4 = 98
        this.setBurnedCalories(calories);
    }

    @Override
    public String toString() {
        return  "TrackRunning -{ ID: " + this.getId() +
                " | NAME: " + this.getName() +
                " | BEGIN: " + this.getBegin().toString() +
                " | END: " + this.getEnd().toString() +
                " | BURNED CALORIES: " + this.getBurnedCalories() +
                " | ID_USER: " + this.getIdUser() +
                " | HEART_RATE: " + this.getHeartRate() +
                " | DISTANCE: " + this.getDistance() +
                " | HURDLE JUMP: " + this.getHurdleJump() +
                " }-\n";
    }

    public TrackRunning clone() { return new TrackRunning(this); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TrackRunning that = (TrackRunning) o;
        return getHurdleJump() == that.getHurdleJump();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getHurdleJump());
    }
}
