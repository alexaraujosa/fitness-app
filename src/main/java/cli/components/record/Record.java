package cli.components.record;

import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;

public interface Record {
    public void processRecord(WindowBasedTextGUI textGUI, Window window, Panel contentPanel);
}
