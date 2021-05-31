package rm.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import rm.Main;
import rm.bean.FastMutableConnections;
import rm.bean.HousingInfo;
import rm.bean.RoomInfo;
import rm.bean.TeacherInfo;
import rm.database.mySql.RTModifySQL;
import rm.service.Beans;

import java.util.HashMap;

public class AdminPanelController {
    @FXML
    private AnchorPane parent;
    @FXML
    private RoomsTableController roomsTableController;
    @FXML
    private TeachersTableController teachersTableController;
    @FXML
    private EditController editController;

    private RTModifySQL getSql;
    private double xOffSet;
    private double yOffSet;

    @FXML
    private void minimize_stage(MouseEvent event) {
        Main.stage.setIconified(true);
    }

    @FXML
    private void close_app(MouseEvent event) {
        System.exit(0);
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

    public void reloadData() {
        try {
            HashMap<Integer, HousingInfo> housings = new HashMap<>();
            HashMap<Integer, RoomInfo> rooms = new HashMap<>();
            HashMap<Integer, TeacherInfo> teachers = new HashMap<>();
            FastMutableConnections rtAccess = new FastMutableConnections();
            getSql.getHousings(housings);
            getSql.getRooms(rooms, null);
            getSql.getTeachers(teachers);
            getSql.getRtAccess(rtAccess);

            roomsTableController.setRooms(rooms, housings, rtAccess);
            teachersTableController.setTeachers(teachers, rtAccess);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveData() {

    }

    @FXML
    public void initialize() {
        if(getSql == null) {
            getSql = (RTModifySQL) Beans.context().get("databaseQueries");
            reloadData();
        }
        makeStageDraggable();
    }
}
