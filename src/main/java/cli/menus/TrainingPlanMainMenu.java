package cli.menus;

import TrainingPlan.TrainingPlan;
import activities.Activity;
import cli.Constants;
import com.googlecode.lanterna.gui2.*;
import exceptions.ErrorHardActivityCloseException;
import exceptions.InvalidValueException;
import josefinFA.JosefinFitnessApp;
import utils.Logger;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TrainingPlanMainMenu extends AbstractWindow implements MenuPage {
    private WindowBasedTextGUI textGUI;
    private JosefinFitnessApp app;

    private MenuId result;

    public TrainingPlanMainMenu(WindowBasedTextGUI textGUI, String title, JosefinFitnessApp app) throws NoSuchMethodException {
        super(title.isEmpty() ? "Training Plan List" : title);
        this.setHints(java.util.Set.copyOf(Collections.singletonList(Hint.CENTERED)));

        String trueTitle = AdminMenu.isAdminMode() ? "[" + AdminMenu.ADMIN_MARK + "] " + this.getTitle() : this.getTitle();
        this.setTitle(trueTitle);

        this.textGUI = textGUI;
        this.app = app;

        this.result = MenuId.USER_MENU;

        List<TrainingPlan> trainingPlans = app.getUserController().getUsers().getUserWithId(AdminMenu.getExplicitLoadedUserId(app)).getTrainingSchedule();

        Panel contentPanel = new Panel();
        contentPanel.setLayoutManager((new GridLayout(2)).setLeftMarginSize(1).setRightMarginSize(1));
        contentPanel.setTheme(Constants.ENABLED_THEME);

        Logger.logger.info("Loaded Training Plans: " + trainingPlans.size());

        Button createNewBtn = new Button(
                "Create New",
                () -> {
                    try {
                        TrainingPlan plan = (TrainingPlan) new TrainingPlanDataMenu(textGUI, "", app, null, false).show();

                        Logger.logger.info("Training Plan: " + plan);
                        if (plan != null) {
                            app.addManualTrainingPlan(AdminMenu.getLoadedUserId(app), plan.getActivities(), plan.getDoDate(), plan.getRepeat());
                            app.saveState(Constants.getSaveFilePath());
                        }

                        Logger.logger.info("Training Plans: " + trainingPlans.size());
                    } catch (NoSuchMethodException | InvalidValueException | ErrorHardActivityCloseException e) {
                        Logger.logger.warning("Unable to create or update Training Plan:" + e.getMessage());
                    }
                }
        ).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(createNewBtn);

        contentPanel.addComponent(new EmptySpace());

        Button generateNewBtn = new Button(
                "Generate New",
                () -> {
                    try {
                        new TrainingPlanAutomaticMenu(textGUI, "", app).show();
                    } catch (NoSuchMethodException e) {
                        Logger.logger.warning("Unable to generate Training Plan:" + e.getMessage());
                    }
//                    try {
//                        app.addAutomaticTrainingPlan(
//                                -1,
//                                false,
//                                1,
//                                LocalDate.now(),
//                                new boolean[]{false, false, false, false, false, false, false},
//                                1000,
//                                1
//                        );
//                        app.saveState(Constants.getSaveFilePath());
//
//                        Logger.logger.info("Training Plans: " + trainingPlans.size());
//                    } catch (InvalidValueException | ErrorHardActivityCloseException e) {
//                        Logger.logger.warning("Unable to generate Training Plan:" + e.getMessage());
//                    }
                }
        ).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(generateNewBtn);

//        Logger.logger.info("Training Plans: " + trainingPlans.size());

        // Would be interesting, but time is running out.
//        ComboBox<String> trainingPlanComboBox = new ComboBox<>();
//        for (TrainingPlan plan : trainingPlans) {
//            trainingPlanComboBox.addItem(plan.getDoDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
//        }
//        if (trainingPlans.isEmpty()) trainingPlanComboBox.setEnabled(false).setTheme(Constants.DISABLED_THEME);
//        contentPanel.addComponent(trainingPlanComboBox);
//
//        contentPanel.addComponent(new EmptySpace());

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
