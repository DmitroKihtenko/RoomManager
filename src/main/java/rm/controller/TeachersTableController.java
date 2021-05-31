package rm.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;
import rm.bean.ConnectionsList;
import rm.bean.TeacherInfo;
import rm.service.Assertions;
import rm.service.Beans;

import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

public class TeachersTableController {
    private static final Logger logger =
            Logger.getLogger(AdminController.class);
    private static final String DEF_TEACHER_NAME = "Teacher";
    private static final String NO_INIT_SYMBOL = "-";

    @FXML
    private TextField searchField;
    @FXML
    private TableView<TeacherInfo> teachersTable;
    @FXML
    private TableColumn<TeacherInfo, String> teacherSurnameCol;
    @FXML
    private TableColumn<TeacherInfo, String> teacherInitialsCol;

    private ObjectProperty<TeacherInfo> selectedTeacher;
    private ConnectionsList rtAccess;
    private HashMap<Integer, TeacherInfo> teachers;
    private ChangeListener<String> initialsChange;

    public void setTeachers(HashMap<Integer, TeacherInfo> teachers,
                            ConnectionsList rtAccess) {
        Assertions.isNotNull(teachers, "Teachers map", logger);
        Assertions.isNotNull(rtAccess, "Access connections", logger);

        this.teachers = teachers;
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
            searchField.textProperty().addListener((observableValue,
                                                    oldValue,
                                                    newValue) -> {
                if(!oldValue.equals(newValue)) {
                    searchTeachers();
                }
            });
        }
    }

    @FXML
    public void newTeacher() {
        ObservableList<TeacherInfo> teachersList =
                teachersTable.getItems();
        TeacherInfo newTeacher = new TeacherInfo(DEF_TEACHER_NAME);
        newTeacher.createUniqueId();
        teachersList.add(newTeacher);
        teachers.put(newTeacher.getId(), newTeacher);
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
            teachers.remove(teacherToDelete.getId());
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

    public void searchTeachers() {
        String text = searchField.getText();
        ObservableList<TeacherInfo> teachersList =
                teachersTable.getItems();
        teachersList.clear();
        for (TeacherInfo teacher : teachers.values()) {
            if(text.length() == 0) {
                teachersList.add(teacher);
            } else {
                StringBuilder summaryValue =
                        new StringBuilder();
                summaryValue.append(teacher.getSurname());
                if(teacher.getName() != null) {
                    summaryValue.append(teacher.getName());
                }
                if(teacher.getPatronymic() != null) {
                    summaryValue.append(teacher.getPatronymic());
                }
                Pattern pattern = Pattern.compile(".*" + text + ".*");
                if(pattern.matcher(summaryValue).matches()) {
                    teachersList.add(teacher);
                }
            }
        }
    }
}
