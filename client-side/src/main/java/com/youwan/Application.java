package com.youwan;

import com.youwan.config.Browser;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application extends javafx.application.Application {
//public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        launch(args);
    }


    @Override
    public void start(Stage stage) {
        Scene scene;
        // create the scene
        stage.setTitle("劳务实名制管理系统");

        scene = new Scene(new Browser(), 900, 600, Color.web("#666970"));
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setAlwaysOnTop(true);
        stage.setFullScreen(true);
        stage.setIconified(true);
        stage.show();
    }
}
