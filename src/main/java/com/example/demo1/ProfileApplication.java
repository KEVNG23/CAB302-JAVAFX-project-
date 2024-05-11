package com.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // load the fxml file
        FXMLLoader fxmlLoader = new FXMLLoader(ProfileApplication.class.getResource("profile-view.fxml"));

        // create a new scene with the fxml file and set the size
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        // set the title
        stage.setTitle("Profile Settings");

        // set the minimum width and height
        stage.setMinWidth(800);
        stage.setMinHeight(500);

        // set the scene
        stage.setScene(scene);

        // show the stage
        stage.show();
    }

    // launch the javafx application
    public static void main(String[] args) {
        launch();
    }
}