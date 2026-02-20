package legoat.ui;

import java.io.IOException;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import legoat.exceptions.DoubleCompletionException;
import legoat.exceptions.DoubleIncompleteException;
import legoat.exceptions.EmptyListException;
import legoat.exceptions.EventTimeException;
import legoat.exceptions.WrongFormatDeadlineException;
import legoat.exceptions.WrongFormatDeleteException;
import legoat.exceptions.WrongFormatEventException;
import legoat.exceptions.WrongFormatFindException;
import legoat.exceptions.WrongFormatTodoException;
import legoat.exceptions.WrongFormatUnknownException;
import legoat.exceptions.WrongFormatUpdateException;
import legoat.handlers.LeGoatOutputHandler;

/**
* <p>MainWindow class handles the UI and formatting of the GUI with FXML.
* @since v0.2
*/
public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    @SuppressWarnings("unused")
    private Button sendButton;

    private LeGoatOutputHandler leGoat;
    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/Bronny.png"));
    private final Image leGoatImage = new Image(this.getClass().getResourceAsStream("/images/LeBron.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setLeGoat(LeGoatOutputHandler leGoat) {
        this.leGoat = leGoat;
        String welcomeMessage = "Hi, i'm,\n" + StringFormat.LOGO_STRING;
        dialogContainer.getChildren().add(DialogBox.getLeGoatLogoDialog(welcomeMessage, leGoatImage));
    }

    @FXML
    @SuppressWarnings("unused")
    private void handleUserInput() throws IOException {
        String rawInput = userInput.getText();
        String[] input = rawInput.split(" ");
        String response;
        try {
            response = leGoat.handleCommand(input);
        } catch (DoubleCompletionException | DoubleIncompleteException
                | EmptyListException | EventTimeException | WrongFormatDeadlineException
                | WrongFormatDeleteException | WrongFormatEventException
                | WrongFormatFindException | WrongFormatTodoException
                | WrongFormatUnknownException | WrongFormatUpdateException e) {
            response = e.getMessage();
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(rawInput, userImage),
                DialogBox.getLeGoatDialog(response, leGoatImage)
        );
        userInput.clear();
        if (rawInput.trim().equals("bye")) {
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> System.exit(0));
            pause.play();
        }
    }
}
