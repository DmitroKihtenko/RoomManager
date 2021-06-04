package rm.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;
import rm.model.HousingInfo;
import rm.model.Room;
import rm.model.RoomInfo;
import rm.service.Assertions;
import rm.service.Beans;

import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

public class RoomsTableController {
    private static final Logger logger =
            Logger.getLogger(RoomsTableController.class);
    private static final String SEPARATOR = "-";
    @FXML
    private TableView<Room> roomsTable;
    @FXML
    private TableColumn<Room, String> roomNameCol;
    @FXML
    private TextField searchField;
    private HashMap<Integer, Room> rooms;
    private HashMap<Integer, HousingInfo> housings;
    private final ObjectProperty<Room> selectedRoom;

    /**
     * Default constructor. Object initialization
     */
    public RoomsTableController() {
        selectedRoom = (ObjectProperty<Room>)
                Beans.context().get("selectedRoom");
    }

    /**
     * Initialization room, housing, rtAccess, roomList
     * @param rooms cloned rooms
     * @param housings cloned housing
     */
    public void setRooms(HashMap<Integer, Room> rooms,
                         HashMap<Integer, HousingInfo> housings) {
        Assertions.isNotNull(rooms, "Rooms map", logger);
        Assertions.isNotNull(housings, "Housings map", logger);

        this.rooms = rooms;
        this.housings = housings;

        ObservableList<Room> roomsList = roomsTable.getItems();
        roomsList.clear();
        roomsList.addAll(rooms.values());
    }

    /**
     * Setter listeners for initialized objects
     */
    @FXML
    public void initialize() {
        if(selectedRoom != null) {
            roomsTable.getSelectionModel().selectedItemProperty().
                    addListener((observableValue, roomInfo,
                                 t1) -> {
                if(roomInfo != null && !roomInfo.equals(t1)) {
                    selectedRoom.set(t1);
                } else if (roomInfo == null && t1 != null) {
                    selectedRoom.set(t1);
                }
            });
            roomNameCol.setCellValueFactory(roomFeatures
                    -> new SimpleStringProperty(getRoomString
                    (roomFeatures.getValue())));
            searchField.textProperty().addListener((observableValue,
                                                    oldValue,
                                                    newValue) -> {
                if(!oldValue.equals(newValue)) {
                    searchRooms();
                }
            });
        }
    }

    /**
     * Search room in rooms list
     */
    public void searchRooms() {
        String text = searchField.getText().toLowerCase(Locale.ROOT);
        ObservableList<Room> roomsList = roomsTable.getItems();
        roomsList.clear();
        for (Room room : rooms.values()) {
            if(text.length() == 0) {
                roomsList.add(room);
            } else {
                String summary = getRoomString(room);
                Pattern pattern = Pattern.compile(".*" + text + ".*");
                if(pattern.matcher(summary.toLowerCase(Locale.ROOT)).
                        matches()) {
                    roomsList.add(room);
                }
            }
        }
    }

    /**
     * Creates room string with housing
     * @param room selected room
     * @return string value of received room
     */
    private String getRoomString(RoomInfo room) {
        String value = "";
        HousingInfo housing = housings.get(room.getHousingId());
        if(housing != null) {
            value += housing.getName() + SEPARATOR;
        }
        value += room.getNumber();
        return value;
    }
}
