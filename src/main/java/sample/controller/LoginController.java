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
import sample.dto.EmployeeCredentialsDTO;
import sample.factory.PopupFactory;
import sample.rest.Authenticator;
import sample.rest.AuthenticatorImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private static final String APP_FXML = "/fxml/app.fxml";
    private static final String APP_TITLE = "Restaurant sales system";

    private final PopupFactory popupFactory;
    private final Authenticator authenticator;

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

        EmployeeCredentialsDTO userDTO = EmployeeCredentialsDTO.of(login, password);
        authenticator.authenticate(userDTO,
                (authenticationResult) -> Platform.runLater(()->{
                    waitingPopup.close();
                    if (authenticationResult.isAuthenticated()) {
                        openAppAndCloseLoginStage();
                    } else {
                        showIncorrectCredentialsMessage();
                    }
                }));
    }

    private void showIncorrectCredentialsMessage() {
        //TODO implement showIncorrectCredentialsMessage();
        System.out.println("Incorrect credentials");
    }

    private void openAppAndCloseLoginStage() {
        try {
            Stage appStage = new Stage();
            Parent appRoot = FXMLLoader.load(getClass().getResource(APP_FXML));
            Scene scene = new Scene(appRoot, 1920, 1080);
            appStage.setTitle(APP_TITLE);
            appStage.setScene(scene);
            appStage.show();
            getLoginStage().close();
        } catch (
                IOException e) {
            e.printStackTrace();
            System.out.println("3");
        }
    }

    private void initializeExitButton() {
        exitButton.setOnAction(x -> getLoginStage().close());
    }

    private Stage getLoginStage() {
        return (Stage) loginAnchorPane.getScene().getWindow();
    }
}
