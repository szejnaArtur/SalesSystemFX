package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.dto.BillDTO;
import sample.dto.MenuItemDTO;
import sample.dto.MenuItemTypeDTO;
import sample.dto.OrderItemDTO;
import sample.factory.PopupFactory;
import sample.rest.MenuItemRestClient;
import sample.rest.MenuItemTypeRestClient;
import sample.rest.OrderItemRestClient;
import sample.table.OrderTableModel;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class AppController implements Initializable {

    private static final String APP_TITLE = "POS Restaurant System";
    private final static String RESTAURANT_PANEL_FXML = "/fxml/restaurantPanel.fxml";
    private final static String MENUITEM_FXML = "/fxml/menuItem.fxml";
    private final static String SETTLEMENT_FXML = "/fxml/settlementPanel.fxml";

    private final MenuItemTypeRestClient menuItemTypeRestClient;
    private final MenuItemRestClient menuItemRestClient;

    private final ObservableList<OrderTableModel> data;
    private final PopupFactory popupFactory;

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
        StartController.bill = new BillDTO();
        StartController.bill.setOrderDate(LocalDateTime.now());
        StartController.bill.setEmployeeDTO(StartController.employeeDTO);
        StartController.orderItemDTOList = new ArrayList<>();
        OrderItemRestClient orderItemRestClient = new OrderItemRestClient();
        this.popupFactory = new PopupFactory();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadView();
        initializeMenuItemTabPane();
        initializeOrderTableView();
        initializeRemoveButton();
        initializesettlementButton();
    }

    private void initializeRemoveButton() {
        removeButton.setOnAction(x -> {
            OrderTableModel selectedItem = orderTableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                OrderItemDTO orderItemDTO;
                for (OrderItemDTO dto : StartController.orderItemDTOList) {
                    if (dto.getMenuItemDTO().getName().equals(selectedItem.getItem())) {
                        StartController.orderItemDTOList.remove(dto);
                        loadMenuOrderData();
                        totalLabel.setText(String.format("Total: %.2f PLN", StartController.getTotalPrice()));
                        break;
                    }
                }
            }
        });
    }

    private void initializesettlementButton() {
        settlementButton.setOnAction(x -> {
            if (StartController.orderItemDTOList.size() > 0) {
                Stage settlementStage = new Stage();
                settlementStage.initStyle(StageStyle.UNDECORATED);
                settlementStage.initModality(Modality.APPLICATION_MODAL);
                try {
                    Parent addAGCRaportParent = FXMLLoader.load(getClass().getResource(SETTLEMENT_FXML));
                    Scene scene = new Scene(addAGCRaportParent, 1400, 1000);
                    settlementStage.setScene(scene);
                    settlementStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Stage infoPopup = popupFactory.createInfoPopup("The order is empty.");
                infoPopup.show();
            }

//                orderItemRestClient.saveOrderItems(orderItemDTOList);
//                openStartPanelAndCloseRestaurantPanel();
//                Stage infoPopup = popupFactory.createInfoPopup("The order has been registered.");
//                infoPopup.show();
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
            if (StartController.orderItemDTOList.size() == 0) {
                OrderItemDTO orderItemDTO = new OrderItemDTO(item);
                orderItemDTO.setBillDTO(StartController.bill);
                StartController.orderItemDTOList.add(orderItemDTO);
            } else {
                if (isOrderItem(item)) {
                    for (OrderItemDTO orderItemDTO : StartController.orderItemDTOList) {
                        if (orderItemDTO.getMenuItemDTO().getName().equals(item.getName())) {
                            orderItemDTO.increaseTheQuantity();
                        }
                    }
                } else {
                    OrderItemDTO orderItemDTO = new OrderItemDTO(item);
                    orderItemDTO.setBillDTO(StartController.bill);
                    StartController.orderItemDTOList.add(orderItemDTO);
                }
            }

            loadMenuOrderData();
            totalLabel.setText(String.format("Total: %.2f PLN", StartController.getTotalPrice()));
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
            data.clear();
            data.addAll(StartController.orderItemDTOList.stream().map(OrderTableModel::of).collect(Collectors.toList()));
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

    private Boolean isOrderItem(MenuItemDTO item) {
        for (OrderItemDTO dto : StartController.orderItemDTOList) {
            if (dto.getMenuItemDTO().getName().equals(item.getName())) {
                return true;
            }
        }
        return false;
    }

    private void openStartPanelAndCloseRestaurantPanel() {
        try {
            Stage startPanelStage = new Stage();
            Parent startPanelRoot = FXMLLoader.load(getClass().getResource(RESTAURANT_PANEL_FXML));
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
}
