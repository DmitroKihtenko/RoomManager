package rm.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import rm.database.mySql.RTGetSQL;
import rm.model.*;
import rm.service.Beans;

import java.util.HashMap;

public class AdminPanelController {
    private static final Logger logger =
            Logger.getLogger(AdminPanelController.class);
    @FXML
    private AnchorPane parent;
    @FXML
    private RoomsTableController roomsTableController;
    @FXML
    private TeachersTableController teachersTableController;
    @FXML
    private EditController editController;

    private final Notifications notifications;
    private final RTGetSQL getSql;

    private double xOffSet;
    private double yOffSet;

    /**
     * Default constructor. Object initialization
     */
    public AdminPanelController() {
        getSql = (RTGetSQL) Beans.context().
                get("databaseQueries");
        notifications = (Notifications) Beans.context().
                get("notifications");
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
     * Closing the program
     */
    @FXML
    private void closeApp() {
        Platform.exit();
    }

    /**
     * Moves the work window
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
     * Load the required data
     */
    public void reloadData() {
        HashMap<Integer, HousingInfo> housings = new HashMap<>();
        HashMap<Integer, RoomInfo> rooms = new HashMap<>();
        HashMap<Integer, TeacherInfo> teachers = new HashMap<>();
        ConnectionsList rtAccess = new FastAccessConnections();
        HashMap<Integer, Room> castedRooms =
                new HashMap<>(rooms.size());
        HashMap<Integer, Teacher> castedTeachers =
                new HashMap<>(rooms.size());

        try {
            getSql.getProvider().connect();
            getSql.getHousings(housings);
            getSql.getRooms(rooms, null);
            getSql.getTeachers(teachers);
            getSql.getRtAccess(rtAccess);
        } catch (Exception e) {
            notifications.push("Data loading error: " +
                    e.getMessage());
        } finally {
            for (RoomInfo room : rooms.values()) {
                castedRooms.put(room.getId(), (Room) room);
            }
            for (TeacherInfo teacher : teachers.values()) {
                castedTeachers.put(teacher.getId(), (Teacher) teacher);
            }
            roomsTableController.setRooms(castedRooms, housings);
            teachersTableController.setTeachers(castedTeachers);
            editController.setEditTeachers(rtAccess, housings);

            getSql.getProvider().disconnect();
        }
    }

    /**
     * Initialization reloadData and makeStageDraggable
     */
    @FXML
    public void initialize() {
        if(getSql != null) {
            getSql.setDefaultHousing(new HousingInfo("X"));
            getSql.setDefaultRoom(new Room("0"));
            getSql.setDefaultTeacher(new Teacher("Name"));
            reloadData();
            makeStageDraggable();
        }
    }
}
