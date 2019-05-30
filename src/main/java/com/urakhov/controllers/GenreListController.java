package com.urakhov.controllers;

import com.urakhov.DatabaseHandler;
import com.urakhov.alert.AlertMaker;
import com.urakhov.controllers.add.AddGenreController;
import com.urakhov.dao.postgres.GenreDaoImpl;
import com.urakhov.domain.Genre;
import com.urakhov.utils.SongsAnalyzerUtil;
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

public class GenreListController {

    private ObservableList<Genre> list = FXCollections.observableArrayList();

    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private TableView<Genre> tableView;
    @FXML
    private TableColumn<Genre, String> nameCol;
    @FXML
    private TableColumn<Genre, String> idCol;
    private GenreDaoImpl genreDao;

    @FXML
    void initialize() {
        initCol();
        loadData();
    }

    private void initCol() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void loadData() {
        list.clear();

        genreDao = (GenreDaoImpl) DatabaseHandler.getInstanceForAdminConnection().getBean("genreDao");
        list.addAll(genreDao.getGenreList());
        tableView.setItems(list);
    }

    @FXML
    private void addGenre(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/add/addGenre.fxml"));
            Parent parent = loader.load();

            AddGenreController controller = loader.getController();
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
    private void updateGenre(ActionEvent actionEvent) {
        Genre selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            AlertMaker.showErrorMessage("No genre selected", "Please select a genre for updating");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/add/addGenre.fxml"));
            Parent parent = loader.load();

            AddGenreController controller = loader.getController();
            controller.fillFieldsForEditing(selectedItem);

            Stage stage = new Stage();
            stage.setTitle("Edit Book");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

            stage.setOnHiding((e) -> {
                loadData();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteGenre(ActionEvent actionEvent) {
    }

    @FXML
    private void handleRowSelect(MouseEvent mouseEvent) {
        Genre selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null && mouseEvent.getClickCount() == 2) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/add/addGenre.fxml"));
                Parent parent = loader.load();

                AddGenreController controller = loader.getController();
                controller.fillFieldsForEditing(selectedItem);

                Stage stage = new Stage();
                stage.setTitle("Edit Genre");
                stage.setScene(new Scene(parent));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();

                stage.setOnHiding((e) -> {
                    loadData();
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
