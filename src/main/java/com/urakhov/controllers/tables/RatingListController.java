package com.urakhov.controllers.tables;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.urakhov.DatabaseHandler;
import com.urakhov.dao.postgres.RatingDaoImpl;
import com.urakhov.domain.Rating;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class RatingListController {

    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private TableView<Rating> tableView;
    @FXML
    private TableColumn<Rating, String> idCol;
    @FXML
    private TableColumn<Rating, String> songNameCol;
    @FXML
    private TableColumn<Rating, String> ratingCol;
    private RatingDaoImpl ratingDao;
    @FXML
    void initialize() {
        ratingDao = (RatingDaoImpl) DatabaseHandler.getInstanceForAdminConnection().getBean("ratingDao");

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        songNameCol.setCellValueFactory(new PropertyValueFactory<>("name_song"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    @FXML
    void deleteRating(ActionEvent event) {
        JFXButton button = (JFXButton) event.getSource();
        BorderPane currentTabBorderPane = (BorderPane) button.getParent().getParent();
        JFXTabPane currentTabPane = (JFXTabPane) currentTabBorderPane.getParent().getParent().getParent().getParent().getParent();
        String tabId = currentTabPane.getSelectionModel().getSelectedItem().getId();
        switch (tabId) {
            case "generalTab": {
                ratingDao.removeRating(7);
                tableView.getItems().clear();
                tableView.setItems(FXCollections.observableArrayList(ratingDao.getGeneralRatingList()));
            }   break;
            case "menTab": {
                ratingDao.removeRating(1);
                tableView.getItems().clear();
                tableView.setItems(FXCollections.observableArrayList(ratingDao.getMenRatingList()));
            }break;
            case "womenTab": {
                ratingDao.removeRating(2);
                tableView.getItems().clear();
                tableView.setItems(FXCollections.observableArrayList(ratingDao.getWomenRatingList()));
            }break;
            case "tenTab": {
                ratingDao.removeRating(3);
                tableView.getItems().clear();
                tableView.setItems(FXCollections.observableArrayList(ratingDao.getTenRatingList()));
            }break;
            case "sixteenTab": {
                ratingDao.removeRating(4);
                tableView.getItems().clear();
                tableView.setItems(FXCollections.observableArrayList(ratingDao.getSixteenRatingList()));
            }break;
            case "twentytwoTab":{
                ratingDao.removeRating(5);
                tableView.getItems().clear();
                tableView.setItems(FXCollections.observableArrayList(ratingDao.getTwentytwoRatingList()));
            }break;
            case "thirtysixTab":{
                ratingDao.removeRating(6);
                tableView.getItems().clear();
                tableView.setItems(FXCollections.observableArrayList(ratingDao.getThirtySixRatingList()));
            }break;
        }
    }
}
