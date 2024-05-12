package cli.menus;

import cli.Constants;
import cli.components.record.RecordWindow;
import cli.components.record.UserRecord;
import cli.util.Transformer;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import josefinFA.JosefinFitnessApp;
import users.User;
import utils.Logger;
import utils.Tuple;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class ListRecordsMenu extends AbstractWindow implements MenuPage {
    private WindowBasedTextGUI textGUI;
    private JosefinFitnessApp app;

    private MenuId result;

    private Panel contentPanel;
    private Label errorLabel;

    public ListRecordsMenu(WindowBasedTextGUI textGUI, String title, JosefinFitnessApp app) throws NoSuchMethodException {
        super(title.isEmpty() ? "List Records" : title);
        this.setHints(java.util.Set.copyOf(Collections.singletonList(Hint.CENTERED)));

//        String trueTitle = AdminMenu.isAdminMode() ? "[" + AdminMenu.ADMIN_MARK + "] " + this.getTitle() : this.getTitle();
//        this.setTitle(trueTitle);

        this.textGUI = textGUI;
        this.app = app;

        this.result = MenuId.MAIN_MENU;

        app.loadStats();

        TerminalSize boxSize = new TerminalSize(20, 1);

        var lambdaReferences = new Object() {
            LocalDateTime fromDate = null;
            Button mostCaloriesBurnedButton = null;
        };

        contentPanel = new Panel();
        contentPanel.setLayoutManager((new GridLayout(2)).setLeftMarginSize(1).setRightMarginSize(1));
        contentPanel.setTheme(Constants.ENABLED_THEME);

        TextBox systemDateBox = new TextBox(app.getSystemDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                .setValidationPattern(Pattern.compile("^[\\d/ :]{0,21}$"))
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("System date:"));
        contentPanel.addComponent(systemDateBox);
        systemDateBox.setTextChangeListener((text, textChange) -> {
            if (text.isEmpty()) {
                return;
            }

            try {
                LocalDateTime date = Transformer.transformToLocalDateTime(text);
                app.setSystemDate(date);
            } catch (Exception e) {
                // Do fuck all
            }
        });

        TextBox fromDateBox = new TextBox()
                .setValidationPattern(Pattern.compile("^[\\d/ :]{0,21}$"))
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Start date:"));
        contentPanel.addComponent(fromDateBox);
        fromDateBox.setTextChangeListener((text, textChange) -> {
            if (text.isEmpty()) {
                lambdaReferences.fromDate = null;
                lambdaReferences.mostCaloriesBurnedButton.setEnabled(false);
                return;
            }

            try {
                lambdaReferences.fromDate = Transformer.transformToLocalDateTime(text);
                fromDateBox.setTheme(Constants.ENABLED_THEME);
                lambdaReferences.mostCaloriesBurnedButton.setEnabled(true);
                lambdaReferences.mostCaloriesBurnedButton.setTheme(Constants.ENABLED_THEME);
            } catch (Exception e) {
                lambdaReferences.fromDate = null;
                fromDateBox.setTheme(Constants.WARNING_TEXTBOX);
                lambdaReferences.mostCaloriesBurnedButton.setEnabled(false);
                lambdaReferences.mostCaloriesBurnedButton.setTheme(Constants.DISABLED_THEME);
            }
        });

        Button mostCaloriesBurnedButton = new Button(
                "Most Calories Burned",
                () -> {
                    Logger.logger.info("FROM DATE: " + lambdaReferences.fromDate);
                    Tuple<User, Integer> record = app.userWithMostCaloriesBurned(lambdaReferences.fromDate);
                    new RecordWindow(textGUI, "Most Calories Burned", new UserRecord(record.getLeft(), record.getRight())).show();
                }
        );
        mostCaloriesBurnedButton.setTheme(Constants.DISABLED_THEME);
        mostCaloriesBurnedButton.setEnabled(false);
        contentPanel.addComponent(mostCaloriesBurnedButton);
        lambdaReferences.mostCaloriesBurnedButton = mostCaloriesBurnedButton;

        contentPanel.addComponent(new EmptySpace());
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
