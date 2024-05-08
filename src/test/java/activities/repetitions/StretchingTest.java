package activities.repetitions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StretchingTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        Stretching atividade = new Stretching(1, 1, 1, true);
        assertNotNull(atividade);
        Stretching atividade2 = new Stretching(atividade);
        assertNotNull(atividade2);
    }

    @Test
    public void testGetHelped() {
        Stretching atividade = new Stretching(1, 1, 1, true);
        assertTrue(atividade.getHelped());
    }

    @Test
    public void testSetHelped() {
        Stretching atividade = new Stretching(1, 1, 1, true);
        assertTrue(atividade.getHelped());
        atividade.setHelped(false);
        assertFalse(atividade.getHelped());
    }

    @Test
    public void testCalculateCalories() {
        Stretching atividade = new Stretching(1, 1, 5, true);
        atividade.calculateCalories();
        double helpImpact = (atividade.getHelped()) ? 1.3 : 1;
        double caloriesMultiplierPerRepetition = 0.06;
        assertEquals(atividade.getBurnedCalories(), (int) (caloriesMultiplierPerRepetition*atividade.getNRepetitions()*helpImpact));
    }
}