package com.urakhov.controllers.add;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import com.jfoenix.validation.RequiredFieldValidator;
import com.urakhov.DatabaseHandler;
import com.urakhov.alert.AlertMaker;
import com.urakhov.dao.postgres.GenreDaoImpl;
import com.urakhov.dao.postgres.SongDaoImpl;
import com.urakhov.dao.postgres.StyleDaoImpl;
import com.urakhov.domain.Genre;
import com.urakhov.domain.Song;
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
import org.apache.commons.lang3.math.NumberUtils;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;

public class AddSongController {

    @FXML
    private JFXTextField sec;
    @FXML
    private JFXTextField min;
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane mainContainer;
    @FXML
    private JFXTextField songName;
    @FXML
    private JFXComboBox<Style> style;
    @FXML
    private JFXComboBox<Genre> genre;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;

    private TableView<Song> tableView;
    private SongDaoImpl songDao;
    private StyleDaoImpl styleDao;
    private GenreDaoImpl genreDao;
    private Boolean isInEditMode = Boolean.FALSE;
    private int idEditedSong;
    private ObservableList<Style> styles;
    private ObservableList<Genre> genres;

    @FXML
    void initialize() {
        songDao = (SongDaoImpl) DatabaseHandler.getInstanceForAdminConnection().getBean("songDao");
        styleDao = (StyleDaoImpl) DatabaseHandler.getInstanceForAdminConnection().getBean("styleDao");
        genreDao = (GenreDaoImpl) DatabaseHandler.getInstanceForAdminConnection().getBean("genreDao");

        styles = FXCollections.observableList(styleDao.getStyleList());
        genres = FXCollections.observableArrayList(genreDao.getGenreList());

        style.setItems(styles);
        genre.setItems(genres);
    }

    @FXML
    void addSong(ActionEvent event) {
        if (songName.getText().equals("") || style.getValue() == null || genre.getValue() == null ||
                (min.getText().equals("") && sec.getText().equals(""))) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in name-field.");
            return;
        }

        if (isInEditMode) {
            handleEditOperation();
            return;
        }

        String name = songName.getText();
        Time duration = Time.valueOf(LocalTime.of(Integer.parseInt(min.getText()), Integer.parseInt(sec.getText())));
        int id_style = style.getValue().getId();
        songDao.addSong(name, duration, id_style);
        clearFields();
        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableArrayList(songDao.getSongList()));
    }

    @FXML
    private void selectRelatedGenre(ActionEvent actionEvent) {
        genre.setValue(genres.filtered(item ->
                item.getName().equals(genreDao.getGenreById(style.getValue().getId_genre()).getName())).get(0));
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    public void setTableView(TableView<Song> tableView) {
        this.tableView = tableView;
    }

    private void handleEditOperation() {
        if (songName.getText().equals("") || genre.getValue() == null || style.getValue() == null) {
            AlertMaker.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in name-field.");
            return;
        }

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Wrong type of parameter");
        min.getValidators().add(validator);
        min.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!NumberUtils.isDigits(newValue.toString()))
                validator.validate();
        });
        sec.focusedProperty().addListener((observableValue, oldVelue, newValue) -> {
            if (NumberUtils.isDigits(newValue.toString()))
                validator.validate();
        });

        songDao.updateSong(
                idEditedSong,
                songName.getText(),
                Time.valueOf(LocalTime.of(0, Integer.parseInt(min.getText()), Integer.parseInt(sec.getText()))),
                style.getValue().getId()
        );
        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableArrayList(songDao.getSongList()));

        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    public void fillFieldsForEditing(Song song, Style style, Genre genre) {
        songName.setText(song.getName());
        FilteredList<Style> styleFilteredList = styles.filtered(item -> item.getName().equals(style.getName()));
        FilteredList<Genre> genreFilteredList = genres.filtered(item -> item.getName().equals(genre.getName()));
        this.genre.setValue(genreFilteredList.get(0));
        this.style.setValue(styleFilteredList.get(0));
        idEditedSong = song.getId();
        isInEditMode = Boolean.TRUE;
    }

    private void clearFields() {
        songName.clear();
        genre.getSelectionModel().clearSelection();
        style.getSelectionModel().clearSelection();
    }

}
