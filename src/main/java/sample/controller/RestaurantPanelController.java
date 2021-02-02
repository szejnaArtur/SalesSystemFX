package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.dto.EmployeeDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RestaurantPanelController implements Initializable {

    private static final String APP_FXML = "/fxml/app.fxml";
    private static final String STARTPANEL_FXML = "/fxml/startPanel.fxml";

    @FXML
    private Pane restaurantPanelPane;

    @FXML
    private Button logoutButton;

    @FXML
    private Button orderButton;

    @FXML
    private TextField loggedTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeOrderButton();
        initializeLogoutButton();
        initializeLoggedTextField();
    }

    private void initializeLoggedTextField() {
        String firstName = StartController.employeeDTO.getFirstName();
        String lastName = StartController.employeeDTO.getLastName();
        if(firstName != null){
            loggedTextField.setText(firstName + " " + lastName);
        }
    }

    private void initializeLogoutButton() {
        logoutButton.setOnAction(x -> {
            StartController.employeeDTO = new EmployeeDTO();
            openAppCloseStage(STARTPANEL_FXML, getStage());
            getStage().close();
        });
    }

    private void initializeOrderButton() {
        orderButton.setOnAction(x -> openAppCloseStage(APP_FXML, getStage()));
    }

    private void openAppCloseStage(String FXML_URL, Stage closeStage) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_URL));
            Scene scene = new Scene(loader.load(), 1920, 1020);
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
            closeStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Stage getStage() {
        return (Stage) restaurantPanelPane.getScene().getWindow();
    }
}
