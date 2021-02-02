package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sample.dto.MenuItemTypeDTO;
import sample.rest.MenuItemTypeRestClient;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    private final static String MENUITEM_FXML = "/fxml/menuItem.fxml";

    private final MenuItemTypeRestClient menuItemTypeRestClient;

    @FXML
    private Pane menuPane;

    @FXML
    private Button tableOneButton;

    @FXML
    private Button tableTwoButton;

    @FXML
    private Button tableThreeButton;

    @FXML
    private Button tableFourButton;

    @FXML
    private Button tableFiveButton;

    @FXML
    private Button tableSixButton;

    @FXML
    private Button tableSevenButton;

    @FXML
    private Button tableEightButton;

    @FXML
    private Button tableNineButton;

    @FXML
    private Button tableTenButton;

    @FXML
    private Button tableIElevenButton;

    @FXML
    private Button tableTwelveButton;

    @FXML
    private Button tableThirteenButton;

    @FXML
    private Button tableFouteenButton;

    @FXML
    private Button tableTakeawayButton;

    @FXML
    private TableView<?> orderTableView;

    @FXML
    private TabPane menuItemTabPane;

    public AppController() {
        menuItemTypeRestClient = new MenuItemTypeRestClient();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadView();
        initializeOneButton();
        initializeMenuItemTabPane();
    }

    private void initializeMenuItemTabPane() {
        List<MenuItemTypeDTO> menuItemTypes = menuItemTypeRestClient.getMenuItemTypes();
        for (MenuItemTypeDTO dto : menuItemTypes){
            Tab tab = new Tab(dto.getName());
            menuItemTabPane.getTabs().add(tab);
        }
    }

    private String orangeButtonStyle() {
        return ".orange-button{\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-background-color: #fabf1e;\n" +
                "    -fx-border-color: #003366;\n" +
                "    -fx-background-radius: 0;\n" +
                "    -fx-font-size: 25px;\n" +
                "}";
    }

    private void initializeOneButton() {
        tableOneButton.setOnAction(x-> tableOneButton.setStyle(orangeButtonStyle()));
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
