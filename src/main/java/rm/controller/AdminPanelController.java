package rm.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import rm.Main;
import rm.bean.*;
import rm.controller.util.ChangesDetector;
import rm.database.mySql.RTModifySQL;
import rm.service.Beans;

import java.util.HashMap;
import java.util.Map;

public class AdminPanelController {
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

    @FXML
    private void minimize_stage() {
        Main.stage.setIconified(true);
    }

    @FXML
    private void close_app() {
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
        parent.setOnDragDone((event) ->
                Main.stage.setOpacity(1.0f));
        parent.setOnMouseReleased((event) ->
                Main.stage.setOpacity(1.0f));
    }

    public void reloadData() {
        try {
            HashMap<Integer, HousingInfo> housings = new HashMap<>();
            HashMap<Integer, RoomInfo> rooms = new HashMap<>();
            HashMap<Integer, TeacherInfo> teachers = new HashMap<>();
            originalAccess = new FastMutableConnections();

            sqlQueries.getHousings(housings);
            sqlQueries.getRooms(rooms, null);
            sqlQueries.getTeachers(teachers);
            sqlQueries.getRtAccess(originalAccess);

            HashMap<Integer, HousingInfo> clonedHousings =
                    new HashMap<>(housings.size());
            HashMap<Integer, RoomInfo> clonedRooms =
                    new HashMap<>(rooms.size());
            HashMap<Integer, TeacherInfo> clonedTeachers =
                    new HashMap<>(teachers.size());
            clonedAccess = (ConnectionsList)
                    originalAccess.clone();
            for (HousingInfo housing : housings.values()) {
                clonedHousings.put(housing.getId(), housing.clone());
            }
            for (RoomInfo room : rooms.values()) {
                clonedRooms.put(room.getId(), room.clone());
            }
            for (TeacherInfo teacher : teachers.values()) {
                clonedTeachers.put(teacher.getId(), teacher.clone());
            }

            roomsTableController.setRooms(clonedRooms, clonedHousings,
                    originalAccess);
            teachersTableController.setTeachers(clonedTeachers,
                    originalAccess);
            housingsTableController.setHousings(clonedHousings,
                    clonedRooms);
            editController.setEditTeachers(clonedAccess);

            housingsDetector.setOriginal(housings);
            roomsDetector.setOriginal(rooms);
            teachersDetector.setOriginal(teachers);

            housingsDetector.setChanged(housings);
            roomsDetector.setChanged(rooms);
            teachersDetector.setChanged(teachers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        try {
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

            sqlQueries.addAccess(addedConnections);
            sqlQueries.removeAccess(removedConnections);
            sqlQueries.removeRooms(roomsDetector.getRemoved().
                    values());
            sqlQueries.addRooms(roomsDetector.getAdded().values());
            sqlQueries.updateRooms(roomsDetector.getUpdated().
                    values());
            sqlQueries.removeHousings(housingsDetector.getRemoved().
                    values());
            sqlQueries.addHousings(housingsDetector.getAdded().
                    values());
            sqlQueries.updateHousings(housingsDetector.
                    getUpdated().values());
            sqlQueries.removeTeachers(teachersDetector.getRemoved().
                    values());
            sqlQueries.addTeachers(teachersDetector.getAdded().
                    values());
            sqlQueries.updateTeachers(teachersDetector.getUpdated().
                    values());

            housingsDetector.setOriginal(housingsDetector.
                    getChanged());
            teachersDetector.setOriginal(teachersDetector.
                    getChanged());
            roomsDetector.setOriginal(roomsDetector.
                    getChanged());

            housingsDetector.discardFound();
            roomsDetector.discardFound();
            teachersDetector.discardFound();
            reloadData();

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
                clonedHousings.put(housing.getId(), housing.clone());
            }
            for (RoomInfo room : rooms.values()) {
                clonedRooms.put(room.getId(), room.clone());
            }
            for (TeacherInfo teacher : teachers.values()) {
                clonedTeachers.put(teacher.getId(), teacher.clone());
            }

            roomsTableController.setRooms(clonedRooms, clonedHousings,
                    originalAccess);
            teachersTableController.setTeachers(clonedTeachers,
                    originalAccess);
            housingsTableController.setHousings(clonedHousings,
                    clonedRooms);
            editController.setEditTeachers(clonedAccess);

            housingsDetector.setOriginal(housings);
            roomsDetector.setOriginal(rooms);
            teachersDetector.setOriginal(teachers);

            housingsDetector.setChanged(housings);
            roomsDetector.setChanged(rooms);
            teachersDetector.setChanged(teachers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        if(sqlQueries == null) {
            sqlQueries = (RTModifySQL) Beans.context().get("databaseQueries");
            roomsDetector = new ChangesDetector<>();
            teachersDetector = new ChangesDetector<>();
            housingsDetector = new ChangesDetector<>();
            reloadData();
        }
        makeStageDraggable();
    }
}
