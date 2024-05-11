package cli;


import cli.menus.MainMenu;
import cli.menus.MenuPage;
import cli.menus.UserMenu;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import josefinFA.JosefinFitnessApp;
import users.User;
import utils.Logger;
import cli.menus.MenuId;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CLI {
//    public static void main(String[] args) throws IOException {
//        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
//        Terminal terminal = null;
//
//        try {
//            terminal = defaultTerminalFactory.createTerminal();
//            terminal.enterPrivateMode();
//            terminal.clearScreen();
//            terminal.setCursorVisible(false);
//
//            final TextGraphics textGraphics = terminal.newTextGraphics();
//            textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
//            textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
//
//            textGraphics.putString(2, 1, "Lanterna Tutorial 2 - Press ESC to exit", SGR.BOLD);
//            textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
//            textGraphics.setBackgroundColor(TextColor.ANSI.DEFAULT);
//            textGraphics.putString(5, 3, "Terminal Size: ", SGR.BOLD);
//            textGraphics.putString(5 + "Terminal Size: ".length(), 3, terminal.getTerminalSize().toString());
//
//            terminal.flush();
//
//            terminal.addResizeListener((terminal1, newSize) -> {
//                textGraphics.drawLine(5, 3, newSize.getColumns() - 1, 3, ' ');
//                textGraphics.putString(5, 3, "Terminal Size: ", SGR.BOLD);
//                textGraphics.putString(5 + "Terminal Size: ".length(), 3, newSize.toString());
//
//                try {
//                    terminal1.flush();
//                } catch(IOException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//
//            textGraphics.putString(5, 4, "Last Keystroke: ", SGR.BOLD);
//            textGraphics.putString(5 + "Last Keystroke: ".length(), 4, "<Pending>");
//            terminal.flush();
//
//            KeyStroke keyStroke = terminal.readInput();
//            while(keyStroke.getKeyType() != KeyType.Escape) {
//                textGraphics.drawLine(5, 4, terminal.getTerminalSize().getColumns() - 1, 4, ' ');
//                textGraphics.putString(5, 4, "Last Keystroke: ", SGR.BOLD);
//                textGraphics.putString(5 + "Last Keystroke: ".length(), 4, keyStroke.toString());
//                terminal.flush();
//                keyStroke = terminal.readInput();
//            }
//
//        } catch(IOException e) {
//            e.printStackTrace();
//        } finally {
//            if(terminal != null) {
//                try {
//                    terminal.close();
//                } catch(IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


//    public static void main(String[] args) {
//        /*
//        In this forth tutorial we will finally look at creating a multi-window text GUI, all based on text. Just like
//        the Screen-layer in the previous tutorial was based on the lower-level Terminal layer, the GUI classes we will
//        use here are all build upon the Screen interface. Because of this, if you use these classes, you should never
//        interact with the underlying Screen that backs the GUI directly, as it might modify the screen in a way the
//        GUI isn't aware of.
//
//        The GUI system is designed around a background surface that is usually static, but can have components, and
//        multiple windows. The recommended approach it to make all windows modal and not let the user switch between
//        windows, but the latter can also be done. Components are added to windows by using a layout manager that
//        determines the position of each component.
//         */
//        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
//        Screen screen = null;
//
//        try {
//            /*
//            The DefaultTerminalFactory class doesn't provide any helper method for creating a Text GUI, you'll need to
//             get a Screen like we did in the previous tutorial and start it so it puts the terminal in private mode.
//             */
//            screen = terminalFactory.createScreen();
//            screen.startScreen();
//
//            /*
//            There are a couple of different constructors to MultiWindowTextGUI, we are going to go with the defaults for
//            most of these values. The one thing to consider is threading; with the default options, lanterna will use
//            the calling thread for all UI operations which mean that you are basically letting the calling thread block
//            until the GUI is shut down. There is a separate TextGUIThread implementaiton you can use if you'd like
//            Lanterna to create a dedicated UI thread and not lock the caller. Just like with AWT and Swing, you should
//            be scheduling any kind of UI operation to always run on the UI thread but lanterna tries to be best-effort
//            if you attempt to mutate the GUI from another thread. Another default setting that will be applied is that
//            the background of the GUI will be solid blue.
//             */
//            final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
//
//            /*
//            Creating a new window is relatively uncomplicated, you can optionally supply a title for the window
//             */
//            final Window window = new BasicWindow("My Root Window");
//
//            /*
//            The window has no content initially, you need to call setComponent to populate it with something. In this
//            case, and quite often in fact, you'll want to use more than one component so we'll create a composite
//            'Panel' component that can hold multiple sub-components. This is where we decide what the layout manager
//            should be.
//             */
//            Panel contentPanel = new Panel(new GridLayout(2));
//
//            /*
//             * Lanterna contains a number of built-in layout managers, the simplest one being LinearLayout that simply
//             * arranges components in either a horizontal or a vertical line. In this tutorial, we'll use the GridLayout
//             * which is based on the layout manager with the same name in SWT. In the constructor above we have
//             * specified that we want to have a grid with two columns, below we customize the layout further by adding
//             * some spacing between the columns.
//             */
//            GridLayout gridLayout = (GridLayout)contentPanel.getLayoutManager();
//            gridLayout.setHorizontalSpacing(3);
//
//            /*
//            One of the most basic components is the Label, which simply displays a static text. In the example below,
//            we use the layout data field attached to each component to give the layout manager extra hints about how it
//            should be placed. Obviously the layout data has to be created from the same layout manager as the container
//            is using, otherwise it will be ignored.
//             */
//            Label title = new Label("This is a label that spans two columns");
//            title.setLayoutData(GridLayout.createLayoutData(
//                    GridLayout.Alignment.BEGINNING, // Horizontal alignment in the grid cell if the cell is larger than the component's preferred size
//                    GridLayout.Alignment.BEGINNING, // Vertical alignment in the grid cell if the cell is larger than the component's preferred size
//                    true,       // Give the component extra horizontal space if available
//                    false,        // Give the component extra vertical space if available
//                    2,                  // Horizontal span
//                    1));                  // Vertical span
//            contentPanel.addComponent(title);
//
//            /*
//            Since the grid has two columns, we can do something like this to add components when we don't need to
//            customize them any further.
//             */
//            contentPanel.addComponent(new Label("Text Box (aligned)"));
//            contentPanel.addComponent(
//                    new TextBox()
//                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER)));
//
//            /*
//            Here is an example of customizing the regular text box component so it masks the content and can work for
//            password input.
//             */
//            contentPanel.addComponent(new Label("Password Box (right aligned)"));
//            contentPanel.addComponent(
//                    new TextBox()
//                            .setMask('*')
//                            .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER)));
//
//            /*
//            While we are not going to demonstrate all components here, here is an example of combo-boxes, one that is
//            read-only and one that is editable.
//             */
//            contentPanel.addComponent(new Label("Read-only Combo Box (forced size)"));
//            List<String> timezonesAsStrings = new ArrayList<>(Arrays.asList(TimeZone.getAvailableIDs()));
//            ComboBox<String> readOnlyComboBox = new ComboBox<>(timezonesAsStrings);
//            readOnlyComboBox.setReadOnly(true);
//            readOnlyComboBox.setPreferredSize(new TerminalSize(20, 1));
//            contentPanel.addComponent(readOnlyComboBox);
//
//            contentPanel.addComponent(new Label("Editable Combo Box (filled)"));
//            contentPanel.addComponent(
//                    new ComboBox<>("Item #1", "Item #2", "Item #3", "Item #4")
//                            .setReadOnly(false)
//                            .setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1)));
//
//            /*
//            Some user interactions, like buttons, work by registering callback methods. In this example here, we're
//            using one of the pre-defined dialogs when the button is triggered.
//             */
//            contentPanel.addComponent(new Label("Button (centered)"));
//            contentPanel.addComponent(new Button("Button", () -> MessageDialog.showMessageDialog(textGUI, "MessageBox", "This is a message box", MessageDialogButton.OK)).setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER)));
//
//            /*
//            Close off with an empty row and a separator, then a button to close the window
//             */
//            contentPanel.addComponent(
//                    new EmptySpace()
//                            .setLayoutData(
//                                    GridLayout.createHorizontallyFilledLayoutData(2)));
//            contentPanel.addComponent(
//                    new Separator(Direction.HORIZONTAL)
//                            .setLayoutData(
//                                    GridLayout.createHorizontallyFilledLayoutData(2)));
//            contentPanel.addComponent(
//                    new Button("Close", window::close).setLayoutData(
//                            GridLayout.createHorizontallyEndAlignedLayoutData(2)));
//
//            /*
//            We now have the content panel fully populated with components. A common mistake is to forget to attach it to
//            the window, so let's make sure to do that.
//             */
//            window.setComponent(contentPanel);
//
//            /*
//            Now the window is created and fully populated. As discussed above regarding the threading model, we have the
//            option to fire off the GUI here and then later on decide when we want to stop it. In order for this to work,
//            you need a dedicated UI thread to run all the GUI operations, usually done by passing in a
//            SeparateTextGUIThread object when you create the TextGUI. In this tutorial, we are using the conceptually
//            simpler SameTextGUIThread, which essentially hijacks the caller thread and uses it as the GUI thread until
//            some stop condition is met. The absolutely simplest way to do this is to simply ask lanterna to display the
//            window and wait for it to be closed. This will initiate the event loop and make the GUI functional. In the
//            "Close" button above, we tied a call to the close() method on the Window object when the button is
//            triggered, this will then break the even loop and our call finally returns.
//             */
//            textGUI.addWindowAndWait(window);
//
//            /*
//            When our call has returned, the window is closed and no longer visible. The screen still contains the last
//            state the TextGUI left it in, so we can easily add and display another window without any flickering. In
//            this case, we want to shut down the whole thing and return to the ordinary prompt. We just need to stop the
//            underlying Screen for this, the TextGUI system does not require any additional disassembly.
//             */
//
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        finally {
//            if(screen != null) {
//                try {
//                    /*
//                    The close() call here will restore the terminal by exiting from private mode which was done in
//                    the call to startScreen(), and also restore things like echo mode and intr
//                     */
//                    screen.close();
//                }
//                catch(IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }





    public static void main(String[] args) {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Screen screen = null;

        try {
            Logger.setup("out");

            JosefinFitnessApp app = new JosefinFitnessApp();
            app.loadState("/home/rafaelsf/Desktop/Cadeiras/2ano/2sem/POO/Project/project/TesteFile");
            app.logout(); // The app might have a user loaded, for some fucking reason. Prevent UI lock.

            Logger.logger.info("User count: " + app.getUserController().getUsers().getUsersList().size());
            Logger.logger.info(
                    "Users: "
                            + app.getUserController().getUsers().getUsersList().stream().map(u -> "|" + u.getUsername() + "|")
                            .toList()
            );

            screen = terminalFactory.createScreen();
            screen.startScreen();

            // Base for the entire CLI
            final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen, TextColor.ANSI.BLACK);

//            final Window window = new BasicWindow("Josefin Fitness App");
//            window.setHints(new HashSet(Collections.singletonList(Window.Hint.CENTERED)));

//            ((GUIBackdrop)(textGUI.getBackgroundPane().getComponent())).setColor(TextColor.ANSI.RED);

//            Panel contentPanel = new MainMenu(window, textGUI, app);
//
//            window.setComponent(contentPanel); // .withBorder(Borders.doubleLine("Test"))
//            textGUI.addWindowAndWait(window);

            MenuId menu = MenuId.MAIN_MENU;
            while (true) {
                Window window = null;
                Logger.logger.info("New Menu: " + menu);

                switch (menu) {
                    case MenuId.MAIN_MENU: {
                        window = new MainMenu(textGUI, "Josefin Fitness App", app);
                        break;
                    }
                    case MenuId.USER_MENU: {
                        String username = app.getUserController().getUsers().getUserWithId(app.getUserID()).getUsername();
                        window = new UserMenu(
                                textGUI,
                                username + "'s Panel",
                                app
                        );
                        break;
                    }
                }

                menu = (MenuId)((MenuPage)window).show();
                Logger.logger.info("Return Menu: " + menu);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(screen != null) {
                try {
                    screen.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
