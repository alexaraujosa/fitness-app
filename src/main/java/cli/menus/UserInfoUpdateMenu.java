package cli.menus;

import cli.Constants;
import cli.util.Transformer;
import com.googlecode.lanterna.TerminalSize;
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
    private TextBox nameBox;
    private TextBox usernameBox;
    private TextBox birthDateBox;
    private TextBox addressBox;
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

        TerminalSize boxSize = new TerminalSize(30, 1);

        User userData = app.getUserController().getUsers().getUserWithId(app.getUserID());

        String originalName                    = userData.getName();
        String originalUsername                = userData.getUsername();
        String originalBirthDate               = userData.getBirthdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String originalAddress                 = userData.getAddress();
        String originalEmail                   = userData.getEmail();
        String originalHeight                  = String.valueOf(userData.getHeight());
        String originalWeight                  = String.valueOf(userData.getWeight());
        String originalAverageCardiacFrequency = String.valueOf(userData.getHeartFreq());

        nameBox = new TextBox(originalName)
                .setPreferredSize(boxSize)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Name:"));
        contentPanel.addComponent(nameBox);

        usernameBox = new TextBox(originalUsername)
                .setPreferredSize(boxSize)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Username:"));
        contentPanel.addComponent(usernameBox);

        birthDateBox = new TextBox(originalBirthDate)
                .setPreferredSize(boxSize)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Birth date:"));
        contentPanel.addComponent(birthDateBox);

        addressBox = new TextBox(originalAddress)
                .setPreferredSize(boxSize)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Address:"));
        contentPanel.addComponent(addressBox);

        emailBox = new TextBox(originalEmail)
                .setPreferredSize(boxSize)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Email:"));
        contentPanel.addComponent(emailBox);

        heightBox = new TextBox(originalHeight)
                .setPreferredSize(boxSize)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Height:"));
        contentPanel.addComponent(heightBox);

        weightBox = new TextBox(originalWeight)
                .setPreferredSize(boxSize)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Weight:"));
        contentPanel.addComponent(weightBox);

        averageCardiacFrequencyBox = new TextBox(originalAverageCardiacFrequency)
                .setPreferredSize(boxSize)
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

                        tryUpdateFromTextBox(usernameBox, originalUsername, app::updateUserUsername, Transformer::transformToString);
                        tryUpdateFromTextBox(birthDateBox, originalBirthDate, app::updateUserBirthdate, Transformer::transformToLocalDate);
                        tryUpdateFromTextBox(emailBox, originalEmail, app::updateUserEmail, Transformer::transformToString);
                        tryUpdateFromTextBox(heightBox, originalHeight, app::updateUserHeight, Transformer::transformToDouble);
                        tryUpdateFromTextBox(weightBox, originalWeight, app::updateUserWeight, Transformer::transformToDouble);
                        tryUpdateFromTextBox(
                                averageCardiacFrequencyBox,
                                originalAverageCardiacFrequency,
                                app::updateUserHearFreq,
                                Transformer::transformToInt
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

//    private String transformToString(String orig) {
//        return orig;
//    }
//
//    private Integer transformToInt(String orig) {
//        return Integer.parseInt(orig);
//    }
//
//    private Double transformToDouble(String orig) {
//        return Double.parseDouble(orig);
//    }
//
//    private LocalDate transformToLocalDate(String orig) {
//        return LocalDate.parse(orig, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//    }

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
