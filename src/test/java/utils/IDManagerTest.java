package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IDManagerTest {
    IDManager idManager = new IDManager();

    @Test
    void generateUniqueUserID() {
        List<Integer> userIDs = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int id = idManager.generateUniqueUserID();
            userIDs.add(id);
        }
        for (int i = 1; i <= 100; i++) {
            int valueToCount = i;
            long nr = userIDs.stream().filter(value -> value == valueToCount).count();
            assertEquals(1, nr);
        };
    }

    @Test
    void generateUniqueActivityID() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testToString() {
        for (int i = 0; i < 100; i++) {
            idManager.generateUniqueUserID();
            idManager.generateUniqueActivityID();
        }

        System.out.println(idManager.toString());
    }
}