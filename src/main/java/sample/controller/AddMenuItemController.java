package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddMenuItemController implements Initializable {

    @FXML
    private BorderPane addBorderPane;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField kcalTextField;

    @FXML
    private TextField typeTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCancelButton();
    }

    private void initializeCancelButton() {
        cancelButton.setOnAction(x -> getStage().close());
    }

    private Stage getStage() {
        return (Stage) addBorderPane.getScene().getWindow();
    }
}
