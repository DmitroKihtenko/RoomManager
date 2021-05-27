package rm.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import rm.Main;
import rm.bean.Datasource;
import rm.bean.RoomInfo;
import rm.bean.User;
import rm.database.DbConnect;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rm.database.mySql.RTGetSQL;
import org.apache.log4j.Logger;

public class AdminController implements Initializable {
    private static final Logger logger =
            Logger.getLogger(AdminController.class);

    @FXML
    private AnchorPane parent;

    @FXML
    private TableView<Names> namesTable;
    @FXML
    private TableColumn<Names, String> idCol;
    @FXML
    private TableColumn<Names, String> nameCol;
    @FXML
    private TableColumn<Names, String> editCol;

    private RTGetSQL getSql;
    private HashMap<Integer, RoomInfo> rooms;

    private double xOffSet = 0;
    private double yOffSet = 0;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Names names = null;

    ObservableList<RoomInfo> NamesList = FXCollections.observableArrayList();

    @Override
    public void initialize (URL url, ResourceBundle rb) {
        if(getSql == null) {
            getSql = new RTGetSQL();
            Datasource datasource = getSql.getProvider().getDatasource();
            User user = getSql.getProvider().getUser();
            datasource.setUrl("//localhost");
            datasource.setPort("3306");
            datasource.setSource("mysql");
            datasource.setDatabaseName("roommanager");

            user.setName("root");
            user.setPassword("number1298UA");

            rooms = new HashMap<>();
        }


        loadDate();
        makeStageDragable();
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
    private void getAddView() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/AddName.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void refreshTable() {
        NamesList.clear();
        NamesList.addAll(rooms.values());
    }

    private void loadDate() {
        try {
            getSql.getProvider().connect();
            getSql.getRooms(rooms, null);
        } catch (SQLException e) {
            logger.warn("Rooms get error: " + e.getMessage());
        }
        refreshTable();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("number"));
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
}
