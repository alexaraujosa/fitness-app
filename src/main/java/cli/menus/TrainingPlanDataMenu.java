package cli.menus;

import TrainingPlan.TrainingPlan;
import activities.Activity;
import cli.Constants;
import com.googlecode.lanterna.gui2.*;
import josefinFA.JosefinFitnessApp;
import utils.Logger;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainingPlanDataMenu extends AbstractWindow implements MenuPage {
    private WindowBasedTextGUI textGUI;
    private JosefinFitnessApp app;

    private Object result;

    public TrainingPlanDataMenu(
            WindowBasedTextGUI textGUI,
            String title,
            JosefinFitnessApp app,
            TrainingPlan plan,
            boolean readonly
    ) throws NoSuchMethodException {
        super(title.isEmpty() ? "New Training Plan" : title);
        this.setHints(java.util.Set.copyOf(Collections.singletonList(Hint.CENTERED)));

        String trueTitle = AdminMenu.isAdminMode() ? "[" + AdminMenu.ADMIN_MARK + "] " + this.getTitle() : this.getTitle();
        this.setTitle(trueTitle);

        this.textGUI = textGUI;
        this.app = app;

        this.result = null;

        Panel contentPanel = new Panel();
        contentPanel.setLayoutManager((new GridLayout(2)).setLeftMarginSize(1).setRightMarginSize(1));
        contentPanel.setTheme(Constants.ENABLED_THEME);

        Method[] activityConstructors = AddActivityMenu.constructDefaultActivityConstructors(app, new String[]{
                "createAbdominalExercises",
                "createLegExtension",
                "createMountainBiking",
                "createPushUps",
                "createRoadCycling",
                "createRoadRunning",
                "createRowing",
                "createSkating",
                "createStretching",
                "createTrackRunning",
                "createTrailRunning",
                "createWeightLifting"
        });

        var activityStore = new Object(){
            public Activity act1 = (plan == null) ? null : plan.getActivities().getFirst();
            public Button act1Btn = null;

            public Activity act2 = (plan == null) ? null : plan.getActivities().get(1);
            public Button act2Btn = null;

            public Activity act3 = (plan == null) ? null : plan.getActivities().get(2);
            public Button act3Btn = null;
        };

        Label act1Label = new Label("Activity 1:");
        activityStore.act1Btn = new Button(
                (activityStore.act1 == null) ? "None" : activityStore.act1.getName(),
                () -> {
                    try {
                        assert activityConstructors != null;
                        activityStore.act1 = (Activity)new AddActivityMenu(textGUI, "Activity 1", app, true)
                                .setActivityConstructors(activityConstructors)
                                .setActivityConstructorNamePattern("create(.*)", (s) -> s.replace("create", ""))
                                .init()
                                .show();

                        Logger.logger.info("Activity 1: " + activityStore.act1);

                        if (activityStore.act1 != null) {
                            if (plan != null && !readonly) plan.getActivities().set(0, activityStore.act1);

                            activityStore.act1Btn.setLabel(activityStore.act1.getName());
                            this.textGUI.updateScreen();
                        }
                    } catch (NoSuchMethodException | IOException e) {
                        Logger.logger.warning("Unable to create activity 1: " + e.getMessage());
                    }
                }
        ).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(act1Label);
        contentPanel.addComponent(activityStore.act1Btn);

        Label act2Label = new Label("Activity 2:");
        activityStore.act2Btn = new Button(
                (activityStore.act2 == null) ? "None" : activityStore.act2.getName(),
                () -> {
                    try {
                        assert activityConstructors != null;
                        activityStore.act2 = (Activity)new AddActivityMenu(textGUI, "Activity 2", app, true)
                                .setActivityConstructors(activityConstructors)
                                .setActivityConstructorNamePattern("create(.*)", (s) -> s.replace("create", ""))
                                .init()
                                .show();

                        Logger.logger.info("Activity 2: " + activityStore.act2);

                        if (activityStore.act2 != null) {
                            if (plan != null && !readonly) plan.getActivities().set(1, activityStore.act2);

                            activityStore.act2Btn.setLabel(activityStore.act2.getName());
                            this.textGUI.updateScreen();
                        }
                    } catch (NoSuchMethodException | IOException e) {
                        Logger.logger.warning("Unable to create activity 2: " + e.getMessage());
                    }
                }
        ).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(act2Label);
        contentPanel.addComponent(activityStore.act2Btn);

        Label act3Label = new Label("Activity 3:");
        activityStore.act3Btn = new Button(
                (activityStore.act3 == null) ? "None" : activityStore.act3.getName(),
                () -> {
                    try {
                        assert activityConstructors != null;
                        activityStore.act3 = (Activity)new AddActivityMenu(textGUI, "Activity 3", app, true)
                                .setActivityConstructors(activityConstructors)
                                .setActivityConstructorNamePattern("create(.*)", (s) -> s.replace("create", ""))
                                .init()
                                .show();

                        Logger.logger.info("Activity 3: " + activityStore.act3);

                        if (activityStore.act3 != null) {
                            if (plan != null && !readonly) plan.getActivities().set(2, activityStore.act3);

                            activityStore.act3Btn.setLabel(activityStore.act3.getName());
                            this.textGUI.updateScreen();
                        }
                    } catch (NoSuchMethodException | IOException e) {
                        Logger.logger.warning("Unable to create activity 3: " + e.getMessage());
                    }
                }
        ).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(act3Label);
        contentPanel.addComponent(activityStore.act3Btn);

        if (readonly) {
            activityStore.act1Btn.setEnabled(false);
            activityStore.act2Btn.setEnabled(false);
            activityStore.act3Btn.setEnabled(false);
        }

        // Weekdays checkboxes
        CheckBoxList<String> repeatDaysChBox = new CheckBoxList<String>().setLayoutData(GridLayout.createHorizontallyFilledLayoutData());
        repeatDaysChBox.addItem("M");
        repeatDaysChBox.addItem("T");
        repeatDaysChBox.addItem("W");
        repeatDaysChBox.addItem("Th");
        repeatDaysChBox.addItem("F");
        repeatDaysChBox.addItem("Sa");
        repeatDaysChBox.addItem("Su");

        if (plan != null) {
            for (int i = 0; i < repeatDaysChBox.getItemCount(); i++) {
                // Why the fuck you have methods that operate on indexes for everything EXCEPT the setCheck, lanterna, you fucker?
                repeatDaysChBox.setChecked(repeatDaysChBox.getItemAt(i), plan.getRepeat()[i]);
            }
        }

        contentPanel.addComponent(new Label("Repeat:"));
        contentPanel.addComponent(repeatDaysChBox);
        if (readonly) repeatDaysChBox.setEnabled(false);

        Button confirmButton = new Button(
                "Confirm",
                () -> {
                    List<Activity> activities = new ArrayList<>();
                    if (activityStore.act1 != null) activities.add(activityStore.act1);
                    if (activityStore.act2 != null) activities.add(activityStore.act2);
                    if (activityStore.act3 != null) activities.add(activityStore.act3);

                    this.result = new TrainingPlan(
                            AdminMenu.getExplicitLoadedUserId(app),
                            activities,
                            LocalDate.now(),
                            new boolean[]{
                                    repeatDaysChBox.isChecked("M"),
                                    repeatDaysChBox.isChecked("T"),
                                    repeatDaysChBox.isChecked("W"),
                                    repeatDaysChBox.isChecked("Th"),
                                    repeatDaysChBox.isChecked("F"),
                                    repeatDaysChBox.isChecked("Sa"),
                                    repeatDaysChBox.isChecked("Su")
                            }
                    );
                    this.close();
                }
        );
        contentPanel.addComponent(confirmButton);


        Button backButton = new Button(
                "Back",
                this::close
        );
        contentPanel.addComponent(backButton);

        this.setComponent(contentPanel);
    }

    @Override
    public Object show() {
        this.textGUI.addWindow(this);
        this.waitUntilClosed();
        return this.result;
    }
}
