package cli.menus;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import exceptions.ErrorLoggingInException;
import josefinFA.JosefinFitnessApp;
import utils.Logger;

import java.util.Collections;

public class SignupMenu extends AbstractWindow implements MenuPage  {
    private WindowBasedTextGUI textGUI;
    private JosefinFitnessApp app;
    private TextBox usernameBox;
    private TextBox birthDateBox;
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


    SignupMenu(WindowBasedTextGUI textGUI, String title, JosefinFitnessApp app) {
        super(title);
        this.setHints(java.util.Set.copyOf(Collections.singletonList(Hint.CENTERED)));

        this.textGUI = textGUI;
        this.app = app;

        Panel contentPanel = new Panel();
        contentPanel.setLayoutManager((new GridLayout(2)).setLeftMarginSize(1).setRightMarginSize(1));

        usernameBox = new TextBox().setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Username:"));
        contentPanel.addComponent(usernameBox);

        birthDateBox = new TextBox().setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Birth date:"));
        contentPanel.addComponent(birthDateBox);

        emailBox = new TextBox().setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Email:"));
        contentPanel.addComponent(emailBox);

        heightBox = new TextBox().setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Height:"));
        contentPanel.addComponent(heightBox);

        weightBox = new TextBox().setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Weight:"));
        contentPanel.addComponent(weightBox);

        averageCardiacFrequencyBox = new TextBox().setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Average Cardiac Frequency:"));
        contentPanel.addComponent(averageCardiacFrequencyBox);

        sexBox = new ComboBox<String>();
        sexBox.addItem("Yes");
        sexBox.addItem("No");
        sexBox.addItem("Maybe");
        contentPanel.addComponent(new Label("Sex:"));
        contentPanel.addComponent(sexBox);

        contentPanel.addComponent(new EmptySpace(new TerminalSize(1, 1)));
        contentPanel.addComponent(new EmptySpace(new TerminalSize(1, 1)));
        contentPanel.addComponent(new EmptySpace(new TerminalSize(1, 1)));

        Button signupButton = (Button)new Button(
                "Sign Up",
                () -> {
                    this.close();
                }
        ).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(signupButton);

        errorLabel = new Label("" )
                .setLayoutData(GridLayout.createLayoutData(
                        GridLayout.Alignment.BEGINNING,
                        GridLayout.Alignment.BEGINNING,
                        true,
                        true,
                        5,
                        1
                ))
                .setForegroundColor(TextColor.ANSI.RED_BRIGHT);
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
            errorLabel.setVisible(false);
        }

        return super.handleInput(key);
    }
}
