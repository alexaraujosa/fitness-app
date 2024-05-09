package activities.repetitionsWeight;

import activities.Hard;
import activities.RepetitionWithWeightsAct;

import java.io.Serializable;
import java.time.LocalDate;

public class WeightLifting extends RepetitionWithWeightsAct implements Hard, Serializable {
    private boolean helped;

    //region Constructors
    public WeightLifting(
            int id,
            int idUser,
            int nRepetitions,
            int weight,
            boolean helped
    ) {
        super(id, idUser, nRepetitions, weight);
        this.helped = helped;
    }

    public WeightLifting(
            int id,
            String name,
            LocalDate begin,
            LocalDate end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int nRepetitions,
            int weight,
            boolean helped
    ) {
        super(id, name, begin, end, idUser, burnedCalories, heartRate, nRepetitions, weight);
        this.helped = helped;
    }

    public WeightLifting(
            WeightLifting weightLifting
    ) {
        super(weightLifting);
        this.helped = weightLifting.getHelped();
    }
    //endregion

    //region Getters And Setters
    public boolean getHelped() { return this.helped; }

    public void setHelped(boolean helped) { this.helped = helped; }
    //endregion

    public void calculateCalories() {
        double chairAngleImpact = (this.helped) ? 0.9 : 1;
        double caloriesMultiplierPerRepetition = 0.7; // (0.7*(12+0.04*60)*(0.9)) = 9.072
        int calories = (int) (caloriesMultiplierPerRepetition*(this.getNRepetitions() + 0.04*this.getWeight())*chairAngleImpact);
        this.setBurnedCalories(calories);
    }

    @Override
    public String toString() {
        return  "WeightLifting -{ ID: " + this.getId() +
                " | NAME: " + this.getName() +
                " | BEGIN: " + this.getBegin().toString() +
                " | END: " + this.getEnd().toString() +
                " | BURNED CALORIES: " + this.getBurnedCalories() +
                " | ID_USER: " + this.getIdUser() +
                " | HEART_RATE: " + this.getHeartRate() +
                " | REPETITIONS: " + this.getNRepetitions() +
                " | WEIGHT: " + this.getWeight() +
                " | HELPED: " + this.getHelped() +
                " }-\n";
    }

    public WeightLifting clone() { return new WeightLifting(this); }

}