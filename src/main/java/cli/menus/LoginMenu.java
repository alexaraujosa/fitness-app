package cli.menus;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import exceptions.ErrorLoggingInException;
import josefinFA.JosefinFitnessApp;
import utils.Logger;

import java.util.Collections;
import java.util.HashSet;

public class LoginMenu extends AbstractWindow implements MenuPage {
    private WindowBasedTextGUI textGUI;
    private JosefinFitnessApp app;
    private TextBox usernameBox;
    private Label errorLabel;


    LoginMenu(WindowBasedTextGUI textGUI, String title, JosefinFitnessApp app) {
        super(title);
        this.setHints(java.util.Set.copyOf(Collections.singletonList(Hint.CENTERED)));

        this.textGUI = textGUI;
        this.app = app;

        TerminalSize size = textGUI.getScreen().getTerminalSize();

        Panel contentPanel = new Panel();
        contentPanel.setLayoutManager((new GridLayout(2)).setLeftMarginSize(1).setRightMarginSize(1));
//        contentPanel.setPreferredSize(new TerminalSize(50, 4));

        usernameBox = new TextBox()
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER))
                .setPreferredSize(new TerminalSize(20, 1));
        contentPanel.addComponent(new Label("Username:"));
        contentPanel.addComponent(usernameBox);


//        errorLabel = new Label("Lorem Ipsum dolores sit amet | Size: " + size.getColumns()) // " ".repeat(size.getColumns() - 4 - 4)
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
//                .setPreferredSize(new TerminalSize(20, 1));
        errorLabel.setVisible(false);

        contentPanel.addComponent(errorLabel);

        this.setComponent(contentPanel);
    }

    public String show() {
        this.textGUI.addWindow(this);
        this.waitUntilClosed();
        Logger.logger.info("BOX: '" + usernameBox.getText() + "'");
        return usernameBox.getText();
    }

    @Override
    public boolean handleInput(KeyStroke key) {
        if (key.getKeyType() == KeyType.Escape) {
            this.close();
        } else if (key.getKeyType() == KeyType.Enter) {
            Logger.logger.info("BOX: " + usernameBox.getText());

            try {
                app.login(usernameBox.getText());
                this.close();
            } catch (ErrorLoggingInException e) {
                errorLabel.setText(e.getMessage());
                errorLabel.setVisible(true);
            }
            return false;
        }

        if (errorLabel.isVisible()) {
            errorLabel.setVisible(false);
        }

        return super.handleInput(key);
    }
}
