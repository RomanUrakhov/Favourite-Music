package com.urakhov.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTabPane;
import com.urakhov.utils.SongsAnalyzerUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class ToolBarController {

    @FXML
    private void initialize() {

    }

    @FXML
    private void loadRatingTable(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/tables/categoryTabPane.fxml"));
            StackPane stack = loader.load();
            JFXTabPane tabPane = (JFXTabPane) ((AnchorPane) stack.getChildren().get(0)).getChildren().get(0);

            JFXButton buttonPressed = (JFXButton) actionEvent.getSource();
            StackPane sp = (StackPane) buttonPressed.getParent().getParent().getParent().getParent();
            AnchorPane ap = (AnchorPane) sp.getChildren().get(0);
            BorderPane bp = (BorderPane) ap.getChildren().get(1);
            bp.setTop(null);

            bp.setCenter(tabPane);

            ObservableList<Node> child = sp.getChildren();
            JFXDrawer drawer = (JFXDrawer) child.get(1);
            drawer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void loadRespondentTable(ActionEvent actionEvent) {
        SongsAnalyzerUtil.loadTableWindow(
                getClass().getResource("/view/fxml/tables/respondentsTable.fxml"),
                actionEvent
        );
    }

    @FXML
    private void loadSongTable(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/tables/songsTable.fxml"));
            StackPane stack = loader.load();
            TableView tableView = (TableView) ((AnchorPane) stack.getChildren().get(0)).getChildren().get(0);
            HBox crudButtons = (HBox)  ((AnchorPane) stack.getChildren().get(0)).getChildren().get(1);

            JFXButton buttonPressed = (JFXButton) actionEvent.getSource();
            StackPane sp = (StackPane) buttonPressed.getParent().getParent().getParent().getParent();
            AnchorPane ap = (AnchorPane) sp.getChildren().get(0);
            BorderPane bp = (BorderPane) ap.getChildren().get(1);

            bp.setCenter(tableView);
            bp.setTop(crudButtons);
            BorderPane.setAlignment(crudButtons, Pos.CENTER);
            BorderPane.setMargin(tableView, new Insets(0, 60, 60, 60));

            ObservableList<Node> child = sp.getChildren();
            JFXDrawer drawer = (JFXDrawer) child.get(1);
            drawer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadStyleTable(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/tables/stylesTable.fxml"));
            StackPane stack = loader.load();
            TableView tableView = (TableView) ((AnchorPane) stack.getChildren().get(0)).getChildren().get(0);
            HBox crudButtons = (HBox)  ((AnchorPane) stack.getChildren().get(0)).getChildren().get(1);

            JFXButton buttonPressed = (JFXButton) actionEvent.getSource();
            StackPane sp = (StackPane) buttonPressed.getParent().getParent().getParent().getParent();
            AnchorPane ap = (AnchorPane) sp.getChildren().get(0);
            BorderPane bp = (BorderPane) ap.getChildren().get(1);

            bp.setCenter(tableView);
            bp.setTop(crudButtons);
            BorderPane.setAlignment(crudButtons, Pos.CENTER);
            BorderPane.setMargin(tableView, new Insets(0, 60, 60, 60));

            ObservableList<Node> child = sp.getChildren();
            JFXDrawer drawer = (JFXDrawer) child.get(1);
            drawer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadGenreTable(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/genresTable.fxml"));
            StackPane stack = loader.load();
            TableView tableView = (TableView) ((AnchorPane) stack.getChildren().get(0)).getChildren().get(0);
            HBox crudButtons = (HBox)  ((AnchorPane) stack.getChildren().get(0)).getChildren().get(1);

            JFXButton buttonPressed = (JFXButton) actionEvent.getSource();
            StackPane sp = (StackPane) buttonPressed.getParent().getParent().getParent().getParent();
            AnchorPane ap = (AnchorPane) sp.getChildren().get(0);
            BorderPane bp = (BorderPane) ap.getChildren().get(1);

            bp.setCenter(tableView);
            bp.setTop(crudButtons);
            BorderPane.setAlignment(crudButtons, Pos.CENTER);
            BorderPane.setMargin(tableView, new Insets(0, 60, 60, 60));

            ObservableList<Node> child = sp.getChildren();
            JFXDrawer drawer = (JFXDrawer) child.get(1);
            drawer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
