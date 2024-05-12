package users;

import TrainingPlan.TrainingPlan;
import activities.*;
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

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class UserController implements Serializable {
    private Users users;

    //region Constructors
    public UserController() {
        this.users = new Users();
    }

    public UserController(
            Users users
    ) {
        this.users = users.clone();
    }

    public UserController(
            UserController controller
    ) {
        this.users = controller.getUsers();
    }
    //endregion

    //region Getters And Setters
    public Users getUsers() {
        return this.users.clone();
    }

    public void setUsers(Users users) {
        this.users = users;
    }
    //endregion

    public boolean isUsernameAvailable(String username) {
        return this.users.isUsernameAvailable(username);
    }

    public boolean userWithIdExits(int id) {
        return this.users.containsUser(id);
    }

    public int getUsernameID(String username) {
        return this.users.getUserWithUsername(username).getId();
    }

    public void removeUser(int id) throws ErrorRemovingUserException {
        this.users.removeUser(id);
    }

    public boolean userWithUsernameExists(String username) {
        return this.users.containsUser(username);
    }

    //region Add Methods
    public void addCasualUser(
            int id,
            String name,
            String username,
            LocalDate birthdate,
            String address,
            String email,
            boolean sex,
            double height,
            double weight,
            int heartFreq
    ) {
        CasualUser casualUser = new CasualUser(id,name,username,birthdate,address,email,sex,height,weight,heartFreq);
        this.users.addUser(casualUser);
    }

    public void addAmateurUser(
            int id,
            String name,
            String username,
            LocalDate birthdate,
            String address,
            String email,
            boolean sex,
            double height,
            double weight,
            int heartFreq
    ) {
        AmateurUser amateurUser = new AmateurUser(id,name,username,birthdate,address,email,sex,height,weight,heartFreq);
        this.users.addUser(amateurUser);
    }

    public void addProfessionalUser(
            int id,
            String name,
            String username,
            LocalDate birthdate,
            String address,
            String email,
            boolean sex,
            double height,
            double weight,
            int heartFreq
    ) {
        ProfessionalUser professionalUser = new ProfessionalUser(id,name,username,birthdate,address,email,sex,height,weight,heartFreq);
        this.users.addUser(professionalUser);
    }
    //endregion

    //region Update Methods
    public void updateUserName(int id, String name) throws ErrorUpdatingUserException {
        this.users.updateUserName(id,name);
        if(!this.users.getUserWithId(id).getName().equals(name)) {
            throw new ErrorUpdatingUserException("Error updating user name!");
        }
    }

    public void updateUserUsername(int id, String username) throws ErrorUpdatingUserException, UsernameAlreadyExistsException {
        this.users.updateUserUsername(id,username);
        if(!this.users.getUserWithId(id).getUsername().equals(username)) {
            throw new ErrorUpdatingUserException("Error updating user username!");
        }
    }

    public void updateUserUsername(String currentUsername, String newUsername) throws ErrorUpdatingUserException, UsernameAlreadyExistsException {
        this.users.updateUserUsername(currentUsername,newUsername);
        if(this.users.containsUser(currentUsername)) {
            throw new ErrorUpdatingUserException("Error updating user username!");
        }
    }

    public void updateUserBirthdate(int id, LocalDate birthdate) throws ErrorUpdatingUserException {
        this.users.updateUserBirthdate(id,birthdate);

        if(!this.users.getUserWithId(id).getBirthdate().isEqual(birthdate)) {
            throw new ErrorUpdatingUserException("Error updating user birthdate!");
        }
    }

    public void updateUserAddress(int id, String address) throws ErrorUpdatingUserException {
        this.users.updateUserAddress(id,address);
        if(!this.users.getUserWithId(id).getAddress().equals(address)) {
            throw new ErrorUpdatingUserException("Error updating user address!");
        }
    }

    public void updateUserEmail(int id, String email) throws ErrorUpdatingUserException {
        this.users.updateUserEmail(id,email);
        if(!this.users.getUserWithId(id).getEmail().equals(email)) {
            throw new ErrorUpdatingUserException("Error updating user email!");
        }
    }

    public void updateUserHeight(int id, double height)throws ErrorUpdatingUserException {
        this.users.updateUserHeight(id,height);
        if(this.users.getUserWithId(id).getHeight() != height) {
            throw new ErrorUpdatingUserException("Error updating user height!");
        }
    }

    public void updateUserWeight(int id, double weight) throws ErrorUpdatingUserException{
        this.users.updateUserWeight(id,weight);
        if(this.users.getUserWithId(id).getWeight() != weight) {
            throw new ErrorUpdatingUserException("Error updating user weight!");
        }
    }

    public void updateUserHeartFrequency(int id, int heartFrequency) throws ErrorUpdatingUserException {
        this.users.updateUserHeartFrequency(id,heartFrequency);
        if(this.users.getUserWithId(id).getHeartFreq() != heartFrequency) {
            throw new ErrorUpdatingUserException("Error updating user heartFreq!");
        }
    }
    //endregion

    //region AddingActivities
    public void addRowing(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            int personsOnBoard,
            boolean rowAgainstTide
    ) throws ErrorAddingActivityException {
        Rowing activity = new Rowing(id, name, begin, end, idUser, burnedCalories, heartRate, distance, personsOnBoard, rowAgainstTide);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    public void addSkating(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            double skateWeight,
            boolean freestyle
    ) throws ErrorAddingActivityException {
        Skating activity = new Skating(id, name, begin, end, idUser, burnedCalories, heartRate, distance, skateWeight, freestyle);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    public void addTrackRunning(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            boolean hurdleJump
    ) throws ErrorAddingActivityException {
        TrackRunning activity = new TrackRunning(id, name, begin, end, idUser, burnedCalories, heartRate, distance, hurdleJump);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    public void addMountainBiking(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            int altimetry,
            boolean bigTires
    ) throws ErrorAddingActivityException {
        MountainBiking activity = new MountainBiking(id, name, begin, end, idUser, burnedCalories, heartRate, distance, altimetry, bigTires);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    public void addRoadCycling(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            int altimetry,
            boolean windAgainst
    ) throws ErrorAddingActivityException {
        RoadCycling activity = new RoadCycling(id, name, begin, end, idUser, burnedCalories, heartRate, distance, altimetry, windAgainst);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    public void addRoadRunning(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            int altimetry,
            boolean windAgainst
    ) throws ErrorAddingActivityException {
        RoadRunning activity = new RoadRunning(id, name, begin, end, idUser, burnedCalories, heartRate, distance, altimetry, windAgainst);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    public void addTrailRunning(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int distance,
            int altimetry,
            boolean wetFloor
    ) throws ErrorAddingActivityException {
        TrailRunning activity = new TrailRunning(id, name, begin, end, idUser, burnedCalories, heartRate, distance, altimetry, wetFloor);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    public void addAbdominalExercises(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int nRepetitions,
            boolean helped
    ) throws ErrorAddingActivityException {
        AbdominalExercises activity = new AbdominalExercises(id, name, begin, end, idUser, burnedCalories, heartRate, nRepetitions, helped);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    public void addPushUps(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int nRepetitions,
            boolean diamondIntercalated
    ) throws ErrorAddingActivityException {
        PushUps activity = new PushUps(id, name, begin, end, idUser, burnedCalories, heartRate, nRepetitions, diamondIntercalated);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    public void addStretching(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int nRepetitions,
            boolean helped
    ) throws ErrorAddingActivityException {
        Stretching activity = new Stretching(id, name, begin, end, idUser, burnedCalories, heartRate, nRepetitions, helped);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    public void addLegExtension(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int nRepetitions,
            int weight,
            int chairAngle
    ) throws ErrorAddingActivityException {
        LegExtension activity = new LegExtension(id, name, begin, end, idUser, burnedCalories, heartRate, nRepetitions, weight, chairAngle);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }

    public void addWeightLifting(
            int id,
            String name,
            LocalDateTime begin,
            LocalDateTime end,
            int idUser,
            int burnedCalories,
            int heartRate,
            int nRepetitions,
            int weight,
            boolean helped
    ) throws ErrorAddingActivityException {
        WeightLifting activity = new WeightLifting(id, name, begin, end, idUser, burnedCalories, heartRate, nRepetitions, weight, helped);
        User u = this.users.getUserWithId(idUser);
        u.addActivity(activity);
        this.users.updateUser(u);

        if(!this.users.getUserWithId(u.getId()).equals(u)){
            throw new ErrorAddingActivityException("Error adding activity!");
        };
    }
    //endregion

    //region Training Plan Methods
    private void verifyCloseHardActivity(User u, LocalDate doDate) throws ErrorHardActivityCloseException {
        List<TrainingPlan> userTps = u.getTrainingSchedule().stream()
                .filter(tp -> (tp.getDoDate().isAfter(doDate.minusDays(2)) && tp.getDoDate().isBefore(doDate.plusDays(2))))
                .toList();
        for(TrainingPlan tp : userTps) {
            if(tp.containsHardActivity())
                throw new ErrorHardActivityCloseException("A close training plan already has an hard activity.");
        }
    }

    private void verifyCloseHardRepeatActivity(boolean[] repeat) throws ErrorHardActivityCloseException {
        for(int i = 0 ; i < repeat.length - 1 ; i++) {
            if(repeat[i] && repeat[i + 1])
                throw new ErrorHardActivityCloseException("You can't repeat the same training plan with hard activities on consecutive days.");
        }
    }

    private void verifySameDayTrainingPlan(User u, LocalDate doDate) throws ErrorSameDayTrainingPlanException {
        if(u.getTrainingSchedule().stream().anyMatch(tp -> tp.getDoDate().equals(doDate)))
            throw new ErrorSameDayTrainingPlanException("It already exists a training plan on the same day.");
    }

    private void verifyRepeatTrainingPlan(User u, boolean[] repeat) throws ErrorSameDayTrainingPlanException {
        for(TrainingPlan tp : u.getTrainingSchedule()) {
            boolean[] tpRepeat = tp.getRepeat();
            for(int i = 0 ; i < repeat.length ; i++) {
                if(repeat[i] && tpRepeat[i]) {
                    throw new ErrorSameDayTrainingPlanException("You can't repeat the training plan due to a training plan being repeated already on the same day.");
                }
            }
        }
    }

    public void addManualTrainingPlan(
            int id,
            int idUser,
            List<Activity> activities,
            LocalDate doDate,
            boolean[] repeat
    ) throws InvalidValueException, ErrorHardActivityCloseException, ErrorSameDayTrainingPlanException {
        if(activities.size() > 3) throw new InvalidValueException("Number of activities must be less or equals to 3.");
        int hardActivities = (int) activities.stream().filter(a -> a instanceof Hard).count();
        if(hardActivities > 1) throw new InvalidValueException("Number of hard activities must be less or equals to 1.");

        User u = this.users.getUserWithId(idUser);
        if(hardActivities != 0) verifyCloseHardRepeatActivity(repeat);
        verifySameDayTrainingPlan(u, doDate);
        verifyCloseHardActivity(u, doDate);
        verifyRepeatTrainingPlan(u, repeat);

        TrainingPlan tp = new TrainingPlan(id, activities, doDate, repeat);
        u.addTrainingPlan(tp);
        this.users.updateUser(u);
    }

    public void addAutomaticTrainingPlan(
            int id,
            int idUser,
            int[] idsActivity,
            boolean wantsHard,
            int maximumActivitiesPerDay,
            LocalDate doDate,
            boolean[] repeat,
            int minimumCaloriesConsumption,
            int planType
    ) throws ErrorHardActivityCloseException, InvalidValueException {
        if(maximumActivitiesPerDay <= 0 || maximumActivitiesPerDay > 3)
            throw new InvalidValueException("The system only supports a number of activities per day between 1 and 3.");

        User u = this.users.getUserWithId(idUser);
        if(wantsHard) {
            verifyCloseHardActivity(u, doDate);
        }

        List<Activity> activities = new ArrayList<>();
        switch(planType) {
            case 1 : {
                generateEquilibrateActivities(
                        activities,
                        idsActivity,
                        idUser,
                        minimumCaloriesConsumption,
                        maximumActivitiesPerDay,
                        wantsHard,
                        doDate,
                        planType
                );
                break;
            }
            case 2 : {
                generateStrengthActivities(
                        activities,
                        idsActivity,
                        idUser,
                        minimumCaloriesConsumption,
                        maximumActivitiesPerDay,
                        wantsHard,
                        doDate,
                        planType
                );
                break;
            }
            case 3 : {
                generateCardioActivities(
                        activities,
                        idsActivity,
                        idUser,
                        minimumCaloriesConsumption,
                        maximumActivitiesPerDay,
                        wantsHard,
                        doDate,
                        planType
                );
                break;
            }
        }

        TrainingPlan tp = new TrainingPlan(id, activities, doDate, repeat);
        u.addTrainingPlan(tp);
        this.users.updateUser(u);
    }

    //region Automatic Generators
    private Set<Integer> randomCategorySelector(int maximumActivitiesPerDay, int numCategories) {
        Set<Integer> categories = new HashSet<>();
        Random random = new Random();
        int category = random.nextInt(2) + 1;
        categories.add(category);
        while(categories.size() < maximumActivitiesPerDay) {
            category = random.nextInt(numCategories) + 1;
            categories.add(category);
        }

        return categories;
    }

    private Activity generateDistanceActivity(int id, int idUser, List<Activity> activities, int minimumCaloriesConsumption, int maximumActivitiesPerDay, LocalDate doDate, boolean hasHard, boolean wantsHard) {
        Random random = new Random();
        int type = random.nextInt(3) + 1; // Quantidade de Atividades

        // Se o tipo for de uma atividade hard, gerar outro tipo
        if(hasHard && type == 1) {
            while(type == 1) {
                type = random.nextInt(3) + 1;   // Quantidade de Atividades
            }
        }

        if(!hasHard && wantsHard) {
            type = 1;
        }

        DistanceAct activity;
        int startDistance = 500;
        switch(type) {
            case 1 : {
                    activity = new Rowing(id, idUser, random.nextInt(startDistance), random.nextInt(6) + 1, random.nextBoolean());
                    break;
            }
            case 2: {
                    activity = new Skating(id, idUser, random.nextInt(startDistance), random.nextDouble(13) + 3, random.nextBoolean());
                    break;
            }
            case 3: {
                    activity = new TrackRunning(id, idUser, random.nextInt(startDistance), random.nextBoolean());
                    break;
            }
            default : {
                    activity = new Rowing(id, idUser, random.nextInt(startDistance), random.nextInt(6) + 1, random.nextBoolean());
            }
        }
        activity.setBegin(doDate.atTime(random.nextInt(24), random.nextInt(60)));
        activity.setEnd(activity.getBegin().plusMinutes(30));
        if(activities.size() > 0) {
            Activity a = activities.getLast();
            while (!(activity.getBegin().isAfter(a.getEnd()) || activity.getEnd().isBefore(a.getBegin()))) {
                activity.setBegin(doDate.atTime(random.nextInt(24), random.nextInt(60)));
            }
            activity.setEnd(activity.getBegin().plusMinutes(30));
        }

        User u = this.users.getUserWithId(idUser);
        activity.setHeartRate((int) (u.getHeartFreq()*1.2));
        int burnedCalories = u.speculateBurnedCalories(activity);

        while(burnedCalories < minimumCaloriesConsumption) {
            int select = random.nextInt(2) + 1;
            switch(select) {
                case 1 : {
                    startDistance += random.nextInt(200);
                    activity.setDistance(startDistance);
                    break;
                }
                case 2 : {
                    activity.setEnd(activity.getEnd().plusMinutes(random.nextInt(5)));
                    break;
                }
            }
            activity.calculateCalories();
            burnedCalories = u.speculateBurnedCalories(activity);
        }

        return activity;
    }

    private Activity generateDistanceAndAltimetryActivity(int id, int idUser, List<Activity> activities, int minimumCaloriesConsumption, int maximumActivitiesPerDay, LocalDate doDate, boolean hasHard, boolean wantsHard) {
        Random random = new Random();
        int type = random.nextInt(4) + 1;

        // Se o tipo for de uma atividade hard, gerar outro tipo
        if(hasHard && (type == 1 || type == 4)) {
            while(type == 1) {
                type = random.nextInt(4) + 1;   // Quantidade de atividades
            }
        }

        if(!hasHard && wantsHard) {
            switch(random.nextInt(2)) {
                case 0 : {
                    type = 1;
                    break;
                }
                case 1 : {
                    type = 4;
                    break;
                }
            }
        }

        DistanceAndAltimetryAct activity;
        int startDistance = 400;
        int startAltimetry = 150;
        switch(type) {
            case 1 : {
                activity = new MountainBiking(id, idUser, random.nextInt(startDistance), random.nextInt(startAltimetry), random.nextBoolean());
                break;
            }
            case 2: {
                activity = new RoadCycling(id, idUser, random.nextInt(startDistance), random.nextInt(startAltimetry), random.nextBoolean());
                break;
            }
            case 3: {
                activity = new RoadRunning(id, idUser, random.nextInt(startDistance), random.nextInt(startAltimetry), random.nextBoolean());
                break;
            }
            default : {
                activity = new TrailRunning(id, idUser, random.nextInt(startDistance), random.nextInt(startAltimetry), random.nextBoolean());
            }
        }
        activity.setBegin(doDate.atTime(random.nextInt(24), random.nextInt(60)));
        activity.setEnd(activity.getBegin().plusMinutes(30));
        if(activities.size() > 0) {
            Activity a = activities.getLast();
            while (!(activity.getBegin().isAfter(a.getEnd()) || activity.getEnd().isBefore(a.getBegin()))) {
                activity.setBegin(doDate.atTime(random.nextInt(24), random.nextInt(60)));
            }
            activity.setEnd(activity.getBegin().plusMinutes(30));
        }

        User u = this.users.getUserWithId(idUser);
        activity.setHeartRate((int) (u.getHeartFreq()*1.2));
        int burnedCalories = u.speculateBurnedCalories(activity);
        while(burnedCalories < minimumCaloriesConsumption) {
            int select = random.nextInt(3) + 1;
            switch(select) {
                case 1 : {
                    startDistance += random.nextInt(200);
                    activity.setDistance(startDistance);
                    break;
                }
                case 2 : {
                    startAltimetry += random.nextInt(65);
                    activity.setAltimetry(startAltimetry);
                    break;
                }
                case 3 : {
                    activity.setEnd(activity.getEnd().plusMinutes(random.nextInt(5)));
                    break;
                }
            }
            activity.calculateCalories();
            burnedCalories = u.speculateBurnedCalories(activity);
        }

        return activity;
    }

    private Activity generateRepetitionsActivity(
            int id,
            int idUser,
            List<Activity> activities,
            int minimumCaloriesConsumption,
            int maximumActivitiesPerDay,
            LocalDate doDate,
            boolean hasHard,
            boolean wantsHard,
            int planType
    ) {
        Random random = new Random();
        int type = random.nextInt(3) + 1; // Quantidade de Atividades

        // Se o tipo for de uma atividade hard, gerar outro tipo
        if(hasHard && type == 2) {
            while(type == 2) {
                type = random.nextInt(3) + 1;   // Quantidade de Atividades
            }
        }

        if(!hasHard && wantsHard) {
            type = 2;
        }

        RepetitionAct activity;
        int startRepetitions = 10;
        switch(type) {
            case 1 : {
                activity = new AbdominalExercises(id, idUser, random.nextInt(startRepetitions) + 1, random.nextBoolean());
                break;
            }
            case 2: {
                activity = new PushUps(id, idUser, random.nextInt(startRepetitions) + 1, random.nextBoolean());
                break;
            }
            case 3: {
                activity = new Stretching(id, idUser, random.nextInt(startRepetitions) + 1, random.nextBoolean());
                break;
            }
            default : {
                activity = new AbdominalExercises(id, idUser, random.nextInt(startRepetitions) + 1, random.nextBoolean());
            }
        }
        activity.setBegin(doDate.atTime(random.nextInt(24), random.nextInt(60)));
        activity.setEnd(activity.getBegin().plusMinutes(30));
        if(activities.size() > 0) {
            Activity a = activities.getLast();
            while (!(activity.getBegin().isAfter(a.getEnd()) || activity.getEnd().isBefore(a.getBegin()))) {
                activity.setBegin(doDate.atTime(random.nextInt(24), random.nextInt(60)));
            }
            activity.setEnd(activity.getBegin().plusMinutes(4));
        }

        User u = this.users.getUserWithId(idUser);
        activity.setHeartRate((int) (u.getHeartFreq()*1.2));
        int burnedCalories = u.speculateBurnedCalories(activity);
        int decider = minimumCaloriesConsumption/10;
        if(planType == 2) decider = (int) (minimumCaloriesConsumption*0.03);    // TODO CHANGE
        while(burnedCalories < decider) {
            startRepetitions += random.nextInt(5);
            activity.setNRepetitions(startRepetitions);
            activity.setEnd(activity.getBegin().plusMinutes((long) (startRepetitions*0.2)));
            activity.calculateCalories();
            burnedCalories = u.speculateBurnedCalories(activity);
        }

        return activity;
    }

    private Activity generateRepetitionsWeightActivity(
            int id,
            int idUser,
            List<Activity> activities,
            int minimumCaloriesConsumption,
            int maximumActivitiesPerDay,
            LocalDate doDate,
            boolean hasHard,
            boolean wantsHard,
            int planType
    ) {
        Random random = new Random();
        int type = random.nextInt(2) + 1;

        // Se o tipo for de uma atividade hard, gerar outro tipo
        if(hasHard && type == 2) {
            while(type == 2) {
                type = random.nextInt(2) + 1;   // Quantidade de atividades
            }
        }

        if(!hasHard && wantsHard) {
            type = 2;
        }

        RepetitionWithWeightsAct activity;
        int startRepetitions = 4;
        int startWeight = 50;
        switch(type) {
            case 1 : {
                activity = new LegExtension(id, idUser, random.nextInt(startRepetitions) + 1, random.nextInt(startWeight) + 1, random.nextInt(50) + 30);
                break;
            }
            case 2: {
                activity = new WeightLifting(id, idUser, random.nextInt(startRepetitions) + 1, random.nextInt(startWeight) + 1, random.nextBoolean());
                break;
            }
            default : {
                activity = new LegExtension(id, idUser, random.nextInt(startRepetitions) + 1, random.nextInt(startWeight) + 1, random.nextInt(50) + 30);
            }
        }
        activity.setBegin(doDate.atTime(random.nextInt(24), random.nextInt(60)));
        activity.setEnd(activity.getBegin().plusMinutes(30));
        if(activities.size() > 0) {
            Activity a = activities.getLast();
            while (!(activity.getBegin().isAfter(a.getEnd()) || activity.getEnd().isBefore(a.getBegin()))) {
                activity.setBegin(doDate.atTime(random.nextInt(24), random.nextInt(60)));
            }
            activity.setEnd(activity.getBegin().plusMinutes(30));
        }


        User u = this.users.getUserWithId(idUser);
        activity.setHeartRate((int) (u.getHeartFreq()*1.2));
        int burnedCalories = u.speculateBurnedCalories(activity);

        int decider = minimumCaloriesConsumption/100;
        if(planType == 2) decider = (int) (minimumCaloriesConsumption*0.03);    // TODO CHANGE

        while(burnedCalories < decider) {
            int select = random.nextInt(2) + 1;
            switch(select) {
                case 1 : {
                    startRepetitions += random.nextInt(5);
                    activity.setNRepetitions(startRepetitions);
                    break;
                }
                case 2 : {
                    startWeight += random.nextInt(3);
                    activity.setWeight(startWeight);
                    break;
                }
            }
            activity.setEnd(activity.getBegin().plusMinutes((long) (startRepetitions*0.2)));
            activity.calculateCalories();
            burnedCalories = u.speculateBurnedCalories(activity);
        }

        return activity;
    }

    private void generateEquilibrateActivities(
            List<Activity> activities,
            int[] idsActivity,
            int idUser,
            int minimumCaloriesConsumption,
            int maximumActivitiesPerDay,
            boolean wantsHard,
            LocalDate doDate,
            int planType
    ) {
        Set<Integer> categories = randomCategorySelector(maximumActivitiesPerDay, 4);
        boolean hasHard = false;
        int idIndex = -1;
        for(int category : categories) {
            Activity activity;
            if(idIndex < idsActivity.length) idIndex++;
            switch(category) {
                case 1 : {
                    activity = generateDistanceActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard);
                    break;
                }
                case 2 : {
                    activity = generateDistanceAndAltimetryActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard);
                    break;
                }
                case 3 : {
                    activity = generateRepetitionsActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard, planType);
                    break;
                }
                case 4 : {
                    activity = generateRepetitionsWeightActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard, planType);
                    break;
                }
                default: {
                    activity = generateDistanceActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard);
                    break;
                }
            }
            activities.add(activity);
            hasHard = activities.stream().anyMatch(a -> a instanceof Hard);
        }
    }

    private void generateStrengthActivities(
            List<Activity> activities,
            int[] idsActivity,
            int idUser,
            int minimumCaloriesConsumption,
            int maximumActivitiesPerDay,
            boolean wantsHard,
            LocalDate doDate,
            int planType
    ) {
        Set<Integer> categories = randomCategorySelector(maximumActivitiesPerDay, 3);
        boolean hasHard = false;
        int idIndex = -1;
        for(int category : categories) {
            Activity activity;
            if(idIndex < idsActivity.length) idIndex++;
            switch(category) {
                case 1 : {
                    activity = generateRepetitionsActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard, planType);
                    break;
                }
                case 2 : {
                    activity = generateDistanceAndAltimetryActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard);
                }
                default: {
                    activity = generateRepetitionsWeightActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard, planType);
                    break;
                }
            }
            activities.add(activity);
            hasHard = activities.stream().anyMatch(a -> a instanceof Hard);
        }
    }

    private void generateCardioActivities(
            List<Activity> activities,
            int[] idsActivity,
            int idUser,
            int minimumCaloriesConsumption,
            int maximumActivitiesPerDay,
            boolean wantsHard,
            LocalDate doDate,
            int planType
    ) {
        Set<Integer> categories = randomCategorySelector(maximumActivitiesPerDay, 3);
        boolean hasHard = false;
        int idIndex = -1;
        for(int category : categories) {
            Activity activity;
            if(idIndex < idsActivity.length) idIndex++;
            switch(category) {
                case 1 : {
                    activity = generateDistanceActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard);
                    break;
                }
                case 2 : {
                    activity = generateDistanceAndAltimetryActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard);
                    break;
                }
                case 3 : {
                    activity = generateRepetitionsActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard, planType);
                }
                default: {
                    activity = generateDistanceActivity(idsActivity[idIndex], idUser, activities, minimumCaloriesConsumption, maximumActivitiesPerDay, doDate, hasHard, wantsHard);
                    break;
                }
            }
            activities.add(activity);
            hasHard = activities.stream().anyMatch(a -> a instanceof Hard);
        }
    }
    //endregion

    //endregion

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserController that = (UserController) o;
        return this.getUsers().equals(that.getUsers());
    }

    public UserController clone(){
        return new UserController(this);
    }

//    public void updateUser(User user) {
//        this.users.removeUser(user.getId());
//        this.users.addUser(user);
//    }

}
