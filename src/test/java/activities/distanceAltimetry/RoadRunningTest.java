package activities.distanceAltimetry;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoadRunningTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        RoadRunning atividade = new RoadRunning(1, 1, 1, 1, true);
        assertNotNull(atividade);
        RoadRunning atividade2 = new RoadRunning(atividade);
        assertNotNull(atividade2);
    }

    @Test
    public void testGetWindAgainst() {
        RoadRunning atividade = new RoadRunning(1, 1, 1, 5, true);
        assertTrue(atividade.getWindAgainst());
    }

    @Test
    public void testSetWingAgainst() {
        RoadRunning atividade = new RoadRunning(1, 1, 1, 1, true);
        assertTrue(atividade.getWindAgainst());
        atividade.setWindAgainst(false);
        assertFalse(atividade.getWindAgainst());
    }

    @Test
    public void testCalculateCalories() {
        RoadRunning atividade = new RoadRunning(1, 1, 300, 5, true);
        atividade.calculateCalories();
        double windImpact = (atividade.getWindAgainst()) ? 1.5 : 1;
        double caloriesMultiplier = 0.05;
        assertEquals(atividade.getBurnedCalories(), (int) (caloriesMultiplier*(atividade.getDistance() + 2.2*atividade.getAltimetry())*windImpact));
    }
}