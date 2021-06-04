package rm.controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import rm.model.Notifications;
import rm.database.mySql.RTModifySQL;
import rm.service.Beans;

public class LoginController {
    @FXML
    private AnchorPane parent;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private Notifications notifications;
    private RTModifySQL getSql;
    private double xOffSet = 0;
    private double yOffSet = 0;

    /**
     * Setter listeners for initialized objects
     */
    @FXML
    public void initialize() {
        if(getSql == null) {
            getSql = (RTModifySQL) Beans.context().
                    get("databaseQueries");
            notifications = (Notifications) Beans.context().
                    get("notifications");
            usernameField.textProperty().addListener((
                    observableValue, s, t1) -> {
                if(!t1.equals("")) {
                    if(!s.equals(t1)) {
                        try {
                            getSql.getProvider().getUser().setName(t1);
                        } catch (IllegalArgumentException e) {
                            notifications.push("Username syntax " +
                                    "error: " + e.getMessage());
                        }
                    }
                }
            });
            passwordField.textProperty().addListener((observableValue, s,
                                                      t1) -> {
                if(!t1.equals("")) {
                    if(!s.equals(t1)) {
                        try {
                            getSql.getProvider().getUser().
                                    setPassword(t1);
                        } catch (IllegalArgumentException e) {
                            notifications.push("Password syntax " +
                                    "error: " + e.getMessage());
                        }
                    }
                }
            });
        }
        makeStageDraggable();
    }

    /**
     * Move the work window
     */
    private void makeStageDraggable() {
        parent.setOnMousePressed((event) -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });
        parent.setOnMouseDragged((event) -> {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffSet);
            stage.setY(event.getScreenY() - yOffSet);
            stage.setOpacity(0.8f);
        });
        parent.setOnDragDone((event) -> {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setOpacity(1.0f);
        });
        parent.setOnMouseReleased((event) -> {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setOpacity(1.0f);
        });
    }

    /**
     * Minimize the program
     * @param event mouse event
     */
    @FXML
    private void minimizeStage(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Close the program
     */
    @FXML
    private void closeApp() {
        Platform.exit();
    }

    /**
     * Call a new scene on successful connection
     * @param event action event
     * @throws IOException
     */
    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        try {
            getSql.getProvider().getUser().setName(usernameField.getText());
            getSql.getProvider().getUser().setPassword(passwordField.
                    getText());
            getSql.getProvider().connect();

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setWidth(1360);
            stage.setHeight(760);
            Scene scene = stage.getScene();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                    getResource("/adminPanel.fxml"));
            scene.setRoot(fxmlLoader.load());
        } catch (SQLException e) {
                notifications.push("Connection error: "
                        + e.getMessage());
        } catch (IllegalArgumentException e) {
            notifications.push("User data error: "
                    + e.getMessage());
        }
    }
}
