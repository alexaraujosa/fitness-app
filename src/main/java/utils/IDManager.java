package utils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class IDManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Set<Integer> usedUserIDs;
    private final Set<Integer> usedActivityIDs;

    public IDManager() {
        this.usedUserIDs = new HashSet<>();
        this.usedActivityIDs = new HashSet<>();
    }

    public int generateUniqueUserID() {
        int newID;
        do {
            newID = generateRandomID();
        } while (usedUserIDs.contains(newID));
        usedUserIDs.add(newID);
        return newID;
    }

    public int generateUniqueActivityID() {
        int newID;
        do {
            newID = generateRandomID();
        } while (usedActivityIDs.contains(newID));
        usedActivityIDs.add(newID);
        return newID;
    }

    private int generateRandomID() {
        Random random = new Random();
        return random.nextInt(Integer.MAX_VALUE); // Generate a random int ID
    }

}
