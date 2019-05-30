package com.urakhov;

import com.urakhov.utils.SongsAnalyzerUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) {
        SongsAnalyzerUtil.loadMovableWindow(getClass().getResource("/view/fxml/profile.fxml"), "Main", null);

        new Thread(() -> {
            DatabaseHandler.getInstanceForAdminConnection();
            DatabaseHandler.getInstanceForGuestConnection();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
