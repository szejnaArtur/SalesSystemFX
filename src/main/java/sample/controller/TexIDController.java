package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.factory.PopupFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TexIDController implements Initializable {

    private final PopupFactory popupFactory;

    private String amount = "";

    @FXML
    private AnchorPane texIDAnchorPane;

    @FXML
    private TextField texIDTextField;

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

    public TexIDController() {
        popupFactory = new PopupFactory();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeClearButton();
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
        initializeCancelButton();
        initializeOkButton();
    }

    private void initializeOkButton() {
        okButton.setOnAction(x->{
            if(amount.length() == 10){
                Long texID = Long.parseLong(amount);
                StartController.bill.setTexID(texID);
                getStage().close();
            } else {
                Stage infoPopup = popupFactory.createInfoPopup("Not valid format.");
                infoPopup.show();
            }
        });
    }

    private void initializeCancelButton() {
        cancelButton.setOnAction(x -> getStage().close());
    }

    private Stage getStage() {
        return (Stage) texIDAnchorPane.getScene().getWindow();
    }

    private void initializeClearButton() {
        clearButton.setOnAction(x -> {
            amount = "";
            texIDTextField.setText("");
        });
    }


    private void initializeNineButton() {
        nineButton.setOnAction(x -> {
            amount = amount + "9";
            texIDTextField.setText(amount);
        });
    }


    private void initializeEightButton() {
        eightButton.setOnAction(x -> {
            amount = amount + "8";
            texIDTextField.setText(amount);
        });
    }


    private void initializeSevenButton() {
        sevenButton.setOnAction(x -> {
            amount = amount + "7";
            texIDTextField.setText(amount);
        });
    }

    private void initializeSixButton() {
        sixButton.setOnAction(x -> {
            amount = amount + "6";
            texIDTextField.setText(amount);
        });
    }

    private void initializeFiveButton() {
        fiveButton.setOnAction(x -> {
            amount = amount + "5";
            texIDTextField.setText(amount);
        });
    }

    private void initializeFourButton() {
        fourButton.setOnAction(x -> {
            amount = amount + "4";
            texIDTextField.setText(amount);
        });
    }

    private void initializeThreeButton() {
        threeButton.setOnAction(x -> {
            amount = amount + "3";
            texIDTextField.setText(amount);
        });
    }

    private void initializeTwoButton() {
        twoButton.setOnAction(x -> {
            amount = amount + "2";
            texIDTextField.setText(amount);
        });
    }

    private void initializeOneButton() {
        oneButton.setOnAction(x -> {
            amount = amount + "1";
            texIDTextField.setText(amount);
        });
    }

    private void initializeZeroButton() {
        zeroButton.setOnAction(x -> {
            amount = amount + "0";
            texIDTextField.setText(amount);
        });
    }
}
