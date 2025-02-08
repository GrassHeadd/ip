package Ozymandias.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.util.concurrent.TimeUnit;

/**
 * Controller for the main GUI.
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

    private Ozymandias ozymandias;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image ozymandiasImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        // Show greeting message when window opens
        dialogContainer.getChildren().add(
            DialogBox.getOzymandiasDialog(Ui.greetHello(), ozymandiasImage)
        );
    }

    /** Injects the Ozymandias instance */
    public void setOzymandias(Ozymandias d) {
        ozymandias = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Ozymandias's reply and then appends them to
     * the dialog container. Clears the user input after processing. Exit after calling bye
     */
    @FXML
    private void handleUserInput() throws InterruptedException {
        String input = userInput.getText();
        String response = ozymandias.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getOzymandiasDialog(response, ozymandiasImage)
        );
        userInput.clear();

        if (input.trim().equalsIgnoreCase("bye")) {
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5); // Wait for 5 seconds
                    javafx.application.Platform.runLater(() -> javafx.application.Platform.exit());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}
