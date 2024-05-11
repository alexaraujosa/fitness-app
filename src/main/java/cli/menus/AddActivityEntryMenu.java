package cli.menus;

import cli.Constants;
import cli.util.Transformer;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import josefinFA.JosefinFitnessApp;
import utils.Logger;

import java.util.Collections;
import java.util.regex.Pattern;

public class AddActivityEntryMenu extends AbstractWindow implements MenuPage {
    private WindowBasedTextGUI textGUI;
    private JosefinFitnessApp app;

    private Panel contentPanel;
    private Label errorLabel;

    private Component[] inputs;
    private Object[] result; // Types depend on the caller.

    private static final String BOOLEAN_TRUE = "Yes";
    private static final String BOOLEAN_FALSE = "No";

    public AddActivityEntryMenu(WindowBasedTextGUI textGUI, String title, JosefinFitnessApp app, Argument[] args) {
        super(title);
        this.setHints(java.util.Set.copyOf(Collections.singletonList(Hint.CENTERED)));

        this.textGUI = textGUI;
        this.app = app;

        contentPanel = new Panel();
        contentPanel.setLayoutManager((new GridLayout(2)).setLeftMarginSize(1).setRightMarginSize(1));
        contentPanel.setTheme(Constants.ENABLED_THEME);

        this.inputs = new Component[args.length];
        this.result = null;

        for (int i = 0; i < args.length; i++) {
            Argument arg = args[i];

            Logger.logger.info("" + arg.getType() + ": " + arg.getName());

            switch (arg.getType()) {
                case ArgumentType.ARGUMENT_STRING: {
                    TextBox box = new TextBox()
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
                    contentPanel.addComponent(new Label(arg.getName() + ":"));
                    contentPanel.addComponent(box);

                    inputs[i] = box;
                    break;
                }
                case ARGUMENT_DOUBLE: {
                    TextBox box = new TextBox()
                            .setValidationPattern(Pattern.compile("^[1-9]*\\.\\d*$"))
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
                    contentPanel.addComponent(new Label(arg.getName() + ":"));
                    contentPanel.addComponent(box);

                    inputs[i] = box;
                    break;
                }
                case ARGUMENT_INT: {
                    TextBox box = new TextBox()
                            .setValidationPattern(Pattern.compile("^\\d*$"))
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
                    contentPanel.addComponent(new Label(arg.getName() + ":"));
                    contentPanel.addComponent(box);

                    inputs[i] = box;
                    break;
                }
                case ARGUMENT_LOCALDATE: {
                    TextBox box = new TextBox()
                            .setValidationPattern(Pattern.compile("^[\\d/]{0,10}$"))
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
                    contentPanel.addComponent(new Label(arg.getName() + ":"));
                    contentPanel.addComponent(box);

                    inputs[i] = box;
                    break;
                }
                case ARGUMENT_LOCALDATETIME: {
                    TextBox box = new TextBox()
                            .setValidationPattern(Pattern.compile("^[\\d/ :]{0,21}$"))
                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER));
                    contentPanel.addComponent(new Label(arg.getName() + ":"));
                    contentPanel.addComponent(box);

                    inputs[i] = box;
                    break;
                }
                case ARGUMENT_BOOLEAN: {
                    ComboBox cbox = new ComboBox<String>();
                    cbox.addItem(BOOLEAN_TRUE);
                    cbox.addItem(BOOLEAN_FALSE);
                    contentPanel.addComponent(new Label(arg.getName() + ":"));
                    contentPanel.addComponent(cbox);

                    inputs[i] = cbox;
                    break;
                }
            }
        }

        Button confirmButton = (Button)new Button(
                "Confirm",
                () -> {
                    // Reset all inputs for previous errors.
                    for (Component input : inputs) {
                        input.setTheme(Constants.ENABLED_THEME);
                    }

                    Object[] parsedArgs = new Object[inputs.length];

                    boolean valid = true;
                    for (int i = 0; i < args.length; i++) {
                        String msgOverride = "";
                        try {
                            switch (args[i].getType()) {
                                case ArgumentType.ARGUMENT_STRING: {
                                    if (((TextBox)inputs[i]).getText().isEmpty()) {
                                        msgOverride = "Argument is required.";
                                        throw new Exception();
                                    }

                                    parsedArgs[i] = ((TextBox)inputs[i]).getText();
                                    break;
                                }
                                case ArgumentType.ARGUMENT_DOUBLE: {
                                    if (((TextBox)inputs[i]).getText().isEmpty()) {
                                        msgOverride = "Argument is required.";
                                        throw new Exception();
                                    }

                                    parsedArgs[i] = Transformer.transformToDouble(((TextBox)inputs[i]).getText());
                                    break;
                                }
                                case ArgumentType.ARGUMENT_INT: {
                                    if (((TextBox)inputs[i]).getText().isEmpty()) {
                                        msgOverride = "Argument is required.";
                                        throw new Exception();
                                    }

                                    parsedArgs[i] = Transformer.transformToInt(((TextBox)inputs[i]).getText());
                                    break;
                                }
                                case ArgumentType.ARGUMENT_LOCALDATE: {
                                    if (((TextBox)inputs[i]).getText().isEmpty()) {
                                        msgOverride = "Argument is required.";
                                        throw new Exception();
                                    }

                                    msgOverride = "Invalid date.";
                                    parsedArgs[i] = Transformer.transformToLocalDate(((TextBox)inputs[i]).getText());
                                    break;
                                }
                                case ArgumentType.ARGUMENT_LOCALDATETIME: {
                                    if (((TextBox)inputs[i]).getText().isEmpty()) {
                                        msgOverride = "Argument is required.";
                                        throw new Exception();
                                    }

                                    msgOverride = "Invalid date.";
                                    parsedArgs[i] = Transformer.transformToLocalDateTime(((TextBox)inputs[i]).getText());
                                    break;
                                }
                                case ArgumentType.ARGUMENT_BOOLEAN: {
                                    parsedArgs[i] = ((ComboBox<String>)inputs[i]).getSelectedItem().equals(BOOLEAN_TRUE);
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            String msg = !msgOverride.isEmpty() ? msgOverride : e.getMessage();
                            Logger.logger.warning("Unable to add activity: " + e.getMessage() + "\n" + e.getStackTrace());

                            inputs[i].setTheme(Constants.WARNING_TEXTBOX);
                            errorLabel.setText(msg);
                            errorLabel.setVisible(true);
                            contentPanel.addComponent(errorLabel);

                            valid = false;
                            break;
                        }
                    }

                    if (valid) {
                        this.result = parsedArgs;
                        this.close();
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

        return super.handleInput(key);
    }

    public static enum ArgumentType {
        ARGUMENT_STRING,
        ARGUMENT_DOUBLE,
        ARGUMENT_INT,
        ARGUMENT_LOCALDATE,
        ARGUMENT_LOCALDATETIME,
        ARGUMENT_BOOLEAN
    }

    public static ArgumentType getArgumentTypeFromClass(Class<?> clazz) {
        String typeName = clazz.getTypeName();

        if ("java.lang.String".equals(typeName)) return ArgumentType.ARGUMENT_STRING;
        if ("double".equals(typeName)) return ArgumentType.ARGUMENT_DOUBLE;
        if ("int".equals(typeName)) return ArgumentType.ARGUMENT_INT;
        if ("boolean".equals(typeName)) return ArgumentType.ARGUMENT_BOOLEAN;
        if ("java.time.LocalDate".equals(typeName)) return ArgumentType.ARGUMENT_LOCALDATE;
        if ("java.time.LocalDateTime".equals(typeName)) return ArgumentType.ARGUMENT_LOCALDATETIME;

        return ArgumentType.ARGUMENT_STRING;
    }

    public static class Argument {
        private final String name;
        private final ArgumentType type;

        public Argument(String name, ArgumentType type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public ArgumentType getType() {
            return type;
        }
    }
}
