package activities.distanceAltimetry;

import activities.distance.Rowing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MountainBikingTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        MountainBiking atividade = new MountainBiking(1, 1, 1, 1, true);
        assertNotNull(atividade);
        MountainBiking atividade2 = new MountainBiking(atividade);
        assertNotNull(atividade2);
    }

    @Test
    public void testGetBigTires() {
        MountainBiking atividade = new MountainBiking(1, 1, 1, 5, true);
        assertTrue(atividade.getBigTires());
    }

    @Test
    public void testSetBigTires() {
        MountainBiking atividade = new MountainBiking(1, 1, 1, 1, true);
        assertTrue(atividade.getBigTires());
        atividade.setBigTires(false);
        assertFalse(atividade.getBigTires());
    }

    @Test
    public void testCalculateCalories() {
        MountainBiking atividade = new MountainBiking(1, 1, 300, 5, true);
        atividade.calculateCalories();
        double tiresImpact = (atividade.getBigTires()) ? 1.5 : 1;
        double caloriesMultiplier = 0.07;
        assertEquals(atividade.getBurnedCalories(), (int) (caloriesMultiplier*(atividade.getDistance() + 2.2*atividade.getAltimetry())*tiresImpact));
    }
}