package org.monopoly.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * A class that we can use to test features of JavaFX in a isolated environment.
 * This can be removed once we have our final product.
 * @author walshj05
 */
public class TestingGUI extends Application {

    /**
     * Starts the JavaFX application.
     * @param stage the primary stage for this application
     * @author walshj05
     */
    @Override
    public void start(Stage stage) throws IOException {
        //Auction
        String fxmlPath2 = "/org/monopoly/View/GameScene/Auction/Auction.fxml";
        Parent root2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath2)));
        stage.setTitle("Auction");
        stage.setScene(new Scene(root2, 740, 400));
        stage.show();
    }

    /**
     * Main method to launch the application.
     * @param args command line arguments
     * @author walshj05
     */
    public static void main(String[] args) {
        launch();
    }
}
