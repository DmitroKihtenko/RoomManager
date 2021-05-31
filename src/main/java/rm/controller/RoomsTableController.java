package rm.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import org.apache.log4j.Logger;
import rm.bean.ConnectionsList;
import rm.bean.HousingInfo;
import rm.bean.RoomInfo;
import rm.service.Assertions;
import rm.service.Beans;

import java.util.HashMap;

public class RoomsTableController {
    private static final Logger logger =
            Logger.getLogger(AdminController.class);
    private static final String DEF_ROOM_NAME = "0";
    private static final String NO_HOUSING_SYMBOL = "-";
    @FXML
    private TableView<RoomInfo> roomsTable;
    @FXML
    private TableColumn<RoomInfo, String> roomNameCol;
    @FXML
    private TableColumn<RoomInfo, String> roomHousingCol;
    private HashMap<Integer, HousingInfo> housings;
    private ConnectionsList rtAccess;
    private ObjectProperty<RoomInfo> selectedRoom;

    private ChangeListener<Number> housingListener;
    private ChangeListener<String> housingNameListener;

    public void setRooms(HashMap<Integer, RoomInfo> rooms,
                         HashMap<Integer, HousingInfo> housings,
                         ConnectionsList rtAccess) {
        Assertions.isNotNull(rooms, "Rooms map", logger);
        Assertions.isNotNull(housings, "Housings map", logger);
        Assertions.isNotNull(rtAccess, "Access connections", logger);

        this.housings = housings;
        this.rtAccess = rtAccess;

        ObservableList<RoomInfo> roomsList = roomsTable.getItems();
        roomsList.clear();
        roomsList.addAll(rooms.values());
        roomsTable.setItems(roomsList);
    }

    @FXML
    public void initialize() {
        if(selectedRoom == null) {
            selectedRoom = (ObjectProperty<RoomInfo>)
                    Beans.context().get("selectedRoom");
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
                    -> roomFeatures.getValue().numberProperty());
            roomHousingCol.setCellValueFactory(roomFeatures -> {
                Integer housing = roomFeatures.getValue().
                        getHousingId();
                HousingInfo object;
                SimpleStringProperty property = new
                        SimpleStringProperty(null);
                if(housing != null) {
                    object = housings.get(housing);
                    if(object != null) {
                        if(housingNameListener == null) {
                            housingNameListener = (observableValue, s, t1) -> {
                                if(!s.equals(t1)) {
                                    property.set(object.getName());
                                }
                            };
                        }
                        object.nameProperty().removeListener(housingNameListener);
                        object.nameProperty().addListener(housingNameListener);
                        property.set(object.getName());
                    } else {
                        property.set(NO_HOUSING_SYMBOL);
                    }
                } else {
                    property.set(NO_HOUSING_SYMBOL);
                }
                if(housingListener == null) {
                    housingListener = (observableValue, integer, t1) -> {
                        Integer housing1 = roomFeatures.
                                getValue().getHousingId();
                        HousingInfo object1;
                        if(housing1 != null) {
                            object1 = housings.get(housing1);
                            if(object1 != null) {
                                property.set(object1.getName());
                            } else {
                                property.set(NO_HOUSING_SYMBOL);
                            }
                        } else {
                            property.set(NO_HOUSING_SYMBOL);
                        }
                    };
                }
                roomFeatures.getValue().housingIdProperty().
                        removeListener(housingListener);
                roomFeatures.getValue().housingIdProperty().
                        addListener(housingListener);
                return property;
            });
        }
    }

    @FXML
    public void newRoom() {
        ObservableList<RoomInfo> roomsList = roomsTable.getItems();
        RoomInfo newRoom = new RoomInfo(DEF_ROOM_NAME);
        roomsList.add(newRoom);
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
        }
    }
}
