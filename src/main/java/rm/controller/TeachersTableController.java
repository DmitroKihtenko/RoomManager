package rm.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.log4j.Logger;
import rm.bean.ConnectionsList;
import rm.bean.TeacherInfo;
import rm.service.Assertions;
import rm.service.Beans;

import java.util.HashMap;
import java.util.Locale;

public class TeachersTableController {
    private static final Logger logger =
            Logger.getLogger(AdminController.class);
    private static final String DEF_TEACHER_NAME = "Teacher";
    private static final String NO_INIT_SYMBOL = "-";
    @FXML
    private TableView<TeacherInfo> teachersTable;
    @FXML
    private TableColumn<TeacherInfo, String> teacherSurnameCol;
    @FXML
    private TableColumn<TeacherInfo, String> teacherInitialsCol;

    private ObjectProperty<TeacherInfo> selectedTeacher;
    private ConnectionsList rtAccess;
    private ChangeListener<String> initialsChange;

    public void setTeachers(HashMap<Integer, TeacherInfo> teachers,
                            ConnectionsList rtAccess) {
        Assertions.isNotNull(teachers, "Teachers map", logger);

        this.rtAccess = rtAccess;
        ObservableList<TeacherInfo> teachersList =
                teachersTable.getItems();
        teachersList.clear();
        teachersList.addAll(teachers.values());
        teachersTable.setItems(teachersList);
    }

    @FXML
    public void initialize() {
        if(selectedTeacher == null) {
            selectedTeacher = (ObjectProperty<TeacherInfo>)
                    Beans.context().get("selectedTeacher");
            teachersTable.getSelectionModel().selectedItemProperty().
                    addListener((observableValue, teacherInfo,
                                 t1) -> {
                        if(teacherInfo != null && !teacherInfo.equals(t1)) {
                            selectedTeacher.set(t1);
                        } else if (teacherInfo == null && t1 != null) {
                            selectedTeacher.set(t1);
                        }
                    });
            teacherSurnameCol.setCellValueFactory(teacherFeatures
                    -> teacherFeatures.getValue().surnameProperty());
            teacherInitialsCol.setCellValueFactory(teacherFeatures -> {
                TeacherInfo teacher = teacherFeatures.getValue();
                SimpleStringProperty property =
                        new SimpleStringProperty(getInitials(teacher));
                if (initialsChange == null) {
                    initialsChange = (observableValue, s, t1) -> {
                        if(s != null && !s.equals(t1)) {
                            property.set(getInitials(teacher));
                        }
                    };
                }
                teacher.nameProperty().removeListener(initialsChange);
                teacher.nameProperty().addListener(initialsChange);
                teacher.patronymicProperty().removeListener(
                        initialsChange);
                teacher.patronymicProperty().addListener(
                        initialsChange);
                return property;
            });
        }
    }

    @FXML
    public void newTeacher() {
        ObservableList<TeacherInfo> teachersList =
                teachersTable.getItems();
        TeacherInfo newTeacher = new TeacherInfo(DEF_TEACHER_NAME);
        teachersList.add(newTeacher);
        teachersTable.getSelectionModel().select(newTeacher);
    }

    @FXML
    public void deleteTeacher() {
        ObservableList<TeacherInfo> teachersList =
                teachersTable.getItems();
        TeacherInfo teacherToDelete = teachersTable.getSelectionModel().
                getSelectedItem();
        if(teacherToDelete != null) {
            rtAccess.removeFirstConnections(teacherToDelete.getId());
            teachersList.remove(teacherToDelete);
        }
    }

    private String getInitials(TeacherInfo teacher) {
        String initials = NO_INIT_SYMBOL + ".";
        if(teacher.getName() != null) {
            initials = String.valueOf(teacher.getName().charAt(0)).
                    toUpperCase(Locale.ROOT) + ".";
        }
        if(teacher.getPatronymic() != null) {
            initials += String.valueOf(teacher.getPatronymic().
                    charAt(0)).toUpperCase(Locale.ROOT) + ".";
        } else {
            initials += NO_INIT_SYMBOL + ".";
        }
        return initials;
    }
}
