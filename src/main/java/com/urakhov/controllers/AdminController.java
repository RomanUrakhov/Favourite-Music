package com.urakhov.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class AdminController {

    @FXML
    private JFXButton analyzerButton;
    @FXML
    private JFXButton pollButton;
    @FXML
    private JFXButton ratingButton;
    @FXML
    private JFXButton respondentButton;
    @FXML
    private JFXButton songsButton;
    @FXML
    private JFXButton genresButton;
    @FXML
    private JFXButton stylesButton;
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private BorderPane contentBorderPane;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXButton mainPageButton;
    @FXML
    private AnchorPane greetingsPane;
    @FXML
    private AnchorPane headerPane;
    @FXML
    private JFXButton exitButton;

    @FXML
    void initialize() {
        initDropDownNavBar();
    }

    private void initDropDownNavBar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/dropdown.fxml"));
            VBox box = loader.load();
            drawer.setSidePane(box);

            HamburgerBasicCloseTransition burgerTask = new HamburgerBasicCloseTransition(hamburger);
            burgerTask.setRate(-1);

            hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
                drawer.toggle();
            });
            drawer.setOnDrawerOpening((event) -> {
                burgerTask.setRate(burgerTask.getRate() * -1);
                burgerTask.play();
                AnchorPane.setLeftAnchor(contentBorderPane, (double) 150);
                drawer.toFront();
            });
            drawer.setOnDrawerClosing((event) -> {
                drawer.toBack();
                burgerTask.setRate(burgerTask.getRate() * -1);
                AnchorPane.setLeftAnchor(contentBorderPane, (double) 0);
                burgerTask.play();
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openMainPage(ActionEvent actionEvent) {
        contentBorderPane.getChildren().clear();
        contentBorderPane.setCenter(greetingsPane);
        if (drawer.isOpened()) drawer.close();
    }

    @FXML
    private void openAnalyzingPage(ActionEvent actionEvent) {
        try {
            contentBorderPane.getChildren().clear();
            AnchorPane analyzerAnchorPane = FXMLLoader.load(getClass().getResource("/view/fxml/analyzer.fxml"));
            contentBorderPane.setCenter(analyzerAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void exit(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
