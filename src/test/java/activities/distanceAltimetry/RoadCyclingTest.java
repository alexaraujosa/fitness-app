package activities.distanceAltimetry;

import activities.distance.Rowing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoadCyclingTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        RoadCycling atividade = new RoadCycling(1, 1, 1, 1, true);
        assertNotNull(atividade);
        RoadCycling atividade2 = new RoadCycling(atividade);
        assertNotNull(atividade2);
    }

    @Test
    public void testGetWindAgainst() {
        RoadCycling atividade = new RoadCycling(1, 1, 1, 5, true);
        assertTrue(atividade.getWindAgainst());
    }

    @Test
    public void testSetWingAgainst() {
        RoadCycling atividade = new RoadCycling(1, 1, 1, 1, true);
        assertTrue(atividade.getWindAgainst());
        atividade.setWindAgainst(false);
        assertFalse(atividade.getWindAgainst());
    }

    @Test
    public void testCalculateCalories() {
        RoadCycling atividade = new RoadCycling(1, 1, 300, 5, true);
        atividade.calculateCalories();
        double windImpact = (atividade.getWindAgainst()) ? 1.3 : 1;
        double caloriesMultiplier = 0.04;
        assertEquals(atividade.getBurnedCalories(), (int) (caloriesMultiplier*(atividade.getDistance() + 2.2*atividade.getAltimetry())*windImpact));
    }
}