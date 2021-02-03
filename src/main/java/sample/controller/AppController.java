package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import sample.dto.MenuItemDTO;
import sample.dto.MenuItemTypeDTO;
import sample.rest.MenuItemRestClient;
import sample.rest.MenuItemTypeRestClient;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AppController implements Initializable {

    private final static String MENUITEM_FXML = "/fxml/menuItem.fxml";

    private final MenuItemTypeRestClient menuItemTypeRestClient;
    private final MenuItemRestClient menuItemRestClient;

    Map<String, Integer> order = new HashMap<>();

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
        menuItemRestClient = new MenuItemRestClient();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadView();
        initializeMenuItemTabPane();
    }

    private void initializeMenuItemTabPane() {
        List<MenuItemTypeDTO> menuItemTypes = menuItemTypeRestClient.getMenuItemTypes();
        List<MenuItemDTO> menuItems = menuItemRestClient.getMenuItems();
        Map<String, List<MenuItemDTO>> menu = new HashMap<>();
        for (MenuItemTypeDTO type : menuItemTypes) {
            menu.put(type.getName(), new ArrayList<>());
        }

        for (MenuItemDTO menuItem : menuItems) {
            menu.get(menuItem.getType()).add(menuItem);
        }

        for (MenuItemTypeDTO type : menuItemTypes) {
            Tab tab = new Tab(type.getName());
            AnchorPane anchorPane = new AnchorPane();
            GridPane gridPane = new GridPane();

            int y = 0;
            int x = 0;
            for (MenuItemDTO item : menu.get(type.getName())) {
                if (x == 7) {
                    y++;
                    x = 0;
                }
                Button button = getButton(item);
                gridPane.add(button, x++, y);
            }
            anchorPane.getChildren().add(gridPane);
            tab.setContent(anchorPane);
            menuItemTabPane.getTabs().add(tab);
        }
    }

    private Button getButton(MenuItemDTO item) {
        Button button = new Button(item.getName() + "\n" + item.getPrice());
        button.setPrefSize(275, 128);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setFont(Font.font(18));
        button.setOnAction(x -> {

        });
        return button;
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
