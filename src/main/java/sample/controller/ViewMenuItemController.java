package sample.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.dto.MenuItemDto;
import sample.handler.MenuItemLoadedHandler;
import sample.rest.MenuItemRestClient;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewMenuItemController implements Initializable {

    private final MenuItemRestClient menuItemRestClient;

    @FXML
    private BorderPane viewBorderPane;

    @FXML
    private Button okButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField kcalTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField descriptionTextField;

    public ViewMenuItemController() {
        this.menuItemRestClient = new MenuItemRestClient();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameTextField.setEditable(false);
        priceTextField.setEditable(false);
        kcalTextField.setEditable(false);
        typeTextField.setEditable(false);
        descriptionTextField.setEditable(false);
        initializeOkButton();
    }

    public void loadMenuItemData(Long idMenuItem, MenuItemLoadedHandler handler) {
        Thread thread = new Thread(() -> {
            MenuItemDto dto = menuItemRestClient.getMenuItem(idMenuItem);
            Platform.runLater(() -> {
                nameTextField.setText(dto.getName());
                priceTextField.setText(Double.toString(dto.getPrice()));
                kcalTextField.setText(Integer.toString(dto.getKcal()));
                typeTextField.setText(dto.getType());
                descriptionTextField.setText(dto.getDescription());
            });
        });
        thread.start();
    }

    private void initializeOkButton() {
        okButton.setOnAction(x -> getStage().close());
    }

    private Stage getStage() {
        return (Stage) viewBorderPane.getScene().getWindow();
    }
}
