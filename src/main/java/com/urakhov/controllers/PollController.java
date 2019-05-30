package com.urakhov.controllers;

import com.jfoenix.controls.*;
import com.urakhov.DatabaseHandler;
import com.urakhov.alert.AlertMaker;
import com.urakhov.dao.postgres.*;
import com.urakhov.domain.Genre;
import com.urakhov.domain.Respondent;
import com.urakhov.domain.Song;
import com.urakhov.domain.Style;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.avalon.framework.activity.Suspendable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PollController {

    //region FXML
    @FXML
    private JFXTextField firstSongText;
    @FXML
    private JFXComboBox<Genre> firstSongGenre;
    @FXML
    private JFXComboBox<Style> firstSongStyle;
    @FXML
    private JFXButton toSecondSongButton;
    @FXML
    private JFXSlider firstSongRating;
    @FXML
    private Tab secondSongTab;
    @FXML
    private JFXTextField secondSongText;
    @FXML
    private JFXComboBox<Genre> secondSongGenre;
    @FXML
    private JFXComboBox<Style> secondSongStyle;
    @FXML
    private JFXButton toThirdSongButton;
    @FXML
    private JFXSlider secondSongRating;
    @FXML
    private Tab thirdSongTab;
    @FXML
    private JFXTextField thirdSongText;
    @FXML
    private JFXComboBox<Genre> thirdSongGenre;
    @FXML
    private JFXComboBox<Style> thirdSongStyle;
    @FXML
    private JFXButton toFourthSongButton;
    @FXML
    private JFXSlider thirdSongRating;
    @FXML
    private Tab fourthSongTab;
    @FXML
    private JFXTextField fourthSongText;
    @FXML
    private JFXComboBox<Genre> fourthSongGenre;
    @FXML
    private JFXComboBox<Style> fourthSongStyle;
    @FXML
    private JFXButton toFifthSongButton;
    @FXML
    private JFXSlider fourthSongRating;
    @FXML
    private Tab fifthSongTab;
    @FXML
    private JFXTextField fifthSongText;
    @FXML
    private JFXComboBox<Genre> fifthSongGenre;
    @FXML
    private JFXComboBox<Style> fifthSongStyle;
    @FXML
    private JFXButton finisPollButton;
    @FXML
    private JFXSlider fifthSongRating;
    @FXML
    private JFXButton backToContactButton;
    @FXML
    private JFXButton backToFirstSongButton;
    @FXML
    private JFXButton backToSecondSongButton;
    @FXML
    private JFXButton backToThirdSongButton;
    @FXML
    private JFXButton backToFourthSongTab;
    @FXML
    private StackPane finalRootStackPane;
    @FXML
    private JFXTabPane navTabPane;
    @FXML
    private Tab general;
    @FXML
    private JFXTextField surnameText;
    @FXML
    private JFXButton stepToContactButton;
    @FXML
    private JFXDatePicker birthDate;
    @FXML
    private JFXRadioButton maleRadio;
    @FXML
    private JFXRadioButton femaleRadio;
    @FXML
    private Tab contact;
    @FXML
    private JFXTextField emailText;
    @FXML
    private JFXTextField phoneText;
    @FXML
    private JFXButton stepToPollButton;
    @FXML
    private JFXButton stepToGeneralButton;
    @FXML
    private StackPane rootStackPane;
    @FXML
    private Tab firstSongTab;
    @FXML
    private StackPane mainRootStackPane;
    @FXML
    private AnchorPane lastAnchorPane;

    //endregion

    @FXML
    void initialize() {

        createPhoneField();

        maleRadio.setOnAction(actionEvent -> {
            if (maleRadio.isSelected()) {
                femaleRadio.setSelected(false);
            } else femaleRadio.setSelected(true);
        });
        femaleRadio.setOnAction(actionEvent -> {
            if (femaleRadio.isSelected()) {
                maleRadio.setSelected(false);
            } else maleRadio.setSelected(true);
        });


        //region nav buttons
        stepToContactButton.setOnAction(actionEvent -> navTabPane.getSelectionModel().selectNext());
        stepToPollButton.setOnAction(actionEvent -> navTabPane.getSelectionModel().selectNext());
        stepToGeneralButton.setOnAction(actionEvent -> navTabPane.getSelectionModel().selectPrevious());
        stepToPollButton.setOnAction(actionEvent -> navTabPane.getSelectionModel().selectNext());
        toSecondSongButton.setOnAction(actionEvent -> navTabPane.getSelectionModel().selectNext());
        toThirdSongButton.setOnAction(actionEvent -> navTabPane.getSelectionModel().selectNext());
        toFourthSongButton.setOnAction(actionEvent -> navTabPane.getSelectionModel().selectNext());
        toFifthSongButton.setOnAction(actionEvent -> navTabPane.getSelectionModel().selectNext());
        backToContactButton.setOnAction(actionEvent -> navTabPane.getSelectionModel().selectPrevious());
        backToFirstSongButton.setOnAction(actionEvent -> navTabPane.getSelectionModel().selectPrevious());
        backToSecondSongButton.setOnAction(actionEvent -> navTabPane.getSelectionModel().selectPrevious());
        backToThirdSongButton.setOnAction(actionEvent -> navTabPane.getSelectionModel().selectPrevious());
        backToFourthSongTab.setOnAction(actionEvent -> navTabPane.getSelectionModel().selectPrevious());
        //endregion

        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Guest.xml");
        StyleDaoImpl styleDao =  (StyleDaoImpl) context.getBean("styleDao");
        GenreDaoImpl genreDao = (GenreDaoImpl) context.getBean("genreDao");
        SongDaoImpl songDao = (SongDaoImpl) context.getBean("songDao");
        RespondentDaoImpl respondentDao = (RespondentDaoImpl) context.getBean("respondentDao");
        PollDaoImpl pollDao = (PollDaoImpl) context.getBean("pollDao");


        ObservableList<Genre> genreObservableList = FXCollections.observableList(genreDao.getGenreList());
        ObservableList<Style> styleObservableList = FXCollections.observableList(styleDao.getStyleList());
        ObservableList<Song> songList = FXCollections.observableList(songDao.getSongList());

        FilteredList<Style> styleFilteredList = new FilteredList<>(styleObservableList, p -> true);
        genreObservableList.add(new Genre(100, "..."));

        Map<Song, Integer> songGenreMap = new HashMap<>();
        for (Song song: songList) {
            int v = (styleDao.getStyleById(song.getId_style())).getId_genre();
            songGenreMap.put(song, v);
        }
        Map<String, Song> nameSongMap = new HashMap<>();
        for (Song song: songList) {
            nameSongMap.put(song.getName(), song);
        }

        JFXAutoCompletePopup<Song> autoCompletePopup = new JFXAutoCompletePopup<>();
        autoCompletePopup.getSuggestions().addAll(songList);

        HashMap<JFXTextField, Song> songsNotToBeSuggested = new HashMap<>();

        navTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int tabIndex = navTabPane.getSelectionModel().getSelectedIndex();
            if (tabIndex > 1) {
                AnchorPane ap = (AnchorPane) newValue.getContent();

                if (tabIndex == 6) {
                    StackPane sp = (StackPane) ap.getChildren().get(0);
                    ap = (AnchorPane) sp.getChildren().get(0);
                }

                JFXTextField jfxTextField = (JFXTextField) ap.getChildren().get(0);
                JFXComboBox<Genre> genreJFXComboBox = (JFXComboBox) ap.getChildren().get(1);
                JFXComboBox<Style> styleJFXComboBox = (JFXComboBox) ap.getChildren().get(2);

                styleFilteredList.setPredicate(item -> true);
                genreJFXComboBox.setItems(genreObservableList);
                styleJFXComboBox.setItems(styleFilteredList);

                genreJFXComboBox.getSelectionModel().selectedItemProperty().addListener((obsG, oldGenre, newGenre) -> {
                    if (newGenre != null) {
                        if (newGenre.toString().equals("...")) {
                            Platform.runLater(() -> genreJFXComboBox.getSelectionModel().clearSelection());
                            if (styleJFXComboBox.getValue() != null) {
                                styleJFXComboBox.setValue(null);
                            }
                            styleFilteredList.setPredicate(item -> true);
                        } else {
                            if (styleJFXComboBox.getValue() != null) {
                                styleJFXComboBox.setValue(null);
                            }
                            styleFilteredList.setPredicate(item -> item.getId_genre()== newGenre.getId());
                        }
                    } else {
                        Platform.runLater(() -> genreJFXComboBox.getSelectionModel().clearSelection());
                    }
                });
                styleJFXComboBox.getSelectionModel().selectedItemProperty().addListener((obsS, oldStyle, newStyle) -> {
                    if (newStyle != null) {

                    }
                });

                jfxTextField.textProperty().addListener((obsT, oldText, newText) -> {

                    autoCompletePopup.setSelectionHandler(songJFXAutoCompleteEvent -> {
                        String selectedSong = songJFXAutoCompleteEvent.getObject().getName();
                        jfxTextField.setText(selectedSong);

                        songsNotToBeSuggested.put(jfxTextField, songJFXAutoCompleteEvent.getObject());
                        autoCompletePopup.getSuggestions().remove(songJFXAutoCompleteEvent.getObject());

                        autoCompletePopup.hide();
                    });

                    if (jfxTextField.getText().length() == 0) {
                        Song deletedSong = songsNotToBeSuggested.get(jfxTextField);
                        if (deletedSong != null) {
                            autoCompletePopup.getSuggestions().addAll(deletedSong);
                            //autoCompletePopup.getSuggestions().add(deletedSong);
                            songsNotToBeSuggested.remove(jfxTextField);
                        }
                    }
                    //МОЖНО ПОПРОБОВАТЬ ИЗМЕНИТЬ ПРЕДИКАТ ИМЕННО ДЛЯ ФИЛЬТРА aoutoCompletePopUP!!!!
                    autoCompletePopup.filter(item -> {
                        if (styleJFXComboBox.getValue() != null) {
                            if (item.getName().toLowerCase().startsWith(newText.toLowerCase())
                                    && item.getId_style() == styleJFXComboBox.getValue().getId()) {
                                return true;
                            }
                        } else if (genreJFXComboBox.getValue() != null) {
                            if (item.getName().toLowerCase().startsWith(newText.toLowerCase())
                                && songGenreMap.get(item) == genreJFXComboBox.getValue().getId()) {
                                    return true;
                            }
                        } else if (item.getName().toLowerCase().startsWith(newText.toLowerCase()))
                            return true;
                        return false;
                    });

                    if (autoCompletePopup.getFilteredSuggestions().isEmpty() || jfxTextField.getText().isEmpty()) {
                        autoCompletePopup.hide();
                    } else {
                        autoCompletePopup.show(jfxTextField);
                    }
                });
            }
        });

        finisPollButton.setOnAction(actionEvent -> {
            if (!isFieldsMentioned()) {
                AlertMaker.showMaterialDialog(finalRootStackPane, lastAnchorPane, new ArrayList<>(),
                        "Can't finish the poll",
                        "Some required fields weren't specified");
                /*JFXDialogLayout content = new JFXDialogLayout();
                content.setHeading(new Text("Can't finish the poll"));
                content.setBody(new Text("Some required fields weren't specified"));
                JFXDialog dialog = new JFXDialog(finalRootStackPane, content, JFXDialog.DialogTransition.CENTER);
                JFXButton okButton = new JFXButton("Accept");
                okButton.setOnAction(actionEvent1 -> dialog.close());
                content.setActions(okButton);
                dialog.show();*/
            } else {
                List<String> songNames = getMentionedSongs();
                String gender = "";
                if (maleRadio.isSelected())
                    gender="M";
                else
                    gender="F";
                List<Float> assessments = new ArrayList<>();
                assessments.add((float) (firstSongRating.getValue()/20));
                assessments.add((float) (secondSongRating.getValue()/20));
                assessments.add((float) (thirdSongRating.getValue()/20));
                assessments.add((float) (fourthSongRating.getValue()/20));
                assessments.add((float) (fifthSongRating.getValue()/20));

                LocalDate birth = birthDate.getValue();
                respondentDao.addRespondent(surnameText.getText(), birth, gender, emailText.getText(), phoneText.getText());
                Respondent respondent = FXCollections.observableArrayList(
                        respondentDao.getRespondentList().get(respondentDao.getRespondentList().size()-1)).get(0);
                int i = 0;
                for (String songName: songNames) {
                    pollDao.addVote(respondent.getId(), nameSongMap.get(songName).getId(), assessments.get(i));
                    i++;
                }
                Stage currentStage = (Stage) finisPollButton.getScene().getWindow();
                currentStage.close();
            }
        });

    }

    private boolean isFieldsMentioned() {
        if (surnameText.getText().equals("") || birthDate.getValue().toString().equals("") ||
                (maleRadio.isSelected() && femaleRadio.isSelected())) {
            return false;
        } else {
            for (String songName: getMentionedSongs()) {
                if (songName.equals("")) {
                    return false;
                }
            }
        }
        return true;
    }
    private List<String> getMentionedSongs() {
        List<String> result = new ArrayList<>(FXCollections.observableArrayList(
                firstSongText.getText(),
                secondSongText.getText(),
                thirdSongText.getText(),
                fourthSongText.getText(),
                fifthSongText.getText()));
        return  result;
    }

    private void createPhoneField() {
        phoneText.setPrefColumnCount(12);
        TextFormatter<String> formatter =
                new TextFormatter<String>(this:: addPhoneNumberMask);
        phoneText.setTextFormatter(formatter);
    }

    private TextFormatter.Change addPhoneNumberMask(TextFormatter.Change change) {
        if (!change.isContentChange() && !change.getControlNewText().isEmpty()) {
            return change;
        }

        String text = change.getControlNewText();
        int start = change.getRangeStart();
        int end = change.getRangeEnd();

        int anchor = change.getAnchor();
        int caret = change.getCaretPosition();

        StringBuilder newText = new StringBuilder(text);

        int dash;
        while ((dash = newText.lastIndexOf("-")) >= start) {
            newText.deleteCharAt(dash);
            if (caret > dash) {
                caret--;
            }
            if (anchor > dash) {
                anchor--;
            }
        }

        while (newText.length() < 3) {
            newText.append('#');
        }
        if (newText.length() == 3 || newText.charAt(3) != '-') {
            newText.insert(3, '-');
            if (caret > 3 || (caret == 3 && end <= 3 && change.isDeleted())) {
                caret++;
            }
            if (anchor > 3 || (anchor == 3 && end <= 3 && change.isDeleted())) {
                anchor++;
            }
        }

        while (newText.length() < 7) {
            newText.append('#');
        }
        if (newText.length() == 7 || newText.charAt(7) != '-') {
            newText.insert(7, '-');
            if (caret > 7 || (caret == 7 && end <= 7 && change.isDeleted())) {
                caret++;
            }
            if (anchor > 7 || (anchor == 7 && end <= 7 && change.isDeleted())) {
                anchor++;
            }
        }

        while (newText.length() < 12) {
            newText.append('#');
        }

        if(newText.length() > 12) {
            newText.delete(12, newText.length());
        }

        text = newText.toString();
        anchor = Math.min(anchor, 12);
        caret = Math.min(caret, 12);

        change.setText(text);
        change.setRange(0, change.getControlText().length());
        change.setAnchor(anchor);
        change.setCaretPosition(caret);

        return change;
    }

    public void exit(ActionEvent actionEvent) {
        JFXButton cancel = (JFXButton) actionEvent.getSource();
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
