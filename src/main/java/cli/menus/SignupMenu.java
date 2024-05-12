package cli.menus;

import cli.Constants;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import exceptions.ErrorLoggingInException;
import exceptions.UsernameAlreadyExistsException;
import josefinFA.JosefinFitnessApp;
import utils.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Objects;
import java.util.regex.Pattern;

public class SignupMenu extends AbstractWindow implements MenuPage  {
    private WindowBasedTextGUI textGUI;
    private JosefinFitnessApp app;

    private Panel contentPanel;
    private ComboBox<String> userTypeBox;
    private TextBox nameBox;
    private TextBox usernameBox;
    private TextBox birthDateBox;
    private TextBox addressBox;
    private TextBox emailBox;
    private TextBox heightBox;
    private TextBox weightBox;
    private TextBox averageCardiacFrequencyBox;
    private ComboBox<String> sexBox;
    private Label errorLabel;

    // Username
    // Data nascimento
    // Morada
    // Email
    // Altura
    // Peso
    // Frequência Cardiaca Média
    // Sexo

    TerminalSize boxSize = new TerminalSize(25, 1);

    SignupMenu(WindowBasedTextGUI textGUI, String title, JosefinFitnessApp app) {
        super(title);
        this.setHints(java.util.Set.copyOf(Collections.singletonList(Hint.CENTERED)));

        this.textGUI = textGUI;
        this.app = app;

        contentPanel = new Panel();
        contentPanel.setLayoutManager((new GridLayout(2)).setLeftMarginSize(1).setRightMarginSize(1));

        userTypeBox = new ComboBox<String>().setPreferredSize(boxSize);
        userTypeBox.addItem("Amateur");
        userTypeBox.addItem("Casual");
        userTypeBox.addItem("Professional");
        contentPanel.addComponent(new Label("Type:"));
        contentPanel.addComponent(userTypeBox);

        nameBox = new TextBox()
                .setPreferredSize(boxSize)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Name:"));
        contentPanel.addComponent(nameBox);

        usernameBox = new TextBox()
                .setPreferredSize(boxSize)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Username:"));
        contentPanel.addComponent(usernameBox);

        birthDateBox = new TextBox()
                .setPreferredSize(boxSize)
                .setValidationPattern(Pattern.compile("^[\\d/]{0,10}$"))
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Birth date:"));
        contentPanel.addComponent(birthDateBox);

        addressBox = new TextBox()
                .setPreferredSize(boxSize)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Address:"));
        contentPanel.addComponent(addressBox);

        emailBox = new TextBox()
                .setPreferredSize(boxSize)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Email:"));
        contentPanel.addComponent(emailBox);

        heightBox = new TextBox()
                .setPreferredSize(boxSize)
                .setValidationPattern(Pattern.compile("^[1-9]+\\.?\\d*$"))
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Height:"));
        contentPanel.addComponent(heightBox);

        weightBox = new TextBox()
                .setPreferredSize(boxSize)
                .setValidationPattern(Pattern.compile("^[1-9]+\\.?\\d*$"))
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Weight:"));
        contentPanel.addComponent(weightBox);

        averageCardiacFrequencyBox = new TextBox()
                .setPreferredSize(boxSize)
                .setValidationPattern(Pattern.compile("^\\d*$"))
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Average Cardiac Frequency:"));
        contentPanel.addComponent(averageCardiacFrequencyBox);

        sexBox = new ComboBox<String>().setPreferredSize(boxSize);
        sexBox.addItem("Yes");
        sexBox.addItem("No");
        contentPanel.addComponent(new Label("Sex:"));
        contentPanel.addComponent(sexBox);

        contentPanel.addComponent(new EmptySpace(new TerminalSize(1, 1)));
        contentPanel.addComponent(new EmptySpace(new TerminalSize(1, 1)));
//        contentPanel.addComponent(new EmptySpace(new TerminalSize(1, 1)));

        Button signupButton = (Button)new Button(
                AdminMenu.isAdminMode() ? "Create User" : "Signup",
                () -> {
                    try {
                        String name = "";
                        String username = "";
                        LocalDate birthDate = null;
                        String address = "";
                        String email = "";
                        double height = 0;
                        double weight = 0;
                        int averageCardiacFrequency = 0;

                        if (nameBox.getText().isEmpty()) {
                            nameBox.setTheme(Constants.WARNING_TEXTBOX);
                            throw new ErrorLoggingInException("Name must be filled");
                        }
                        name = nameBox.getText();

                        if (usernameBox.getText().isEmpty()) {
                            usernameBox.setTheme(Constants.WARNING_TEXTBOX);
                            throw new ErrorLoggingInException("Username must be filled");
                        }
                        username = usernameBox.getText();

                        if (birthDateBox.getText().isEmpty()) {
                            birthDateBox.setTheme(Constants.WARNING_TEXTBOX);
                            throw new ErrorLoggingInException("Birth date must be filled");
                        }
                        birthDate = LocalDate.parse(birthDateBox.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                        if (addressBox.getText().isEmpty()) {
                            addressBox.setTheme(Constants.WARNING_TEXTBOX);
                            throw new ErrorLoggingInException("Address must be filled");
                        }
                        address = addressBox.getText();

                        if (emailBox.getText().isEmpty()) {
                            emailBox.setTheme(Constants.WARNING_TEXTBOX);
                            throw new ErrorLoggingInException("Email must be filled");
                        }
                        email = emailBox.getText();

                        if (heightBox.getText().isEmpty()) {
                            heightBox.setTheme(Constants.WARNING_TEXTBOX);
                            throw new ErrorLoggingInException("Height must be filled");
                        }
                        height = Double.parseDouble(heightBox.getText());

                        if (weightBox.getText().isEmpty()) {
                            weightBox.setTheme(Constants.WARNING_TEXTBOX);
                            throw new ErrorLoggingInException("Weight must be filled");
                        }
                        weight = Double.parseDouble(weightBox.getText());

                        if (averageCardiacFrequencyBox.getText().isEmpty()) {
                            averageCardiacFrequencyBox.setTheme(Constants.WARNING_TEXTBOX);
                            throw new ErrorLoggingInException("Average Cardiac Frequency must be filled");
                        }
                        averageCardiacFrequency = Integer.parseInt(averageCardiacFrequencyBox.getText());

                        switch (userTypeBox.getText()) {
                            case "Amateur":
                                app.signupAmateurUser(
                                        name,
                                        username,
                                        birthDate,
                                        address,
                                        email,
                                        Objects.equals(sexBox.getText(), "Yes"),
                                        height,
                                        weight,
                                        averageCardiacFrequency
                                );
                                break;
                            case "Casual":
                                app.signupCasualUser(
                                        name,
                                        username,
                                        birthDate,
                                        address,
                                        email,
                                        Objects.equals(sexBox.getText(), "Yes"),
                                        height,
                                        weight,
                                        averageCardiacFrequency
                                );
                                break;
                            case "Professional":
                                app.signupProfessionalUser(
                                        name,
                                        username,
                                        birthDate,
                                        address,
                                        email,
                                        Objects.equals(sexBox.getText(), "Yes"),
                                        height,
                                        weight,
                                        averageCardiacFrequency
                                );
                                break;
                        }

                        Logger.logger.info("Added new user: " + app.getUserController().getUsers().getUserWithUsername(username));

                        app.saveState(Constants.getSaveFilePath());
                        this.close();
                    } catch (Exception e) {
                        errorLabel.setText(e.getMessage());
                        errorLabel.setVisible(true);
                        contentPanel.addComponent(errorLabel);
                    }
                }
        ).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(signupButton);

        Button backButton = new Button(
                "Back",
                this::close
        ).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(backButton);

        errorLabel = new Label("" )
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

    public String show() {
        this.textGUI.addWindow(this);
        this.waitUntilClosed();
        return usernameBox.getText();
    }

    @Override
    public boolean handleInput(KeyStroke key) {
//        if (key.getKeyType() == KeyType.Escape) {
//            this.close();
//        }
//        else if (key.getKeyType() == KeyType.Enter) {
//            Logger.logger.info("BOX: " + usernameBox.getText());
//
//            try {
//                app.login(usernameBox.getText());
//                this.close();
//            } catch (ErrorLoggingInException e) {
//                errorLabel.setText(e.getMessage());
//                errorLabel.setVisible(true);
//            }
//            return false;
//        }

        if (errorLabel.isVisible()) {
            nameBox.setTheme(Constants.ENABLED_THEME);
            usernameBox.setTheme(Constants.ENABLED_THEME);
            birthDateBox.setTheme(Constants.ENABLED_THEME);
            addressBox.setTheme(Constants.ENABLED_THEME);
            emailBox.setTheme(Constants.ENABLED_THEME);
            heightBox.setTheme(Constants.ENABLED_THEME);
            weightBox.setTheme(Constants.ENABLED_THEME);
            averageCardiacFrequencyBox.setTheme(Constants.ENABLED_THEME);

            errorLabel.setVisible(false);
            contentPanel.removeComponent(errorLabel);
        }

        return super.handleInput(key);
    }
}
