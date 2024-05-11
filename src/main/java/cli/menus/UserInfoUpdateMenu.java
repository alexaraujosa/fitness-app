package cli.menus;

import cli.Constants;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import exceptions.UserDoesNotExistsException;
import exceptions.UsernameAlreadyExistsException;
import josefinFA.JosefinFitnessApp;
import users.User;
import utils.ExceptionBiConsumer;
import utils.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;

public class UserInfoUpdateMenu extends AbstractWindow implements MenuPage {
    private WindowBasedTextGUI textGUI;
    private JosefinFitnessApp app;

    private Panel contentPanel;
    private TextBox usernameBox;
    private TextBox birthDateBox;
    private TextBox emailBox;
    private TextBox heightBox;
    private TextBox weightBox;
    private TextBox averageCardiacFrequencyBox;

    private Label errorLabel;


    public UserInfoUpdateMenu(WindowBasedTextGUI textGUI, String title, JosefinFitnessApp app) {
        super(!Objects.equals(title, "") ? title : "User Information Update");
        this.setHints(java.util.Set.copyOf(Collections.singletonList(Hint.CENTERED)));

        this.textGUI = textGUI;
        this.app = app;

        contentPanel = new Panel();
        contentPanel.setLayoutManager((new GridLayout(2)).setLeftMarginSize(1).setRightMarginSize(1));
        contentPanel.setTheme(Constants.ENABLED_THEME);

        // Name
        // Username
        // Birth Date
        // Address
        // Email
        // Height
        // Weight
        // HeartFreq

        User userData = app.getUserController().getUsers().getUserWithId(app.getUserID());

        String originalUsername                = userData.getUsername();
        String originalBirthDate               = userData.getBirthdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String originalEmail                   = userData.getEmail();
        String originalHeight                  = String.valueOf(userData.getHeight());
        String originalWeight                  = String.valueOf(userData.getWeight());
        String originalAverageCardiacFrequency = String.valueOf(userData.getWeight());

        usernameBox = new TextBox(originalUsername)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Username:"));
        contentPanel.addComponent(usernameBox);

        birthDateBox = new TextBox(originalBirthDate)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Birth date:"));
        contentPanel.addComponent(birthDateBox);

        emailBox = new TextBox(originalEmail).
                setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Email:"));
        contentPanel.addComponent(emailBox);

        heightBox = new TextBox(originalHeight)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Height:"));
        contentPanel.addComponent(heightBox);

        weightBox = new TextBox(originalWeight)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Weight:"));
        contentPanel.addComponent(weightBox);

        averageCardiacFrequencyBox = new TextBox(originalAverageCardiacFrequency)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Average Cardiac Frequency:"));
        contentPanel.addComponent(averageCardiacFrequencyBox);

        Button confirmButton = (Button)new Button(
                "Confirm",
                () -> {
                    try {
                        // Reset all TextBoxes
                        usernameBox.setTheme(Constants.ENABLED_THEME);
                        birthDateBox.setTheme(Constants.ENABLED_THEME);
                        emailBox.setTheme(Constants.ENABLED_THEME);
                        heightBox.setTheme(Constants.ENABLED_THEME);
                        weightBox.setTheme(Constants.ENABLED_THEME);
                        averageCardiacFrequencyBox.setTheme(Constants.ENABLED_THEME);

                        tryUpdateFromTextBox(usernameBox, originalUsername, app::updateUserUsername, this::transformToString);
                        tryUpdateFromTextBox(birthDateBox, originalBirthDate, app::updateUserBirthdate, this::transformToLocalDate);
                        tryUpdateFromTextBox(emailBox, originalEmail, app::updateUserEmail, this::transformToString);
                        tryUpdateFromTextBox(heightBox, originalHeight, app::updateUserHeight, this::transformToDouble);
                        tryUpdateFromTextBox(weightBox, originalWeight, app::updateUserWeight, this::transformToDouble);
                        tryUpdateFromTextBox(
                                averageCardiacFrequencyBox,
                                originalAverageCardiacFrequency,
                                app::updateUserHearFreq,
                                this::transformToInt
                        );

                        app.saveState(Constants.getSaveFilePath());
                        this.close();
                    } catch (Exception e) {
                        // Purposeful exception thrown to avoid updating other properties when it encounters a malformed input.
                        Logger.logger.warning("E");
                    }
                }
            ).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(confirmButton);

        Button cancelButton = (Button)new Button(
                "Cancel",
                () -> {
                    this.close();
                }
        ).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(cancelButton);

        errorLabel = new Label("")
                .setLayoutData(GridLayout.createLayoutData(
                        GridLayout.Alignment.BEGINNING,
                        GridLayout.Alignment.BEGINNING,
                        true,
                        true,
                        5,
                        1
                ))
                .setForegroundColor(TextColor.ANSI.RED);
        errorLabel.setVisible(false);

        this.setComponent(contentPanel);
    }

    private <T> boolean tryUpdateFromTextBox(
            TextBox box,
            String control,
            ExceptionBiConsumer<Integer, T, Exception> updater,
            Function<String, T> transformer
    ) throws Exception {
        if (!box.getText().equals(control)) {
            try {
                T trueValue = transformer.apply(box.getText());
                updater.accept(app.getUserID(), trueValue);
            } catch (Exception e) {
                Logger.logger.warning("Unable to update property: " + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));

                String msg = "Invalid input.";
                if (e instanceof UsernameAlreadyExistsException) msg = e.getMessage();

                box.setTheme(Constants.WARNING_TEXTBOX);
                errorLabel.setText(msg);
                errorLabel.setVisible(true);
                contentPanel.addComponent(errorLabel);
                throw new Exception();
            }
        }

        return true;
    }

    private String transformToString(String orig) {
        return orig;
    }

    private Integer transformToInt(String orig) {
        return Integer.parseInt(orig);
    }

    private Double transformToDouble(String orig) {
        return Double.parseDouble(orig);
    }

    private LocalDate transformToLocalDate(String orig) {
        return LocalDate.parse(orig, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public Object show() {
        this.textGUI.addWindow(this);
        this.waitUntilClosed();
        return MenuId.USER_MENU;
    }

    @Override
    public boolean handleInput(KeyStroke key) {
        if (errorLabel.isVisible()) {
            errorLabel.setVisible(false);
            contentPanel.removeComponent(errorLabel);
        }

        return super.handleInput(key);
    }
}
