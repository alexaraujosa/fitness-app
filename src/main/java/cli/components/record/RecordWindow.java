package cli.components.record;

import cli.Constants;
import cli.menus.MenuId;
import cli.menus.MenuPage;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;

import java.util.Collections;

public class RecordWindow extends AbstractWindow implements MenuPage {
    private WindowBasedTextGUI textGUI;
    private MenuId result;

    public RecordWindow(WindowBasedTextGUI textGUI, String title, Record record) {
        super(title.isEmpty() ? "REcord" : title);
        this.setHints(java.util.Set.copyOf(Collections.singletonList(Hint.CENTERED)));

        this.textGUI = textGUI;

        this.result = MenuId.MAIN_MENU;

        Panel contentPanel = new Panel();
        contentPanel.setLayoutManager((new GridLayout(2)).setLeftMarginSize(1).setRightMarginSize(1));
        contentPanel.setTheme(Constants.ENABLED_THEME);

        record.processRecord(textGUI, this, contentPanel);

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
