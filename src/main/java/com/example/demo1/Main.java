package com.example.demo1;


import com.example.demo1.Calendar.CalendarActivity;
import com.example.demo1.Models.SqliteConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

/**
 * The Main class serves as the entry point for the JavaFX application.
 * It initializes the primary stage with the login view and sets up the application window.
 */
public class Main extends Application {

    /** The width of the application window. */
    public static final int WIDTH = 744;

    /** The height of the application window. */
    public static final int HEIGHT = 642;
    private CalendarActivity primaryStage;

    /**
     * The entry point for the JavaFX application.
     * Initializes the primary stage with the login view and sets up the application window.
     *
     * @param stage The primary stage of the application.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/demo1/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle("ScreenTrack");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method of the application.
     * Launches the JavaFX application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Connection connection = SqliteConnection.getInstance();
        launch(args);
    }

}
