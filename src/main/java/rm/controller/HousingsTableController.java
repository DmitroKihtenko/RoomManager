package rm.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;
import rm.bean.HousingInfo;
import rm.bean.RoomInfo;
import rm.service.Assertions;
import rm.service.Beans;

import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

public class HousingsTableController {
    private static final Logger logger =
            Logger.getLogger(HousingsTableController.class);
    private static final String DEF_HOUSING_NAME = "X";

    @FXML
    private TextField searchField;
    @FXML
    private TableView<HousingInfo> housingsTable;
    @FXML
    private TableColumn<HousingInfo, String> housingNameCol;

    private ObjectProperty<HousingInfo> selectedHousing;
    private HashMap<Integer, RoomInfo> rooms;
    private HashMap<Integer, HousingInfo> housings;
    private ChangeListener<Object> refreshListener;

    public void setHousings(HashMap<Integer, HousingInfo> housings,
                            HashMap<Integer, RoomInfo> rooms) {
        Assertions.isNotNull(rooms, "Rooms map", logger);
        Assertions.isNotNull(housings, "Housings map", logger);

        this.housings = housings;
        this.rooms = rooms;
        ObservableList<HousingInfo> housingsList =
                housingsTable.getItems();
        housingsList.clear();
        housingsList.addAll(housings.values());
        housingsTable.setItems(housingsList);
    }

    @FXML
    public void initialize() {
        if(selectedHousing == null) {
            selectedHousing = (ObjectProperty<HousingInfo>)
                    Beans.context().get("selectedHousing");
            refreshListener = (observableValue, o, t1) ->
                    housingsTable.refresh();
            housingsTable.getItems().addListener((ListChangeListener
                    <HousingInfo>) change -> {
                while(change.next()) {
                    if(change.wasAdded()) {
                        for (HousingInfo housing : change.
                                getAddedSubList()) {
                            housing.nameProperty().
                                    addListener(refreshListener);
                        }
                    } else if(change.wasRemoved()) {
                        for (HousingInfo housing : change.getRemoved()) {
                            housing.nameProperty().
                                    addListener(refreshListener);
                        }
                    }
                }
            });
            housingsTable.getSelectionModel().selectedItemProperty().
                    addListener((observableValue, housing,
                                 t1) -> {
                        if(housing != null && !housing.equals(t1)) {
                            selectedHousing.set(t1);
                        } else if (housing == null && t1 != null) {
                            selectedHousing.set(t1);
                        }
                    });
            housingNameCol.setCellValueFactory(housingFeatures
                    -> housingFeatures.getValue().nameProperty());
            searchField.textProperty().addListener((observableValue,
                                                    oldValue,
                                                    newValue) -> {
                if(!oldValue.equals(newValue)) {
                    searchHousings();
                }
            });
        }
    }

    public void newHousing() {
        ObservableList<HousingInfo> housingsList =
                housingsTable.getItems();
        HousingInfo newHousing = new HousingInfo(DEF_HOUSING_NAME);
        newHousing.createUniqueId();
        housingsList.add(newHousing);
        housings.put(newHousing.getId(), newHousing);
        housingsTable.getSelectionModel().select(newHousing);
    }

    public void deleteHousing() {
        ObservableList<HousingInfo> housingsList =
                housingsTable.getItems();
        HousingInfo housingToRemove =
                housingsTable.getSelectionModel().getSelectedItem();
        if(housingToRemove != null) {
            housingsList.remove(housingToRemove);
            housings.remove(housingToRemove.getId());
            for (RoomInfo room : rooms.values()) {
                Integer housingId = room.getHousingId();
                if(housingId != null && housingId.equals(
                        housingToRemove.getId())) {
                    room.setHousingId(null);
                }
            }
        }
    }

    public void searchHousings() {
        String text = searchField.getText().toLowerCase(Locale.ROOT);
        ObservableList<HousingInfo> housingsList =
                housingsTable.getItems();
        housingsList.clear();
        for (HousingInfo housing : housings.values()) {
            if(text.length() == 0) {
                housingsList.add(housing);
            } else {
                StringBuilder summaryValue =
                        new StringBuilder();
                summaryValue.append(housing.getName());
                Pattern pattern = Pattern.compile(".*" + text + ".*");
                if(pattern.matcher(summaryValue.toString().
                        toLowerCase(Locale.ROOT)).matches()) {
                    housingsList.add(housing);
                }
            }
        }
    }
}
