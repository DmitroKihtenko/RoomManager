package main.java.rm.controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;

import java.net.URL;
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

        /*for (int i = 0; i < st.length; i++) {
            CheckBox c = new CheckBox(st[i]);
            checkboxes_room.add(c);
        }

        for (int i = 0; i < checkboxes_room.size(); i++) {
            room.getChildren().add(checkboxes_room.get(i));
            room.setVgap(15);
            room.setTileAlignment(Pos.CENTER_LEFT);

        }*/

        for (int i = 0; i < str.length; i++) {
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
        String str[] = {"Колесніков Валерій Анатолійович",
                "Лаврик Тетяна Володимирівна",
                "Назаренко Людмила Дмитрівна",
                "Зарецький Микола Олександрович",
                "Шовкопляс Оксана Анатоліївна",
                "Шуда Ірина Олександрівна",
                "Лаврик Тетяна Володимирівна",
                "Назаренко Людмила Дмитрівна",
                "Зарецький Микола Олександрович",
                "Шовкопляс Оксана Анатоліївна",
                "Шуда Ірина Олександрівна"};

        for (int i = 0; i < str.length; i++) {
            CheckBox c = new CheckBox(str[i]);
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

