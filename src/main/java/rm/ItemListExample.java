package main.java.rm;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.java.rm.bean.TeacherInfo;
import main.java.rm.bean.injection.Beans;
import main.java.rm.exceptions.NameDoesNotExistException;
import main.java.rm.view.ItemList;
import main.java.rm.view.SelectItem;

import java.net.URL;
import java.util.*;

public class ItemListExample implements Initializable {
    Map<Integer, TeacherInfo> allTeachers;
    ObjectProperty<TeacherInfo> selectedTeacher;

    @FXML
    ItemList teachers;

    @FXML
    Label selectedString;

    public void handleSelection() {
        SelectItem selectedItem = teachers.getSelectedItem();
        if(selectedItem != null) {
            selectedTeacher.set(allTeachers.get(selectedItem.getSelectableId()));
            selectedString.setText(selectedTeacher.get().getName());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedTeacher = new SimpleObjectProperty<>(null);
        try {
            allTeachers = (Map<Integer, TeacherInfo>) Beans.getContext("main").get("allTeachers");
        } catch (NameDoesNotExistException | ClassCastException e) {
            e.printStackTrace();
            allTeachers = new HashMap<>();
        }
        teachers.setSelectedStyle(getClass().getResource("/selectedItem.css").toExternalForm());
        teachers.onSelectedProperty().addListener(new ChangeListener<SelectItem>() {
            @Override
            public void changed(ObservableValue<? extends SelectItem> observableValue, SelectItem selectItem, SelectItem t1) {
                handleSelection();
            }
        });
        Label textLabel;
        SelectItem teacherItem;
        LinkedList<SelectItem> items = new LinkedList<>();
        for(Map.Entry<Integer, TeacherInfo> node : allTeachers.entrySet()) {
            teacherItem = new SelectItem(node.getKey());
            textLabel = new Label();
            textLabel.setText(node.getValue().getName());
            teacherItem.setLeft(textLabel);
            items.add(teacherItem);
        }
        teachers.setItems(items);
    }
}
