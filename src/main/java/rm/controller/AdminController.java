package rm.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import rm.Main;
import rm.bean.RoomInfo;
import rm.bean.TeacherInfo;
import rm.bean.User;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;
import rm.database.mySql.RTModifySQL;
import rm.service.Beans;

public class AdminController implements Initializable {
    private static final Logger logger =
            Logger.getLogger(AdminController.class);
    @FXML
    private AnchorPane parent;
    @FXML
    private TableView<RoomInfo> roomsTable;
    @FXML
    private TableView<TeacherInfo> teachersTable;
    @FXML
    private TableColumn<RoomInfo, String> roomIdCol;
    @FXML
    private TableColumn<RoomInfo, String> roomNameCol;
    @FXML
    private TableColumn<RoomInfo, CheckBox> editCol;
    @FXML
    private TableColumn<TeacherInfo, String> teacherNameCol;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField patronymicTextField;

    private RTModifySQL getSql;

    private HashMap<Integer, RoomInfo> rooms;
    private HashMap<Integer, TeacherInfo> teachers;

    TeacherInfo temp;

    private double xOffSet = 0;
    private double yOffSet = 0;

    ObservableList<RoomInfo> NamesList = FXCollections.observableArrayList();
    ObservableList<TeacherInfo> TeachersList = FXCollections.observableArrayList();

    @Override
    public void initialize (URL url, ResourceBundle rb) {
        if(getSql == null) {
            getSql = (RTModifySQL) Beans.context().get("databaseQueries");

            User user = getSql.getProvider().getUser();


            user.setName("root");
            //user.setPassword("number1298UA");

            rooms = new HashMap<>();
            teachers = new HashMap<>();
        }
        //text();
        loadDate();
        makeStageDragable();
    }

    @FXML
    private void minimize_stage(MouseEvent event) {
        Main.stage.setIconified(true);
    }

    @FXML
    private void close_app(MouseEvent event) {
        System.exit(0);
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
    private void refreshTable() {
        NamesList.clear();
        NamesList.addAll(rooms.values());

        TeachersList.clear();
        TeachersList.addAll(teachers.values());
    }

    private void loadDate() {

        try {
            getSql.getProvider().connect();
            getSql.getRooms(rooms, null);
            getSql.getTeachers(teachers);

        } catch (SQLException e) {
            logger.warn("Rooms get error: " + e.getMessage());
        }
        refreshTable();

        roomNameCol.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getNumber()));

        roomsTable.setItems(NamesList);

        teacherNameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TeacherInfo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TeacherInfo, String> p) {
                SimpleStringProperty smp = new SimpleStringProperty(p.getValue().getSurname() + " " +
                        p.getValue().getName() + " " + p.getValue().getPatronymic());
                return smp;
            }
        });
        teachersTable.setItems(TeachersList);


    }

    @FXML
    private void addNewName(MouseEvent mouseEvent) {

    }


    public void selectContractContractTab(MouseEvent mouseEvent) {


        temp = teachersTable.getSelectionModel().getSelectedItem();
        nameTextField.setText(temp.getName());
        surnameTextField.setText(temp.getSurname());
        patronymicTextField.setText(temp.getPatronymic());

        renameName(null);
    }

    public void renameName(ActionEvent actionEvent) {
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String patronymic = patronymicTextField.getText();
        //System.out.println(temp.getName());
        temp.setName(name);
        temp.setSurname(surname);
        temp.setPatronymic(patronymic);

        refreshTable();
        //System.out.println(temp.getName());
    }

    private void makeStageDragable() {
        parent.setOnMousePressed((event) -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });
        parent.setOnMouseDragged((event) -> {
            Main.stage.setX(event.getScreenX() - xOffSet);
            Main.stage.setY(event.getScreenY() - yOffSet);
            Main.stage.setOpacity(0.8f);
        });
        parent.setOnDragDone((event) -> {
            Main.stage.setOpacity(1.0f);
        });
        parent.setOnMouseReleased((event) -> {
            Main.stage.setOpacity(1.0f);
        });
    }
}
