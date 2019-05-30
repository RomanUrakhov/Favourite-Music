package com.urakhov.utils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class SongsAnalyzerUtil {

    private static double xOffset;
    private static double yOffset;

    public static Object loadMovableWindow(URL loc, String title, Stage parentStage) {
        Object controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            StackPane parent = loader.load();
            AnchorPane header = (AnchorPane) ((AnchorPane) parent.getChildren().get(1)).getChildren().get(0);

            controller = loader.getController();
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
            }
            stage.setTitle(title);
            Scene scene = new Scene(parent);

            Stage finalStage = stage;
            header.setOnMouseDragged(mouseEvent -> {
                finalStage.setX(mouseEvent.getScreenX() + xOffset);
                finalStage.setY(mouseEvent.getScreenY() + yOffset);
            });
            header.setOnMousePressed(mouseEvent -> {
                xOffset = finalStage.getX() - mouseEvent.getScreenX();
                yOffset = finalStage.getY() - mouseEvent.getScreenY();
            });

            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return controller;
    }

    public static Object loadCommonWindow(URL loc, String title, Stage parentStage) {
        Object controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            StackPane parent = loader.load();

            controller = loader.getController();
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
            }
            stage.setTitle(title);
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return controller;
    }

    public static void loadTableWindow(URL loc, ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(loc);
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
