package woody.ui;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class WoodyGuiLauncher {
    public static void main(String[] args) {
        Application.launch(WoodyGui.class, args);
    }
}
