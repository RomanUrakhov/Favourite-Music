package com.urakhov.controllers.add;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import com.urakhov.DatabaseHandler;
import com.urakhov.alert.AlertMaker;
import com.urakhov.dao.postgres.GenreDaoImpl;
import com.urakhov.dao.postgres.StyleDaoImpl;
import com.urakhov.domain.Genre;
import com.urakhov.domain.Style;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AddStyleController {

    @FXML
    private JFXComboBox<Genre> genre;
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane mainContainer;
    @FXML
    private JFXTextField styleName;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    private StyleDaoImpl styleDao;
    private ObservableList<Genre> genres;
    private Boolean isInEditMode = Boolean.FALSE;
    private int idEditedStyle;
    private TableView<Style> tableView;


    @FXML
    void initialize() {
        styleDao = (StyleDaoImpl) DatabaseHandler.getInstanceForAdminConnection().getBean("styleDao");
        genres = FXCollections.observableArrayList(((GenreDaoImpl) DatabaseHandler.getInstanceForAdminConnection().getBean("genreDao")).getGenreList());

        genre.setItems(genres);
    }

    @FXML
    void addStyle(ActionEvent event) {

        if (styleName.getText().equals("") || genre.getValue() == null) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in name-field.");
            return;
        }

        if (isInEditMode) {
            handleEditOperation();
            return;
        }

        String name = styleName.getText();
        int genre_id = genre.getValue().getId();

        styleDao.addStyle(name, genre_id);
        clearFields();
        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableArrayList(styleDao.getStyleList()));
    }


    private void handleEditOperation() {
        if (styleName.getText().equals("") || genre.getValue() == null) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in name-field.");
            return;
        }
        styleDao.updateStyle(idEditedStyle, styleName.getText(), genre.getValue().getId());
        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableArrayList(styleDao.getStyleList()));
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    public void fillFieldsForEditing(Style style, Genre genre) {
        styleName.setText(style.getName());
        FilteredList<Genre> filteredList = genres.filtered(item -> item.getName().equals(genre.getName()));
        this.genre.setValue(filteredList.get(0));
        idEditedStyle = style.getId();
        this.isInEditMode = Boolean.TRUE;
    }

    public void setTableView(TableView<Style> tableView) {
        this.tableView = tableView;
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    private void clearFields() {
        styleName.clear();
        genre.getSelectionModel().clearSelection();
    }
}
