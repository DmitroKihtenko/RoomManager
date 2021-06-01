package rm.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rm.Main;
import rm.bean.User;
import rm.database.mySql.RTModifySQL;
import rm.service.Beans;


public class LoginController {
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

    public static Stage stage = null;

    private RTModifySQL getSql;
    private double xOffSet = 0;
    private double yOffSet = 0;

    @FXML
    public void initialize() {
        if(getSql == null) {
            getSql = (RTModifySQL) Beans.context().get("databaseQueries");
            User user = getSql.getProvider().getUser();
            user.setName("admin");
            user.setPassword("admin");
        }
        makeStageDraggable();
    }

    private void makeStageDraggable() {
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
        try {
            getSql.getProvider().connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        //stage.setMaximized(true);
        //stage.setMaxHeight(760);
        //stage.setMaxWidth(1360);
        stage.setWidth(1360);
        stage.setHeight(760);
        Scene scene = stage.getScene();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                getResource("/adminPanel.fxml"));
        scene.setRoot(fxmlLoader.load());




    }
}
