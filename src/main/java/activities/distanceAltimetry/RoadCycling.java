package activities.distanceAltimetry;

import activities.DistanceAndAltimetryAct;

import java.io.Serializable;
import java.time.LocalDate;

public class RoadCycling extends DistanceAndAltimetryAct implements Serializable {
    private boolean windAgainst;

    //region Constructors
    public RoadCycling(
            int id,
            int idUser,
            int distance,
            int altimetry,
            boolean windAgainst
    ) {
        super(id, idUser, distance, altimetry);
        this.windAgainst = windAgainst;
    }

    public RoadCycling(
            int id,
            String name,
            LocalDate begin,
            LocalDate end,
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

    public RoadCycling(
            RoadCycling roadCycling
    ) {
        super(roadCycling);
        this.windAgainst = roadCycling.getWindAgainst();
    }
    //endregion

    //region Getters And Setters
    public boolean getWindAgainst() { return this.windAgainst; }

    public void setWindAgainst(boolean windAgainst) { this.windAgainst = windAgainst; }
    //endregion

    public void calculateCalories() {
        double windImpact = (this.windAgainst) ? 1.3 : 1;
        double caloriesMultiplier = 0.04; // 0.04*(1000+2.2*200)*1.3 = 74.88

        int calories = (int) (caloriesMultiplier*(this.getDistance() + 2.2*this.getAltimetry())*windImpact);
        this.setBurnedCalories(calories);
    }

    @Override
    public String toString() {
        return  "RoadCycling -{ ID: " + this.getId() +
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

    public RoadCycling clone() { return new RoadCycling(this); }

}
