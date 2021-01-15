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
    private static final String VIEW_MENUITEM_FXML = "/fxml/view-menuItem.fxml";

    private final MenuItemRestClient menuItemRestClient;
    private final PopupFactory popupFactory;

    private final ObservableList<MenuItemTableModel> data;

    @FXML
    private Button addButton;

    @FXML
    private Button viewButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button refreshButton;

    @FXML
    private TableView<MenuItemTableModel> menuItemTableView;

    public MenuItemController() {
        this.menuItemRestClient = new MenuItemRestClient();
        this.data = FXCollections.observableArrayList();
        this.popupFactory = new PopupFactory();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeAddButton();
        initializeViewButton();
        initializeMenuItemTableView();
        initializeRefreshButton();
    }

    private void initializeViewButton() {
        viewButton.setOnAction(x -> {
            MenuItemTableModel menuItem = menuItemTableView.getSelectionModel().getSelectedItem();
            if (menuItem != null) {
                try {
                    Stage waitingPopup = popupFactory.createWaitingPopup("Loading menu item data...");
                    waitingPopup.show();
                    Stage viewMenuItemStage = new Stage();
                    viewMenuItemStage.initStyle(StageStyle.UNDECORATED);
                    viewMenuItemStage.initModality(Modality.APPLICATION_MODAL);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_MENUITEM_FXML));
                    Scene scene = new Scene(loader.load(), 1200, 900);
                    viewMenuItemStage.setScene(scene);

                    ViewMenuItemController controller = loader.getController();
                    controller.loadMenuItemData(menuItem.getIdMenuItem(), () -> {
                        waitingPopup.close();
                        viewMenuItemStage.show();
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initializeRefreshButton() {
        refreshButton.setOnAction(x -> loadMenuItemData());
    }

    private void initializeAddButton() {
        addButton.setOnAction((x) -> {
            Stage addMenuItemStage = new Stage();
            addMenuItemStage.initStyle(StageStyle.UNDECORATED);
            addMenuItemStage.initModality(Modality.APPLICATION_MODAL);
            try {
                Parent addMenuItemParent = FXMLLoader.load(getClass().getResource(ADD_MENUITEM_FXML));
                Scene scene = new Scene(addMenuItemParent, 1200, 900);
                addMenuItemStage.setScene(scene);
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

        loadMenuItemData();

        menuItemTableView.setItems(data);
    }

    private void loadMenuItemData() {
        Thread thread = new Thread(() -> {
            List<MenuItemDto> menuItems = menuItemRestClient.getMenuItems();
            data.clear();
            data.addAll(menuItems.stream().map(MenuItemTableModel::of).collect(Collectors.toList()));
        });
        thread.start();
    }
}