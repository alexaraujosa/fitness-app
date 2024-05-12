package cli.components.record;

import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import users.User;

public class UserRecord implements Record {
    private final User user;
    private final int stat;

    public UserRecord(User user, int stat) {
        this.user = user;
        this.stat = stat;
    }

    @Override
    public void processRecord(WindowBasedTextGUI textGUI, Window window, Panel contentPanel) {
        contentPanel.addComponent(new Label(user.getUsername() + " - " + stat));
    }
}
