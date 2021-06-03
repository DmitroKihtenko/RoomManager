package rm.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;
import rm.Main;
import rm.model.*;
import rm.controller.util.ChangesDetector;
import rm.database.mySql.RTModifySQL;
import rm.service.Beans;

import java.util.HashMap;
import java.util.Map;

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
    @FXML
    private HousingsTableController housingsTableController;

    private RTModifySQL sqlQueries;
    private double xOffSet;
    private double yOffSet;

    private ChangesDetector<RoomInfo> roomsDetector;
    private ChangesDetector<TeacherInfo> teachersDetector;
    private ChangesDetector<HousingInfo> housingsDetector;
    private ConnectionsList clonedAccess;
    private ConnectionsList originalAccess;
    private Notifications notifications;

    @FXML
    private void minimizeStage() {
        Main.stage.setIconified(true);
    }

    @FXML
    private void closeApp() {
        Platform.exit();
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
        parent.setOnDragDone((event) ->
                Main.stage.setOpacity(1.0f));
        parent.setOnMouseReleased((event) ->
                Main.stage.setOpacity(1.0f));
    }

    public void reloadData() throws CloneNotSupportedException {
        HashMap<Integer, HousingInfo> housings = new HashMap<>();
        HashMap<Integer, RoomInfo> rooms = new HashMap<>();
        HashMap<Integer, TeacherInfo> teachers = new HashMap<>();
        originalAccess = new FastMutableConnections();
        try {
            sqlQueries.getHousings(housings);
            sqlQueries.getRooms(rooms, null);
            sqlQueries.getTeachers(teachers);
            sqlQueries.getRtAccess(originalAccess);
        } catch (Exception e) {
            notifications.push("Data loading error: " +
                    e.getMessage());
        } finally {
            HashMap<Integer, HousingInfo> clonedHousings =
                    new HashMap<>(housings.size());
            HashMap<Integer, RoomInfo> clonedRooms =
                    new HashMap<>(rooms.size());
            HashMap<Integer, TeacherInfo> clonedTeachers =
                    new HashMap<>(teachers.size());
            clonedAccess = (ConnectionsList)
                    originalAccess.clone();
            for (HousingInfo housing : housings.values()) {
                clonedHousings.put(housing.getId(), (HousingInfo)
                        housing.clone());
            }
            for (RoomInfo room : rooms.values()) {
                clonedRooms.put(room.getId(), (RoomInfo)
                        room.clone());
            }
            for (TeacherInfo teacher : teachers.values()) {
                clonedTeachers.put(teacher.getId(), (TeacherInfo)
                        teacher.clone());
            }

            roomsTableController.setRooms(clonedRooms, clonedHousings,
                    originalAccess);
            teachersTableController.setTeachers(clonedTeachers,
                    originalAccess);
            housingsTableController.setHousings(clonedHousings,
                    clonedRooms);
            editController.setEditTeachers(clonedAccess, clonedHousings);

            housingsDetector.setOriginal(housings);
            roomsDetector.setOriginal(rooms);
            teachersDetector.setOriginal(teachers);

            housingsDetector.setChanged(clonedHousings);
            roomsDetector.setChanged(clonedRooms);
            teachersDetector.setChanged(clonedTeachers);
        }
    }

    public void saveData() throws CloneNotSupportedException {
        ConnectionsList addedConnections =
                new FastMutableConnections();
        ConnectionsList removedConnections =
                new FastMutableConnections();
        originalAccess.differences(clonedAccess,
                addedConnections,
                removedConnections);
        roomsDetector.findChanges();
        housingsDetector.findChanges();
        teachersDetector.findChanges();
        try {
            sqlQueries.removeAccess(removedConnections);
            sqlQueries.removeHousings(housingsDetector.getRemoved().
                    values());
            sqlQueries.removeRooms(roomsDetector.getRemoved().
                    values());
            sqlQueries.removeTeachers(teachersDetector.getRemoved().
                    values());
            sqlQueries.addHousings(housingsDetector.getAdded().
                    values());
            sqlQueries.addTeachers(teachersDetector.getAdded().
                    values());
            sqlQueries.addRooms(roomsDetector.getAdded().values());
            sqlQueries.addAccess(addedConnections);
            sqlQueries.updateHousings(housingsDetector.
                    getUpdated().values());
            sqlQueries.updateRooms(roomsDetector.getUpdated().
                    values());
            sqlQueries.updateTeachers(teachersDetector.getUpdated().
                    values());
            sqlQueries.provideChanges();
        } catch (Exception e) {
            notifications.push("Save data error: " +
                    e.getMessage());
        } finally {
            housingsDetector.setOriginal(housingsDetector.
                    getChanged());
            teachersDetector.setOriginal(teachersDetector.
                    getChanged());
            roomsDetector.setOriginal(roomsDetector.
                    getChanged());

            housingsDetector.discardFound();
            roomsDetector.discardFound();
            teachersDetector.discardFound();

            Map<Integer, HousingInfo> housings = housingsDetector.
                    getChanged();
            Map<Integer, RoomInfo> rooms = roomsDetector.
                    getChanged();
            Map<Integer, TeacherInfo> teachers = teachersDetector.
                    getChanged();
            originalAccess = clonedAccess;

            HashMap<Integer, HousingInfo> clonedHousings =
                    new HashMap<>(housings.size());
            HashMap<Integer, RoomInfo> clonedRooms =
                    new HashMap<>(rooms.size());
            HashMap<Integer, TeacherInfo> clonedTeachers =
                    new HashMap<>(teachers.size());
            clonedAccess = (ConnectionsList)
                    originalAccess.clone();
            for (HousingInfo housing : housings.values()) {
                clonedHousings.put(housing.getId(), (HousingInfo)
                        housing.clone());
            }
            for (RoomInfo room : rooms.values()) {
                clonedRooms.put(room.getId(), (RoomInfo) room.clone());
            }
            for (TeacherInfo teacher : teachers.values()) {
                clonedTeachers.put(teacher.getId(), (TeacherInfo)
                        teacher.clone());
            }

            roomsTableController.setRooms(clonedRooms, clonedHousings,
                    originalAccess);
            teachersTableController.setTeachers(clonedTeachers,
                    originalAccess);
            housingsTableController.setHousings(clonedHousings,
                    clonedRooms);
            editController.setEditTeachers(clonedAccess,
                    clonedHousings);

            housingsDetector.setOriginal(housings);
            roomsDetector.setOriginal(rooms);
            teachersDetector.setOriginal(teachers);

            housingsDetector.setChanged(clonedHousings);
            roomsDetector.setChanged(clonedRooms);
            teachersDetector.setChanged(clonedTeachers);
        }
    }

    @FXML
    public void initialize() throws CloneNotSupportedException {
        if(sqlQueries == null) {
            sqlQueries = (RTModifySQL) Beans.context().
                    get("databaseQueries");
            notifications = (Notifications) Beans.context().
                    get("notifications");
            roomsDetector = new ChangesDetector<>();
            teachersDetector = new ChangesDetector<>();
            housingsDetector = new ChangesDetector<>();
            reloadData();
        }
        makeStageDraggable();
    }
}
