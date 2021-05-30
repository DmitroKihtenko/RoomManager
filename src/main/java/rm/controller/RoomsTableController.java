package rm.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;
import rm.bean.HousingInfo;
import rm.bean.RoomInfo;
import rm.database.mySql.RTModifySQL;
import rm.service.Assertions;
import rm.service.Beans;

import java.io.IOException;
import java.util.HashMap;

public class RoomsTableController {
    private static final Logger logger =
            Logger.getLogger(AdminController.class);
    @FXML
    private TableView<RoomInfo> roomsTable;
    @FXML
    private TableColumn<RoomInfo, String> roomNameCol;
    @FXML
    private TableColumn<RoomInfo, String> roomHousingCol;

    private RTModifySQL getSql;
    private HashMap<Integer, RoomInfo> rooms;
    private HashMap<Integer, HousingInfo> housings;

    ObservableList<RoomInfo> roomsList = FXCollections.observableArrayList();

    public void setRooms(HashMap<Integer, RoomInfo> rooms,
                         HashMap<Integer, HousingInfo> housings) {
        Assertions.isNotNull(rooms, "Rooms map", logger);
        Assertions.isNotNull(housings, "Housings map", logger);

        this.rooms = rooms;
        this.housings = housings;
        roomsList.clear();
        roomsList.addAll(rooms.values());
        roomsTable.setItems(roomsList);
    }

    @FXML
    public void initialize() {
        if(getSql == null) {
            getSql = (RTModifySQL) Beans.context().
                    get("databaseQueries");
            rooms = new HashMap<>();
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
                        property.set(object.getName());
                    }
                }
                roomFeatures.getValue().housingIdProperty().
                        addListener((observableValue,
                                     number, t1) -> {
                    Integer housing1 = roomFeatures.
                            getValue().getHousingId();
                    HousingInfo object1;
                    if(housing1 != null) {
                        object1 = housings.get(housing1);
                        if(object1 != null) {
                            property.set(object1.getName());
                        }
                    }
                });
                return property;
            });
        }
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
    private void addNewName(MouseEvent mouseEvent) {

    }
}
