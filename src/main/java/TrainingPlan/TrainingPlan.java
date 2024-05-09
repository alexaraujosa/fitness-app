package TrainingPlan;

import activities.Activity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * NOTE: Isto vai dar dor de cabeça lmao
 */
public class TrainingPlan implements Serializable {
    private int id;
    private List<Activity> activities;
    private LocalDate doDate;
    private boolean repeat;
    private int calories;

    public int getId() {
        return this.id;
    }

    //TODO: O que o utilziador pode escolher no plano de treino?
    /**
     * o tipo de atividades que quer ter
     * o numero maximo de atividades por dia e numero maximo de atividades distintas
     * a recurrencia semanal das atividades
     * o consumo calorico minimo que pertende ter
     */
    // Ao tentar adicionar o plano de treino terão de ser feitas as verificações que estão em user.addTrainingplan
}
