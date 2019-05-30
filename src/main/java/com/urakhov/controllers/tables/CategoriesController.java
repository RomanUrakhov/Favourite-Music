package com.urakhov.controllers.tables;

import com.jfoenix.controls.JFXTabPane;

import com.urakhov.DatabaseHandler;
import com.urakhov.dao.postgres.RatingDaoImpl;
import com.urakhov.domain.Rating;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class CategoriesController {

    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private JFXTabPane navTab;
    @FXML
    private Tab generalTab;
    @FXML
    private Tab menTab;
    @FXML
    private Tab womenTab;
    @FXML
    private Tab tenTab;
    @FXML
    private Tab sixteenTab;
    @FXML
    private Tab twentytwoTab;
    @FXML
    private Tab thirtysixTab;

    private ObservableList<Rating> listGeneral;
    private ObservableList<Rating> listMen;
    private ObservableList<Rating> listWomen;
    private RatingDaoImpl ratingDao = (RatingDaoImpl) DatabaseHandler.getInstanceForAdminConnection().getBean("ratingDao");


    @FXML
    void initialize() {
        int i = 0;
        for(Tab tab: navTab.getTabs()) {
           try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/tables/ratingTable.fxml"));
               StackPane stack = loader.load();
               BorderPane borderPane = (BorderPane) ((AnchorPane) stack.getChildren().get(0)).getChildren().get(0);
               TableView tableView = (TableView) borderPane.getCenter();

               ObservableList<Rating> list = FXCollections.observableArrayList();
               switch (i) {
                   case 0: list.addAll(ratingDao.getGeneralRatingList()); break;
                   case 1: list.addAll(ratingDao.getMenRatingList()); break;
                   case 2: list.addAll(ratingDao.getWomenRatingList()); break;
                   case 3: list.addAll(ratingDao.getTenRatingList()); break;
                   case 4: list.addAll(ratingDao.getSixteenRatingList()); break;
                   case 5: list.addAll(ratingDao.getTwentytwoRatingList()); break;
                   case 6: list.addAll(ratingDao.getThirtySixRatingList()); break;

               }
               tableView.setItems(list);

               AnchorPane ap = (AnchorPane) tab.getContent();
               AnchorPane.setBottomAnchor(borderPane, 0.0);
               AnchorPane.setRightAnchor(borderPane, 0.0);
               AnchorPane.setTopAnchor(borderPane, 0.0);
               AnchorPane.setLeftAnchor(borderPane, 0.0);

               ap.getChildren().add(borderPane);
               i++;
           } catch (IOException e) {
               e.printStackTrace();
           }
        }
    }

    private void initData(ObservableList<Rating> list, TableView<Rating> tableView) {
        list.clear();

        list.addAll(ratingDao.getGeneralRatingList());

    }
}
