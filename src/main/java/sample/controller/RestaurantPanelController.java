package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RestaurantPanelController implements Initializable {

    private static final String APP_FXML = "/fxml/app.fxml";

    @FXML
    private Pane restaurantPanelPane;

    @FXML
    private Button orderButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeOrderButton();
    }

    private void initializeOrderButton() {
        orderButton.setOnAction(x -> openAppAndCloseRestaurantPanel());
    }

    private void openAppAndCloseRestaurantPanel(){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(APP_FXML));
            Scene scene = new Scene(loader.load(), 1920, 1020);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
            getStage().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Stage getStage() {
        return (Stage) restaurantPanelPane.getScene().getWindow();
    }
}
