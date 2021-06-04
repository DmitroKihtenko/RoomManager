package rm.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.log4j.Logger;
import rm.database.mySql.RTGetSQL;
import rm.model.*;
import rm.saving.DatabaseCheckSaving;
import rm.saving.HousingSaving;
import rm.saving.RTKeysSaving;
import rm.saving.XmlSavingsHandler;
import rm.service.Beans;
import rm.threads.DatabaseCheck;
import rm.threads.ServiceThread;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminPanelController {
    private static final Logger logger =
            Logger.getLogger(AdminPanelController.class);
    private static final String PROPERTIES_PATH = "properties.xml";
    private static final String EMPLOYEE_PATH = "employeeData.xml";
    @FXML
    private AnchorPane parent;
    @FXML
    private RoomsTableController roomsTableController;
    @FXML
    private TeachersTableController teachersTableController;
    @FXML
    private EditController editController;
    @FXML
    private ComboBox<HousingInfo> housingComboBox;

    private final Notifications notifications;
    private final RTGetSQL getSql;
    private final XmlSavingsHandler dataSavings;
    private final HousingSaving housingSaving;
    private final HashMap<Integer, Room> castedRooms;
    private final HashMap<Integer, Teacher> castedTeachers;
    private final HashMap<Integer, HousingInfo> housings;
    private final DatabaseCheck checkOperation;
    boolean firstLaunch;

    private double xOffSet;
    private double yOffSet;

    /**
     * Default constructor. Object initialization
     */
    public AdminPanelController() {
        firstLaunch = true;
        castedRooms = new HashMap<>();
        castedTeachers = new HashMap<>();
        housings = new HashMap<>();
        getSql = (RTGetSQL) Beans.context().
                get("databaseQueries");
        notifications = (Notifications) Beans.context().
                get("notifications");
        dataSavings = (XmlSavingsHandler) Beans.context().
                get("dataSavings");
        DatabaseCheckSaving checkSaving = (DatabaseCheckSaving)
                Beans.context().get("checkSaving");
        housingSaving = (HousingSaving)
                Beans.context().get("housingSaving");
        RTKeysSaving rtKeysSaving = (RTKeysSaving) Beans.context().
                get("rtKeysSaving");
        rtKeysSaving.setKeysData(castedRooms, castedTeachers);
        checkOperation = new DatabaseCheck(notifications, getSql);
        checkSaving.setDatabaseCheck(checkOperation);
        dataSavings.propertiesForPath(PROPERTIES_PATH,
                housingSaving,
                checkSaving);
        dataSavings.propertiesForPath(EMPLOYEE_PATH,
                rtKeysSaving);
        dataSavings.readForPath(PROPERTIES_PATH);
        ServiceThread thread = new ServiceThread();
        thread.addOperation(checkOperation);
        thread.start();
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
        dataSavings.writeForPath(PROPERTIES_PATH);
        dataSavings.writeForPath(EMPLOYEE_PATH);
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
        if(!firstLaunch) {
            dataSavings.writeForPath(EMPLOYEE_PATH);
        }
        housings.clear();
        HashMap<Integer, TeacherInfo> teachers = new HashMap<>();
        ConnectionsList rtAccess = new FastAccessConnections();
        castedTeachers.clear();

        try {
            getSql.getProvider().connect();
            getSql.getHousings(housings);
            getSql.getTeachers(teachers);
            getSql.getRtAccess(rtAccess);
            checkOperation.setChangesVersion(getSql.getDatabaseChanges());
            logger.info("Reloaded data from database");
        } catch (Exception e) {
            notifications.push("Data loading error: " +
                    e.getMessage());
        } finally {
            getSql.getProvider().disconnect();
            for (TeacherInfo teacher : teachers.values()) {
                castedTeachers.put(teacher.getId(), (Teacher) teacher);
            }
            teachersTableController.setTeachers(castedTeachers);
            editController.setEditTeachers(rtAccess, housings);
            ObservableList<HousingInfo> housingsList =
                    FXCollections.observableList(new
                            ArrayList<>(housings.values()));
            housingComboBox.setItems(housingsList);
            if(housingSaving.getSelectedHousing() == null) {
                for (HousingInfo housing : housings.values()) {
                    housingComboBox.getSelectionModel().
                            select(housing);
                    break;
                }
            } else {
                housingComboBox.getSelectionModel().
                        select(housings.get(housingSaving.
                                getSelectedHousing()));
            }

        }
    }

    /**
     * Initialization reloadData and makeStageDraggable
     */
    @FXML
    public void initialize() {
        if(firstLaunch) {
            getSql.setDefaultHousing(new HousingInfo("X"));
            getSql.setDefaultRoom(new Room("0"));
            getSql.setDefaultTeacher(new Teacher("Name"));
            housingComboBox.setConverter(new StringConverter<>() {
                @Override
                public String toString(HousingInfo housingInfo) {
                    if(housingInfo == null) {
                        return "No";
                    }
                    return housingInfo.getName();
                }

                @Override
                public HousingInfo fromString(String s) {
                    return null;
                }
            });
            housingComboBox.getSelectionModel().selectedItemProperty().
                    addListener((observableValue, oldV, newV) -> {
                        if(oldV != newV && newV != null) {
                            housingSaving.setSelectedHousing(newV.getId());
                            reloadRooms();
                            dataSavings.readForPath(EMPLOYEE_PATH);
                            logger.info("Selected housing: " + newV);
                        }
                    });
            reloadData();
            makeStageDraggable();
        }
        firstLaunch = false;
    }

    private void reloadRooms() {
        HashMap<Integer, RoomInfo> rooms = new HashMap<>();
        castedRooms.clear();

        try {
            getSql.getProvider().connect();
            getSql.getRooms(rooms, housingComboBox.
                    getSelectionModel().getSelectedItem());
            logger.info("Reloaded rooms for housing " +
                    housingComboBox.getSelectionModel().
                            getSelectedItem());
        } catch (Exception e) {
            notifications.push("Data loading error: " +
                    e.getMessage());
        } finally {
            getSql.getProvider().disconnect();
            for (RoomInfo room : rooms.values()) {
                castedRooms.put(room.getId(), (Room) room);
            }
            dataSavings.readForPath(EMPLOYEE_PATH);

            roomsTableController.setRooms(castedRooms, housings);
        }
    }
}
