package rm.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;
import rm.model.Teacher;
import rm.model.TeacherInfo;
import rm.service.Assertions;
import rm.service.Beans;

import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

public class TeachersTableController {
    private static final Logger logger =
            Logger.getLogger(TeachersTableController.class);
    private static final String NO_INIT_SYMBOL = "-";

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Teacher> teachersTable;
    @FXML
    private TableColumn<Teacher, String> teacherNameCol;

    private final ObjectProperty<Teacher> selectedTeacher;
    private HashMap<Integer, Teacher> teachers;

    /**
     * Default constructor. Object initialization
     */
    public TeachersTableController() {
        selectedTeacher = (ObjectProperty<Teacher>)
                Beans.context().get("selectedTeacher");
    }

    /**
     * Initialization teachers, rtAccess, teacher list
     * @param teachers cloned teachers
     */
    public void setTeachers(HashMap<Integer, Teacher> teachers) {
        Assertions.isNotNull(teachers, "Teachers map", logger);

        this.teachers = teachers;
        ObservableList<Teacher> teachersList =
                teachersTable.getItems();
        teachersList.clear();
        teachersList.addAll(teachers.values());
        teachersTable.setItems(teachersList);
    }

    /**
     * Setter listeners for initialized objects
     */
    @FXML
    public void initialize() {
        if(selectedTeacher != null) {
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
            selectedTeacher.addListener((observableValue, teacher, t1)
                    -> {
                if(t1 != null) {
                    logger.info("Selected teacher: " + t1);
                }
            });
        }
    }

    /**
     * Method that creates the short name of the teacher
     * @param teacher teacher info
     * @return name selected teacher of string
     */
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

    /**
     * Search teacher in teachers list
     */
    public void searchTeachers() {
        String text = searchField.getText().toLowerCase(Locale.ROOT);
        ObservableList<Teacher> teachersList =
                teachersTable.getItems();
        teachersList.clear();
        for (Teacher teacher : teachers.values()) {
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
        if(!text.equals("")) {
            logger.info("Searched teacher by pattern " + text);
        }
    }
}
