package com.example.demo1;


import com.example.demo1.Models.CalendarActivity;
import com.example.demo1.Models.SqliteConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        Connection connection = SqliteConnection.getInstance();
        launch(args);
    }

}
