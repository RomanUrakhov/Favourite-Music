package com.urakhov.controllers.tables;

import java.net.URL;
import java.util.ResourceBundle;

import com.urakhov.DatabaseHandler;
import com.urakhov.alert.AlertMaker;
import com.urakhov.dao.postgres.RespondentDaoImpl;
import com.urakhov.domain.Respondent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class RespondentListController {


    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private TableView<Respondent> tableView;
    @FXML
    private TableColumn<Respondent, String> idCol;
    @FXML
    private TableColumn<Respondent, String> surnameCol;
    @FXML
    private TableColumn<Respondent, String> birthCol;
    @FXML
    private TableColumn<Respondent, String> emailCol;
    @FXML
    private TableColumn<Respondent, String> phoneCol;
    @FXML
    private TableColumn<Respondent, String> genderCol;

    private RespondentDaoImpl respondentDao;
    private ObservableList<Respondent> list = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        respondentDao = (RespondentDaoImpl) DatabaseHandler.getInstanceForAdminConnection().getBean("respondentDao");
        initCol();
        loadData();
    }

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        birthCol.setCellValueFactory(new PropertyValueFactory<>("birth"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
    }

    private void loadData() {
        list.clear();

        list.addAll(respondentDao.getRespondentList());
        tableView.setItems(list);
    }

    @FXML
    void deleteRespondent(ActionEvent event) {
        Respondent selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            AlertMaker.showErrorMessage("No record selected", "Please select record to be deleted.");
            return;
        }

        respondentDao.removeRespondent(selectedItem.getId());
        loadData();
    }

     



}
