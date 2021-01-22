package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.dto.EmployeeDTO;
import sample.rest.EmployeeRestClient;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class StartPanelController implements Initializable {

    private static final String APP_FXML = "/fxml/app.fxml";

    private final EmployeeRestClient employeeRestClient;
    private EmployeeDTO employeeDTO;

    private String userPIN = "";

    @FXML
    private Button timeButton;

    @FXML
    private Button RVCButton;

    @FXML
    private Button availableButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button onOffButton;

    @FXML
    private Button notAvailableButton;

    @FXML
    private Button sevenButton;

    @FXML
    private Button fourButton;

    @FXML
    private Button oneButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button eightButton;

    @FXML
    private Button fiveButton;

    @FXML
    private Button twoButton;

    @FXML
    private Button zeroButton;

    @FXML
    private Button nineButton;

    @FXML
    private Button sixButton;

    @FXML
    private Button threeButton;

    @FXML
    private Button okButton;

    @FXML
    private Label nameLabel;

    @FXML
    private TextArea mainTextArea;

    public StartPanelController() {
        this.employeeRestClient = new EmployeeRestClient();
        this.employeeDTO = new EmployeeDTO();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeZeroButton();
        initializeOneButton();
        initializeTwoButton();
        initializeThreeButton();
        initializeFourButton();
        initializeFiveButton();
        initializeSixButton();
        initializeSevenButton();
        initializeEightButton();
        initializeNineButton();
        initializeClearButton();
        initializeOkButton();
        initializeTimeButton();
    }

    private void initializeTimeButton() {
        timeButton.setOnAction(x -> {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());

            mainTextArea.setText(formatter.format(date));
        });
    }

    private void initializeOkButton() {
        okButton.setOnAction(x -> {
            employeeDTO = employeeRestClient.getEmployeeByPIN(userPIN);
            userPIN = "";

            if(employeeDTO.isAuthenticated()){
                try {
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(APP_FXML));
                    Scene scene = new Scene(loader.load(), 1920, 1020);
                    stage.setScene(scene);
                    stage.setFullScreen(true);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                nameLabel.setText("Employee not found");
            }
        });
    }

    private void initializeClearButton() {
        clearButton.setOnAction(x -> userPIN = "");
    }

    private void initializeZeroButton() {
        zeroButton.setOnAction(x -> userPIN += "0");
    }

    private void initializeOneButton() {
        oneButton.setOnAction(x -> userPIN += "1");
    }

    private void initializeTwoButton() {
        twoButton.setOnAction(x -> userPIN += "2");
    }

    private void initializeThreeButton() {
        threeButton.setOnAction(x -> userPIN += "3");
    }

    private void initializeFourButton() {
        fourButton.setOnAction(x -> userPIN += "4");
    }

    private void initializeFiveButton() {
        fiveButton.setOnAction(x -> userPIN += "5");
    }

    private void initializeSixButton() {
        sixButton.setOnAction(x -> userPIN += "6");
    }

    private void initializeSevenButton() {
        sevenButton.setOnAction(x -> userPIN += "7");
    }

    private void initializeEightButton() {
        eightButton.setOnAction(x -> userPIN += "8");
    }

    private void initializeNineButton() {
        nineButton.setOnAction(x -> userPIN += "9");
    }


}
