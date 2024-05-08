package activities;

import java.time.LocalDate;

public abstract class RepetitionAct extends Activity{
    private int nRepetitions;

    //region Constructors
    public RepetitionAct(
            int id,
            int idUser,
            int nRepetitions
    ) {
        super(id, idUser);
        this.nRepetitions = nRepetitions;
    }

    public RepetitionAct(
            int id,
            String name,
            LocalDate begin,
            LocalDate end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int nRepetitions
    ) {
        super(id, name, begin, end, idUser, burnedCalories, heartRate);
        this.nRepetitions = nRepetitions;
    }

    public RepetitionAct(
            RepetitionAct activity
    ) {
        super(activity);
        this.nRepetitions = activity.getNRepetitions();
    }
    //endregion

    public int getNRepetitions() { return this.nRepetitions; }

    public void setNRepetitions(int nRepetitions) { this.nRepetitions = nRepetitions; }

    public abstract void calculateCalories();

}
