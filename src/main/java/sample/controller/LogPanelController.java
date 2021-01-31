package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.dto.EmployeeDTO;
import sample.dto.WorkHoursDTO;
import sample.factory.PopupFactory;
import sample.rest.EmployeeRestClient;
import sample.rest.WorkHoursRestClient;

import java.net.URL;
import java.util.ResourceBundle;

public class LogPanelController implements Initializable {

    private static final int PASSWORD_LENGTH = 4;

    private final EmployeeRestClient employeeRestClient;
    private final WorkHoursRestClient workHoursRestClient;
    private final PopupFactory popupFactory;

    private String password = "";

    @FXML
    private AnchorPane logAnchorPane;

    @FXML
    private TextField loginTextField;

    @FXML
    private Button sevenButton;

    @FXML
    private Button eightButton;

    @FXML
    private Button nineButton;

    @FXML
    private Button fourButton;

    @FXML
    private Button fiveButton;

    @FXML
    private Button sixButton;

    @FXML
    private Button oneButton;

    @FXML
    private Button twoButton;

    @FXML
    private Button threeButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button zeroButton;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    public LogPanelController() {
        employeeRestClient = new EmployeeRestClient();
        popupFactory = new PopupFactory();
        workHoursRestClient = new WorkHoursRestClient();
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
        initializeCleanButton();
        initializeOkButton();
        initializeCancelButton();
    }

    private void initializeOkButton() {
        okButton.setOnAction(x -> {
            if (password.length() == PASSWORD_LENGTH) {
                EmployeeDTO employee = employeeRestClient.getEmployeeByPIN(password);
                if (employee.getFirstName() == null) {
                    Stage infoPopup = popupFactory.createInfoPopup("Employee is not exist.");
                    loginTextField.setText("");
                    password = "";
                    infoPopup.show();
                } else {
                    WorkHoursDTO lastWorkHours = workHoursRestClient.getLastWorkHours(employee.getIdEmployee());
                    if (lastWorkHours.getStartWork() != null && lastWorkHours.getEndWork() != null) {
                        Stage infoPopupWithTwoButtons = popupFactory.createInfoPopupWithTwoButtons("Do you want to start work " + employee.getFirstName() + "?", () -> {
                            workHoursRestClient.saveWorkHours(employee, () -> {
                                Stage infoPopup = popupFactory.createInfoPopup("You logged in to work.");
                                infoPopup.show();
                            });
                            getStage().close();
                        }, () -> getStage().close());
                        loginTextField.setText("");
                        password = "";
                        infoPopupWithTwoButtons.show();
                    } else {
                        Stage infoPopupWithTwoButtons;
                        if(lastWorkHours.getStartWork() != null && lastWorkHours.getEndWork() == null) {
                            infoPopupWithTwoButtons = popupFactory.createInfoPopupWithTwoButtons("Do you want to quit your job " + employee.getFirstName() + "?", () -> {
                                workHoursRestClient.saveWorkHours(employee, () -> {
                                    Stage infoPopup = popupFactory.createInfoPopup("You have logged out of work.");
                                    infoPopup.show();
                                });
                                getStage().close();
                            }, () -> getStage().close());
                        } else {
                            infoPopupWithTwoButtons = popupFactory.createInfoPopupWithTwoButtons("Do you want to start work " + employee.getFirstName() + "?", () -> {
                                workHoursRestClient.saveWorkHours(employee, () -> {
                                    Stage infoPopup = popupFactory.createInfoPopup("You logged in to work.");
                                    infoPopup.show();
                                });
                                getStage().close();
                            }, () -> getStage().close());
                        }
                        loginTextField.setText("");
                        password = "";
                        infoPopupWithTwoButtons.show();
                    }
                }
            } else {
                Stage infoPopup = popupFactory.createInfoPopup("Invalid data formats.", () -> {
                });
                loginTextField.setText("");
                password = "";
                infoPopup.show();
            }
        });
    }

    private void initializeCleanButton() {
        clearButton.setOnAction(x -> {
            password = "";
            loginTextField.setText("");
        });
    }

    private void initializeCancelButton() {
        cancelButton.setOnAction(x -> getStage().close());
    }

    private void initializeNineButton() {
        nineButton.setOnAction(x -> {
            password += "9";
            loginTextField.setText(password);
        });
    }

    private void initializeEightButton() {
        eightButton.setOnAction(x -> {
            password += "8";
            loginTextField.setText(password);
        });
    }

    private void initializeSevenButton() {
        sevenButton.setOnAction(x -> {
            password += "7";
            loginTextField.setText(password);
        });
    }

    private void initializeSixButton() {
        sixButton.setOnAction(x -> {
            password += "6";
            loginTextField.setText(password);
        });
    }

    private void initializeFiveButton() {
        fiveButton.setOnAction(x -> {
            password += "5";
            loginTextField.setText(password);
        });
    }

    private void initializeFourButton() {
        fourButton.setOnAction(x -> {
            password += "4";
            loginTextField.setText(password);
        });
    }

    private void initializeThreeButton() {
        threeButton.setOnAction(x -> {
            password += "3";
            loginTextField.setText(password);
        });
    }

    private void initializeTwoButton() {
        twoButton.setOnAction(x -> {
            password += "2";
            loginTextField.setText(password);
        });
    }

    private void initializeOneButton() {
        oneButton.setOnAction(x -> {
            password += "1";
            loginTextField.setText(password);
        });
    }

    private void initializeZeroButton() {
        zeroButton.setOnAction(x -> {
            password += "0";
            loginTextField.setText(password);
        });
    }

    private Stage getStage() {
        return (Stage) logAnchorPane.getScene().getWindow();
    }

}
