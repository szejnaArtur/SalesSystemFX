package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.dto.EmployeeDTO;
import sample.dto.WorkHoursDTO;
import sample.factory.PopupFactory;
import sample.rest.EmployeeRestClient;
import sample.rest.WorkHoursRestClient;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class StartPanelController implements Initializable {

    private static final String RESTAURANT_PANEL_FXML = "/fxml/restaurantPanel.fxml";
    private static final String LOG_PANEL_FXML = "/fxml/logPanel.fxml";
    private static final String EMPLOYEE_PANEL_FXML = "/fxml/employeesAtWorkPanel.fxml";
    private static final String APP_TITLE = "POS Restaurant System";

    private final EmployeeRestClient employeeRestClient;
    private final WorkHoursRestClient workHoursRestClient;
    private final PopupFactory popupFactory;

    private String userPIN = "";

    @FXML
    private Pane startPanelPane;

    @FXML
    private Label PINLabel;

    @FXML
    private Button timeButton;

    @FXML
    private Button RVCButton;

    @FXML
    private Button availableButton;

    @FXML
    private Button logButton;

    @FXML
    private Button employeeAtWorkButton;

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
        this.popupFactory = new PopupFactory();
        this.workHoursRestClient = new WorkHoursRestClient();
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
        initializeLogButton();
        initializeEmployeeAtWorkButton();
    }

    private void initializeEmployeeAtWorkButton() {
        employeeAtWorkButton.setOnAction(x -> openEmployeeAtWorkPanel());
    }

    private void initializeLogButton() {
        logButton.setOnAction(x -> openLogPanel());
    }

    private void initializeTimeButton() {
        timeButton.setOnAction(x -> {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());

            mainTextArea.setText(formatter.format(date));
        });
    }

    private void initializeOkButton() {
        okButton.setOnAction(x -> employeeAuthorization());
    }

    private void employeeAuthorization() {
        if (logDataValidation()) {
            EmployeeDTO employee = employeeRestClient.getEmployeeByPIN(userPIN);
            if (employee.isAuthenticated()) {
                WorkHoursDTO lastWorkHours = workHoursRestClient.getLastWorkHours(employee.getIdEmployee());
                if (lastWorkHours.getStartWork() != null && lastWorkHours.getEndWork() == null) {
                    StartController.employeeDTO = employee;
                    openRestaurantPanelAndCloseStartPanel();
                } else {
                    popupFactory.createInfoPopup("The employee is out of work now.").show();
                    resetUserPIN();
                }
            } else {
                popupFactory.createInfoPopup("Employee is not exist.").show();
                resetUserPIN();
            }
        } else {
            popupFactory.createInfoPopup("Invalid data formats.").show();
            resetUserPIN();
        }
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

    private Stage getStage() {
        return (Stage) startPanelPane.getScene().getWindow();
    }

    private Boolean logDataValidation() {
        if (userPIN.equals("")) return false;
        return userPIN.length() == 4;
    }

    private void resetUserPIN() {
        userPIN = "";
    }

    private void openRestaurantPanelAndCloseStartPanel() {
        try {
            Stage restaurantPanelStage = new Stage();
            Parent startPanelRoot = FXMLLoader.load(getClass().getResource(RESTAURANT_PANEL_FXML));
            Scene scene = new Scene(startPanelRoot, 1920, 1000);
            restaurantPanelStage.setTitle(APP_TITLE);
            restaurantPanelStage.setFullScreen(true);
            restaurantPanelStage.setScene(scene);
            restaurantPanelStage.show();
            getStage().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openLogPanel() {
        try {
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(StartPanelController.LOG_PANEL_FXML));
            Scene scene = new Scene(loader.load(), 550, 800);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openEmployeeAtWorkPanel() {
        try {
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(StartPanelController.EMPLOYEE_PANEL_FXML));
            Scene scene = new Scene(loader.load(), 1000, 700);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
