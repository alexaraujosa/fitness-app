package activities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class RepetitionAct extends Activity implements Serializable {
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
            LocalDateTime begin,
            LocalDateTime end,
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

    //region Getters And Setters
    public int getNRepetitions() { return this.nRepetitions; }

    public void setNRepetitions(int nRepetitions) { this.nRepetitions = nRepetitions; }
    //endregion

    public abstract void calculateCalories();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RepetitionAct that = (RepetitionAct) o;
        return getNRepetitions() == that.getNRepetitions();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nRepetitions);
    }
}
