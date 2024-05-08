package activities.distance;

import activities.DistanceAct;

import java.time.LocalDate;

public class Skating extends DistanceAct {
    private double skateWeight;
    private boolean freestyle;

    //region Constructors
    public Skating(
            int id,
            int idUser,
            int distance,
            double skateWeight,
            boolean freestyle
    ) {
        super(id, idUser, distance);
        this.skateWeight = skateWeight;
        this.freestyle = freestyle;
    }

    public Skating(
            int id,
            String name,
            LocalDate begin,
            LocalDate end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            double skateWeight,
            boolean freestyle
    ) {
        super(id, name, begin, end, idUser, burnedCalories, heartRate, distance);
        this.skateWeight = skateWeight;
        this.freestyle = freestyle;
    }

    public Skating(
            Skating skating
    ) {
        super(skating);
        this.skateWeight = skating.getSkateWeight();
        this.freestyle = skating.getFreestyle();
    }
    //endregion

    //region Getters And Setters
    public double getSkateWeight() { return this.skateWeight; }
    public boolean getFreestyle() { return this.freestyle; }

    public void setSkateWeight(double skateWeight) { this.skateWeight = skateWeight; }
    public void setFreestyle(boolean freestyle) { this.freestyle = freestyle; }
    //endregion

    public void calculateCalories() {
        double freestyleImpact = (this.freestyle) ? 1.7 : 1;
        double caloriesMultiplierPerDistance = 0.03 + this.skateWeight/1000;

        int calories = (int) (caloriesMultiplierPerDistance*this.getDistance()*freestyleImpact); // (0.03+5/1000)*1000*1.7 = 59.5
        this.setBurnedCalories(calories);
    }

    @Override
    public String toString() {
        return  "Skating -{ ID: " + this.getId() +
                " | NAME: " + this.getName() +
                " | BEGIN: " + this.getBegin().toString() +
                " | END: " + this.getEnd().toString() +
                " | BURNED CALORIES: " + this.getBurnedCalories() +
                " | ID_USER: " + this.getIdUser() +
                " | HEART_RATE: " + this.getHeartRate() +
                " | DISTANCE: " + this.getDistance() +
                " | FREESTYLE: " + this.getFreestyle() +
                " | SKATE WEIGHT: " + this.getSkateWeight() +
                " }-\n";
    }

    public Skating clone() { return new Skating(this); }

}