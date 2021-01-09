package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.dto.MenuItemDto;
import sample.factory.PopupFactory;
import sample.rest.MenuItemRestClient;
import sample.table.MenuItemTableModel;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MenuItemController implements Initializable {

    private static final String ADD_MENUITEM_FXML = "/fxml/add-menuItem.fxml";

    private final MenuItemRestClient menuItemRestClient;

    @FXML
    private Button addButton;

    @FXML
    private Button viewButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<MenuItemTableModel> menuItemTableView;

    public MenuItemController() {
        this.menuItemRestClient = new MenuItemRestClient();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeAddButton();
        initializeMenuItemTableView();
    }

    private void initializeAddButton() {
        addButton.setOnAction((x)->{
            Stage addMenuItemStage = new Stage();
            addMenuItemStage.initStyle(StageStyle.UNDECORATED);
            addMenuItemStage.initModality(Modality.APPLICATION_MODAL);
            try {
                Parent addMenuItemParent = FXMLLoader.load(getClass().getResource(ADD_MENUITEM_FXML));
                Scene scene = new Scene(addMenuItemParent, 500, 450);
                addMenuItemStage.setScene(scene);
                addMenuItemStage.setFullScreen(true);
                addMenuItemStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initializeMenuItemTableView() {
        menuItemTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(300);
        nameColumn.setCellValueFactory(new PropertyValueFactory<MenuItemTableModel, String>("name"));

        TableColumn priceColumn = new TableColumn("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<MenuItemTableModel, Double>("price"));

        TableColumn kcalColumn = new TableColumn("Kcal");
        kcalColumn.setMinWidth(100);
        kcalColumn.setCellValueFactory(new PropertyValueFactory<MenuItemTableModel, Integer>("kcal"));

        TableColumn typeColumn = new TableColumn("Rodzaj");
        typeColumn.setMinWidth(200);
        typeColumn.setCellValueFactory(new PropertyValueFactory<MenuItemTableModel, String>("type"));

        menuItemTableView.getColumns().addAll(nameColumn, priceColumn, kcalColumn, typeColumn);

        ObservableList<MenuItemTableModel> data = FXCollections.observableArrayList();

        loadMenuItemData(data);

        menuItemTableView.setItems(data);
    }

    private void loadMenuItemData(ObservableList<MenuItemTableModel> data) {
        Thread thread = new Thread(() -> {
            List<MenuItemDto> menuItems = menuItemRestClient.getMenuItems();
            data.addAll(menuItems.stream().map(MenuItemTableModel::of).collect(Collectors.toList()));
        });
        thread.start();
    }
}