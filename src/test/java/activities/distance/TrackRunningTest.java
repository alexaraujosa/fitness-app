package activities.distance;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrackRunningTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        TrackRunning atividade = new TrackRunning(1, 1, 1, true);
        assertNotNull(atividade);
        TrackRunning atividade2 = new TrackRunning(atividade);
        assertNotNull(atividade2);
    }

    @Test
    public void testGetHurdleJump() {
        TrackRunning atividade = new TrackRunning(1, 1, 5, true);
        assertTrue(atividade.getHurdleJump());
    }

    @Test
    public void testSetHurdleJump() {
        TrackRunning atividade = new TrackRunning(1, 1, 1, true);
        assertTrue(atividade.getHurdleJump());
        atividade.setHurdleJump(false);
        assertFalse(atividade.getHurdleJump());
    }

    @Test
    public void testCalculateCalories() {
        TrackRunning atividade = new TrackRunning(1, 1, 300, true);
        atividade.calculateCalories();
        double hurdleImpact = (atividade.getHurdleJump()) ? 1.4 : 1;
        double caloriesMultiplierPerDistance = 0.07;
        assertEquals(atividade.getBurnedCalories(), (int) (caloriesMultiplierPerDistance*atividade.getDistance()*hurdleImpact));
    }
}