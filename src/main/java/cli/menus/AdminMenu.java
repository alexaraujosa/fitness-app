package cli.menus;

import cli.Constants;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import josefinFA.JosefinFitnessApp;

import java.util.Collections;
import java.util.regex.Pattern;

public class AdminMenu extends AbstractWindow implements MenuPage {
    private WindowBasedTextGUI textGUI;
    private JosefinFitnessApp app;

    private MenuId result;
    private boolean userKeyStroke = false;

    private Panel contentPanel;
    private Label errorLabel;

    private static boolean adminMode = false;
    private static int loadedUserId = -1;

    public static final String ADMIN_MARK = "ADMIN";

    public AdminMenu(
            WindowBasedTextGUI textGUI,
            String title,
            JosefinFitnessApp app
    ) throws NoSuchMethodException {
        super(title.isEmpty() ? "Admin Menu" : title);
        this.setHints(java.util.Set.copyOf(Collections.singletonList(Hint.CENTERED)));

        this.textGUI = textGUI;
        this.app = app;

        this.result = MenuId.MAIN_MENU;

        TerminalSize boxSize = new TerminalSize(20, 1);

        contentPanel = new Panel();
        contentPanel.setLayoutManager((new GridLayout(2)).setLeftMarginSize(1).setRightMarginSize(1));
        contentPanel.setTheme(Constants.ENABLED_THEME);

        // Add reference here to enable reactivity
        var usernameBoxRef = new Object() {
            TextBox usernameBox = null;
        };
        TextBox userIdBox = new TextBox()
                .setPreferredSize(boxSize)
                .setValidationPattern(Pattern.compile("^\\d*$"))
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("User Id:"));
        contentPanel.addComponent(userIdBox);
        userIdBox.setTextChangeListener((text, textChange) -> {
            // Recursion guard
            if (!userKeyStroke) return;
            userKeyStroke = false;

            try {
                int id = Integer.parseInt(text);
                if (app.getUserController().userWithIdExits(id))
                    usernameBoxRef.usernameBox.setText(app.getUserController().getUsers().getUserWithId(id).getUsername());
                else usernameBoxRef.usernameBox.setText("");
            } catch (NumberFormatException e) {
                usernameBoxRef.usernameBox.setText("");
            }
        });

        TextBox usernameBox = new TextBox()
                .setPreferredSize(boxSize)
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(new Label("Username:"));
        contentPanel.addComponent(usernameBox);
        usernameBox.setTextChangeListener((text, textChange) -> {
            // Recursion guard
            if (!userKeyStroke) return;
            userKeyStroke = false;

            if (app.getUserController().userWithUsernameExists(text))
                userIdBox.setText("" + app.getUserController().getUsers().getUserWithUsername(text).getId());
            else userIdBox.setText("");
        });
        usernameBoxRef.usernameBox = usernameBox;

        Button backButton = new Button(
                "Exit",
                () -> {
                    adminMode = false;
                    this.close();
                }
        ).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(backButton);

        Button enterButton = new Button(
                "Enter as User",
                () -> {
                    try {
                        if (userIdBox.getText().isEmpty()) {
                            throw new Exception("User ID cannot be empty");
                        }

                        int userId = Integer.parseInt(userIdBox.getText());
                        if (userId < 0) {
                            throw new Exception("User ID must be a positive number");
                        }

                        loadedUserId = Integer.parseInt(userIdBox.getText());
                        adminMode = true;
                        this.close();
                    } catch (Exception e) {
                        errorLabel.setText(e.getMessage());
                        errorLabel.setVisible(true);
                        contentPanel.addComponent(errorLabel);
                    }

                    this.result = MenuId.USER_MENU;
                }
        ).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER));
        contentPanel.addComponent(enterButton);

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

    @Override
    public Object show() {
        this.textGUI.addWindow(this);
        this.waitUntilClosed();
        return this.result;
    }

    @Override
    public boolean handleInput(KeyStroke key) {
        if (errorLabel.isVisible()) {
            errorLabel.setVisible(false);
            contentPanel.removeComponent(errorLabel);
        }

        this.userKeyStroke = true;
        boolean ret = super.handleInput(key);
        this.userKeyStroke = false;

        return ret;
    }

    public static boolean isAdminMode() {
        return adminMode;
    }

    public static MenuId getBaseMenuId() {
        if (isAdminMode()) {
            return MenuId.ADMIN_MENU;
        } else {
            return MenuId.MAIN_MENU;
        }
    }

    public static int getLoadedUserId(JosefinFitnessApp app) {
        if (isAdminMode()) {
            return loadedUserId;
        } else {
            return -1;
        }
    }

    public static int getExplicitLoadedUserId(JosefinFitnessApp app) {
        if (isAdminMode()) {
            return loadedUserId;
        } else {
            return app.getUserID();
        }
    }
}
