package activities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class RepetitionWithWeightsAct extends Activity implements Serializable {
    private int nRepetitions;
    private int weight;

    //region Constructors
    public RepetitionWithWeightsAct(
            int id,
            int idUser,
            int nRepetitions,
            int weight
    ) {
        super(id, idUser);
        this.nRepetitions = nRepetitions;
        this.weight = weight;
    }

    public RepetitionWithWeightsAct(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int nRepetitions,
            int weight
    ) {
        super(id, name, begin, end, idUser, burnedCalories, heartRate);
        this.nRepetitions = nRepetitions;
        this.weight = weight;
    }

    public RepetitionWithWeightsAct(
            RepetitionWithWeightsAct activity
    ) {
        super(activity);
        this.nRepetitions = activity.getNRepetitions();
        this.weight = activity.getWeight();
    }
    //endregion

    //region Getters And Setters
    public int getNRepetitions() { return this.nRepetitions; }
    public int getWeight() { return this.weight; }

    public void setNRepetitions(int nRepetitions) { this.nRepetitions = nRepetitions; }
    public void setWeight(int weight) { this.weight = weight; }
    //endregion

    public abstract void calculateCalories();

}
