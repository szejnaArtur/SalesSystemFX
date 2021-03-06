package sample.controller;

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
import sample.dto.*;
import sample.factory.PopupFactory;
import sample.rest.MenuItemRestClient;
import sample.rest.MenuItemTypeRestClient;
import sample.rest.OrderItemRestClient;
import sample.table.OrderTableModel;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class OrderController implements Initializable {

    private final static String MENUITEM_FXML = "/fxml/menuItem.fxml";
    private final static String SETTLEMENT_FXML = "/fxml/settlementPanel.fxml";

    private final MenuItemTypeRestClient menuItemTypeRestClient;
    private final MenuItemRestClient menuItemRestClient;

    private final PopupFactory popupFactory;

    @FXML
    private Pane menuPane;

    @FXML
    private GridPane addonGridPane;

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

    public OrderController() {
        this.menuItemTypeRestClient = new MenuItemTypeRestClient();
        this.menuItemRestClient = new MenuItemRestClient();
        OrderItemRestClient orderItemRestClient = new OrderItemRestClient();
        this.popupFactory = new PopupFactory();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadView();
        addonGridPane.setVgap(5);
        addonGridPane.setHgap(5);
        initializeMenuItemTabPane();
        initializeOrderTableView();
        initializeRemoveButton();
        initializeSettlementButton();
        initializeAddonsGridPane();
    }

    private void initializeAddonsGridPane() {
        orderTableView.setOnMousePressed(action -> {
            OrderTableModel selectedItem = orderTableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                for (MenuItemDTO dto : StartController.menuItemsDTO) {
                    if (dto.getName().equals(selectedItem.getItem())) {
                        addonGridPane.getChildren().clear();
                        int y = 0;
                        int x = 0;
                        for (AddonDTO addon : dto.getAddons()) {
                            if (x == 5) {
                                y++;
                                x = 0;
                            }
                            Button button = getButton(addon);
                            addonGridPane.add(button, x++, y);
                        }
                        break;
                    }
                }
            } else {
                addonGridPane.getChildren().clear();
            }
        });
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

    private void initializeSettlementButton() {
        settlementButton.setOnAction(x -> {
            if (StartController.orderItemDTOList.size() > 0) {
                Stage settlementStage = new Stage();
                settlementStage.initStyle(StageStyle.UNDECORATED);
                settlementStage.initModality(Modality.APPLICATION_MODAL);
                try {
                    Parent addAGCRaportParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(SETTLEMENT_FXML)));
                    Scene scene = new Scene(addAGCRaportParent, 1920, 1080);
                    settlementStage.setScene(scene);
                    settlementStage.show();
                    getStage().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Stage infoPopup = popupFactory.createInfoPopup("The order is empty.");
                infoPopup.show();
            }
        });
    }

    private void initializeMenuItemTabPane() {
        menuItemTabPane.setTabMinHeight(80);
        menuItemTabPane.setTabMinWidth(150);
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

    private Button getButton(AddonDTO addon) {
        Button button = new Button(addon.getName() + "\n" + addon.getPrice());
        button.setPrefSize(275, 128);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setFont(Font.font(10));
        button.setStyle(getStyle());
        button.setOnMouseEntered(x -> button.setStyle(getHoverStyle()));
        button.setOnMouseExited(x -> button.setStyle(getStyle()));
        button.setOnMousePressed(x -> button.setStyle(getPressedStyle()));
        button.setOnMouseClicked(x -> button.setStyle(getHoverStyle()));

        button.setOnAction(x -> {
            OrderTableModel selectedItem = orderTableView.getSelectionModel().getSelectedItem();
            int selectedIndex = orderTableView.getSelectionModel().getSelectedIndex();

            for (OrderItemDTO orderItemDTO : StartController.orderItemDTOList) {
                if (orderItemDTO.getMenuItemDTO().getName().equals(selectedItem.getItem())) {
                    if (StartController.orderAddonDTOList.size() == 0) {
                        StartController.orderAddonDTOList.add(new OrderAddonDTO(addon, orderItemDTO));
                    } else {
                        if (isOrderAddon(addon, orderItemDTO)){
                            for (OrderAddonDTO orderAddonDTO : StartController.orderAddonDTOList) {
                                if (orderAddonDTO.getOrderItemDTO() == orderItemDTO) {
                                    if (addon.getName().equals(orderAddonDTO.getAddonDTO().getName())) {
                                        orderAddonDTO.increaseTheQuantity();
                                    }
                                }
                            }
                        } else {
                            StartController.orderAddonDTOList.add(new OrderAddonDTO(addon, orderItemDTO));
                        }
                    }
                    break;
                }
            }
            loadMenuOrderData();
            orderTableView.getSelectionModel().select(selectedIndex);
            totalLabel.setText(String.format("Total: %.2f PLN", StartController.getTotalPrice()));
        });
        return button;
    }

    private boolean isOrderAddon(AddonDTO addonDTO, OrderItemDTO orderItemDTO) {
        for (OrderAddonDTO orderAddon : StartController.orderAddonDTOList){
            if (orderAddon.getOrderItemDTO().getMenuItemDTO().getName().equals(orderItemDTO.getMenuItemDTO().getName())
            && orderAddon.getAddonDTO().getName().equals(addonDTO.getName())){
                return true;
            }
        }
        return false;
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

        TableColumn discountColumn = new TableColumn("Discount");
        discountColumn.setMinWidth(100);
        discountColumn.setCellValueFactory(new PropertyValueFactory<OrderTableModel, Double>("discount"));

        orderTableView.getColumns().addAll(nameColumn, quantityColumn, priceColumn, totalColumn, discountColumn);

        loadMenuOrderData();

        orderTableView.setItems(StartController.data);
    }

    private void loadMenuOrderData() {
        StartController.data.clear();
        List<OrderTableModel> orderTableModelList = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : StartController.orderItemDTOList) {
            orderTableModelList.add(OrderTableModel.of(orderItemDTO));
            for (OrderAddonDTO orderAddonDTO : StartController.orderAddonDTOList) {
                if (orderAddonDTO.getOrderItemDTO() == orderItemDTO) {
                    orderTableModelList.add(OrderTableModel.of(orderAddonDTO));
                }
            }
        }
        StartController.data.addAll(orderTableModelList);
    }

    private void loadView() {
        try {
            BorderPane borderPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(MENUITEM_FXML)));
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
}
