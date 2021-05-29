package rm.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import rm.Main;
import rm.bean.Datasource;
import rm.bean.RoomInfo;
import rm.bean.User;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rm.database.mySql.RTGetSQL;
import org.apache.log4j.Logger;
import rm.database.mySql.RTModifySQL;
import rm.service.Beans;

public class AdminController implements Initializable {
    private static final Logger logger =
            Logger.getLogger(AdminController.class);

    @FXML
    private AnchorPane parent;

    @FXML
    private TableView<RoomInfo> namesTable;
    @FXML
    private TableColumn<RoomInfo, String> idCol;
    @FXML
    private TableColumn<RoomInfo, String> nameCol;
    @FXML
    private TableColumn<RoomInfo, CheckBox> editCol;

    private RTModifySQL getSql;
    private HashMap<Integer, RoomInfo> rooms;

    private double xOffSet = 0;
    private double yOffSet = 0;

    ObservableList<RoomInfo> NamesList = FXCollections.observableArrayList();

    @Override
    public void initialize (URL url, ResourceBundle rb) {
        if(getSql == null) {
            getSql = (RTModifySQL) Beans.context().get("databaseQueries");

            User user = getSql.getProvider().getUser();


            user.setName("root");
            //user.setPassword("number1298UA");

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

        nameCol.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getNumber()));

        namesTable.setItems(NamesList);

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
