package activities.repetitionsWeight;

import activities.repetitions.Stretching;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LegExtensionTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        LegExtension atividade = new LegExtension(1, 1, 1, 3, 90);
        assertNotNull(atividade);
        LegExtension atividade2 = new LegExtension(atividade);
        assertNotNull(atividade2);
    }

    @Test
    public void testGetChairAngle() {
        LegExtension atividade = new LegExtension(1, 1, 1, 5, 90);
        assertEquals(atividade.getChairAngle(), 90);
    }

    @Test
    public void testSetChairAngle() {
        LegExtension atividade = new LegExtension(1, 1, 1, 2, 90);
        assertEquals(atividade.getChairAngle(), 90);
        atividade.setChairAngle(78);
        assertEquals(atividade.getChairAngle(), 78);
    }

    @Test
    public void testCalculateCalories() {
        LegExtension atividade = new LegExtension(1, 1, 5, 6, 90);
        atividade.calculateCalories();
        double chairAngleImpact = 0.02*atividade.getChairAngle();
        double caloriesMultiplierPerRepetition = 0.4;
        assertEquals(atividade.getBurnedCalories(), (int) (caloriesMultiplierPerRepetition*(atividade.getNRepetitions() + 0.04*atividade.getWeight())*chairAngleImpact));
    }
}