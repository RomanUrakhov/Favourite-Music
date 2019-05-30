package com.urakhov.controllers.tables;

import com.urakhov.DatabaseHandler;
import com.urakhov.alert.AlertMaker;
import com.urakhov.controllers.add.AddStyleController;
import com.urakhov.dao.postgres.GenreDaoImpl;
import com.urakhov.dao.postgres.StyleDaoImpl;
import com.urakhov.domain.Genre;
import com.urakhov.domain.Style;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class StyleListController {

    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private TableView<Style> tableView;
    @FXML
    private TableColumn<Style, String> idCol;
    @FXML
    private TableColumn<Style, String> nameCol;
    @FXML
    private TableColumn<Style, String> genreCol;

    private ObservableList<Style> list = FXCollections.observableArrayList();
    private StyleDaoImpl styleDao;
    private GenreDaoImpl genreDao;

    @FXML
    void initialize() {
        styleDao = (StyleDaoImpl) DatabaseHandler.getInstanceForAdminConnection().getBean("styleDao");
        genreDao = (GenreDaoImpl) DatabaseHandler.getInstanceForAdminConnection().getBean("genreDao");
        initCol();
        loadData();
    }

    private void initCol() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        genreCol.setCellValueFactory(new PropertyValueFactory<>("name_genre"));
    }

    private void loadData() {
        list.clear();

        list.addAll(styleDao.getStyleListJoinGenre());
        tableView.setItems(list);
    }

    @FXML
    void addStyle(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/add/addStyle.fxml"));
            Parent parent = loader.load();

            AddStyleController controller = loader.getController();
            controller.setTableView(tableView);

            Stage stage = new Stage();
            stage.setTitle("Add Style");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteStyle(ActionEvent event) {
        Style selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            AlertMaker.showErrorMessage("No record selected", "Please select record to be deleted.");
            return;
        }

        styleDao.removeStyle(selectedItem.getId());
        loadData();
    }

    @FXML
    void handleRowSelect(MouseEvent event) {
        Style selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null && event.getClickCount() == 2) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/add/addStyle.fxml"));
                Parent parent = loader.load();

                AddStyleController controller = loader.getController();
                Genre relatedGenre = genreDao.getGenreById(selectedItem.getId_genre());
                controller.fillFieldsForEditing(selectedItem, relatedGenre);
                controller.setTableView(tableView);

                Stage stage = new Stage();
                stage.setTitle("Edit Style");
                stage.setScene(new Scene(parent));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void updateStyle(ActionEvent event) {
        Style selectedItem = tableView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            AlertMaker.showErrorMessage("No record selected", "Please select record to be updated.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/add/addStyle.fxml"));
            Parent parent = loader.load();

            AddStyleController controller = loader.getController();
            Genre relatedGenre = genreDao.getGenreById(selectedItem.getId_genre());
            controller.fillFieldsForEditing(selectedItem, relatedGenre);
            controller.setTableView(tableView);

            Stage stage = new Stage();
            stage.setTitle("Edit Style");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
