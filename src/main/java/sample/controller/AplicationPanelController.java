package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AplicationPanelController implements Initializable {

    private static final String START_PANEL_FXML = "/fxml/startPanel.fxml";
    private static final String RESTAURANT_PANEL_FXML = "/fxml/restaurantPanel.fxml";
    private static final String APP_TITLE = "POS Restaurant System";

    @FXML
    private Pane aplicationPanelPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadStartPanel();
    }

    private void loadStartPanel() {
        try {
            Pane pane = FXMLLoader.load(getClass().getResource(START_PANEL_FXML));
            getAplicationPanelPane().getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadRestaurantPanel(){
        try {
            Pane pane = FXMLLoader.load(getClass().getResource(RESTAURANT_PANEL_FXML));
            getAplicationPanelPane().getChildren().clear();
            getAplicationPanelPane().getChildren().add(pane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public Pane getAplicationPanelPane(){
        return aplicationPanelPane;
    }
}
