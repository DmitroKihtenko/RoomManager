package rm.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;
import rm.model.ConnectionsList;
import rm.model.HousingInfo;
import rm.model.RoomInfo;
import rm.service.Assertions;
import rm.service.Beans;

import java.util.HashMap;
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

    private final ChangeListener<Object> refreshListener;
    private final ChangeListener<Number> housingsId;
    private final HashMap<Integer, Integer> roomsForHousings;

    public RoomsTableController() {
        refreshListener = (observableValue, o, t1) ->
                roomsTable.refresh();
        housingsId = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number>
                                        observableValue,
                                Number oldV,
                                Number newV) {
                Integer integer = oldV.intValue();
                Integer t1 = newV.intValue();
                if(integer == Integer.MIN_VALUE) {
                    integer = null;
                }
                if(t1 == Integer.MIN_VALUE) {
                    t1 = null;
                }

                HousingInfo housing;
                if(integer == null && t1 != null) {
                    housing = housings.get(t1);
                    if(housing != null) {
                        if(!roomsForHousings.containsKey(t1)) {
                            roomsForHousings.put(t1, 1);
                        } else {
                            roomsForHousings.put(t1,
                                    roomsForHousings.get(t1) + 1);
                        }
                        housing.nameProperty().
                                removeListener(refreshListener);
                        housing.nameProperty().
                                addListener(refreshListener);
                    }
                } else if(integer != null && t1 == null) {
                    housing = housings.get(integer);
                    if(housing != null) {
                        if(roomsForHousings.get(integer) <= 1) {
                            roomsForHousings.remove(integer);
                            housing.nameProperty().
                                    removeListener(refreshListener);
                        } else {
                            roomsForHousings.put(integer,
                                    roomsForHousings.get(integer) - 1);
                        }
                    }
                } else if(t1 != null) {
                    housing = housings.get(integer);
                    if(housing != null) {
                        if(roomsForHousings.get(integer) <= 1) {
                            roomsForHousings.remove(integer);
                            housing.nameProperty().
                                    removeListener(refreshListener);
                        } else {
                            roomsForHousings.put(integer,
                                    roomsForHousings.get(integer) - 1);
                        }
                    }
                    housing = housings.get(t1);
                    if(housing != null) {
                        if(!roomsForHousings.containsKey(t1)) {
                            roomsForHousings.put(t1, 1);
                        } else {
                            roomsForHousings.put(t1,
                                    roomsForHousings.get(t1) + 1);
                        }
                        housing.nameProperty().
                                removeListener(refreshListener);
                        housing.nameProperty().
                                addListener(refreshListener);
                    }
                }
            }
        };
        roomsForHousings = new HashMap<>();
    }

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
            roomsTable.getItems().addListener((ListChangeListener
                    <RoomInfo>) change -> {
                Integer housingId;
                HousingInfo housing;
                while(change.next()) {
                    if(change.wasAdded()) {
                        for (RoomInfo room : change.
                                getAddedSubList()) {
                            housingId = room.getHousingId();
                            room.numberProperty().
                                    addListener(refreshListener);
                            room.housingIdProperty().
                                    addListener(refreshListener);
                            room.housingIdProperty().
                                    addListener(housingsId);
                            housing = housings.get(housingId);
                            if(housing != null) {
                                housing.nameProperty().
                                        removeListener(
                                                refreshListener);
                                housing.nameProperty().
                                        addListener(
                                                refreshListener);
                                if(!roomsForHousings.containsKey(
                                        housingId)) {
                                    roomsForHousings.put(housingId,
                                            1);
                                } else {
                                    roomsForHousings.put(housingId,
                                            roomsForHousings.get(
                                                    housingId) + 1);
                                }
                            }
                        }
                    } else if(change.wasRemoved()) {
                        for (RoomInfo room : change.getRemoved()) {
                            room.numberProperty().
                                    removeListener(refreshListener);
                            room.housingIdProperty().
                                    removeListener(refreshListener);
                            room.housingIdProperty().
                                    addListener(housingsId);
                            housingId = room.getHousingId();
                            housing = housings.get(housingId);
                            if(housing != null) {
                                housing.nameProperty().removeListener(
                                        refreshListener);
                                if(roomsForHousings.containsKey(
                                        housingId)) {
                                    roomsForHousings.put(housingId,
                                            roomsForHousings.get(
                                                    housingId) - 1);
                                    if(roomsForHousings.get(housingId)
                                            <= 0) {
                                        room.housingIdProperty().
                                                removeListener(
                                                refreshListener);
                                        roomsForHousings.remove(
                                                housingId);
                                    }
                                }
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
