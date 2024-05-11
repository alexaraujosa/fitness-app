package cli;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.Theme;

public class Constants {
    public static final Theme ENABLED_THEME = SimpleTheme.makeTheme(
            true,
            TextColor.ANSI.WHITE,
            TextColor.ANSI.CYAN,
            TextColor.ANSI.WHITE,
            TextColor.ANSI.BLACK,
            TextColor.ANSI.BLUE,
            TextColor.ANSI.YELLOW,
            TextColor.ANSI.BLACK
    );

    public static final Theme DISABLED_THEME = SimpleTheme.makeTheme(
            true,
            TextColor.ANSI.BLACK,
            TextColor.ANSI.CYAN,
            TextColor.ANSI.WHITE,
            TextColor.ANSI.BLACK,
            TextColor.ANSI.BLUE,
            TextColor.ANSI.YELLOW,
            TextColor.ANSI.BLACK
    );

    public static final Theme WARNING_TEXT = SimpleTheme.makeTheme(
            true,
            TextColor.ANSI.RED,
            ENABLED_THEME.getDefaultDefinition().getNormal().getBackground(),
            ENABLED_THEME.getDefaultDefinition().getPreLight().getBackground(),
            ENABLED_THEME.getDefaultDefinition().getPreLight().getForeground(),
            ENABLED_THEME.getDefaultDefinition().getActive().getBackground(),
            ENABLED_THEME.getDefaultDefinition().getActive().getForeground(),
            TextColor.ANSI.BLACK
    );
}
