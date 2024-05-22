package com.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The ProfileApplication class is the main entry point for the JavaFX application.
 * It extends the Application class and provides the necessary setup for the application window.
 */
public class ProfileApplication extends Application {
    /**
     * The start method is called when the application is launched.
     * It sets up the primary stage, loads the FXML file, creates a scene, and shows the stage.
     *
     * @param stage the primary stage for this application
     * @throws IOException if an I/O error occurs while loading the FXML file
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ProfileApplication.class.getResource("profile-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        stage.setTitle("Profile Settings");

        stage.setMinWidth(800);
        stage.setMinHeight(500);

        stage.setScene(scene);

        stage.show();
    }
    /**
     * The main method is the entry point of the JavaFX application.
     * It launches the application by calling the launch method of the Application class.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}