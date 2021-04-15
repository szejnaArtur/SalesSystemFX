package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.dto.BillDTO;
import sample.dto.EmployeeDTO;
import sample.rest.AverageGuestCheckRestClient;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class RestaurantPanelController implements Initializable {

    private static final String ORDER_PANEL_FXML = "/fxml/orderPanel.fxml";
    private static final String START_PANEL_FXML = "/fxml/startPanel.fxml";
    private static final String AGC_RAPORT_FXML = "/fxml/AGCRaport.fxml";

    @FXML
    private Pane restaurantPanelPane;

    @FXML
    private Button logoutButton;

    @FXML
    private Button orderButton;

    @FXML
    private TextField loggedTextField;

    @FXML
    private Button AGCButton;

    public RestaurantPanelController() {
        AverageGuestCheckRestClient averageGuestCheckRestClient = new AverageGuestCheckRestClient();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeOrderButton();
        initializeLogoutButton();
        initializeLoggedTextField();
        initializeAGCButton();
    }

    private void initializeAGCButton() {
        AGCButton.setOnAction(x -> {
            Stage agcRaportStage = new Stage();
            agcRaportStage.initModality(Modality.APPLICATION_MODAL);
            try {
                Parent addAGCRaportParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(AGC_RAPORT_FXML)));
                Scene scene = new Scene(addAGCRaportParent, 1280, 800);
                agcRaportStage.setScene(scene);
                agcRaportStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initializeLoggedTextField() {
        String firstName = StartController.employeeDTO.getFirstName();
        String lastName = StartController.employeeDTO.getLastName();
        if (firstName != null) {
            loggedTextField.setText(firstName + " " + lastName);
        }
    }

    private void initializeLogoutButton() {
        logoutButton.setOnAction(x -> {
            StartController.employeeDTO = new EmployeeDTO();
            openAppCloseStage(START_PANEL_FXML, getStage());
            getStage().close();
        });
    }

    private void initializeOrderButton() {
        orderButton.setOnAction(x -> {
            StartController.bill = new BillDTO();
            StartController.bill.setOrderDate(LocalDateTime.now());
            StartController.bill.setEmployeeDTO(StartController.employeeDTO);
            StartController.orderItemDTOList = new ArrayList<>();
            StartController.orderAddonDTOList = new ArrayList<>();
            openAppCloseStage(ORDER_PANEL_FXML, getStage());
        });
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
