package main.java.rm.controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField searchBox_left;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private RadioButton onlyEmployed;

    @FXML
    private RadioButton onlyFree;

    @FXML
    private TextField searchBox_right;

    @FXML
    private TilePane room;

    @FXML
    private TilePane teacher;

    @FXML
    private Label LabelTeacher;

    @FXML
    private Label LabelRoom;


    public void printRoomList(ArrayList<TestRoom> rooms)//список аудиторий
    {

        for (int i = 0; i < rooms.size(); i++) {

            CheckBox c = new CheckBox(rooms.get(i).getName());
            room.getChildren().add(c);
            room.setVgap(15);
            room.setTileAlignment(Pos.CENTER_LEFT);

            int iterator = i;
            c.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    if (!c.isSelected())
                        LabelRoom.setText("");

                    else {
                        System.out.println(c.getText());
                        LabelRoom.setText("Аудиторія:\n" + rooms.get(iterator).getName() + "\nСтатус: " +
                                rooms.get(iterator).getStatus() + "\nМає доступ: " + rooms.get(iterator).getAccess() +
                                "\nАудиторія:  " + rooms.get(iterator).getCondition() + "\nКлюч у " + rooms.get(iterator).getLastKey());
                        LabelRoom.setAlignment(Pos.TOP_LEFT);
                    }
                }
            });
        }


    }

    public void printTeacherList(ArrayList<TestTeacher> teachers)//список преподавателей
    {
        for (int i = 0; i < teachers.size(); i++) {
            CheckBox c = new CheckBox(teachers.get(i).getName());
            teacher.getChildren().add(c);
            teacher.setVgap(15);
            teacher.setTileAlignment(Pos.CENTER_LEFT);

            int iterator = i;
            c.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (!c.isSelected())
                        LabelTeacher.setText("");

                    else {
                        System.out.println(c.getText());
                        LabelTeacher.setText("Викладач:\n" + teachers.get(iterator).getName() + "\nМає доступ до:\n" +
                                teachers.get(iterator).getRoom() + "\nМає ключ до:\n" + teachers.get(iterator).getKey());
                    }
                }
            });
        }
    }

    public void roomInfo() {
        ArrayList<TestRoom> rooms = new ArrayList<>();

        rooms.add(new TestRoom("Н-107", "доступна", "Альошина А.І", "не зайнята", "Вахтер"));
        rooms.add(new TestRoom("Н-101", "недоступна", "Альошина А.І", "Зайнята", "Колесніков"));
        rooms.add(new TestRoom("Н-108", "доступна", "Альошина А.І", "Зайнята", "Вахтер"));

        printRoomList(rooms);
    }



    private static int radio;

    public void teacherInfo() {
        ArrayList<TestTeacher> teachers = new ArrayList<>();

        teachers.add(new TestTeacher("Колесніков Валерій Анатолійович", "Н-107", "Н-109"));
        teachers.add(new TestTeacher("Лаврик Тетяна Володимирівна", "Ц-107", "ЕТ-109"));
        teachers.add(new TestTeacher("Назаренко Людмила Дмитрівна", "Н-107", "не має"));
        teachers.add(new TestTeacher("Зарецький Микола Олександрович", "Н-221", "Н-101"));
        teachers.add(new TestTeacher("Шовкопляс Оксана Анатоліївна", "Н-105", "Ц-223"));

        printTeacherList(teachers);
    }


    public void radioBtn() {
        ToggleGroup group = new ToggleGroup();
        onlyFree.setToggleGroup(group);
        onlyEmployed.setToggleGroup(group);

        onlyFree.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("свободные");
                radio = 1;
            }
        });

        onlyEmployed.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("занятые");
                radio = 2;
            }
        });

    }

    @FXML
    void initialize() {

        roomInfo();
        teacherInfo();
        radioBtn();

    }
}

