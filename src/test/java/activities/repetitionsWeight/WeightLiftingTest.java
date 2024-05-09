package activities.repetitionsWeight;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeightLiftingTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        WeightLifting atividade = new WeightLifting(1, 1, 1, 3, false);
        assertNotNull(atividade);
        WeightLifting atividade2 = new WeightLifting(atividade);
        assertNotNull(atividade2);
    }

    @Test
    public void testGetHelped() {
        WeightLifting atividade = new WeightLifting(1, 1, 1, 5, true);
        assertTrue(atividade.getHelped());
    }

    @Test
    public void testSetHelped() {
        WeightLifting atividade = new WeightLifting(1, 1, 1, 2, true);
        assertTrue(atividade.getHelped());
        atividade.setHelped(false);
        assertFalse(atividade.getHelped());
    }

    @Test
    public void testCalculateCalories() {
        WeightLifting atividade = new WeightLifting(1, 1, 5, 6, false);
        atividade.calculateCalories();
        double chairAngleImpact = (atividade.getHelped()) ? 0.9 : 1;
        double caloriesMultiplierPerRepetition = 0.7;
        assertEquals(atividade.getBurnedCalories(), (int) (caloriesMultiplierPerRepetition*(atividade.getNRepetitions() + 0.04*atividade.getWeight())*chairAngleImpact));
    }
}