package activities.distanceAltimetry;

import activities.DistanceAndAltimetryAct;
import activities.Hard;

public class TrailRunning extends DistanceAndAltimetryAct implements Hard {
    private boolean wetFloor;

    //region Constructors
    public TrailRunning(int id, int idUser, int distance, int altimetry, boolean wetFloor) {
        super(id, idUser, distance, altimetry);
        this.wetFloor = wetFloor;
    }

    public TrailRunning(TrailRunning trailRunning) {
        super(trailRunning);
        this.wetFloor = trailRunning.getWetFloor();
    }
    //endregion

    //region Getters And Setters
    public boolean getWetFloor() { return this.wetFloor; }

    public void setWetFloor(boolean wetFloor) { this.wetFloor = wetFloor; }
    //endregion

    public void calculateCalories() {
        double wetFloorImpact = (this.wetFloor) ? 0.9 : 1;
        double caloriesMultiplier = 0.07; // 0.07*(1000+2.2*200)*0.9 = 90.72

        int calories = (int) (caloriesMultiplier*(this.getDistance() + 2.2*this.getAltimetry())*wetFloorImpact);
        this.setBurnedCalories(calories);
    }

    @Override
    public String toString() {
        return  "TrailRunning -{ ID: " + this.getId() +
                " | NAME: " + this.getName() +
                " | BEGIN: " + this.getBegin().toString() +
                " | END: " + this.getEnd().toString() +
                " | BURNED CALORIES: " + this.getBurnedCalories() +
                " | ID_USER: " + this.getIdUser() +
                " | HEART_RATE: " + this.getHeartRate() +
                " | DISTANCE: " + this.getDistance() +
                " | ALTIMETRY: " + this.getAltimetry() +
                " | WET FLOOR: " + this.getWetFloor() +
                " }-\n";
    }

    public TrailRunning clone() { return new TrailRunning(this); }

}
