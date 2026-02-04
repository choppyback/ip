package woody.ui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import woody.Woody;

/**
 * Controls the main GUI window of the Woody application.
 * Handles user input and displays dialog interactions.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Woody woody;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /**
     * Initializes the main window after the FXML file has been loaded.
     * Binds the scroll pane to automatically scroll as dialog content grows.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Woody instance used to process user commands.
     *
     * @param w The Woody application instance.
     */
    public void setWoody(Woody w) {
        woody = w;
    }

    /**
     * Handles user input from the text field.
     * Sends the input to the Woody application, displays the response,
     * and clears the input field. Closes the window after a short delay
     * if the user enters the bye command.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = woody.run(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getWoodyDialog(response, dukeImage)
        );
        userInput.clear();
        if (input.equals("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(3.0));
            delay.setOnFinished(event -> {
                Stage stage = (Stage) userInput.getScene().getWindow();
                stage.close();
            });
            delay.play();
        }
    }
}
