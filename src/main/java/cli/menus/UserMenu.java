package cli.menus;

import cli.Constants;
import cli.components.CustomMessageDialog;
import com.googlecode.lanterna.gui2.*;
import exceptions.ErrorRemovingUserException;
import josefinFA.JosefinFitnessApp;
import utils.Logger;

import java.util.Collections;

public class UserMenu extends AbstractWindow implements MenuPage {
    private WindowBasedTextGUI textGUI;
    private JosefinFitnessApp app;

    private MenuId result;

    public UserMenu(WindowBasedTextGUI textGUI, String title, JosefinFitnessApp app) {
        super(title);
        this.setHints(java.util.Set.copyOf(Collections.singletonList(Hint.CENTERED)));

        String trueTitle = AdminMenu.isAdminMode() ? "[" + AdminMenu.ADMIN_MARK + "] " + this.getTitle() : this.getTitle();
        this.setTitle(trueTitle);

        this.textGUI = textGUI;
        this.app = app;

//        this.result = MenuId.MAIN_MENU;
        this.result = AdminMenu.getBaseMenuId();

        Panel contentPanel = new Panel();
        contentPanel.setLayoutManager((new GridLayout(1)).setLeftMarginSize(1).setRightMarginSize(1));
        contentPanel.setTheme(Constants.ENABLED_THEME);

        Button updateInfoButton = (Button)new Button(
                "Update Information",
                () -> {
                    new UserInfoUpdateMenu(textGUI, "", app).show();
                }
        );
        contentPanel.addComponent(updateInfoButton);

        Button listRecords = (Button)new Button(
                "List Records",
                () -> {

                }
        );
        contentPanel.addComponent(listRecords);

        Button addTrainingPlanButton = (Button)new Button(
                "Add Training Plan",
                () -> {
                    this.result = MenuId.TRAINING_PLAN_MENU;
                    this.close();
                }
        );
        contentPanel.addComponent(addTrainingPlanButton);

        Button addActivityButton = (Button)new Button(
                "Add Activity",
                () -> {
                    try {
                        new AddActivityMenu(textGUI,"", app, false).show();
                    } catch (NoSuchMethodException e) {
                        Logger.logger.warning("Unable to open AddActivityMenu: " + e.getMessage() + "\n" + e.getStackTrace());
                    }
                }
        );
        contentPanel.addComponent(addActivityButton);

        Button removeAccountButton = (Button)new Button(
                "Remove Account",
                () -> CustomMessageDialog.showMessageDialog(
                        this.textGUI,
                        "Remove Account",
                        "Are you sure you want to delete your account?" +
                                "\n This action cannot be undone!",
                        new CustomMessageDialog.CustomMessageDialogButton("No", (cmd) -> {
                            cmd.close();
                        }, 1),
                        new CustomMessageDialog.CustomMessageDialogButton("Yes", (cmd) -> {
                            cmd.close();
                            try {
                                app.deleteAccount();
                                this.close();
                            } catch (ErrorRemovingUserException e) {
                                // We are guaranteed to have the user exist at this point. However, log it anyway.
                                Logger.logger.warning(e.getMessage());
                            }
                        }, 2)
                )
        ).setTheme(Constants.WARNING_TEXT);
        contentPanel.addComponent(removeAccountButton);

        Button logoutButton = (Button)new Button(
                "Logout",
                () -> {
                    app.logout();
                    this.close();
                }
        );
        contentPanel.addComponent(logoutButton);

        this.setComponent(contentPanel);
    }

    @Override
    public Object show() {
        this.textGUI.addWindow(this);
        this.waitUntilClosed();
        return this.result;
    }
}
