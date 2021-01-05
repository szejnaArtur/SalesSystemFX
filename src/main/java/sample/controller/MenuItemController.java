package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.dto.MenuItemDto;
import sample.rest.MenuItemRestClient;
import sample.table.MenuItemTableModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MenuItemController implements Initializable {

    private final MenuItemRestClient menuItemRestClient;

    @FXML
    private TableView<MenuItemTableModel> menuItemTableView;

    public MenuItemController() {
        this.menuItemRestClient = new MenuItemRestClient();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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