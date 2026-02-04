package woody.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import woody.Woody;

/**
 * A GUI for Duke using FXML.
 */
public class WoodyGui extends Application {

    private Woody woody = new Woody();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(WoodyGui.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setWoody(woody);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
