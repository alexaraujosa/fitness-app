package josefinFA;

import activities.Activity;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import users.*;
import utils.IDManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class JosefinFitnessAppTest {
    JosefinFitnessApp app = new JosefinFitnessApp();

    String name = "Paulo";
    String username = "paulex";
    LocalDate birthday = LocalDate.of(2002, Month.DECEMBER, 17);
    String address = "123 Main Street";
    String email = "paulo1234@gmail.com";
    double weight = 85.2;
    double height = 188.6;
    int heartFreq = 70;
    CasualUser caUser = new CasualUser(1,name, username, birthday, address, email, true, weight, height, heartFreq);

    public String generateRandomAlphabeticStrings() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private void populateAppWithUsers(){
        Random rand = new Random();
        int numberOfUsers = rand.nextInt(100,200);
        int numberOfCasuals = 0;
        int numberOfAmateurs = 0;
        int numberOfProfessionals = 0;
        System.out.println("--{ POPULATE APP }--");
        System.out.println("Added Users: " + numberOfUsers);

        for(int i = 0; i < numberOfUsers; i++){
            String name = "Paulo";
            String username = "paulex";
            int birthYear = rand.nextInt(1900, 2014);
            int birthMonth = rand.nextInt(1,12);
            int birthDay = rand.nextInt(1, 28);
            LocalDate birthdate = LocalDate.of(birthYear, birthMonth, birthDay);
            String address = generateRandomAlphabeticStrings();
            String email = generateRandomAlphabeticStrings() + "@gmail.com";
            boolean sex = rand.nextBoolean();
            double weight = rand.nextDouble(45,135);
            double height = rand.nextDouble(150,230);
            int heartFreq = rand.nextInt(45,95);

            int userType = rand.nextInt(3);

            switch (userType){
                //Adds Casual User
                case 0:
                    try {
                        numberOfCasuals++;
                        app.addCasualUser(name + i, username + i, birthdate, address, email, sex, weight, height, heartFreq);
                    } catch (UsernameAlreadyExistsException e){
                        System.err.println(e.getMessage());
                    }
                    break;
                case 1:
                    try {
                        numberOfAmateurs++;
                        app.addAmateurUser(name + i, username + i, birthdate, address, email, sex, weight, height, heartFreq);
                    } catch (UsernameAlreadyExistsException e){
                        System.err.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        numberOfProfessionals++;
                        app.addProfessionalUser(name + i, username + i, birthdate, address, email, sex, weight, height, heartFreq);
                    } catch (UsernameAlreadyExistsException e){
                        System.err.println(e.getMessage());
                    }
                    break;
                default:
                    System.err.println("Error generating user type");
                    break;
            }
        }
        System.out.println("Number of Casuals: " + numberOfCasuals);
        System.out.println("Number of Amateurs: " + numberOfAmateurs);
        System.out.println("Number of Professionals: " + numberOfProfessionals);
        System.out.println("--{ ENDING }--\n");
    }

    private void populateAppWithActivities(){
        if(app.getUserController().getUsers().getUsersList().isEmpty()){
            System.err.println("Not possible to add activities to an empty app! (add users first)");
        } else {
            int nrOfAddedActivities = 0;
            int nrOfRowing = 0;
            int nrOfSkating = 0;
            int nrOfTrackRunning = 0;
            int nrOfMountainBiking = 0;
            int nrOfRoadCycling = 0;
            int nrOfRoadRunning = 0;
            int nrOfTrailRunning = 0;
            int nrOfAbdominalExercises = 0;
            int nrOfPushUps = 0;
            int nrOfStretching = 0;
            int nrOfLegExtension = 0;
            int nrOfWeightLifting = 0;
            Random rand = new Random();
            for (User u : app.getUserController().getUsers().getUsersList()){
                int userId = u.getId();
                int numberOfActivities = rand.nextInt(5,25);
                nrOfAddedActivities += numberOfActivities;
                for(int i = 0; i < numberOfActivities; i++){
                    String name = generateRandomAlphabeticStrings();
                    int year = rand.nextInt(2020, 2028);
                    int month = rand.nextInt(1,12);
                    int day = rand.nextInt(1, 28);
                    int beginHour = rand.nextInt(0,22);
                    int beginMinutes = rand.nextInt(0,58);
                    int endHour = rand.nextInt(beginHour, 23);
                    int endMinutes = beginHour==endHour ? rand.nextInt(beginMinutes, 59) : rand.nextInt(0,59);
                    int heartRate = rand.nextInt(60, 125);

                    LocalDateTime begin = LocalDateTime.of(year, month, day, beginHour, beginMinutes, 0);
                    LocalDateTime end = LocalDateTime.of(year, month, day, endHour, endMinutes, 0);

                    int activityType = rand.nextInt(12);
                    switch (activityType){
                        case 0:
                            try {
                                app.addRowingToUser(userId, name, begin, end, heartRate, rand.nextInt(1000,3000), rand.nextInt(1,4), rand.nextBoolean());
                                nrOfRowing++;
                            } catch (InvalidValueException | ErrorAddingActivityException e) {
                                System.err.println(e.getMessage());
                            }
                            break;
                        case 1:
                            try {
                                app.addSkatingToUser(userId, name, begin, end, heartRate, rand.nextInt(500, 1000), rand.nextDouble(1,5), rand.nextBoolean());
                                nrOfSkating++;
                            } catch (InvalidValueException | ErrorAddingActivityException e) {
                                System.err.println(e.getMessage());
                            }
                            break;
                        case 2:
                            try {
                                app.addTrackRunningToUser(userId, name, begin, end, heartRate, rand.nextInt(500, 1000), rand.nextBoolean());
                                nrOfTrackRunning++;
                            } catch (InvalidValueException | ErrorAddingActivityException e) {
                                System.err.println(e.getMessage());
                            }
                            break;
                        case 3:
                            try {
                                app.addMountainBikingToUser(userId, name, begin, end, heartRate, rand.nextInt(500, 3000), rand.nextInt(150,1000), rand.nextBoolean());
                                nrOfMountainBiking++;
                            } catch (InvalidValueException | ErrorAddingActivityException e) {
                                System.err.println(e.getMessage());
                            }
                            break;
                        case 4:
                            try {
                                app.addRoadCyclingToUser(userId, name, begin, end, heartRate, rand.nextInt(500, 1000), rand.nextInt(150,1000), rand.nextBoolean());
                                nrOfRoadCycling++;
                            } catch (InvalidValueException | ErrorAddingActivityException e) {
                                System.err.println(e.getMessage());
                            }
                            break;
                        case 5:
                            try {
                                app.addRoadRunningToUser(userId, name, begin, end, heartRate, rand.nextInt(500, 1000), rand.nextInt(150,1000), rand.nextBoolean());
                                nrOfRoadRunning++;
                            } catch (InvalidValueException | ErrorAddingActivityException e) {
                                System.err.println(e.getMessage());
                            }
                            break;
                        case 6:
                            try {
                                app.addTrailRunningToUser(userId, name, begin, end, heartRate, rand.nextInt(500, 1000), rand.nextInt(150,1000), rand.nextBoolean());
                                nrOfTrailRunning++;
                            } catch (InvalidValueException | ErrorAddingActivityException e) {
                                System.err.println(e.getMessage());
                            }
                            break;
                        case 7:
                            try {
                                app.addAbdominalExercisesToUser(userId, name, begin, end, heartRate, rand.nextInt(10, 200), rand.nextBoolean());
                                nrOfAbdominalExercises++;
                            } catch (InvalidValueException | ErrorAddingActivityException e) {
                                System.err.println(e.getMessage());
                            }
                            break;
                        case 8:
                            try {
                                app.addPushUpsToUser(userId, name, begin, end, heartRate, rand.nextInt(10, 200), rand.nextBoolean());
                                nrOfPushUps++;
                            } catch (InvalidValueException | ErrorAddingActivityException e) {
                                System.err.println(e.getMessage());
                            }
                            break;
                        case 9:
                            try {
                                app.addStretchingToUser(userId, name, begin, end, heartRate, rand.nextInt(10, 200), rand.nextBoolean());
                                nrOfStretching++;
                            } catch (InvalidValueException | ErrorAddingActivityException e) {
                                System.err.println(e.getMessage());
                            }
                            break;
                        case 10:
                            try {
                                app.addLegExtensionToUser(userId, name, begin, end, heartRate, rand.nextInt(10, 200), rand.nextInt(5,240), rand.nextInt(1,90));
                                nrOfLegExtension++;
                            } catch (InvalidValueException | ErrorAddingActivityException e) {
                                System.err.println(e.getMessage());
                            }
                            break;
                        case 11:
                            try {
                                app.addWeightLiftingToUser(userId, name, begin, end, heartRate, rand.nextInt(10, 200), rand.nextInt(5,240), rand.nextBoolean());
                                nrOfWeightLifting++;
                            } catch (InvalidValueException | ErrorAddingActivityException e) {
                                System.err.println(e.getMessage());
                            }
                            break;
                        default:
                            System.err.println("Error generating user activity type");
                            break;
                    }
                }
            }
            System.out.println("--{ CREATE ACTIVITIES }--");
            System.out.println("Added Activities: " + nrOfAddedActivities);
            System.out.println("Added Rowing: " + nrOfRowing);
            System.out.println("Added Skating: " + nrOfSkating);
            System.out.println("Added TrackRunning: " + nrOfTrackRunning);
            System.out.println("Added MountainBiking: " + nrOfMountainBiking);
            System.out.println("Added RoadCycling: " + nrOfRoadCycling);
            System.out.println("Added RoadRunning: " + nrOfRoadRunning);
            System.out.println("Added TrailRunning: " + nrOfTrailRunning);
            System.out.println("Added AbdominalExercises: " + nrOfAbdominalExercises);
            System.out.println("Added PushUps: " + nrOfPushUps);
            System.out.println("Added Stretching: " + nrOfStretching);
            System.out.println("Added LegExtension: " + nrOfLegExtension);
            System.out.println("Added WeightLifting: " + nrOfWeightLifting);
            System.out.println("--{ END }--\n");
        }
    }

    private void fillApp() {
        //Stores original STDOUT
        PrintStream originalOut = System.out;

        try {
            // Redirect standard output to appInfo file, truncating if it already exists
            PrintStream fileOut = new PrintStream(new File("appInfo.txt"));
            System.setOut(fileOut);

            // Call methods to populate app with users and activities
            this.populateAppWithUsers();
            this.populateAppWithActivities();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void FillNStore(){
        //WARN: CUIDADO COM O USO DESTA FUNÇÂO, PODE LIMPAR O FICHEIRO DO STATE E DAR BREAK AOS TESTES
        //this.fillApp();
        //app.saveState("JosefinFitnessApp_State");
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
        try{
            app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);
        } catch (UsernameAlreadyExistsException e){
            System.err.println(e.getMessage());
        }

        app.login("paulex");

        try {
            app.login("jojo");
        } catch (ErrorLoggingInException e) {
            assertEquals(e.getMessage(),"User jojo does not exist");
        }
        assertEquals(app.getUserID(), app.getUserController().getUsernameID("paulex"));
    }

    @Test
    void logout() {

        try {
            app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);
        } catch (UsernameAlreadyExistsException e){
            System.err.println(e.getMessage());
        }

        app.login("paulex");
        app.logout();
        assertEquals(app.getUserID(), -1);
    }

    @Test
    void getLoggedUserInfo() {

        //Adding user to test login
        try {
            app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);
        } catch (UsernameAlreadyExistsException e){
            System.err.println(e.getMessage());
        }

        app.login("paulex");

        assertEquals(app.getLoggedUserInfo(), caUser.toString());
    }

    @Test
    void updateUserName() {
        try {
            app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);
        } catch (UsernameAlreadyExistsException e){
            System.err.println(e.getMessage());
        }

        app.login("paulex");

        try {
            app.updateUserName(-1, "LindaFlor");
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulex").getName(), "LindaFlor");
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithUsername("paulex").toString());

        try {
            app.updateUserName(1, "EuSouLindo");
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulex").getName(), "EuSouLindo");
        assertEquals(app.getUserController().getUsers().getUserWithId(1).toString(), app.getUserController().getUsers().getUserWithUsername("paulex").toString());
    }

    @Test
    void updateUserUsername() {
        try {
            app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);
            app.addProfessionalUser("Paulão", "gigaChad", birthday, address, email, true, weight, height, heartFreq);
        } catch (UsernameAlreadyExistsException e) {
            System.err.println(e.getMessage());
        }

        app.login("paulex");

        try {
            app.updateUserUsername(-1, "gigaChad");
        } catch (ErrorUpdatingUserException | UsernameAlreadyExistsException e){
            assertEquals(e.getMessage(),"A User with that username already Exists!");
        }

        try {
            app.updateUserUsername(-1, "LindaFlor");
        } catch (ErrorUpdatingUserException | UsernameAlreadyExistsException e){
            System.err.println(e.getMessage());
        }

        assertFalse(app.getUserController().userWithUsernameExists("paulex"));
        assertTrue(app.getUserController().userWithUsernameExists("LindaFlor"));
        assertEquals(app.getUserController().getUsers().getUserWithUsername("LindaFlor").getUsername(), "LindaFlor");
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithUsername("LindaFlor").toString());


        try {
            app.updateUserUsername(1, "gigaChad");
        } catch (ErrorUpdatingUserException | UsernameAlreadyExistsException e){
            assertEquals(e.getMessage(),"A User with that username already Exists!");
        }

        try {
            app.updateUserUsername(1, "LindaFlori");
        } catch (ErrorUpdatingUserException | UsernameAlreadyExistsException e){
            System.err.println(e.getMessage());
        }

        assertFalse(app.getUserController().userWithUsernameExists("LindaFlor"));
        assertTrue(app.getUserController().userWithUsernameExists("LindaFlori"));
        assertEquals(app.getUserController().getUsers().getUserWithUsername("LindaFlori").getUsername(), "LindaFlori");
        assertEquals(app.getUserController().getUsers().getUserWithId(1).toString(), app.getUserController().getUsers().getUserWithUsername("LindaFlori").toString());
    }

    @Test
    void updateUserBirthdate() {
        try {
            app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);
        } catch (UsernameAlreadyExistsException e) {
            System.err.println(e.getMessage());
        }

        app.login("paulex");

        LocalDate newBirthday = LocalDate.of(2020, Month.DECEMBER, 17);
        try {
            app.updateUserBirthdate(-1, newBirthday);
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulex").getBirthdate(), newBirthday);
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithUsername("paulex").toString());
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithId(1).toString());

        LocalDate newBirthday2 = LocalDate.of(2021, Month.DECEMBER, 13);
        try {
            app.updateUserBirthdate(1, newBirthday2);
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulex").getBirthdate(), newBirthday2);
        assertEquals(app.getUserController().getUsers().getUserWithId(1).toString(), app.getUserController().getUsers().getUserWithUsername("paulex").toString());
    }

    @Test
    void updateUserAddress() {
        try {
            app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);
        } catch (UsernameAlreadyExistsException e) {
            System.err.println(e.getMessage());
        }

        app.login("paulex");

        try {
            app.updateUserAddress(-1, "Rua de abana o pinto");
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulex").getAddress(), "Rua de abana o pinto");
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithUsername("paulex").toString());
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithId(1).toString());

        try {
            app.updateUserAddress(1, "Rua de abana o rabo");
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulex").getAddress(), "Rua de abana o rabo");
        assertEquals(app.getUserController().getUsers().getUserWithId(1).toString(), app.getUserController().getUsers().getUserWithUsername("paulex").toString());
    }

    @Test
    void updateUserEmail() {
        try {
            app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);
        } catch (UsernameAlreadyExistsException e) {
            System.err.println(e.getMessage());
        }

        app.login("paulex");

        try {
            app.updateUserEmail(-1, "panadosComAtum@gmail.com");
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulex").getEmail(), "panadosComAtum@gmail.com");
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithUsername("paulex").toString());
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithId(1).toString());

        try {
            app.updateUserEmail(1, "panadosConTigo@gmail.com");
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulex").getEmail(), "panadosConTigo@gmail.com");
        assertEquals(app.getUserController().getUsers().getUserWithId(1).toString(), app.getUserController().getUsers().getUserWithUsername("paulex").toString());
    }

    @Test
    void updateUserHeight() {
        try {
            app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);
        } catch (UsernameAlreadyExistsException e) {
            System.err.println(e.getMessage());
        }

        app.login("paulex");

        try {
            app.updateUserHeight(-1, 195.2);
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulex").getHeight(), 195.2);
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithUsername("paulex").toString());
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithId(1).toString());

        try {
            app.updateUserHeight(1, 135.2);
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulex").getHeight(), 135.2);
        assertEquals(app.getUserController().getUsers().getUserWithId(1).toString(), app.getUserController().getUsers().getUserWithUsername("paulex").toString());
    }

    @Test
    void updateUserWeight() {
        try {
            app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);
        } catch (UsernameAlreadyExistsException e) {
            System.err.println(e.getMessage());
        }

        app.login("paulex");

        try {
            app.updateUserWeight(-1, 124.5);
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulex").getWeight(), 124.5);
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithUsername("paulex").toString());
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithId(1).toString());

        try {
            app.updateUserWeight(1, 104.5);
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulex").getWeight(), 104.5);
        assertEquals(app.getUserController().getUsers().getUserWithId(1).toString(), app.getUserController().getUsers().getUserWithUsername("paulex").toString());
    }

    @Test
    void updateUserHearFreq() {
        try {
            app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);
        } catch (UsernameAlreadyExistsException e) {
            System.err.println(e.getMessage());
        }

        app.login("paulex");

        try {
            app.updateUserHearFreq(-1, 75);
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulex").getHeartFreq(), 75);
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithUsername("paulex").toString());
        assertEquals(app.getLoggedUserInfo(), app.getUserController().getUsers().getUserWithId(1).toString());

        try {
            app.updateUserHearFreq(-1, 90);
        } catch (ErrorUpdatingUserException e){
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserController().getUsers().getUserWithUsername("paulex").getHeartFreq(), 90);
        assertEquals(app.getUserController().getUsers().getUserWithId(1).toString(), app.getUserController().getUsers().getUserWithUsername("paulex").toString());
    }

    @Test
    void addRowingToUser() {
        this.fillApp();

        app.login("paulex0");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        int activiySizeBeforeA = app.getUsersActivities(app.getUserID()).size();
        int activiySizeBeforeB = app.getUsersActivities(3).size();

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

        assertEquals(activiySizeBeforeA+1, app.getUsersActivities(app.getUserID()).size());
        assertEquals(activiySizeBeforeB+1, app.getUsersActivities(3).size());
    }

    @Test
    void addSkatingToUser() {
        this.fillApp();

        app.login("paulex0");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        int activiySizeBeforeA = app.getUsersActivities(app.getUserID()).size();
        int activiySizeBeforeB = app.getUsersActivities(3).size();

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

        assertEquals(activiySizeBeforeA+1, app.getUsersActivities(app.getUserID()).size());
        assertEquals(activiySizeBeforeB+1, app.getUsersActivities(3).size());
    }

    @Test
    void addTrackRunningToUser() {
        this.fillApp();

        app.login("paulex0");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        int activiySizeBeforeA = app.getUsersActivities(app.getUserID()).size();
        int activiySizeBeforeB = app.getUsersActivities(3).size();

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

        assertEquals(activiySizeBeforeA+1, app.getUsersActivities(app.getUserID()).size());
        assertEquals(activiySizeBeforeB+1, app.getUsersActivities(3).size());
    }

    @Test
    void addMountainBikingToUser() {
        this.fillApp();

        app.login("paulex0");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        int activiySizeBeforeA = app.getUsersActivities(app.getUserID()).size();
        int activiySizeBeforeB = app.getUsersActivities(3).size();

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

        assertEquals(activiySizeBeforeA+1, app.getUsersActivities(app.getUserID()).size());
        assertEquals(activiySizeBeforeB+1, app.getUsersActivities(3).size());
    }

    @Test
    void addRoadCyclingToUser() {
        this.fillApp();

        app.login("paulex0");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        int activiySizeBeforeA = app.getUsersActivities(app.getUserID()).size();
        int activiySizeBeforeB = app.getUsersActivities(3).size();

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

        assertEquals(activiySizeBeforeA+1, app.getUsersActivities(app.getUserID()).size());
        assertEquals(activiySizeBeforeB+1, app.getUsersActivities(3).size());
    }

    @Test
    void addRoadRunningToUser() {
        this.fillApp();

        app.login("paulex0");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        int activiySizeBeforeA = app.getUsersActivities(app.getUserID()).size();
        int activiySizeBeforeB = app.getUsersActivities(3).size();

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

        assertEquals(activiySizeBeforeA+1, app.getUsersActivities(app.getUserID()).size());
        assertEquals(activiySizeBeforeB+1, app.getUsersActivities(3).size());
    }

    @Test
    void addTrailRunningToUser() {
        this.fillApp();

        app.login("paulex0");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        int activiySizeBeforeA = app.getUsersActivities(app.getUserID()).size();
        int activiySizeBeforeB = app.getUsersActivities(3).size();

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

        assertEquals(activiySizeBeforeA+1, app.getUsersActivities(app.getUserID()).size());
        assertEquals(activiySizeBeforeB+1, app.getUsersActivities(3).size());
    }

    @Test
    void addAbdominalExercisesToUser() {
        this.fillApp();

        app.login("paulex0");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        int activiySizeBeforeA = app.getUsersActivities(app.getUserID()).size();
        int activiySizeBeforeB = app.getUsersActivities(3).size();

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

        assertEquals(activiySizeBeforeA+1, app.getUsersActivities(app.getUserID()).size());
        assertEquals(activiySizeBeforeB+1, app.getUsersActivities(3).size());
    }

    @Test
    void addPushUpsToUser() {
        this.fillApp();

        app.login("paulex0");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        int activiySizeBeforeA = app.getUsersActivities(app.getUserID()).size();
        int activiySizeBeforeB = app.getUsersActivities(3).size();

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

        assertEquals(activiySizeBeforeA+1, app.getUsersActivities(app.getUserID()).size());
        assertEquals(activiySizeBeforeB+1, app.getUsersActivities(3).size());
    }

    @Test
    void addStretchingToUser() {
        this.fillApp();

        app.login("paulex0");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        int activiySizeBeforeA = app.getUsersActivities(app.getUserID()).size();
        int activiySizeBeforeB = app.getUsersActivities(3).size();

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

        assertEquals(activiySizeBeforeA+1, app.getUsersActivities(app.getUserID()).size());
        assertEquals(activiySizeBeforeB+1, app.getUsersActivities(3).size());
    }

    @Test
    void addLegExtensionToUser() {
        this.fillApp();

        app.login("paulex0");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        int activiySizeBeforeA = app.getUsersActivities(app.getUserID()).size();
        int activiySizeBeforeB = app.getUsersActivities(3).size();

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

        assertEquals(activiySizeBeforeA+1, app.getUsersActivities(app.getUserID()).size());
        assertEquals(activiySizeBeforeB+1, app.getUsersActivities(3).size());
    }

    @Test
    void addWeightLiftingToUser() {
        this.fillApp();

        app.login("paulex0");

        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        int activiySizeBeforeA = app.getUsersActivities(app.getUserID()).size();
        int activiySizeBeforeB = app.getUsersActivities(3).size();

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

        assertEquals(activiySizeBeforeA+1, app.getUsersActivities(app.getUserID()).size());
        assertEquals(activiySizeBeforeB+1, app.getUsersActivities(3).size());
    }

    @Test
    void addManualTrainingPlan() throws InvalidValueException {
        this.fillApp();

        //Quando mete um por cima de outro, dá close hard activity exception
        //A doDate das activities não muda conforme a do plano de treino, tens de fazer isso

        //Testar o normal
        LocalDateTime begin = LocalDateTime.of(2018,Month.MAY,1,18,12,0);
        LocalDateTime end = LocalDateTime.of(2018,Month.MAY,1,20,0,0);

        Rowing rowing1 = app.createRowing(1, "Mundial 2018", begin, end, 75, 1623, 2, false);
        Rowing rowing2 = app.createRowing(1, "Mundial 2018", begin, end, 75, 1623, 2, false);
        Rowing rowing3 = app.createRowing(1, "Mundial 2018", begin, end, 75, 1623, 2, false);
        Rowing rowing4 = app.createRowing(1, "Mundial 2018", begin, end, 75, 1623, 2, false);
        Rowing rowing5 = app.createRowing(1, "Mundial 2018", begin, end, 75, 1623, 2, false);
        LegExtension legext1 = app.createLegExtension(1, "Mundial 2018", begin, end, 75, 1623, 12, 40);
        LegExtension legext2 = app.createLegExtension(1, "Mundial 2018", begin, end, 75, 1623, 12, 40);
        LegExtension legext3 = app.createLegExtension(1, "Mundial 2018", begin, end, 75, 1623, 12, 40);
        LegExtension legext4 = app.createLegExtension(1, "Mundial 2018", begin, end, 75, 1623, 12, 40);
        LegExtension legext5 = app.createLegExtension(1, "Mundial 2018", begin, end, 75, 1623, 12, 40);
        LegExtension legext6 = app.createLegExtension(1, "Mundial 2018", begin, end, 75, 1623, 12, 40);

        List<Activity> activityList1= new ArrayList<>();
        activityList1.add(rowing1);
        activityList1.add(legext1);
        activityList1.add(legext2);

        LocalDate doDate1 = LocalDate.now();
        boolean[] repeat1 = {false, false, false, false, false, false, false};

        try {
            app.addManualTrainingPlan(1, activityList1, doDate1, repeat1);
        } catch (InvalidValueException | ErrorHardActivityCloseException | ErrorSameDayTrainingPlanException e) {
            System.err.println(e.getMessage());
        }
        //WARN: CLONAR ATIVIDADE
        legext2.setName("Batata");

        //Testar para duas hards no mesmo dia
        List<Activity> activityList2= new ArrayList<>();
        activityList2.add(rowing2);
        activityList2.add(rowing3);
        activityList2.add(legext3);

        LocalDate doDate2 = LocalDate.now().minusDays(3);
        boolean[] repeat2 = {false, false, false, false, false, false, false};

        try {
            app.addManualTrainingPlan(1, activityList2, doDate2, repeat2);
        } catch (InvalidValueException | ErrorHardActivityCloseException | ErrorSameDayTrainingPlanException e) {
            assertEquals(e.getMessage(), "Number of hard activities must be less or equals to 1.");
        }

        //Testar adicionar plano de treino com atividade hard no dia anterior/seguinte a ter feito um plano de treino hard
        List<Activity> activityList3= new ArrayList<>();
        activityList3.add(rowing4);
        activityList3.add(legext4);
        activityList3.add(legext5);

        LocalDate doDate3 = LocalDate.now().minusDays(1);
        boolean[] repeat3 = {true, false, true, false, false, false, false};

        try {
            app.addManualTrainingPlan(1, activityList3, doDate3, repeat3);
        } catch (InvalidValueException | ErrorHardActivityCloseException | ErrorSameDayTrainingPlanException e) {
            assertEquals(e.getMessage(), "A close training plan already has an hard activity.");
        }

        //Testar adicionar plano de treino com repeticao em dias consecutivos, tendo este atividades hard
        List<Activity> activityList4= new ArrayList<>();
        activityList4.add(rowing5);
        activityList4.add(legext6);
        activityList4.add(legext6);

        LocalDate doDate4 = LocalDate.now().minusDays(5);
        boolean[] repeat4 = {true, true, false, true, false, false, false};

        try {
            app.addManualTrainingPlan(1, activityList4, doDate4, repeat4);
        } catch (InvalidValueException | ErrorHardActivityCloseException | ErrorSameDayTrainingPlanException e) {
            assertEquals(e.getMessage(), "You can't repeat the same training plan with hard activities on consecutive days.");
        }

        System.out.println(app.getUserController().getUsers().getUserWithId(1).getTrainingSchedule().toString());
        assertEquals(app.getUserController().getUsers().getUserWithId(1).getTrainingSchedule().size(),1);

        app.loadStats();
        System.out.println(app.getUsersActivities(1).toString());

        //Testar adicionar plano de treino com repeticao em dias consecutivos, nao tendo este atividades hard
        //TODO

        //Testar adicionar plano de treino com repeticao num dia que ja repete um plano de treino
        //TODO
    }

    @Test
    void addAutomaticTrainingPlan() throws UsernameAlreadyExistsException, InvalidValueException, ErrorHardActivityCloseException {
        this.fillApp();

        LocalDate begin = LocalDate.of(2018,Month.MAY,1);
        boolean[] repeat = {true, false, false, false, false, false, false};
        app.addAutomaticTrainingPlan(1, true, 2, begin, repeat, 500, 1);
        System.out.println(app.getUserController().getUsers().getUserWithId(1).getTrainingSchedule().toString());

        app.loadStats();
        System.out.println(app.getUsersActivities(1).toString());
    }

    @Test
    void deleteAccount() {
        this.fillApp();
//        for(User u : app.getUserController().getUsers().getUsersList()){
//            System.out.println(u.toString());
//        }

        app.login("paulex0");

        try {
            app.deleteAccount();
        } catch (ErrorRemovingUserException e) {
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserID(), -1);
        assertFalse(app.getUserController().userWithUsernameExists("paulex0"));
    }

    @Test
    void saveChanges() {
    }

    @Test
    void addCasualUser() throws UsernameAlreadyExistsException {
        CasualUser u = new CasualUser(1, name, username, birthday, address, email, true, weight, height, heartFreq);
        app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);

        assertEquals("CasualUser", app.getUserController().getUsers().getUserWithUsername(username).getClass().getSimpleName());
        assertEquals(app.getUserController().getUsers().getUserWithUsername(username), u);
    }

    @Test
    void addAmateurUser() throws UsernameAlreadyExistsException {
        AmateurUser u = new AmateurUser(1, name, username, birthday, address, email, true, weight, height, heartFreq);
        app.addAmateurUser(name, username, birthday, address, email, true, weight, height, heartFreq);

        assertEquals("AmateurUser", app.getUserController().getUsers().getUserWithUsername(username).getClass().getSimpleName());
        assertEquals(app.getUserController().getUsers().getUserWithUsername(username), u);
    }

    @Test
    void addProfessionalUser() throws UsernameAlreadyExistsException {
        ProfessionalUser u = new ProfessionalUser(1, name, username, birthday, address, email, true, weight, height, heartFreq);
        app.addProfessionalUser(name, username, birthday, address, email, true, weight, height, heartFreq);

        assertEquals("ProfessionalUser", app.getUserController().getUsers().getUserWithUsername(username).getClass().getSimpleName());
        assertEquals(app.getUserController().getUsers().getUserWithUsername(username), u);
    }

    @Test
    void removeUser() throws UsernameAlreadyExistsException {
        app.addCasualUser(name, username, birthday, address, email, true, weight, height, heartFreq);

        app.login("paulex");

        try {
            app.removeUser(app.getUserID());
            app.setUserID(-1);
        } catch (ErrorRemovingUserException | UserDoesNotExistsException e) {
            System.err.println(e.getMessage());
        }

        assertEquals(app.getUserID(), -1);
        try {
            app.getUserController().getUsers().getUserWithUsername("paulex");
        } catch (NullPointerException e){
            System.err.println(e.getMessage());
        }
    }

    @Test
    void loadStats() {
    }

    @Test
    void userWithMostCaloriesBurned() throws UsernameAlreadyExistsException {
        this.fillApp();

        app.loadStats();
        app.setSystemDate(LocalDateTime.now());

        User value = app.userWithMostCaloriesBurned(LocalDateTime.of(2000,Month.MAY,1,18,12,0));
        assertNotNull(value);
    }

    @Test
    void userWithMostActivitiesCompleted() throws UsernameAlreadyExistsException {
        this.fillApp();

        app.loadStats();
        app.setSystemDate(LocalDateTime.now());
        User value = app.userWithMostActivitiesCompleted(LocalDateTime.of(2000,Month.MAY,1,18,12,0));

        System.out.println(app.getStats().getAllTimeUserWithMostActivitiesCompleted());

        assertNotNull(value);
    }

    @Test
    void mostCommonActivity() throws UsernameAlreadyExistsException {
        this.fillApp();
        app.loadStats();

        System.out.println(app.mostCommonActivity());
    }

    @Test
    void distanceDoneByUser() throws UsernameAlreadyExistsException {
        this.fillApp();
        app.loadStats();

        app.setSystemDate(LocalDateTime.now());
        int value = app.distanceDoneByUser(1, LocalDateTime.of(2000,Month.MAY,1,18,12,0));

        int value2 = app.distanceDoneByUser(3, LocalDateTime.of(2000,Month.MAY,1,18,12,0));

        assertTrue(value >= 0);
        assertTrue(value2 >= 0);
    }

    @Test
    void altimetryDoneByUser() throws UsernameAlreadyExistsException {
        this.fillApp();
        app.loadStats();

        app.setSystemDate(LocalDateTime.now());
        int value = app.altimetryDoneByUser(1, LocalDateTime.of(2000,Month.MAY,1,18,12,0));

        int value2 = app.altimetryDoneByUser(3, LocalDateTime.of(2000,Month.MAY,1,18,12,0));

        assertTrue(value >= 0);
        assertTrue(value2 >= 0);
    }

    @Test
    void getUsersActivities() throws UsernameAlreadyExistsException {
        this.fillApp();

        try {
            System.out.println(app.getUsersActivities(2).toString());
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }


    }

    @Test
    void saveState() throws UsernameAlreadyExistsException {
        this.fillApp();

        app.login("paulex0");

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

        //System.out.println(app.getUserController().getUsers().getUserWithUsername("paulex0").getActivityController().getActivities().getActivities().values().toString());

        app.saveState("TesteFile");
    }

    @Test
    void loadState() throws UsernameAlreadyExistsException {
        //Gerar conteudo
        this.fillApp();

        //Guardar no file teste
        app.saveState("TesteFile");

        //Gerar uma app vazia
        JosefinFitnessApp app2 = new JosefinFitnessApp();

        //Carregar a info para a app dois
        app2.loadState("TesteFile");

        //Verificar que a app é igual
        assertEquals(app2.getStats(), this.app.getStats());
        assertEquals(app2.getIdManager(), this.app.getIdManager());
        assertEquals(app2.getSystemDate(), this.app.getSystemDate());
        assertEquals(app2.getUserID(), this.app.getUserID());
        assertEquals(app2.getUserController(), this.app.getUserController());
    }

    @Test
    void testClone() {
        assertEquals(app, app.clone());
    }
}