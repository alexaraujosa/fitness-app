package activities.repetitions;

import activities.RepetitionAct;

import java.io.Serializable;
import java.time.LocalDate;

public class AbdominalExercises extends RepetitionAct implements Serializable {
    private boolean helped;

    //region Constructors
    public AbdominalExercises(
            int id,
            int idUser,
            int nRepetitions,
            boolean helped
    ) {
        super(id, idUser, nRepetitions);
        this.helped = helped;
    }

    public AbdominalExercises(
            int id,
            String name,
            LocalDate begin,
            LocalDate end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int nRepetitions,
            boolean helped
    ) {
        super(id, name, begin, end, idUser, burnedCalories, heartRate, nRepetitions);
        this.helped = helped;
    }

    public AbdominalExercises(
            AbdominalExercises abdominalExercises
    ) {
        super(abdominalExercises);
        this.helped = abdominalExercises.getHelped();
    }
    //endregion

    //region Getters And Setters
    public boolean getHelped() { return this.helped; }

    public void setHelped(boolean helped) { this.helped = helped; }
    //endregion

    public void calculateCalories() {
        double helpImpact = (this.helped) ? 0.8 : 1;
        double caloriesMultiplierPerRepetition = 0.3;

        int calories = (int) (caloriesMultiplierPerRepetition*this.getNRepetitions()*helpImpact); // 0.3*20*0.8 = 4.8
        this.setBurnedCalories(calories);
    }

    @Override
    public String toString() {
        return  "AbdominalExercises -{ ID: " + this.getId() +
                " | NAME: " + this.getName() +
                " | BEGIN: " + this.getBegin().toString() +
                " | END: " + this.getEnd().toString() +
                " | BURNED CALORIES: " + this.getBurnedCalories() +
                " | ID_USER: " + this.getIdUser() +
                " | HEART_RATE: " + this.getHeartRate() +
                " | REPETITIONS: " + this.getNRepetitions() +
                " | HELPED: " + this.getHelped() +
                " }-\n";
    }

    public AbdominalExercises clone() { return new AbdominalExercises(this); }

}
