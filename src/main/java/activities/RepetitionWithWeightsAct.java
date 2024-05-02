package activities;

import users.User;

public class RepetitionWithWeightsAct extends Activity{
    private int nRepetitions;
    private int weight;

    public RepetitionWithWeightsAct(int id, User user, int nRepetitions, int weight) {
        super(id, user);
        this.nRepetitions = Math.max(nRepetitions, 0);
        this.weight = Math.max(weight, 0);
    }

    public RepetitionWithWeightsAct(RepetitionWithWeightsAct activity) {
        super(activity);
        this.nRepetitions = activity.getNRepetitions();
        this.weight = activity.getWeight();
    }

    public int getNRepetitions() { return this.nRepetitions; }
    public int getWeight() { return this.weight; }

    public void setNRepetitions(int nRepetitions) { this.nRepetitions = Math.max(nRepetitions, 0); }
    public void setWeight(int weight) { this.weight = Math.max(weight, 0); }

    public int calculateCalories(User user) {
        return 1; // TODO
    }

    public RepetitionWithWeightsAct clone() { return new RepetitionWithWeightsAct(this); }
}
