package activities.repetitions;

import activities.distance.Rowing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbdominalExercisesTest {

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testConstructor() {
        AbdominalExercises atividade = new AbdominalExercises(1, 1, 1, true);
        assertNotNull(atividade);
        AbdominalExercises atividade2 = new AbdominalExercises(atividade);
        assertNotNull(atividade2);
    }

    @Test
    public void testGetHelped() {
        AbdominalExercises atividade = new AbdominalExercises(1, 1, 1, true);
        assertTrue(atividade.getHelped());
    }

    @Test
    public void testSetHelped() {
        AbdominalExercises atividade = new AbdominalExercises(1, 1, 1, true);
        assertTrue(atividade.getHelped());
        atividade.setHelped(false);
        assertFalse(atividade.getHelped());
    }

    @Test
    public void testCalculateCalories() {
        AbdominalExercises atividade = new AbdominalExercises(1, 1, 5, true);
        atividade.calculateCalories();
        double helpImpact = (atividade.getHelped()) ? 0.8 : 1;
        double caloriesMultiplierPerRepetition = 0.3;
        assertEquals(atividade.getBurnedCalories(), (int) (caloriesMultiplierPerRepetition*atividade.getNRepetitions()*helpImpact));
    }
}