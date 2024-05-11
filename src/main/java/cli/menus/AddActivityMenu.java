package cli.menus;

import cli.Constants;
import com.googlecode.lanterna.gui2.*;
import josefinFA.JosefinFitnessApp;
import utils.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AddActivityMenu extends AbstractWindow implements MenuPage {
    private WindowBasedTextGUI textGUI;
    private JosefinFitnessApp app;

    private Panel contentPanel;
    private boolean isComponent;
    private Method[] activityConstructors;
    private Object result;

    /*
     * Reserved parameter names that should not be displayed in the menu.
     */
    private static final String[] RESERVED_PARAMETER_NAMES = new String[]{"id"};

    // Parameter names are not kept without debug information, keep a local name registry.
    private static final String[][] ACTIVITY_CONSTRUCTOR_PARAMETER_NAMES = new String[][]{
            /* addAbdominalExercisesToUser */ new String[]{"id", "name", "begin", "end", "heartRate", "nRepetitions", "helped"},
            /* addLegExtensionToUser */       new String[]{"id", "name", "begin", "end", "heartRate", "nRepetitions", "weight", "chairAngle"},
            /* addMountainBikingToUser */     new String[]{"id", "name", "begin", "end", "heartRate", "distance", "altimetry", "bigTires"},
            /* addPushUpsToUser */            new String[]{"id", "name", "begin", "end", "heartRate", "nRepetitions", "diamondIntercalated"},
            /* addRoadCyclingToUser */        new String[]{"id", "name", "begin", "end", "heartRate", "distance", "altimetry", "windAgainst"},
            /* addRoadRunningToUser */        new String[]{"id", "name", "begin", "end", "heartRate", "distance", "altimetry", "windAgainst"},
            /* addRowingToUser */             new String[]{"id", "name", "begin", "end", "heartRate", "distance", "personsOnBoard", "rowAgainstTide"},
            /* addSkatingToUser */            new String[]{"id", "name", "begin", "end", "heartRate", "distance", "skateWeight", "freestyle"},
            /* addStretchingToUser */         new String[]{"id", "name", "begin", "end", "heartRate", "nRepetitions", "helped"},
            /* addTrackRunningToUser */       new String[]{"id", "name", "begin", "end", "heartRate", "distance", "hurdleJump"},
            /* addTrailRunningToUser */       new String[]{"id", "name", "begin", "end", "heartRate", "distance", "altimetry", "wetFloor"},
            /* addWeightLiftingToUser */      new String[]{"id", "name", "begin", "end", "heartRate", "nRepetitions", "weight", "helped"},
    };

    private Pattern METHOD_NAME_PATTERN = Pattern.compile("add(.*?)ToUser");
    private Function<String, String> METHOD_NAME_REPLACER = AddActivityMenu::convertCamelCaseToWords;

    public AddActivityMenu(WindowBasedTextGUI textGUI, String title, JosefinFitnessApp app, boolean isComponent) throws NoSuchMethodException {
        super(!Objects.equals(title, "") ? title : "Add Activity");
        this.setHints(java.util.Set.copyOf(Collections.singletonList(Hint.CENTERED)));

        this.textGUI = textGUI;
        this.app = app;
        this.result = null;
        this.isComponent = isComponent;

//        activityConstructors = new Method[]{
//                app.getClass().getDeclaredMethod("addAbdominalExercisesToUser", int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, boolean.class),
//                app.getClass().getDeclaredMethod("addLegExtensionToUser", int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, int.class, int.class),
//                app.getClass().getDeclaredMethod("addMountainBikingToUser", int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, int.class, boolean.class),
//                app.getClass().getDeclaredMethod("addPushUpsToUser", int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, boolean.class),
//                app.getClass().getDeclaredMethod("addRoadCyclingToUser", int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, int.class, boolean.class),
//                app.getClass().getDeclaredMethod("addRoadRunningToUser", int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, int.class, boolean.class),
//                app.getClass().getDeclaredMethod("addRowingToUser", int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, int.class, boolean.class),
//                app.getClass().getDeclaredMethod("addSkatingToUser", int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, double.class, boolean.class),
//                app.getClass().getDeclaredMethod("addStretchingToUser", int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, boolean.class),
//                app.getClass().getDeclaredMethod("addTrackRunningToUser", int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, boolean.class),
//                app.getClass().getDeclaredMethod("addTrailRunningToUser", int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, int.class, boolean.class),
//                app.getClass().getDeclaredMethod("addWeightLiftingToUser", int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, int.class, boolean.class)
//        };
        activityConstructors = constructDefaultActivityConstructors(app, new String[]{
                "addAbdominalExercisesToUser",
                "addLegExtensionToUser",
                "addMountainBikingToUser",
                "addPushUpsToUser",
                "addRoadCyclingToUser",
                "addRoadRunningToUser",
                "addRowingToUser",
                "addSkatingToUser",
                "addStretchingToUser",
                "addTrackRunningToUser",
                "addTrailRunningToUser",
                "addWeightLiftingToUser"
        });

        contentPanel = new Panel();
        contentPanel.setLayoutManager((new GridLayout(1)).setLeftMarginSize(1).setRightMarginSize(1));
        contentPanel.setTheme(Constants.ENABLED_THEME);

        if (!isComponent) this.init();

        this.setComponent(contentPanel);
    }

    public AddActivityMenu init() {
        Logger.logger.info("ACTIVITY CONSTRUCTORS: " + Arrays.toString(activityConstructors));

        for (int i = 0; i < activityConstructors.length; i++) {
            Method constructor = activityConstructors[i];
            String name = convertCamelCaseToWords(Objects.requireNonNull(getMethodActivityName(constructor.getName())));
            int finalI = i;
            Button btn = new Button(
                    name,
                    () -> {
                        Parameter[] params = constructor.getParameters();
                        List<AddActivityEntryMenu.Argument> args = new ArrayList<>();

                        for (int j = 0; j < params.length; j++) {
                            Parameter param = params[j];
                            String paramName = ACTIVITY_CONSTRUCTOR_PARAMETER_NAMES[finalI][j];

                            // Ignore reserved parameter names.
                            if (Arrays.asList(RESERVED_PARAMETER_NAMES).contains(paramName)) {
                                continue;
                            }

                            args.add(new AddActivityEntryMenu.Argument(
                                    paramName,
                                    AddActivityEntryMenu.getArgumentTypeFromClass(param.getType())
                            ));
                        }

                        Map<String, Object> resultData = new AddActivityEntryMenu(
                                textGUI,
                                name,
                                app,
                                args.toArray(new AddActivityEntryMenu.Argument[0])
                        ).show();

                        if (resultData == null) {
                            return;
                        }

                        Object[] result = new Object[params.length];
                        for (int j = 0; j < params.length; j++) {
                            String paramName = ACTIVITY_CONSTRUCTOR_PARAMETER_NAMES[finalI][j];
                            if (Arrays.asList(RESERVED_PARAMETER_NAMES).contains(paramName)) {
                                if (paramName.equals("id")) {
                                    result[j] = app.getUserID();
                                }
                            } else {
                                result[j] = resultData.get(paramName);
                            }
                        }

                        try {
//                            Logger.logger.info("OLD ACTIVITIES: " + app.getUsersActivities(app.getUserID()));
//
//                            Logger.logger.info("RESULT: " + Arrays.toString(result));
//                            Logger.logger.info("PARAMS: " + Arrays.toString(params));

                            Logger.logger.info("METHOD: " + constructor);

                            if (isComponent) {
                                this.result = constructor.invoke(app, result);
                            } else {
                                constructor.invoke(app, result);
                                app.saveState(Constants.getSaveFilePath());
                                this.result = MenuId.USER_MENU;
                            }

//                            Logger.logger.info("NEW ACTIVITIES: " + app.getUsersActivities(app.getUserID()));
                            this.close();
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            Logger.logger.warning("Unable to add activity: " + e.getMessage());
                            Logger.logger.warning(
                                    Arrays.stream(e.getStackTrace())
                                            .map(StackTraceElement::toString)
                                            .collect(Collectors.joining("\n"))
                            );
                        }
                    }
            );
            contentPanel.addComponent(btn);
        }

        contentPanel.addComponent(new EmptySpace());

        Button backButton = new Button(
                "Back",
                this::close
        );
        contentPanel.addComponent(backButton);

        return this;
    }

    public AddActivityMenu setActivityConstructors(Method[] activityConstructors) {
        this.activityConstructors = activityConstructors.clone();
        return this;
    }

    public AddActivityMenu setActivityConstructorNamePattern(String pattern, Function<String, String> replacer) {
        this.METHOD_NAME_PATTERN = Pattern.compile(pattern);
        this.METHOD_NAME_REPLACER = replacer;
        return this;
    }

    public static Method[] constructDefaultActivityConstructors(JosefinFitnessApp app, String[] methodNames) throws NoSuchMethodException {
        if (methodNames.length < 12) return null;

        return new Method[]{
                app.getClass().getDeclaredMethod(methodNames[0], int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, boolean.class),
                app.getClass().getDeclaredMethod(methodNames[1], int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, int.class, int.class),
                app.getClass().getDeclaredMethod(methodNames[2], int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, int.class, boolean.class),
                app.getClass().getDeclaredMethod(methodNames[3], int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, boolean.class),
                app.getClass().getDeclaredMethod(methodNames[4], int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, int.class, boolean.class),
                app.getClass().getDeclaredMethod(methodNames[5], int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, int.class, boolean.class),
                app.getClass().getDeclaredMethod(methodNames[6], int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, int.class, boolean.class),
                app.getClass().getDeclaredMethod(methodNames[7], int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, double.class, boolean.class),
                app.getClass().getDeclaredMethod(methodNames[8], int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, boolean.class),
                app.getClass().getDeclaredMethod(methodNames[9], int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, boolean.class),
                app.getClass().getDeclaredMethod(methodNames[10], int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, int.class, boolean.class),
                app.getClass().getDeclaredMethod(methodNames[11], int.class, String.class, LocalDateTime.class, LocalDateTime.class, int.class, int.class, int.class, boolean.class)
        };
    }

    private String getMethodActivityName(String orig) {
        Logger.logger.info("METHOD ORIG: " + orig);
        Matcher matcher = this.METHOD_NAME_PATTERN.matcher(orig);

        if (matcher.find()) {
            Logger.logger.info("METHOD FOUND: " + matcher.group(1));
            return matcher.group(1);
        } else {
            Logger.logger.info("METHOD NOT FOUND." );
            return null;
        }
    }

    public static String convertCamelCaseToWords(String input) {
        String result = input.replaceAll("(\\p{Lower})(\\p{Upper})", "$1 $2");
        result = result.substring(0, 1).toUpperCase() + result.substring(1);
        return result;
    }

    @Override
    public Object show() {
        this.textGUI.addWindow(this);
        this.waitUntilClosed();
        return this.result;
    }
}
