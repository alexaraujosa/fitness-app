package activities;

import users.User;

public class RepetitionAct extends Activity{
    private int nRepetitions;

    public RepetitionAct(int id, int idUser, int nRepetitions) {
        super(id, idUser);
        this.nRepetitions = Math.max(nRepetitions, 0);
    }

    public RepetitionAct(RepetitionAct activity) {
        super(activity);
        this.nRepetitions = activity.getNRepetitions();
    }

    public int getNRepetitions() { return this.nRepetitions; }

    public void setNRepetitions(int nRepetitions) { this.nRepetitions = Math.max(nRepetitions, 0); }

    public int calculateCalories(User user) {
        return 1; // TODO
    }

    public RepetitionAct clone() { return new RepetitionAct(this); }
}
