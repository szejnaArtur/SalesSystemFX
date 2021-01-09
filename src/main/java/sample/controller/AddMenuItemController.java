package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.dto.MenuItemDto;
import sample.factory.PopupFactory;
import sample.rest.MenuItemRestClient;

import java.net.URL;
import java.util.ResourceBundle;

public class AddMenuItemController implements Initializable {

    private final PopupFactory popupFactory;
    private final MenuItemRestClient menuItemRestClient;

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

    public AddMenuItemController(){
        this.popupFactory = new PopupFactory();
        this.menuItemRestClient = new MenuItemRestClient();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCancelButton();
        initializeSaveButton();
    }

    private void initializeSaveButton() {
        saveButton.setOnAction(x -> {
            MenuItemDto menuItemDto = createMenuItemDto();
            Stage waitingPopup = popupFactory.createWaitingPopup("Connecting to the server...");
            waitingPopup.show();
            menuItemRestClient.saveMenuItem(menuItemDto, () ->{
                waitingPopup.close();

            });
        });
    }

    private MenuItemDto createMenuItemDto() {
        String name = nameTextField.getText();
        Double price = Double.valueOf(priceTextField.getText());
        Integer kcal = Integer.valueOf(kcalTextField.getText());
        String type = typeTextField.getText();
        return new MenuItemDto(name, price, kcal, type);
    }

    private void initializeCancelButton() {
        cancelButton.setOnAction(x -> getStage().close());
    }

    private Stage getStage() {
        return (Stage) addBorderPane.getScene().getWindow();
    }
}
