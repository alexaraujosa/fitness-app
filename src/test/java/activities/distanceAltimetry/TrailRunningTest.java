package activities.distanceAltimetry;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrailRunningTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        TrailRunning atividade = new TrailRunning(1, 1, 1, 1, true);
        assertNotNull(atividade);
        TrailRunning atividade2 = new TrailRunning(atividade);
        assertNotNull(atividade2);
    }

    @Test
    public void testGetWetFloor() {
        TrailRunning atividade = new TrailRunning(1, 1, 1, 5, true);
        assertTrue(atividade.getWetFloor());
    }

    @Test
    public void testSetWetFloor() {
        TrailRunning atividade = new TrailRunning(1, 1, 1, 1, true);
        assertTrue(atividade.getWetFloor());
        atividade.setWetFloor(false);
        assertFalse(atividade.getWetFloor());
    }

    @Test
    public void testCalculateCalories() {
        TrailRunning atividade = new TrailRunning(1, 1, 300, 5, true);
        atividade.calculateCalories();
        double wetFloorImpact = (atividade.getWetFloor()) ? 0.9 : 1;
        double caloriesMultiplier = 0.07;
        assertEquals(atividade.getBurnedCalories(), (int) (caloriesMultiplier*(atividade.getDistance() + 2.2*atividade.getAltimetry())*wetFloorImpact));
    }
}