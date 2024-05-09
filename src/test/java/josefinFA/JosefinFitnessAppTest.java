package josefinFA;

import exceptions.ErrorLoggingInException;
import exceptions.ErrorRemovingUserException;
import exceptions.ErrorUpdatingUserException;
import exceptions.UsernameAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import users.*;
import utils.IDManager;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

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
        LocalDate systemDate = LocalDate.of(2020, Month.JANUARY, 1);
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
    void addRowingToLoggedUser() {
    }

    @Test
    void addSkatingToLoggedUser() {
    }

    @Test
    void addTrackRunningToLoggedUser() {
    }

    @Test
    void addMountainBikingToLoggedUser() {
    }

    @Test
    void addRoadCyclingToLoggedUser() {
    }

    @Test
    void addTrailRunningToLoggedUser() {
    }

    @Test
    void addAbdominalExercisesToLoggedUser() {
    }

    @Test
    void addPushUpsToLoggedUser() {
    }

    @Test
    void addStretchingToLoggedUser() {
    }

    @Test
    void addLegExtensionToLoggedUser() {
    }

    @Test
    void addWeightLiftingToLoggedUser() {
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
    }

    @Test
    void userWithMostActivitiesCompleted() {
    }

    @Test
    void mostCommunActivity() {
    }

    @Test
    void distanceDoneByUser() {
    }

    @Test
    void altimetryDoneByUser() {
    }

    @Test
    void getUsersActivities() {
    }

    @Test
    void saveState(){
        this.fillApp();

        for(User u : app.getUserController().getUsers().getUsersList()){
            System.out.println(u.toString());
        }

        app.login("Casual paulo12340");

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
    }

    @Test
    void testClone() {
        assertEquals(app, app.clone());
    }
}