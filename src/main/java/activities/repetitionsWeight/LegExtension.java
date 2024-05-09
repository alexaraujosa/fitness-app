package activities.repetitionsWeight;

import activities.RepetitionWithWeightsAct;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LegExtension extends RepetitionWithWeightsAct implements Serializable {
    private int chairAngle;

    //region Constructors
    public LegExtension(
            int id,
            int idUser,
            int nRepetitions,
            int weight,
            int chairAngle
    ) {
        super(id, idUser, nRepetitions, weight);
        this.chairAngle = chairAngle;
    }

    public LegExtension(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int nRepetitions,
            int weight,
            int chairAngle
    ) {
        super(id, name, begin, end, idUser, burnedCalories, heartRate, nRepetitions, weight);
        this.chairAngle = chairAngle;
    }

    public LegExtension(
            LegExtension legExtension
    ) {
        super(legExtension);
        this.chairAngle = legExtension.getChairAngle();
    }
    //endregion

    //region Getters And Setters
    public int getChairAngle() { return this.chairAngle; }

    public void setChairAngle(int chairAngle) { this.chairAngle = chairAngle; }
    //endregion

    public void calculateCalories() {
        double chairAngleImpact = 0.02*this.chairAngle;
        double caloriesMultiplierPerRepetition = 0.4; // (0.4*(12+0.04*120)*(60*0.02)) = 8.064
        int calories = (int) (caloriesMultiplierPerRepetition*(this.getNRepetitions() + 0.04*this.getWeight())*chairAngleImpact);
        this.setBurnedCalories(calories);
    }

    @Override
    public String toString() {
        return  "LegExtension -{ ID: " + this.getId() +
                " | NAME: " + this.getName() +
                " | BEGIN: " + this.getBegin().toString() +
                " | END: " + this.getEnd().toString() +
                " | BURNED CALORIES: " + this.getBurnedCalories() +
                " | ID_USER: " + this.getIdUser() +
                " | HEART_RATE: " + this.getHeartRate() +
                " | REPETITIONS: " + this.getNRepetitions() +
                " | WEIGHT: " + this.getWeight() +
                " | CHAIR ANGLE: " + this.getChairAngle() +
                " }-\n";
    }

    public LegExtension clone() { return new LegExtension(this); }

}
