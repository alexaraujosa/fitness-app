package activities.repetitions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PushUpsTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        PushUps atividade = new PushUps(1, 1, 1, true);
        assertNotNull(atividade);
        PushUps atividade2 = new PushUps(atividade);
        assertNotNull(atividade2);
    }

    @Test
    public void testGetDiamondIntercalated() {
        PushUps atividade = new PushUps(1, 1, 1, true);
        assertTrue(atividade.getDiamondIntercalated());
    }

    @Test
    public void testSetDiamondIntercalated() {
        PushUps atividade = new PushUps(1, 1, 1, true);
        assertTrue(atividade.getDiamondIntercalated());
        atividade.setDiamondIntercalated(false);
        assertFalse(atividade.getDiamondIntercalated());
    }

    @Test
    public void testCalculateCalories() {
        PushUps atividade = new PushUps(1, 1, 5, true);
        atividade.calculateCalories();
        double diamondImpact = (atividade.getDiamondIntercalated()) ? 1.3 : 1;
        double caloriesMultiplierPerRepetition = 0.2;
        assertEquals(atividade.getBurnedCalories(), (int) (caloriesMultiplierPerRepetition*atividade.getNRepetitions()*diamondImpact));
    }
}