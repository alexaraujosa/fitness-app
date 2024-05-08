package activities.repetitions;

import activities.RepetitionAct;

import java.time.LocalDate;

public class Stretching extends RepetitionAct {
    private boolean helped;

    //region Constructors
    public Stretching(
            int id,
            int idUser,
            int nRepetitions,
            boolean helped
    ) {
        super(id, idUser, nRepetitions);
        this.helped = helped;
    }

    public Stretching(
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

    public Stretching(
            Stretching stretching
    ) {
        super(stretching);
        this.helped = stretching.getHelped();
    }
    //endregion

    //region Getters And Setters
    public boolean getHelped() { return this.helped; }

    public void setHelped(boolean helped) { this.helped = helped; }
    //endregion

    public void calculateCalories() {
        double helpImpact = (this.helped) ? 1.3 : 1;
        double caloriesMultiplierPerRepetition = 0.06;

        int calories = (int) (caloriesMultiplierPerRepetition*this.getNRepetitions()*helpImpact); // 0.06*20*1.3 = 1.56
        this.setBurnedCalories(calories);
    }

    @Override
    public String toString() {
        return  "Stretching -{ ID: " + this.getId() +
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

    public Stretching clone() { return new Stretching(this); }

}
