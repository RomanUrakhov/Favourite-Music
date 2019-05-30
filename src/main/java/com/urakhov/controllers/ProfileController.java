package com.urakhov.controllers;

import com.jfoenix.controls.JFXButton;
import com.urakhov.utils.SongsAnalyzerUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class ProfileController {

    @FXML
    public JFXButton adminProfileButton;
    @FXML
    private JFXButton pollProfileButton;

    @FXML
    void initialize() {
        pollProfileButton.setOnAction(actionEvent -> {
            SongsAnalyzerUtil.loadMovableWindow(getClass().getResource("/view/fxml/poll.fxml"), "Poll", null);
        });
        adminProfileButton.setOnAction(actionEvent -> {
            SongsAnalyzerUtil.loadMovableWindow(getClass().getResource("/view/fxml/authentication.fxml"), "Poll", null);
        });
    }

    public void exit(ActionEvent actionEvent) {
        JFXButton cancel = (JFXButton) actionEvent.getSource();
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
