package utils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class IDManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Set<Integer> usedUserIDs;
    private final Set<Integer> usedActivityIDs;
    private final Set<Integer> usedTrainingPlanIDs;

    //region Constructors
    public IDManager() {
        this.usedUserIDs = new HashSet<>();
        this.usedActivityIDs = new HashSet<>();
        this.usedTrainingPlanIDs = new HashSet<>();
    }

    public IDManager(
            Set<Integer> usedUserIDs,
            Set<Integer> usedActivityIDs,
            Set<Integer> usedTrainingPlanIDs
    ){
        this.usedUserIDs = usedUserIDs;
        this.usedActivityIDs = usedActivityIDs;
        this.usedTrainingPlanIDs = usedTrainingPlanIDs;
    }

    public IDManager(
            IDManager manager
    ){
        this.usedUserIDs = manager.getUsedUserIDs();
        this.usedActivityIDs = manager.getUsedActivityIDs();
        this.usedTrainingPlanIDs = manager.getUsedTrainingPlanIDs();
    }
    //endregion

    //region Getters
    public Set<Integer> getUsedUserIDs() {
        return new HashSet<>(this.usedUserIDs);
    }

    public Set<Integer> getUsedActivityIDs() {
        return new HashSet<>(this.usedActivityIDs);
    }

    public Set<Integer> getUsedTrainingPlanIDs() { return new HashSet<>(this.usedTrainingPlanIDs); }
    //endregion

    //region Generators
    public int generateUniqueUserID() {
        if(usedUserIDs.isEmpty()) {
            usedUserIDs.add(1);
            return 1;
        }
        int newID = this.usedUserIDs.size()+1;
        usedUserIDs.add(newID);
        return newID;
    }

    public int generateUniqueActivityID() {
        if(usedActivityIDs.isEmpty()) {
            usedActivityIDs.add(1);
            return 1;
        }
        int newID = this.usedActivityIDs.size()+1;
        usedActivityIDs.add(newID);
        return newID;
    }

    public int generateUniqueTrainingPlanID() {
        if(usedTrainingPlanIDs.isEmpty()) {
            usedTrainingPlanIDs.add(1);
            return 1;
        }
        int newID = this.usedTrainingPlanIDs.size()+1;
        usedTrainingPlanIDs.add(newID);
        return newID;
    }

    private int generateRandomID() {
        Random random = new Random();
        return random.nextInt(Integer.MAX_VALUE); // Generate a random int ID
    }
    //endregion

    //region Removers
    public void removeUserIdEntry(int id) {
        this.usedUserIDs.remove(id);
    }
    //endregion

    @Override
    public String toString() {
        return "IDManager{" + '\n' +
                "\tusedUserIDs=" + usedUserIDs.toString() + '\n' +
                "\tusedActivityIDs=" + usedActivityIDs.toString() + '\n' +
                "\tusedTrainingPlanIDs=" + usedTrainingPlanIDs.toString() + '\n' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IDManager idManager = (IDManager) o;
        return  this.getUsedActivityIDs().equals(idManager.getUsedActivityIDs()) &&
                this.getUsedActivityIDs().equals(idManager.getUsedActivityIDs()) &&
                this.getUsedTrainingPlanIDs().equals(idManager.getUsedTrainingPlanIDs());
    }

    public IDManager clone(){
        return new IDManager(this);
    }

}
