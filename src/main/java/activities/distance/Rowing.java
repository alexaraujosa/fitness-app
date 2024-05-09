package activities.distance;

import activities.DistanceAct;
import activities.Hard;

import java.io.Serializable;
import java.time.LocalDate;

public class Rowing extends DistanceAct implements Hard, Serializable {
    private int personsOnBoard;
    private boolean rowAgainstTide;

    //region Constructors
    public Rowing(
            int id,
            int idUser,
            int distance,
            int personsOnBoard,
            boolean rowAgainstTide
    ) {
        super(id, idUser, distance);
        this.personsOnBoard = personsOnBoard;
        this.rowAgainstTide = rowAgainstTide;
    }

    public Rowing(
            int id,
            String name,
            LocalDate begin,
            LocalDate end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            int personsOnBoard,
            boolean rowAgainstTide
    ) {
        super(id, name, begin, end, idUser, burnedCalories, heartRate, distance);
        this.personsOnBoard = personsOnBoard;
        this.rowAgainstTide = rowAgainstTide;
    }

    public Rowing(
            Rowing rowing
    ) {
        super(rowing);
        this.personsOnBoard = rowing.getPersonsOnBoard();
        this.rowAgainstTide = rowing.getRowAgainstTide();
    }
    //endregion

    //region Getters And Setters
    public int getPersonsOnBoard() { return this.personsOnBoard; }
    public boolean getRowAgainstTide() { return this.rowAgainstTide; }

    public void setPersonsOnBoard(int personsOnBoard) { this.personsOnBoard = personsOnBoard; }
    public void setRowAgainstTide(boolean rowAgainstTide) { this.rowAgainstTide = rowAgainstTide; }
    //endregion

    public void calculateCalories() {
        double rowImpact = (this.rowAgainstTide) ? 2.3 : 1;
        double caloriesMultiplierPerDistance = 0.08 - ((double) this.personsOnBoard /100); // (0.08 - (5 /100))*1000*2.3 = 69

        int calories = (int) (caloriesMultiplierPerDistance*this.getDistance()*rowImpact);
        this.setBurnedCalories(calories);
    }

    @Override
    public String toString() {
        return  "Rowing -{ ID: " + this.getId() +
                " | NAME: " + this.getName() +
                " | BEGIN: " + this.getBegin().toString() +
                " | END: " + this.getEnd().toString() +
                " | BURNED CALORIES: " + this.getBurnedCalories() +
                " | ID_USER: " + this.getIdUser() +
                " | HEART_RATE: " + this.getHeartRate() +
                " | DISTANCE: " + this.getDistance() +
                " | PERSONS ON BOARD: " + this.getPersonsOnBoard() +
                " | ROW AGAINST TIDE: " + this.getRowAgainstTide() +
                " }-\n";
    }

    public Rowing clone() { return new Rowing(this); }

}