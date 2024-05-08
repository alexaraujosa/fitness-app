package activities;

public abstract class RepetitionWithWeightsAct extends Activity{
    private int nRepetitions;
    private int weight;

    public RepetitionWithWeightsAct(int id, int idUser, int nRepetitions, int weight) {
        super(id, idUser);
        this.nRepetitions = nRepetitions;
        this.weight = weight;
    }

    public RepetitionWithWeightsAct(RepetitionWithWeightsAct activity) {
        super(activity);
        this.nRepetitions = activity.getNRepetitions();
        this.weight = activity.getWeight();
    }

    public int getNRepetitions() { return this.nRepetitions; }
    public int getWeight() { return this.weight; }

    public void setNRepetitions(int nRepetitions) { this.nRepetitions = nRepetitions; }
    public void setWeight(int weight) { this.weight = weight; }

    public abstract void calculateCalories();

}
