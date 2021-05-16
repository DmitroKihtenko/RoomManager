package main.java.rm.controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;

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



    public void roomList()//список аудиторий
    {

        String str[] = {"Н-108", "Н-107", "Н-106", "Н-109", "Н-110", "Н-207", "Н-212", "Ц-110/1", "Ц-221", "Ц-301", "ЕТ-220", "ЕТ-315"};
        ArrayList<TestRoom> rooms = new ArrayList<>();

        rooms.add(new TestRoom("Н-107","доступна","Альошина А.І","не зайнята", "Вахтер"));
        rooms.add(new TestRoom("Н-101","недоступна","Альошина А.І","Зайнята", "Колесніков"));
        rooms.add(new TestRoom("Н-108","доступна","Альошина А.І","Зайнята", "Вахтер"));


        for (int i = 0; i < rooms.size(); i++) {
            CheckBox c = new CheckBox(str[i]);
            room.getChildren().add(c);
            room.setVgap(15);

            room.setTileAlignment(Pos.CENTER_LEFT);

            c.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (c.isSelected())
                        System.out.println(c.getText());
                }
            });

        }

    }

    public void teacherList()//список преподавателей
    {
        ArrayList<TestTeacher> teachers = new ArrayList<>();

        teachers.add(new TestTeacher("Колесніков Валерій Анатолійович","Н-107","Н-109"));
        teachers.add(new TestTeacher("Лаврик Тетяна Володимирівна","Н-107","Н-109"));
        teachers.add(new TestTeacher("Назаренко Людмила Дмитрівна","Н-107","Н-109"));
        teachers.add(new TestTeacher("Зарецький Микола Олександрович","Н-107","Н-109"));
        teachers.add(new TestTeacher("Шовкопляс Оксана Анатоліївна","Н-107","Н-109"));

        for (int i = 0; i < teachers.size(); i++) {
            CheckBox c = new CheckBox(teachers.get(i).getName());
            teacher.getChildren().add(c);
            teacher.setVgap(15);
            teacher.setTileAlignment(Pos.CENTER_LEFT);

            c.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (c.isSelected())
                        System.out.println(c.getText());
                }
            });

        }
    }

    public void radioBtn() {
        ToggleGroup group = new ToggleGroup();
        onlyFree.setToggleGroup(group);
        onlyEmployed.setToggleGroup(group);
    }

    @FXML
    void initialize() {
        roomList();
        teacherList();
        radioBtn();

    }
}

