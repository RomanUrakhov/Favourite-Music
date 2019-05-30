package com.urakhov.controllers.tables;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

import com.urakhov.DatabaseHandler;
import com.urakhov.alert.AlertMaker;
import com.urakhov.controllers.add.AddSongController;
import com.urakhov.controllers.add.AddStyleController;
import com.urakhov.dao.postgres.GenreDaoImpl;
import com.urakhov.dao.postgres.SongDaoImpl;
import com.urakhov.dao.postgres.StyleDaoImpl;
import com.urakhov.domain.Genre;
import com.urakhov.domain.Song;
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

public class SongListController {

    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private TableView<Song> tableView;
    @FXML
    private TableColumn<Song, String> idCol;
    @FXML
    private TableColumn<Song, String> nameCol;
    @FXML
    private TableColumn<Song, String> styleCol;
    @FXML
    private TableColumn<Song, String> durationCol;

    private ObservableList<Song> list = FXCollections.observableArrayList();
    private SongDaoImpl songDao;
    private StyleDaoImpl styleDao;
    private GenreDaoImpl genreDao;

    @FXML
    void initialize() {
        songDao = (SongDaoImpl) DatabaseHandler.getInstanceForAdminConnection().getBean("songDao");
        styleDao = (StyleDaoImpl) DatabaseHandler.getInstanceForAdminConnection().getBean("styleDao");
        genreDao = (GenreDaoImpl) DatabaseHandler.getInstanceForAdminConnection().getBean("genreDao");
        initCol();
        loadData();
    }

    private void initCol() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        styleCol.setCellValueFactory(new PropertyValueFactory<>("name_style"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
    }

    private void loadData() {
        list.clear();

        list.addAll(songDao.getSongListJoinStyle());
        tableView.setItems(list);
    }

    @FXML
    void addSong(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/add/addSong.fxml"));
            Parent parent = loader.load();

            AddSongController controller = loader.getController();
            controller.setTableView(tableView);

            Stage stage = new Stage();
            stage.setTitle("Add Song");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteSong(ActionEvent event) {
        Song selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            AlertMaker.showErrorMessage("No record selected", "Please select record to be deleted.");
            return;
        }

        songDao.removeSong(selectedItem.getId());
        loadData();
    }

    @FXML
    void handleRowSelect(MouseEvent event) {
        Song selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null && event.getClickCount() == 2) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/add/addSong.fxml"));
                Parent parent = loader.load();

                AddSongController controller = loader.getController();
                Style relatedStyle = styleDao.getStyleById(selectedItem.getId_style());
                Genre relatedGenre = genreDao.getGenreById(relatedStyle.getId_genre());
                controller.fillFieldsForEditing(selectedItem, relatedStyle, relatedGenre);
                controller.setTableView(tableView);

                Stage stage = new Stage();
                stage.setTitle("Edit Song");
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
    void updateSong(ActionEvent event) {
        Song selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            AlertMaker.showErrorMessage("No record selected", "Please select record to be updated.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/add/addSong.fxml"));
            Parent parent = loader.load();

            AddSongController controller = loader.getController();
            Style relatedStyle = styleDao.getStyleById(selectedItem.getId_style());
            Genre relatedGenre = genreDao.getGenreById(relatedStyle.getId_genre());
            controller.fillFieldsForEditing(selectedItem, relatedStyle, relatedGenre);
            controller.setTableView(tableView);

            Stage stage = new Stage();
            stage.setTitle("Edit Song");
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
