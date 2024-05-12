package activities.repetitions;

import activities.Hard;
import activities.RepetitionAct;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class PushUps extends RepetitionAct implements Hard, Serializable {
    public boolean diamondIntercalated;

    //region Constructors
    public PushUps(
            int id,
            int idUser,
            int nRepetitions,
            boolean diamondIntercalated
    ) {
        super(id, idUser, nRepetitions);
        this.diamondIntercalated = diamondIntercalated;
    }

    public PushUps(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int nRepetitions,
            boolean diamondIntercalated
    ) {
        super(id, name, begin, end, idUser, burnedCalories, heartRate, nRepetitions);
        this.diamondIntercalated = diamondIntercalated;
    }

    public PushUps(
            PushUps pushUps
    ) {
        super(pushUps);
        this.diamondIntercalated = pushUps.getDiamondIntercalated();
    }
    //endregion

    //region Getters And Setters
    public boolean getDiamondIntercalated() { return this.diamondIntercalated; }

    public void setDiamondIntercalated(boolean diamondIntercalated) { this.diamondIntercalated = diamondIntercalated; }
    //endregion

    public void calculateCalories() {
        double diamondImpact = (this.diamondIntercalated) ? 1.3 : 1;
        double caloriesMultiplierPerRepetition = 0.2;

        int calories = (int) (caloriesMultiplierPerRepetition*this.getNRepetitions()*diamondImpact); // 0.2*20*1.3 = 5.2
        this.setBurnedCalories(calories);
    }

    @Override
    public String toString() {
        return  "PushUps -{ ID: " + this.getId() +
                " | NAME: " + this.getName() +
                " | BEGIN: " + this.getBegin().toString() +
                " | END: " + this.getEnd().toString() +
                " | BURNED CALORIES: " + this.getBurnedCalories() +
                " | ID_USER: " + this.getIdUser() +
                " | HEART_RATE: " + this.getHeartRate() +
                " | REPETITIONS: " + this.getNRepetitions() +
                " | DIAMOND INTERCALATED: " + this.getDiamondIntercalated() +
                " }-\n";
    }

    public PushUps clone() { return new PushUps(this); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PushUps pushUps = (PushUps) o;
        return getDiamondIntercalated() == pushUps.getDiamondIntercalated();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDiamondIntercalated());
    }
}
