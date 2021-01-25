package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    private final static String MENUITEM_FXML = "/fxml/menuItem.fxml";

    @FXML
    private Pane menuPane;

    @FXML
    private Button tableOne;

    @FXML
    private Button tableTwo;

    @FXML
    private Button tableThree;

    @FXML
    private Button tableFour;

    @FXML
    private Button tableFive;

    @FXML
    private Button tableSix;

    @FXML
    private Button tableSeven;

    @FXML
    private Button tableEight;

    @FXML
    private Button tableNine;

    @FXML
    private Button tableTen;

    @FXML
    private Button tableIEleven;

    @FXML
    private Button tableTwelve;

    @FXML
    private Button tableThirteen;

    @FXML
    private Button tableFouteen;

    @FXML
    private Button tableFiveteen;

    @FXML
    private TableView<?> orderTableView;

    public AppController() {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadView();
    }

    private void loadView() {
        try {
            BorderPane borderPane = FXMLLoader.load(getClass().getResource(MENUITEM_FXML));
            menuPane.getChildren().add(borderPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
