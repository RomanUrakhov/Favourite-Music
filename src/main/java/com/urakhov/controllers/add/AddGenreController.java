package com.urakhov.controllers.add;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import com.urakhov.DatabaseHandler;
import com.urakhov.alert.AlertMaker;
import com.urakhov.dao.postgres.GenreDaoImpl;
import com.urakhov.domain.Genre;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AddGenreController {

    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane mainContainer;
    @FXML
    private JFXTextField genreName;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    private GenreDaoImpl genreDao;
    private Boolean isInEditMode = Boolean.FALSE;
    private int idEditGenre;
    private TableView<Genre> tableView;

    @FXML
    void initialize() {
        genreDao = (GenreDaoImpl) DatabaseHandler.getInstanceForAdminConnection().getBean("genreDao");
    }

    @FXML
    private void addGenre(ActionEvent actionEvent) {
        String name = genreName.getText();

        if (genreName.getText().equals("")) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in name-field.");
            return;
        }

        if (isInEditMode) {
            handleEditOperation();
            return;
        }

        genreDao.addGenre(name);
        clearFields();
        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableArrayList(genreDao.getGenreList()));
    }

    private void clearFields() {
        genreName.clear();
    }

    @FXML
    private void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    private void handleEditOperation() {
        if (genreName.getText().equals("")) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in name-field.");
            return;
        }
        genreDao.updateGenre(idEditGenre, genreName.getText());
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    public void fillFieldsForEditing(Genre genre) {
        genreName.setText(genre.getName());
        idEditGenre = genre.getId();
        this.isInEditMode = Boolean.TRUE;
    }

    public void setTableView(TableView<Genre> tableView) {
        this.tableView = tableView;
    }
}
