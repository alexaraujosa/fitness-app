package activities.distance;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RowingTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        Rowing atividade = new Rowing(1, 1, 1, 1, true);
        assertNotNull(atividade);
        Rowing atividade2 = new Rowing(atividade);
        assertNotNull(atividade2);
    }

    @Test
    public void testGetPersonsOnBoard() {
        Rowing atividade = new Rowing(1, 1, 1, 5, true);
        assertEquals(atividade.getPersonsOnBoard(), 5);
    }

    @Test
    public void testGetRowAgainstTide() {
        Rowing atividade = new Rowing(1, 1, 1, 5, true);
        assertTrue(atividade.getRowAgainstTide());
    }

    @Test
    public void testSetPersonsOnBoard() {
        Rowing atividade = new Rowing(1, 1, 1, 1, true);
        assertNotEquals(atividade.getPersonsOnBoard(), 50);
        atividade.setPersonsOnBoard(50);
        assertEquals(atividade.getPersonsOnBoard(), 50);
    }

    @Test
    public void testSetRowAgainstTide() {
        Rowing atividade = new Rowing(1, 1, 1, 5, false);
        assertFalse(atividade.getRowAgainstTide());
        atividade.setRowAgainstTide(true);
        assertTrue(atividade.getRowAgainstTide());
    }

    @Test
    public void testCalculateCalories() {
        Rowing atividade = new Rowing(1, 1, 300, 5, true);
        atividade.calculateCalories();
        double rowImpact = (atividade.getRowAgainstTide()) ? 2.3 : 1;
        double caloriesMultiplierPerDistance = 0.08 - ((double) atividade.getPersonsOnBoard() /100);
        assertEquals(atividade.getBurnedCalories(), (int) (caloriesMultiplierPerDistance*atividade.getDistance()*rowImpact));
    }
}