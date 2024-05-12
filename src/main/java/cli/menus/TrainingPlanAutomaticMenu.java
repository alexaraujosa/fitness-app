package cli.menus;

import cli.Constants;
import com.googlecode.lanterna.gui2.*;
import exceptions.ErrorHardActivityCloseException;
import exceptions.InvalidValueException;
import josefinFA.JosefinFitnessApp;
import utils.Logger;

import java.time.LocalDate;
import java.util.Collections;
import java.util.regex.Pattern;

public class TrainingPlanAutomaticMenu extends AbstractWindow implements MenuPage {
    private WindowBasedTextGUI textGUI;
    private JosefinFitnessApp app;

    private Object result;

    public TrainingPlanAutomaticMenu(WindowBasedTextGUI textGUI, String title, JosefinFitnessApp app) throws NoSuchMethodException {
        super(title.isEmpty() ? "Generate Training Plan" : title);
        this.setHints(java.util.Set.copyOf(Collections.singletonList(Hint.CENTERED)));

        String trueTitle = AdminMenu.isAdminMode() ? "[" + AdminMenu.ADMIN_MARK + "] " + this.getTitle() : this.getTitle();
        this.setTitle(trueTitle);

        this.textGUI = textGUI;
        this.app = app;

        this.result = null;

        Panel contentPanel = new Panel();
        contentPanel.setLayoutManager((new GridLayout(2)).setLeftMarginSize(1).setRightMarginSize(1));
        contentPanel.setTheme(Constants.ENABLED_THEME);

        ComboBox<String> wantsHardCBox = new ComboBox<String>();
        wantsHardCBox.addItem("Yes");
        wantsHardCBox.addItem("No");
        contentPanel.addComponent(new Label("Generate hard:"));
        contentPanel.addComponent(wantsHardCBox);

        TextBox maxActivitiesPerDatBox = new TextBox()
                .setValidationPattern(Pattern.compile("^\\d*$"))
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Max activities per day:"));
        contentPanel.addComponent(maxActivitiesPerDatBox);

        TextBox minCaloriesBox = new TextBox()
                .setValidationPattern(Pattern.compile("^\\d*$"))
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Min calories to burn:"));
        contentPanel.addComponent(minCaloriesBox);

        // TODO: Plan Types

        Button generateBtn = new Button(
                "Generate",
                () -> {
                    try {
                        app.addAutomaticTrainingPlan(
                                AdminMenu.getLoadedUserId(app),
                                false,
                                1,
                                LocalDate.now(),
                                new boolean[]{false, false, false, false, false, false, false},
                                1000,
                                1
                        );
                        app.saveState(Constants.getSaveFilePath());
                    } catch (ErrorHardActivityCloseException | InvalidValueException e) {
                        Logger.logger.warning("Unable to generate Training Plan:" + e.getMessage());
                    }
                }
        ).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(generateBtn);

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
