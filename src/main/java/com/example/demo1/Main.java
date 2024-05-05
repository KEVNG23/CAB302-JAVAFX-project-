package com.example.demo1;


import com.example.demo1.Calendar.CalendarActivity;
import com.example.demo1.Models.SqliteConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Main extends Application {

    public static final int WIDTH = 600;

    public static final int HEIGHT = 400;
    private CalendarActivity primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/calendar.fxml"));
        loader.setController(this); // Set the controller instance (optional)
        Parent root = loader.load(); // Load the FXML file

        // Optionally, get the controller instance if needed
        // CalendarController controller = loader.getController();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Calendar App");

    }



    public static void main(String[] args) {
        Connection connection = SqliteConnection.getInstance();
        launch(args);
    }

}
