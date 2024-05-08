package activities.distance;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkatingTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        Skating atividade = new Skating(1, 1, 1, 1, true);
        assertNotNull(atividade);
        Skating atividade2 = new Skating(atividade);
        assertNotNull(atividade2);
    }

    @Test
    public void testGetSkateWeight() {
        Skating atividade = new Skating(1, 1, 1, 5, true);
        assertEquals(atividade.getSkateWeight(), 5);
    }

    @Test
    public void testGetFreestyle() {
        Skating atividade = new Skating(1, 1, 1, 5, true);
        assertTrue(atividade.getFreestyle());
    }

    @Test
    public void testSetSkateWeight() {
        Skating atividade = new Skating(1, 1, 1, 1, true);
        assertNotEquals(atividade.getSkateWeight(), 50);
        atividade.setSkateWeight(50);
        assertEquals(atividade.getSkateWeight(), 50);
    }

    @Test
    public void testSetFreestyle() {
        Skating atividade = new Skating(1, 1, 1, 5, false);
        assertFalse(atividade.getFreestyle());
        atividade.setFreestyle(true);
        assertTrue(atividade.getFreestyle());
    }

    @Test
    public void testCalculateCalories() {
        Skating atividade = new Skating(1, 1, 300, 5, true);
        atividade.calculateCalories();
        double freestyleImpact = (atividade.getFreestyle()) ? 1.7 : 1;
        double caloriesMultiplierPerDistance = 0.03 + atividade.getSkateWeight()/1000;
        assertEquals(atividade.getBurnedCalories(), (int) (caloriesMultiplierPerDistance*atividade.getDistance()*freestyleImpact));
    }
}