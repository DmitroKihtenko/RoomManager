package rm.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;
import rm.bean.ConnectionsList;
import rm.bean.HousingInfo;
import rm.bean.RoomInfo;
import rm.service.Assertions;
import rm.service.Beans;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.regex.Pattern;

public class RoomsTableController {
    private static final Logger logger =
            Logger.getLogger(RoomsTableController.class);
    private static final String DEF_ROOM_NAME = "0";
    private static final String SEPARATOR = "-";
    @FXML
    private TableView<RoomInfo> roomsTable;
    @FXML
    private TableColumn<RoomInfo, String> roomNameCol;
    @FXML
    private TextField searchField;
    private HashMap<Integer, RoomInfo> rooms;
    private HashMap<Integer, HousingInfo> housings;
    private ConnectionsList rtAccess;
    private ObjectProperty<RoomInfo> selectedRoom;

    private ChangeListener<Object> refreshListener;

    public void setRooms(HashMap<Integer, RoomInfo> rooms,
                         HashMap<Integer, HousingInfo> housings,
                         ConnectionsList rtAccess) {
        Assertions.isNotNull(rooms, "Rooms map", logger);
        Assertions.isNotNull(housings, "Housings map", logger);
        Assertions.isNotNull(rtAccess, "Access connections", logger);

        this.rooms = rooms;
        this.housings = housings;
        this.rtAccess = rtAccess;

        ObservableList<RoomInfo> roomsList = roomsTable.getItems();
        roomsList.clear();
        roomsList.addAll(rooms.values());
    }

    @FXML
    public void initialize() {
        if(selectedRoom == null) {
            selectedRoom = (ObjectProperty<RoomInfo>)
                    Beans.context().get("selectedRoom");
            refreshListener = (observableValue, o, t1) ->
                    roomsTable.refresh();
            roomsTable.getItems().addListener((ListChangeListener
                    <RoomInfo>) change -> {
                while(change.next()) {
                    if(change.wasAdded()) {
                        HashSet<Integer> usedHousings =
                                new HashSet<>(housings.size());
                        for (RoomInfo room : change.
                                getAddedSubList()) {
                            room.numberProperty().
                                    addListener(refreshListener);
                            HousingInfo housing =
                                    housings.get(room.getHousingId());
                            if(housing != null) {
                                if(!usedHousings.contains(
                                        housing.
                                                getId())) {
                                    usedHousings.add(housing.getId());
                                    room.housingIdProperty().
                                            addListener(
                                                    refreshListener);
                                    housing.nameProperty().
                                            addListener(
                                                    refreshListener);
                                }
                            }
                        }
                    } else if(change.wasRemoved()) {
                        for (RoomInfo room : change.getRemoved()) {
                            room.numberProperty().
                                    removeListener(refreshListener);
                            room.housingIdProperty().
                                    removeListener(refreshListener);
                            HousingInfo housing =
                                    housings.get(room.getHousingId());
                            if(housing != null) {
                                housing.nameProperty().removeListener(
                                        refreshListener);
                            }
                        }
                    }
                }
            });
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

    @FXML
    public void newRoom() {
        ObservableList<RoomInfo> roomsList = roomsTable.getItems();
        RoomInfo newRoom = new RoomInfo(DEF_ROOM_NAME);
        newRoom.createUniqueId();
        roomsList.add(newRoom);
        rooms.put(newRoom.getId(), newRoom);
        roomsTable.getSelectionModel().select(newRoom);
    }

    @FXML
    public void deleteRoom() {
        ObservableList<RoomInfo> roomsList = roomsTable.getItems();
        RoomInfo roomToDelete = roomsTable.getSelectionModel().
                getSelectedItem();
        if(roomToDelete != null) {
            rtAccess.removeSecondConnections(roomToDelete.getId());
            roomsList.remove(roomToDelete);
            rooms.remove(roomToDelete.getId());
        }
    }

    public void searchRooms() {
        String text = searchField.getText().toLowerCase(Locale.ROOT);
        ObservableList<RoomInfo> roomsList = roomsTable.getItems();
        roomsList.clear();
        for (RoomInfo room : rooms.values()) {
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
