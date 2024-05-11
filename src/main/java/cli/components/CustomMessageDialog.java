package cli.components;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

import java.util.Collections;
import java.util.function.Consumer;

public class CustomMessageDialog extends DialogWindow {
    private int result = 0;

    protected CustomMessageDialog(String title, String text, CustomMessageDialogButton... buttons) {
        super(title);

        Button[] btns = new Button[buttons.length];

        if (buttons == null || buttons.length == 0) {
            btns = new Button[]{createButton("OK", (cmd) -> {
                this.close();
            }, 1)};
        }

        Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager((new GridLayout(buttons.length)).setHorizontalSpacing(1));

        for (CustomMessageDialogButton button : buttons) {
            buttonPanel.addComponent(createButton(button.label, button.action, button.id));
        }

        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager((new GridLayout(1)).setLeftMarginSize(1).setRightMarginSize(1));
        mainPanel.addComponent(new Label(text));
        mainPanel.addComponent(new EmptySpace(TerminalSize.ONE));
        ((Panel)buttonPanel.setLayoutData(
                GridLayout.createLayoutData(
                        GridLayout.Alignment.END,
                        GridLayout.Alignment.CENTER,
                        false,
                        false
                )
        )).addTo(mainPanel);
        this.setComponent(mainPanel);
    }

    public Integer showDialog(WindowBasedTextGUI textGUI) {
        super.showDialog(textGUI);
        return this.result;
    }

    public static Integer showMessageDialog(WindowBasedTextGUI textGUI, String title, String text, CustomMessageDialogButton... buttons) {
        CustomMessageDialog cmdb = new CustomMessageDialog(title, text, buttons);
        cmdb.setHints(Collections.singleton(Hint.CENTERED));

        return cmdb.showDialog(textGUI);
    }

    private Button createButton(String label, Consumer<CustomMessageDialog> action, int id) {
        Button btn = new Button(label, () -> {
            action.accept(this);
            this.result = id;
        });
        return btn;
    }

    public static class CustomMessageDialogButton {
        private final String label;
        private final Consumer<CustomMessageDialog> action;
        private final int id;

        public CustomMessageDialogButton(String label, Consumer<CustomMessageDialog> action, int id) {
            this.label = label;
            this.action = action;
            this.id = id;
        }
    }
}
