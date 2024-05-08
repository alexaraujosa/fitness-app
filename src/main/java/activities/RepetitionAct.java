package activities;

public abstract class RepetitionAct extends Activity{
    private int nRepetitions;

    public RepetitionAct(int id, int idUser, int nRepetitions) {
        super(id, idUser);
        this.nRepetitions = nRepetitions;
    }

    public RepetitionAct(RepetitionAct activity) {
        super(activity);
        this.nRepetitions = activity.getNRepetitions();
    }

    public int getNRepetitions() { return this.nRepetitions; }

    public void setNRepetitions(int nRepetitions) { this.nRepetitions = nRepetitions; }

    public abstract void calculateCalories();

}
