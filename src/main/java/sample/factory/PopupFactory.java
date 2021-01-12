package sample.factory;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.handler.InfoPopupOkHandler;

public class PopupFactory {

    public Stage createWaitingPopup(String text) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

        ProgressBar progressBar = new ProgressBar();

        stage.setScene(new Scene(createVBox(createLabel(text), progressBar), 200, 100));
        stage.initModality(Modality.APPLICATION_MODAL);

        return stage;
    }

    private VBox createVBox(Label label, ProgressBar progressBar){
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(10);
        pane.setStyle(waitingPopupPaneStyle());
        pane.getChildren().addAll(label, progressBar);
        return pane;
    }

    private VBox createVBox(Label label, Button button){
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(10);
        pane.setStyle(waitingPopupPaneStyle());
        pane.getChildren().addAll(label, button);
        return pane;
    }

    private Label createLabel(String text){
        Label label = new Label(text);
        label.setStyle(waitingLabelStyle());
        return label;
    }

    private String waitingLabelStyle() {
        return "-fx-text-fill: #003366;";
    }

    private String waitingPopupPaneStyle() {
        return "-fx-background-color: #c7c7c7;" +
                "-fx-border-color: #003366;";
    }

    private String okButtonStyle(){
        return ".login-button{\n" +
                "    -fx-text-fill: #003366;\n" +
                "    -fx-background-color: #c7c7c7;\n" +
                "    -fx-border-color: #003366;\n" +
                "    -fx-background-radius: 0;\n" +
                "}";
    }

    private String okButtonHoverStyle(){
        return ".login-button{\n" +
                "    -fx-text-fill: #003366;\n" +
                "    -fx-background-color: #e1e1e1;\n" +
                "    -fx-border-color: #003366;\n" +
                "    -fx-background-radius: 0;\n" +
                "}";
    }

    public Stage createInfoPopup(String text, InfoPopupOkHandler handler) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setScene(new Scene(createVBox(createLabel(text), getButton(handler, stage)), 200, 100));
        stage.initModality(Modality.APPLICATION_MODAL);

        return stage;
    }

    private Button getButton(InfoPopupOkHandler handler, Stage stage) {
        Button okButton = new Button("OK");
        okButton.setStyle(okButtonStyle());
        okButton.setOnMouseEntered(x-> okButton.setStyle(okButtonHoverStyle()));
        okButton.setOnMouseExited(x-> okButton.setStyle(okButtonStyle()));
        okButton.setOnAction(x->{
            stage.close();
            handler.handle();
        });
        return okButton;
    }
}
