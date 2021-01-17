package sample.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.dto.MenuItemDto;
import sample.factory.PopupFactory;
import sample.handler.MenuItemLoadedHandler;
import sample.rest.MenuItemRestClient;

import java.net.URL;
import java.util.ResourceBundle;

public class EditMenuItemController implements Initializable {

    private final MenuItemRestClient menuItemRestClient;
    private final PopupFactory popupFactory;

    private Long idMenuItem;

    @FXML
    private BorderPane editBorderPane;

    @FXML
    private Button editButton;

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

    @FXML
    private TextField descriptionTextField;

    public EditMenuItemController() {
        menuItemRestClient = new MenuItemRestClient();
        popupFactory = new PopupFactory();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCancelButton();
        initializeEditButton();
    }

    private void initializeEditButton() {
        editButton.setOnAction(x -> {
            Stage waitingPopup = popupFactory.createWaitingPopup("Connecting to the server...");
            waitingPopup.show();
            Thread thread = new Thread(()->{
                MenuItemDto dto = createMenuItemDto();
                menuItemRestClient.saveMenuItem(dto, ()-> Platform.runLater(()->{
                    waitingPopup.close();
                    Stage infoPopup =
                            popupFactory.createInfoPopup("Menu item has been updated.", () -> getStage().close());
                    infoPopup.show();
                }));
            });
            thread.start();
        });
    }

    private MenuItemDto createMenuItemDto() {
        String name = nameTextField.getText();
        Double price = Double.valueOf(priceTextField.getText());
        Integer kcal = Integer.valueOf(kcalTextField.getText());
        String type = typeTextField.getText();
        String descrition = descriptionTextField.getText();
        MenuItemDto dto = MenuItemDto.of(name, price, kcal, type, descrition);
        dto.setIdMenuItem(idMenuItem);
        return dto;
    }

    public void loadMenuItemData(Long idMenuItem, MenuItemLoadedHandler handler) {
        Thread thread = new Thread(() -> {
            MenuItemDto dto = menuItemRestClient.getMenuItem(idMenuItem);
            Platform.runLater(() -> {
                this.idMenuItem = idMenuItem;
                nameTextField.setText(dto.getName());
                priceTextField.setText(Double.toString(dto.getPrice()));
                kcalTextField.setText(Integer.toString(dto.getKcal()));
                typeTextField.setText(dto.getType());
                descriptionTextField.setText(dto.getDescription());
                handler.handle();
            });
        });
        thread.start();
    }

    private void initializeCancelButton() {
        cancelButton.setOnAction(x -> getStage().close());
    }

    private Stage getStage() {
        return (Stage) editBorderPane.getScene().getWindow();
    }
}
