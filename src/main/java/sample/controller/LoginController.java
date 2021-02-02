package sample.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.dto.UserCredentialsDTO;
import sample.factory.PopupFactory;
import sample.rest.Authenticator;
import sample.rest.AuthenticatorImpl;
import sample.rest.MenuItemRestClient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private static final String START_PANEL_FXML = "/fxml/startPanel.fxml";
    private static final String APP_TITLE = "POS Restaurant System";

    private final PopupFactory popupFactory;
    private final Authenticator authenticator;
    private final MenuItemRestClient menuItemRestClient;

    @FXML
    private Button exitButton;

    @FXML
    private Button loginButton;

    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordTestField;

    public LoginController() {
        popupFactory = new PopupFactory();
        authenticator = new AuthenticatorImpl();
        menuItemRestClient = new MenuItemRestClient();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeExitButton();
        initializeLoginButton();
    }

    private void initializeLoginButton() {
        loginButton.setOnAction(x -> performAuthentication());
    }

    private void performAuthentication() {
        Stage waitingPopup = popupFactory.createWaitingPopup("Connecting to the server...");
        waitingPopup.show();

        String login = loginTextField.getText();
        String password = passwordTestField.getText();

        UserCredentialsDTO userDTO = UserCredentialsDTO.of(login, password);
        authenticator.authenticate(userDTO,
                (authenticationResult) -> Platform.runLater(() -> {
                    waitingPopup.close();
                    if (authenticationResult.isAuthenticated()) {
                        StartController.menuItemsDTO.addAll(menuItemRestClient.getMenuItems());
                        openStartPanelAndCloseLoginStage();
                    } else {
                        showIncorrectCredentialsMessage();
                    }
                }));
    }

    private void showIncorrectCredentialsMessage() {
        //TODO implement showIncorrectCredentialsMessage();
        System.out.println("Incorrect credentials");
    }

    private void openStartPanelAndCloseLoginStage() {
        try {
            Stage startPanelStage = new Stage();
            Parent startPanelRoot = FXMLLoader.load(getClass().getResource(START_PANEL_FXML));
            Scene scene = new Scene(startPanelRoot, 1920, 1000);
            startPanelStage.setTitle(APP_TITLE);
            startPanelStage.setFullScreen(true);
            startPanelStage.setScene(scene);
            startPanelStage.show();
            getStage().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeExitButton() {
        exitButton.setOnAction(x -> getStage().close());
    }

    private Stage getStage() {
        return (Stage) loginAnchorPane.getScene().getWindow();
    }
}
