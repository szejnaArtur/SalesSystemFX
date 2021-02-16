package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sample.dto.MenuItemDTO;
import sample.dto.MenuItemTypeDTO;
import sample.dto.OrderBillDTO;
import sample.dto.OrderItemDTO;
import sample.rest.MenuItemRestClient;
import sample.rest.MenuItemTypeRestClient;
import sample.rest.OrderRestClient;
import sample.table.OrderTableModel;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class AppController implements Initializable {

    private final static String MENUITEM_FXML = "/fxml/menuItem.fxml";

    private final MenuItemTypeRestClient menuItemTypeRestClient;
    private final MenuItemRestClient menuItemRestClient;
    private final OrderRestClient orderRestClient;

    private final ObservableList<OrderTableModel> data;

    private final OrderBillDTO order;

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
    private TableView<OrderTableModel> orderTableView;

    @FXML
    private TabPane menuItemTabPane;

    @FXML
    private Button removeButton;

    @FXML
    private Button settlementButton;

    @FXML
    private Label totalLabel;

    public AppController() {
        this.menuItemTypeRestClient = new MenuItemTypeRestClient();
        this.menuItemRestClient = new MenuItemRestClient();
        this.data = FXCollections.observableArrayList();
        this.order = new OrderBillDTO();
        this.orderRestClient = new OrderRestClient();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadView();
        initializeMenuItemTabPane();
        initializeOrderTableView();
        initializeRemoveButton();
        initializesettlementButton();

    }


    private void initializesettlementButton() {
        //TODO not finished yet
        settlementButton.setOnAction(x -> {
            order.setOrderDate(LocalDateTime.now());
            Thread thread = new Thread(()-> orderRestClient.saveOrderBill(order));
            thread.start();
        });
    }

    private void initializeRemoveButton() {
        removeButton.setOnAction(x -> {
            OrderTableModel selectedOrderItem = orderTableView.getSelectionModel().getSelectedItem();
            if (selectedOrderItem != null) {
                OrderItemDTO orderItem = order.findOrderItemByName(selectedOrderItem.getItem());
                order.removeOrderItem(orderItem);
                loadMenuOrderData();
                totalLabel.setText(String.format("Total: %.2f PLN", getTotalPrice()));
            }
        });
    }

    private void initializeMenuItemTabPane() {
        List<MenuItemTypeDTO> menuItemTypes = menuItemTypeRestClient.getMenuItemTypes();
        List<MenuItemDTO> menuItems = menuItemRestClient.getMenuItems();
        Map<String, List<MenuItemDTO>> menu = new HashMap<>();
        for (MenuItemTypeDTO type : menuItemTypes) {
            menu.put(type.getName(), new ArrayList<>());
        }

        for (MenuItemDTO menuItem : menuItems) {
            menu.get(menuItem.getType().getName()).add(menuItem);
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
        button.setStyle(getStyle());
        button.setOnMouseEntered(x -> button.setStyle(getHoverStyle()));
        button.setOnMouseExited(x -> button.setStyle(getStyle()));
        button.setOnMousePressed(x -> button.setStyle(getPressedStyle()));
        button.setOnMouseClicked(x -> button.setStyle(getHoverStyle()));
        button.setOnAction(x -> {
            if (order.isOrderNull()) {
                OrderItemDTO orderItemDTO = new OrderItemDTO(item);
                order.addOrderItemToOrder(orderItemDTO);
            } else {
                if (order.isOrderItem(item)) {
                    for (OrderItemDTO orderItemDTO : order.getOrderItems()) {
                        if (orderItemDTO.getMenuItemDTO().getName().equals(item.getName())) {
                            orderItemDTO.increaseTheQuantity();
                        }
                    }
                } else {
                    OrderItemDTO orderItemDTO = new OrderItemDTO(item);
                    order.addOrderItemToOrder(orderItemDTO);
                }
            }

            loadMenuOrderData();
            totalLabel.setText(String.format("Total: %.2f PLN", getTotalPrice()));
        });
        return button;
    }

    private void initializeOrderTableView() {
        orderTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setMinWidth(300);
        nameColumn.setCellValueFactory(new PropertyValueFactory<OrderTableModel, String>("item"));

        TableColumn quantityColumn = new TableColumn("Quantity");
        quantityColumn.setMinWidth(100);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<OrderTableModel, Integer>("quantity"));

        TableColumn priceColumn = new TableColumn("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<OrderTableModel, Double>("price"));

        TableColumn totalColumn = new TableColumn("Total");
        totalColumn.setMinWidth(100);
        totalColumn.setCellValueFactory(new PropertyValueFactory<OrderTableModel, Double>("total"));

        orderTableView.getColumns().addAll(nameColumn, quantityColumn, priceColumn, totalColumn);

        loadMenuOrderData();

        orderTableView.setItems(data);
    }

    private void loadMenuOrderData() {
        Thread thread = new Thread(() -> {
            List<OrderItemDTO> orderItem = order.getOrderItems();
            data.clear();
            data.addAll(orderItem.stream().map(OrderTableModel::of).collect(Collectors.toList()));
        });
        thread.start();
    }

    private void loadView() {
        try {
            BorderPane borderPane = FXMLLoader.load(getClass().getResource(MENUITEM_FXML));
            menuPane.getChildren().add(borderPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Double getTotalPrice() {
        double total = 0.0;
        for (OrderItemDTO orderItem : order.getOrderItems()) {
            total += orderItem.getQuantity() * orderItem.getMenuItemDTO().getPrice();
        }
        return total;
    }

    private String getStyle() {
        return ".light-green-button {\n" +
                "-fx-text-fill: black;\n" +
                "-fx-background-color: #37D126;\n" +
                "-fx-border-color: #808080;\n" +
                "-fx-background-radius: 0;\n" +
                "-fx-font-size: 25px;}";
    }

    private String getHoverStyle() {
        return ".light-green-button {\n" +
                "-fx-text-fill: black;\n" +
                "-fx-background-color: #48e237;\n" +
                "-fx-border-color: #808080;\n" +
                "-fx-background-radius: 0;\n" +
                "-fx-font-size: 25px;}";
    }

    private String getPressedStyle() {
        return ".light-green-button {\n" +
                "-fx-text-fill: black;\n" +
                "-fx-background-color: #59f348;\n" +
                "-fx-border-color: #808080;\n" +
                "-fx-background-radius: 0;\n" +
                "-fx-font-size: 25px;}";
    }

    private Stage getStage() {
        return (Stage) menuPane.getScene().getWindow();
    }
}
