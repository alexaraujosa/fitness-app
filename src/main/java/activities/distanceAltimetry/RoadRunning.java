package activities.distanceAltimetry;

import activities.DistanceAndAltimetryAct;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class RoadRunning extends DistanceAndAltimetryAct implements Serializable {
    private boolean windAgainst;

    //region Constructors
    public RoadRunning(
            int id,
            int idUser,
            int distance,
            int altimetry,
            boolean windAgainst
    ) {
        super(id, idUser, distance, altimetry);
        this.windAgainst = windAgainst;
    }

    public RoadRunning(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            int altimetry,
            boolean windAgainst
    ) {
        super(id, name, begin, end, idUser, burnedCalories, heartRate, distance, altimetry);
        this.windAgainst = windAgainst;
    }

    public RoadRunning(
            RoadRunning roadRunning
    ) {
        super(roadRunning);
        this.windAgainst = roadRunning.getWindAgainst();
    }
    //endregion

    //region Getters And Setters
    public boolean getWindAgainst() { return this.windAgainst; }

    public void setWindAgainst(boolean windAgainst) { this.windAgainst = windAgainst; }
    //endregion

    public void calculateCalories() {
        double windImpact = (this.windAgainst) ? 1.5 : 1;
        double caloriesMultiplier = 0.05; // 0.05*(1000+2.2*200)*1.2 = 86.4

        int calories = (int) (caloriesMultiplier*(this.getDistance() + 2.2*this.getAltimetry())*windImpact);
        this.setBurnedCalories(calories);
    }

    @Override
    public String toString() {
        return  "RoadRunning -{ ID: " + this.getId() +
                " | NAME: " + this.getName() +
                " | BEGIN: " + this.getBegin().toString() +
                " | END: " + this.getEnd().toString() +
                " | BURNED CALORIES: " + this.getBurnedCalories() +
                " | ID_USER: " + this.getIdUser() +
                " | HEART_RATE: " + this.getHeartRate() +
                " | DISTANCE: " + this.getDistance() +
                " | ALTIMETRY: " + this.getAltimetry() +
                " | WIND AGAINST: " + this.getWindAgainst() +
                " }-\n";
    }

    public RoadRunning clone() { return new RoadRunning(this); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RoadRunning that = (RoadRunning) o;
        return getWindAgainst() == that.getWindAgainst();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getWindAgainst());
    }
}
