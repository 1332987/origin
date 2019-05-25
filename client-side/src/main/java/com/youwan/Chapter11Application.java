package com.youwan;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Chapter11Application extends Application {


    public static void main(String[] args) {
        SpringApplication.run(Chapter11Application.class, args);

        launch(args);
    }


    @Override
    public void start(Stage stage) {
        Scene scene;
        // create the scene
        stage.setTitle("Web View");

        scene = new Scene(new Browser(), 900, 600, Color.web("#666970"));
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setAlwaysOnTop(true);
        stage.setFullScreen(true);
        stage.setIconified(true);
        stage.show();
    }
}
