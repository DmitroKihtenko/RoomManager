package rm.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import rm.Main;


public class LoginController implements Initializable{

    @FXML
    private AnchorPane parent;
    @FXML
    private HBox top;
    @FXML
    private Pane content;
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;

    private double xOffSet = 0;
    private double yOffSet = 0;

    @Override
    public void initialize (URL url, ResourceBundle rb) {
        makeStageDragable();
    }

    private void makeStageDragable() {
        parent.setOnMousePressed((event) -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });
        parent.setOnMouseDragged((event) -> {
            Main.stage.setX(event.getScreenX() - xOffSet);
            Main.stage.setY(event.getScreenY() - yOffSet);
            Main.stage.setOpacity(0.8f);
        });
        parent.setOnDragDone((event) -> {
            Main.stage.setOpacity(1.0f);
        });
        parent.setOnMouseReleased((event) -> {
            Main.stage.setOpacity(1.0f);
        });
    }

    @FXML
    private void minimize_stage(MouseEvent event) {
        Main.stage.setIconified(true);
    }

    @FXML
    private void close_app(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void handle_login(ActionEvent event) throws IOException {
        Parent admin = FXMLLoader.load(getClass().getResource("/Admin.fxml"));
        content.getChildren().removeAll();
        content.getChildren().setAll(admin);
    }


}
