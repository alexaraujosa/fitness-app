package activities.distanceAltimetry;

import activities.DistanceAndAltimetryAct;
import activities.Hard;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class MountainBiking extends DistanceAndAltimetryAct implements Hard, Serializable {
    private boolean bigTires;

    //region Constructors
    public MountainBiking(
            int id,
            int idUser,
            int distance,
            int altimetry,
            boolean bigTires
    ) {
        super(id, idUser, distance, altimetry);
        this.bigTires = bigTires;
    }

    public MountainBiking(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            int altimetry,
            boolean bigTires
    ) {
        super(id, name, begin, end, idUser, burnedCalories, heartRate, distance, altimetry);
        this.bigTires = bigTires;
    }

    public MountainBiking(
            MountainBiking mountainBiking
    ) {
        super(mountainBiking);
        this.bigTires = mountainBiking.getBigTires();
    }
    //endregion

    //region Getters And Setters
    public boolean getBigTires() { return this.bigTires; }

    public void setBigTires(boolean bigTires) { this.bigTires = bigTires; }
    //endregion

    public void calculateCalories() {
        double tiresImpact = (this.bigTires) ? 1.5 : 1;
        double caloriesMultiplier = 0.07; // 0.07*(1000+2.2*200)*1.5 = 151.2

        int calories = (int) (caloriesMultiplier*(this.getDistance() + 2.2*this.getAltimetry())*tiresImpact);
        this.setBurnedCalories(calories);
    }

    @Override
    public String toString() {
        return  "MountainBiking -{ ID: " + this.getId() +
                " | NAME: " + this.getName() +
                " | BEGIN: " + this.getBegin().toString() +
                " | END: " + this.getEnd().toString() +
                " | BURNED CALORIES: " + this.getBurnedCalories() +
                " | ID_USER: " + this.getIdUser() +
                " | HEART_RATE: " + this.getHeartRate() +
                " | DISTANCE: " + this.getDistance() +
                " | ALTIMETRY: " + this.getAltimetry() +
                " | BIG TIRES: " + this.getBigTires() +
                " }-\n";
    }

    public MountainBiking clone() { return new MountainBiking(this); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MountainBiking that = (MountainBiking) o;
        return getBigTires() == that.getBigTires();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBigTires());
    }
}
