package sample.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.factory.PopupFactory;
import sample.rest.MenuItemRestClient;
import sample.table.MenuItemTableModel;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteMenuItemController implements Initializable {

    private final MenuItemRestClient menuItemRestClient;
    private final PopupFactory popupFactory;

    private Long idMenuItem;

    @FXML
    private BorderPane deleteMenuItemBorderPane;

    @FXML
    private Label nameLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private Button cancelButton;

    public DeleteMenuItemController() {
        menuItemRestClient = new MenuItemRestClient();
        popupFactory = new PopupFactory();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCancelButton();
        initializeDeleteButton();
    }

    private void initializeDeleteButton() {
        deleteButton.setOnAction(x -> {
            Stage waitingPopup = popupFactory.createWaitingPopup("Deleting menu item...");
            waitingPopup.show();
            Thread thread = new Thread(() -> menuItemRestClient.deleteMenuItem(idMenuItem, () -> Platform.runLater(()->{
                waitingPopup.close();
                Stage infoPopup = popupFactory.createInfoPopup("Menu item has been deleted.", () -> getStage().close());
                infoPopup.show();
            })));
            thread.start();
        });
    }

    public void loadMenuItemData(MenuItemTableModel menuItem) {
        this.idMenuItem = menuItem.getIdMenuItem();
        nameLabel.setText(menuItem.getName());
    }

    private void initializeCancelButton() {
        cancelButton.setOnAction(x -> getStage().close());
    }

    private Stage getStage() {
        return (Stage) deleteMenuItemBorderPane.getScene().getWindow();
    }
}
