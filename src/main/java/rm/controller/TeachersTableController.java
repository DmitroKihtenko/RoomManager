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
import rm.model.ConnectionsList;
import rm.model.TeacherInfo;
import rm.service.Assertions;
import rm.service.Beans;

import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

public class TeachersTableController {
    private static final Logger logger =
            Logger.getLogger(TeachersTableController.class);
    private static final String DEF_TEACHER_NAME = "Teacher";
    private static final String NO_INIT_SYMBOL = "-";

    @FXML
    private TextField searchField;
    @FXML
    private TableView<TeacherInfo> teachersTable;
    @FXML
    private TableColumn<TeacherInfo, String> teacherNameCol;

    private ObjectProperty<TeacherInfo> selectedTeacher;
    private ConnectionsList rtAccess;
    private HashMap<Integer, TeacherInfo> teachers;

    private ChangeListener<Object> refreshListener;

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
            refreshListener = (observableValue, o, t1) ->
                    teachersTable.refresh();
            teachersTable.getItems().addListener((ListChangeListener
                    <TeacherInfo>) change -> {
                while(change.next()) {
                    if(change.wasAdded()) {
                        for (TeacherInfo teacher : change.
                                getAddedSubList()) {
                            teacher.nameProperty().
                                    addListener(refreshListener);
                            teacher.surnameProperty().
                                    addListener(refreshListener);
                            teacher.patronymicProperty().
                                    addListener(refreshListener);
                        }
                    } else if(change.wasRemoved()) {
                        for (TeacherInfo teacher : change.getRemoved()) {
                            teacher.nameProperty().
                                    removeListener(refreshListener);
                            teacher.surnameProperty().
                                    removeListener(refreshListener);
                            teacher.patronymicProperty().
                                    removeListener(refreshListener);
                        }
                    }
                }
            });
            teachersTable.getSelectionModel().selectedItemProperty().
                    addListener((observableValue, teacherInfo,
                                 t1) -> {
                        if(teacherInfo != null && !teacherInfo.equals(t1)) {
                            selectedTeacher.set(t1);
                        } else if (teacherInfo == null && t1 != null) {
                            selectedTeacher.set(t1);
                        }
                    });
            teacherNameCol.setCellValueFactory(teacherFeatures ->
                new SimpleStringProperty(getShortName(
                        teacherFeatures.getValue())));
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

    private String getShortName(TeacherInfo teacher) {
        String name = teacher.getSurname() + " ";
        if(teacher.getName() != null) {
            name += String.valueOf(teacher.getName().charAt(0)).
                    toUpperCase(Locale.ROOT) + ".";
        } else {
            name += NO_INIT_SYMBOL + ".";
        }
        name += " ";
        if(teacher.getPatronymic() != null) {
            name += String.valueOf(teacher.getPatronymic().
                    charAt(0)).toUpperCase(Locale.ROOT) + ".";
        } else {
            name += NO_INIT_SYMBOL + ".";
        }
        return name;
    }

    public void searchTeachers() {
        String text = searchField.getText().toLowerCase(Locale.ROOT);
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
                if(pattern.matcher(summaryValue.toString().
                        toLowerCase(Locale.ROOT)).matches()) {
                    teachersList.add(teacher);
                }
            }
        }
    }
}
