package legoat;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import legoat.commands.Parser;
import legoat.ui.MainWindow;

/**
* A GUI for LeGoat.
* @author Russell Lin
*/
public class Main extends Application {
    private final Parser leGoat;

    public Main() throws IOException {
        this.leGoat = new Parser();
    }

    @Override
    public void start(Stage stage) throws RuntimeException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("LeGoat");
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setLeGoat(leGoat);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML file", e);
        }
    }
}
