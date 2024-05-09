package josefinFA;

import activities.distance.Rowing;
import activities.distance.Skating;
import activities.distance.TrackRunning;
import activities.distanceAltimetry.MountainBiking;
import activities.distanceAltimetry.RoadCycling;
import activities.distanceAltimetry.RoadRunning;
import activities.distanceAltimetry.TrailRunning;
import activities.repetitions.AbdominalExercises;
import activities.repetitions.PushUps;
import activities.repetitions.Stretching;
import activities.repetitionsWeight.LegExtension;
import activities.repetitionsWeight.WeightLifting;
import exceptions.*;
import org.junit.jupiter.api.Test;
import users.*;
import utils.IDManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class JosefinFitnessAppTest {
    JosefinFitnessApp app = new JosefinFitnessApp();

    String name = "Paulo";
    String username = "paulo1234";
    LocalDate birthday = LocalDate.of(2002, Month.DECEMBER, 17);
    String address = "123 Main Street";
    String email = "paulo1234@gmail.com";
    double weight = 85.2;
    double height = 188.6;
    int heartFreq = 70;
    CasualUser caUser = new CasualUser(1,name, username, birthday, address, email, true, weight, height, heartFreq);

    private void fillApp(){
        // Adicionando 10 CasualUsers
        for (int i = 0; i < 10; i++) {
            app.addCasualUser("Casual " + name + i, "Casual " + username + i, birthday, address, email, true, weight, height, heartFreq);
        }

        // Adicionando 10 AmateurUsers
        for (int i = 0; i < 10; i++) {
            app.addAmateurUser("Amateur " + name + i, "Amateur " + username + i, birthday, address, email, true, weight, height, heartFreq);
        }

        // Adicionando 10 ProfessionalUsers
        for (int i = 0; i < 10; i++) {
            app.addProfessionalUser("Professional " + name + i, "Professional " + username + i, birthday, address, email, true, weight, height, heartFreq);
        }
    }

    @Test
    void setUserID() {
        int userID = 5;
        app.setUserID(userID);

        assertEquals(app.getUserID(), userID);
    }

    @Test
    void setSystemDate() {
        LocalDateTime systemDate = LocalDateTime.of(2020, Month.JANUARY, 1,10,10,1);
        app.setSystemDate(systemDate);
        assertEquals(app.getSystemDate(), systemDate);
    }

    @Test
    void setUserController() {
        UserController userController = new UserController();
        app.setUserController(userController);
        assertEquals(app.getUserController(), userController);
    }


    @Test
    void setIdManager() {
        IDManager idManager = new IDManager();
        app.setIdManager(idManager);
        assertEquals(app.getIdManager(), idManager);
    }

    @Test
    void login() {
        //Adding user to test login
        app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);

        app.login("paulo1234");

        try {
            app.login("jojo");
        } catch (ErrorLoggingInException e) {
            assertEquals(e.getMessage(),"User jojo does not exist");
        }
        assertEquals(app.getUserID(), app.getUserController().getUsernameID("paulo1234"));
    }

    @Test
    void logout() {

        //Adding user to test login
        app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);

        app.login("paulo1234");
        app.logout();
        assertEquals(app.getUserID(), -1);
    }

    @Test
    void getLoggedUserInfo() {

        //Adding user to test login
        app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);

        app.login("paulo1234");

        assertEquals(app.getLoggedUserInfo(), caUser.toString());
    }

    @Test
    void updateLoggedUserName() {
        app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);

        app.login("paulo1234");

        try {
            app.updateLoggedUserName("LindaFlor");
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulo1234").getName(), "LindaFlor");
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithUsername("paulo1234").toString());
    }

    @Test
    void updateLoggedUserUsername() {
        app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);
        app.addProfessionalUser("Paulão", "gigaChad", birthday, address, email, true, weight, height, heartFreq);

        app.login("paulo1234");

        try {
            app.updateLoggedUserUsername("gigaChad");
        } catch (ErrorUpdatingUserException | UsernameAlreadyExistsException e){
            assertEquals(e.getMessage(),"A User with that username already Exists!");
        }

        try {
            app.updateLoggedUserUsername("LindaFlor");
        } catch (ErrorUpdatingUserException | UsernameAlreadyExistsException e){
            System.err.println(e.getMessage());
        }

        assertFalse(app.getUserController().userWithUsernameExists("paulo1234"));
        assertTrue(app.getUserController().userWithUsernameExists("LindaFlor"));
        assertEquals(app.getUserController().getUsers().getUserWithUsername("LindaFlor").getUsername(), "LindaFlor");
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithUsername("LindaFlor").toString());
    }

    @Test
    void updateLoggedUserBirthdate() {
        app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);

        app.login("paulo1234");

        LocalDate newBirthday = LocalDate.of(2020, Month.DECEMBER, 17);
        try {
            app.updateLoggedUserBirthdate(newBirthday);
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulo1234").getBirthdate(), newBirthday);
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithUsername("paulo1234").toString());
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithId(1).toString());
    }

    @Test
    void updateLoggedUserAddress() {
        app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);

        app.login("paulo1234");

        try {
            app.updateLoggedUserAddress("Rua de abana o pinto");
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulo1234").getAddress(), "Rua de abana o pinto");
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithUsername("paulo1234").toString());
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithId(1).toString());
    }

    @Test
    void updateLoggedUserEmail() {
        app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);

        app.login("paulo1234");

        try {
            app.updateLoggedUserEmail("panadosComAtum@gmail.com");
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulo1234").getEmail(), "panadosComAtum@gmail.com");
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithUsername("paulo1234").toString());
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithId(1).toString());
    }

    @Test
    void updateLoggedUserHeight() {
        app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);

        app.login("paulo1234");

        try {
            app.updateLoggedUserHeight(195.2);
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulo1234").getHeight(), 195.2);
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithUsername("paulo1234").toString());
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithId(1).toString());
    }

    @Test
    void updateLoggedUserWeight() {
        app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);

        app.login("paulo1234");

        try {
            app.updateLoggedUserWeight(124.5);
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulo1234").getWeight(), 124.5);
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithUsername("paulo1234").toString());
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithId(1).toString());
    }

    @Test
    void updateLoggedUserHearFreq() {
        app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);

        app.login("paulo1234");

        try {
            app.updateLoggedUserHearFreq(75);
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulo1234").getHeartFreq(), 75);
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithUsername("paulo1234").toString());
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithId(1).toString());
    }

    @Test
    void addRowingToUser() {
        this.fillApp();

        app.login("Casual paulo12340");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        Rowing act = new Rowing(1, "Mundial 2018", begin, end, 1, 97, 75, 1623, 2, false);
        Rowing act2 = new Rowing(2, "Mundial 2018", begin, end, 3, 97, 75, 1623, 2, false);

        try {
            app.addRowingToUser(-1, "Mundial 2018", begin, end, 75, 1623, 2, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        try {
            app.addRowingToUser(3, "Mundial 2018", begin, end, 75, 1623, 2, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        String finalValue = app.getUserController().getUsers().getUserWithUsername("Casual paulo12340").getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act.toString(), finalValue);
        String finalValue2 = app.getUserController().getUsers().getUserWithId(3).getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act2.toString(), finalValue2);
    }

    @Test
    void addSkatingToUser() {
        this.fillApp();

        app.login("Casual paulo12340");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        Skating act = new Skating(1, "Mundial 2018", begin, end, 1, 51, 75, 1623, 2, false);
        Skating act2 = new Skating(2, "Mundial 2018", begin, end, 3, 51, 75, 1623, 2, false);

        try {
            app.addSkatingToUser(-1, "Mundial 2018", begin, end, 75, 1623, 2, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        try {
            app.addSkatingToUser(3, "Mundial 2018", begin, end, 75, 1623, 2, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        String finalValue = app.getUserController().getUsers().getUserWithUsername("Casual paulo12340").getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act.toString(), finalValue);
        String finalValue2 = app.getUserController().getUsers().getUserWithId(3).getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act2.toString(), finalValue2);
    }

    @Test
    void addTrackRunningToUser() {
        this.fillApp();

        app.login("Casual paulo12340");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        TrackRunning act = new TrackRunning(1, "Mundial 2018", begin, end, 1, 113, 75, 1623, false);
        TrackRunning act2 = new TrackRunning(2, "Mundial 2018", begin, end, 3, 113, 75, 1623, false);

        try {
            app.addTrackRunningToUser(-1, "Mundial 2018", begin, end, 75, 1623, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        try {
            app.addTrackRunningToUser(3, "Mundial 2018", begin, end, 75, 1623, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        String finalValue = app.getUserController().getUsers().getUserWithUsername("Casual paulo12340").getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act.toString(), finalValue);
        String finalValue2 = app.getUserController().getUsers().getUserWithId(3).getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act2.toString(), finalValue2);
    }

    @Test
    void addMountainBikingToUser() {
        this.fillApp();

        app.login("Casual paulo12340");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        MountainBiking act = new MountainBiking(1, "Mundial 2018", begin, end, 1, 260, 75, 1623, 952, false);
        MountainBiking act2 = new MountainBiking(2, "Mundial 2018", begin, end, 3, 260, 75, 1623, 952, false);

        try {
            app.addMountainBikingToUser(-1, "Mundial 2018", begin, end, 75, 1623, 952, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        try {
            app.addMountainBikingToUser(3, "Mundial 2018", begin, end, 75, 1623, 952, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        String finalValue = app.getUserController().getUsers().getUserWithUsername("Casual paulo12340").getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act.toString(), finalValue);
        String finalValue2 = app.getUserController().getUsers().getUserWithId(3).getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act2.toString(), finalValue2);
    }

    @Test
    void addRoadCyclingToUser() {
        this.fillApp();

        app.login("Casual paulo12340");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        RoadCycling act = new RoadCycling(1, "Mundial 2018", begin, end, 1, 148, 75, 1623, 952, false);
        RoadCycling act2 = new RoadCycling(2, "Mundial 2018", begin, end, 3, 148, 75, 1623, 952, false);

        try {
            app.addRoadCyclingToUser(-1, "Mundial 2018", begin, end, 75, 1623, 952, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        try {
            app.addRoadCyclingToUser(3, "Mundial 2018", begin, end, 75, 1623, 952, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        String finalValue = app.getUserController().getUsers().getUserWithUsername("Casual paulo12340").getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act.toString(), finalValue);
        String finalValue2 = app.getUserController().getUsers().getUserWithId(3).getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act2.toString(), finalValue2);
    }

    @Test
    void addRoadRunningToUser() {
        this.fillApp();

        app.login("Casual paulo12340");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        RoadRunning act = new RoadRunning(1, "Mundial 2018", begin, end, 1, 185, 75, 1623, 952, false);
        RoadRunning act2 = new RoadRunning(2, "Mundial 2018", begin, end, 3, 185, 75, 1623, 952, false);

        try {
            app.addRoadRunningToUser(-1, "Mundial 2018", begin, end, 75, 1623, 952, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        try {
            app.addRoadRunningToUser(3, "Mundial 2018", begin, end, 75, 1623, 952, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        String finalValue = app.getUserController().getUsers().getUserWithUsername("Casual paulo12340").getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act.toString(), finalValue);
        String finalValue2 = app.getUserController().getUsers().getUserWithId(3).getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act2.toString(), finalValue2);
    }

    @Test
    void addTrailRunningToUser() {
        this.fillApp();

        app.login("Casual paulo12340");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        TrailRunning act = new TrailRunning(1, "Mundial 2018", begin, end, 1, 260, 75, 1623, 952, false);
        TrailRunning act2 = new TrailRunning(2, "Mundial 2018", begin, end, 3, 260, 75, 1623, 952, false);

        try {
            app.addTrailRunningToUser(-1, "Mundial 2018", begin, end, 75, 1623, 952, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        try {
            app.addTrailRunningToUser(3, "Mundial 2018", begin, end, 75, 1623, 952, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        String finalValue = app.getUserController().getUsers().getUserWithUsername("Casual paulo12340").getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act.toString(), finalValue);
        String finalValue2 = app.getUserController().getUsers().getUserWithId(3).getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act2.toString(), finalValue2);
    }

    @Test
    void addAbdominalExercisesToUser() {
        this.fillApp();

        app.login("Casual paulo12340");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        AbdominalExercises act = new AbdominalExercises(1, "Mundial 2018", begin, end, 1, 486, 75, 1623, false);
        AbdominalExercises act2 = new AbdominalExercises(2, "Mundial 2018", begin, end, 3, 486, 75, 1623, false);

        try {
            app.addAbdominalExercisesToUser(-1, "Mundial 2018", begin, end, 75, 1623, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        try {
            app.addAbdominalExercisesToUser(3, "Mundial 2018", begin, end, 75, 1623, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        String finalValue = app.getUserController().getUsers().getUserWithUsername("Casual paulo12340").getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act.toString(), finalValue);
        String finalValue2 = app.getUserController().getUsers().getUserWithId(3).getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act2.toString(), finalValue2);
    }

    @Test
    void addPushUpsToUser() {
        this.fillApp();

        app.login("Casual paulo12340");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        PushUps act = new PushUps(1, "Mundial 2018", begin, end, 1, 324, 75, 1623, false);
        PushUps act2 = new PushUps(2, "Mundial 2018", begin, end, 3, 324, 75, 1623, false);

        try {
            app.addPushUpsToUser(-1, "Mundial 2018", begin, end, 75, 1623, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        try {
            app.addPushUpsToUser(3, "Mundial 2018", begin, end, 75, 1623, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        String finalValue = app.getUserController().getUsers().getUserWithUsername("Casual paulo12340").getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act.toString(), finalValue);
        String finalValue2 = app.getUserController().getUsers().getUserWithId(3).getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act2.toString(), finalValue2);
    }

    @Test
    void addStretchingToUser() {
        this.fillApp();

        app.login("Casual paulo12340");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        Stretching act = new Stretching(1, "Mundial 2018", begin, end, 1, 97, 75, 1623, false);
        Stretching act2 = new Stretching(2, "Mundial 2018", begin, end, 3, 97, 75, 1623, false);

        try {
            app.addStretchingToUser(-1, "Mundial 2018", begin, end, 75, 1623, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        try {
            app.addStretchingToUser(3, "Mundial 2018", begin, end, 75, 1623, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        String finalValue = app.getUserController().getUsers().getUserWithUsername("Casual paulo12340").getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act.toString(), finalValue);
        String finalValue2 = app.getUserController().getUsers().getUserWithId(3).getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act2.toString(), finalValue2);
    }

    @Test
    void addLegExtensionToUser() {
        this.fillApp();

        app.login("Casual paulo12340");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        LegExtension act = new LegExtension(1, "Mundial 2018", begin, end, 1, 519, 75, 1623, 12, 40);
        LegExtension act2 = new LegExtension(2, "Mundial 2018", begin, end, 3, 519, 75, 1623, 12, 40);

        try {
            app.addLegExtensionToUser(-1, "Mundial 2018", begin, end, 75, 1623, 12, 40);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        try {
            app.addLegExtensionToUser(3, "Mundial 2018", begin, end, 75, 1623, 12, 40);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        String finalValue = app.getUserController().getUsers().getUserWithUsername("Casual paulo12340").getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act.toString(), finalValue);
        String finalValue2 = app.getUserController().getUsers().getUserWithId(3).getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act2.toString(), finalValue2);
    }

    @Test
    void addWeightLiftingToUser() {
        this.fillApp();

        app.login("Casual paulo12340");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        WeightLifting act = new WeightLifting(1, "Mundial 2018", begin, end, 1, 1136, 75, 1623, 12, false);
        WeightLifting act2 = new WeightLifting(2, "Mundial 2018", begin, end, 3, 1136, 75, 1623, 12, false);

        try {
            app.addWeightLiftingToUser(-1, "Mundial 2018", begin, end, 75, 1623, 12, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        try {
            app.addWeightLiftingToUser(3, "Mundial 2018", begin, end, 75, 1623, 12, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        String finalValue = app.getUserController().getUsers().getUserWithUsername("Casual paulo12340").getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act.toString(), finalValue);
        String finalValue2 = app.getUserController().getUsers().getUserWithId(3).getActivityController().getActivities().getActivities().values().iterator().next().toString();
        assertEquals(act2.toString(), finalValue2);
    }

    @Test
    void deleteAccount() {
        this.fillApp();
//        for(User u : app.getUserController().getUsers().getUsersList()){
//            System.out.println(u.toString());
//        }

        app.login("Casual paulo12340");

        try {
            app.deleteAccount();
        } catch (ErrorRemovingUserException e) {
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserID(), -1);
        assertFalse(app.getUserController().userWithUsernameExists("Casual paulo12340"));
    }

    @Test
    void saveChanges() {
    }

    @Test
    void addCasualUser() {
        CasualUser u = new CasualUser(1, name, username, birthday, address, email, true, weight, height, heartFreq);
        app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);

        assertEquals("CasualUser", app.getUserController().getUsers().getUserWithUsername(username).getClass().getSimpleName());
        assertEquals(app.getUserController().getUsers().getUserWithUsername(username), u);
    }

    @Test
    void addAmateurUser() {
        AmateurUser u = new AmateurUser(1, name, username, birthday, address, email, true, weight, height, heartFreq);
        app.addAmateurUser(name, username, birthday, address, email, true, weight, height, heartFreq);

        assertEquals("AmateurUser", app.getUserController().getUsers().getUserWithUsername(username).getClass().getSimpleName());
        assertEquals(app.getUserController().getUsers().getUserWithUsername(username), u);
    }

    @Test
    void addProfessionalUser() {
        ProfessionalUser u = new ProfessionalUser(1, name, username, birthday, address, email, true, weight, height, heartFreq);
        app.addProfessionalUser(name, username, birthday, address, email, true, weight, height, heartFreq);

        assertEquals("ProfessionalUser", app.getUserController().getUsers().getUserWithUsername(username).getClass().getSimpleName());
        assertEquals(app.getUserController().getUsers().getUserWithUsername(username), u);
    }

    @Test
    void removeUser() {
        app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);

        app.login("paulo1234");
    }

    @Test
    void loadStats() {
    }

    @Test
    void userWithMostCaloriesBurned() {
        this.loadState();

        app.loadStats();
        app.setSystemDate(LocalDateTime.now());
        User value = app.userWithMostCaloriesBurned(LocalDateTime.of(2000,Month.MAY,1,18,12,0));
        assertEquals(value, app.getUserController().getUsers().getUserWithId(1));
    }

    @Test
    void userWithMostActivitiesCompleted() {
        this.loadState();

        app.loadStats();
        app.setSystemDate(LocalDateTime.now());
        User value = app.userWithMostActivitiesCompleted(LocalDateTime.of(2000,Month.MAY,1,18,12,0));

        System.out.println(app.getStats().getAllTimeUserWithMostActivitiesCompleted());

        assertEquals(value, app.getUserController().getUsers().getUserWithId(1));
    }

    @Test
    void mostCommonActivity() {
        this.loadState();
        assertEquals(app.mostCommonActivity(), "WeightLifting");
    }

    @Test
    void distanceDoneByUser() {
        this.loadState();

        app.setSystemDate(LocalDateTime.now());
        int value = app.distanceDoneByUser(1, LocalDateTime.of(2000,Month.MAY,1,18,12,0));

        int value2 = app.distanceDoneByUser(3, LocalDateTime.of(2000,Month.MAY,1,18,12,0));

        assertEquals(value, 4869);
        assertEquals(value2, 0);
    }

    @Test
    void altimetryDoneByUser() {
        this.loadState();

        app.setSystemDate(LocalDateTime.now());
        int value = app.altimetryDoneByUser(1, LocalDateTime.of(2000,Month.MAY,1,18,12,0));

        int value2 = app.altimetryDoneByUser(3, LocalDateTime.of(2000,Month.MAY,1,18,12,0));

        assertEquals(value, 952);
        assertEquals(value2, 0);
    }

    @Test
    void getUsersActivities() {
        this.loadState();

        try {
            System.out.println(app.getUsersActivities(2).toString());
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }


    }

    @Test
    void saveState(){
        this.fillApp();


        app.login("Casual paulo12340");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        try {
            app.addRowingToUser(-1, "Mundial 2018", begin, end, 75, 1623, 2, false);
            app.addRowingToUser(-1, "Mundial 2018", begin, end, 75, 1623, 2, false);
            app.addRowingToUser(4, "Mundial 2018", begin, end, 75, 1623, 2, false);
            app.addTrailRunningToUser(-1, "Mundial 2018", begin, end, 75, 1623, 952, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }
        try {
            app.addWeightLiftingToUser(-1, "Mundial 2018", begin, end, 75, 1623, 2, false);
            app.addWeightLiftingToUser(2, "Mundial 2018", begin, end, 75, 1623, 2, false);
            app.addWeightLiftingToUser(3, "Mundial 2018", begin, end, 75, 1623, 2, false);
            app.addWeightLiftingToUser(4, "Mundial 2018", begin, end, 75, 1623, 2, false);
        } catch (InvalidValueException | ErrorAddingActivityException e){
            System.err.println(e.getMessage());
        }

        //System.out.println(app.getUserController().getUsers().getUserWithUsername("Casual paulo12340").getActivityController().getActivities().getActivities().values().toString());

        app.saveState("TesteFile");
    }

    @Test
    void loadState() {
        assertEquals(app.getUserID(), -1);

        app.loadState("TesteFile");

        for(User u : app.getUserController().getUsers().getUsersList()){
            System.out.println(u.toString());
        }

        assertEquals(app.getUserID(), 1);
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithId(1).toString());

        app.addProfessionalUser(name, username, birthday, address, email, true, weight, height, heartFreq);

        assertEquals(app.getUserController().getUsers().getUserWithUsername(username).getId(), 31);

        System.out.println(app.getUserController().getUsers().getUserWithUsername("Casual paulo12340").getActivityController().getActivities().getActivities().values().toString());
    }

    @Test
    void testClone() {
        assertEquals(app, app.clone());
    }
}