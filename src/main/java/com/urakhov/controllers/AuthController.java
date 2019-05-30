package com.urakhov.controllers;

import com.jfoenix.controls.*;
import com.urakhov.alert.AlertMaker;
import com.urakhov.dao.postgres.UserDaoImpl;
import com.urakhov.exceptions.UnknownUserException;
import com.urakhov.utils.SongsAnalyzerUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;


public class AuthController {

    @FXML
    private StackPane loginRootStackPane;
    @FXML
    private JFXTextField loginText;
    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private AnchorPane mainContent;

    @FXML
    private JFXPasswordField passwordText;
    private int SALT_PLACE = 33;
    private int SALT_LENGTH = 10;

    @FXML
    void initialize() {

    }

    private void showAlert(String title, String message) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(title));
        content.setBody(new Text(message));
        JFXDialog dialog = new JFXDialog(loginRootStackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton okButton = new JFXButton("Accept");
        okButton.setOnAction(actionEvent1 -> dialog.close());
        content.setActions(okButton);
        dialog.show();
    }

    //region confirmation
    private boolean fieldsFilled() {
        return !loginButton.getText().equals("") && !passwordText.getText().equals("");
    }

    private boolean isPasswordValid(String encPass, String rawPass) {
        String saltStr = extractSaltFromHash(encPass);
        return encrypt(rawPass, saltStr).equals(encPass);
    }

    private String encrypt(String password, String salt) {
        String saltedPassword = addSaltToPassword(password, salt);
        String hash = getSHA(saltedPassword);
        return addSaltToHash(hash, salt);
    }

    private String addSaltToHash(String hash, String salt) {
        String part1 = StringUtils.substring(hash, 0, SALT_PLACE);
        String part2 = StringUtils.substring(hash, SALT_PLACE);
        return part1 + salt + part2;
    }

    private String getSHA(String rawHash) {
        return DigestUtils.sha256Hex(rawHash);
    }

    private String addSaltToPassword(String password, String salt) {
        return password + salt;
    }

    private String extractSaltFromHash(String hash) {
        return StringUtils.substring(hash, SALT_PLACE, SALT_PLACE + SALT_LENGTH);
    }
    //endregion

    @FXML
    private void login(ActionEvent actionEvent) {
        String user = loginText.getText();
        String password = passwordText.getText();

        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
        UserDaoImpl userDao = (UserDaoImpl) context.getBean("userDao");

        try {
            String encPass = userDao.getUser(user).getPassword();
            if (isPasswordValid(encPass, password)) {
                closeStage();
                loadAdminProfile();
            } else {
                AlertMaker.showMaterialDialog(loginRootStackPane, mainContent, new ArrayList<>(),
                        "Login error",
                        "Invalid password");
            }
        } catch (UnknownUserException e) {
            AlertMaker.showMaterialDialog(loginRootStackPane,mainContent,new ArrayList<>(),
                    "Login error",
                    e.getMessage());
        }
    }

    private void loadAdminProfile() {
        SongsAnalyzerUtil.loadMovableWindow(getClass().getResource("/view/fxml/admin.fxml"), "Songs Analyzer", null);
    }
    private void closeStage() {
        ((Stage) loginText.getScene().getWindow()).close();
    }

    @FXML
    private void cancel(ActionEvent actionEvent) {
        JFXButton cancel = (JFXButton) actionEvent.getSource();
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }


    public void exit(ActionEvent actionEvent) {
        JFXButton cancel = (JFXButton) actionEvent.getSource();
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
