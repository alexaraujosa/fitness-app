package cli.menus;

import cli.Constants;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import josefinFA.JosefinFitnessApp;
import users.UserController;
import utils.Logger;

import java.io.IOException;
import java.util.Collections;

public class MainMenu extends AbstractWindow implements MenuPage {
    private final WindowBasedTextGUI textGUI;
    private final JosefinFitnessApp app;

    private MenuId result = MenuId.MAIN_MENU;

    private static TextColor BACKGROUND = TextColor.ANSI.CYAN;

    public MainMenu(WindowBasedTextGUI textGUI, String title, JosefinFitnessApp app) {
        super(title);
        this.setHints(java.util.Set.copyOf(Collections.singletonList(Hint.CENTERED)));

        this.textGUI = textGUI;
        this.app = app;

        int sizeX = 0;
        int sizeY = 5;
        int offsetX = 3;
        int offsetY = 3;
        TerminalSize size = textGUI.getScreen().getTerminalSize();

        Panel contentPanel = new Panel();
        contentPanel.setFillColorOverride(BACKGROUND);

//        Theme theme = SimpleTheme.makeTheme(
//                true,
//                TextColor.ANSI.WHITE,
//                BACKGROUND,
//                TextColor.ANSI.WHITE,
//                TextColor.ANSI.BLACK,
//                TextColor.ANSI.BLUE,
//                TextColor.ANSI.YELLOW,
//                TextColor.ANSI.MAGENTA
//        );
//
//        Theme disabledTheme = SimpleTheme.makeTheme(
//                true,
//                TextColor.ANSI.BLACK,
//                TextColor.ANSI.CYAN,
//                TextColor.ANSI.WHITE,
//                TextColor.ANSI.BLACK,
//                TextColor.ANSI.BLUE,
//                TextColor.ANSI.YELLOW,
//                TextColor.ANSI.MAGENTA
//        );

        var loginWrapper = new Object(){
            String username = (AdminMenu.getExplicitLoadedUserId(app) != -1)
                    ? app.getUserController().getUsers().getUserWithId(AdminMenu.getExplicitLoadedUserId(app)).getUsername()
                    : "";
            boolean loggedIn = AdminMenu.getExplicitLoadedUserId(app) != -1;
            Button loginButton;
            Button signupButton;

            public void updateAccountStatus(boolean active) {
                if (active) {
                    this.loginButton.setTheme(Constants.DISABLED_THEME);
                    this.loginButton.setEnabled(false);
                    this.signupButton.setTheme(Constants.DISABLED_THEME);
                    this.signupButton.setEnabled(false);
                } else {
                    this.loginButton.setTheme(Constants.ENABLED_THEME);
                    this.loginButton.setEnabled(true);
                    this.signupButton.setTheme(Constants.ENABLED_THEME);
                    this.signupButton.setEnabled(true);
                }
            }
        };

        Logger.logger.info("Logged in: " + loginWrapper.loggedIn + " | Username: '" + loginWrapper.username + "'" + " | ID: " + AdminMenu.getExplicitLoadedUserId(app));

        Label sizeLabel = new Label("Size: {" + size.getColumns() + "x" + size.getRows() + "}" + "\nUsername: <None>\nUser Id: <None>");
        sizeLabel.setTheme(Constants.ENABLED_THEME);
        sizeLabel.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.CENTER,
                GridLayout.Alignment.CENTER,
                false,
                false,
                1,
                1
        ));
        contentPanel.addComponent(sizeLabel);

        Button loginButton = (Button)new Button(
                "Log In",
                () -> {
                    loginWrapper.username = new LoginMenu(this.textGUI, "Login", app).show();
                    Logger.logger.info("LOGIN OUTPUT: " + loginWrapper.username);
//                    loginWrapper.loggedIn = true;
//
//                    sizeLabel.setText(
//                            "Size: {" + size.getColumns() + "x" + size.getRows() + "}"
//                                    + "\nUsername: " + loginWrapper.username
//                                    + "\nUser Id: " + app.getUserID()
//                    );
//                    loginWrapper.updateAccountStatus(true);
//
//                    try {
//                        this.textGUI.updateScreen();
//                    } catch (IOException e) {
//                        // Who tf cares
//                    }

                    Logger.logger.info("LOGIN: " + app.getUserController().isUsernameAvailable(loginWrapper.username));
                    Logger.logger.info("LOGIN: "
                            + (loginWrapper.username.isEmpty() || app.getUserController().isUsernameAvailable(loginWrapper.username))
                    );

                    if (
                            loginWrapper.username.isEmpty()
                                || app.getUserController().isUsernameAvailable(loginWrapper.username)
                    ) this.result = MenuId.MAIN_MENU;
                    else this.result = MenuId.USER_MENU;

                    Logger.logger.info("RESULT: " + this.result);
                    this.close();
                }
        ).setTheme(Constants.ENABLED_THEME)
            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER));

        if (loginWrapper.loggedIn) loginButton.setEnabled(false);
        loginWrapper.loginButton = loginButton;
        contentPanel.addComponent(loginButton);

        Button signupButton = (Button)new Button(
                "Sign Up",
                () -> {
                    new SignupMenu(this.textGUI, "Signup", app).show();
                }
        ).setTheme(Constants.ENABLED_THEME)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER));

        if (loginWrapper.loggedIn) signupButton.setEnabled(false);
        loginWrapper.signupButton = signupButton;
        contentPanel.addComponent(signupButton);

        contentPanel.addComponent(
                new Button(
                        "Admin",
                        () -> {
                            this.result = MenuId.ADMIN_MENU;
                            this.close();
                        }
                )
                .setTheme(Constants.ENABLED_THEME)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER))
        );

        contentPanel.addComponent(
                new Button("Close", () -> {
                    this.result = MenuId.NONE;
                    this.close();
                }).setTheme(Constants.ENABLED_THEME)
                    .setLayoutData(GridLayout.createHorizontallyEndAlignedLayoutData(2))
        );

        // Add padding to the end
//        this.addComponent(
//                new EmptySpace(new TerminalSize(size.getColumns() - offsetX - sizeX, size.getRows() - offsetY - sizeY))
//                        .setTheme(theme)
//        );

        this.setComponent(contentPanel);
    }

    @Override
    public MenuId show() {
        this.textGUI.addWindow(this);
        this.waitUntilClosed();
        Logger.logger.info("LAST RESULT: " + this.result);
        return this.result;
    }

    @Override
    public boolean handleInput(KeyStroke key) {
        if (key.getKeyType() == KeyType.Escape) this.close();
        return super.handleInput(key);
    }
}
