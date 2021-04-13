package main.java.rm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.rm.bean.TeacherInfo;
import main.java.rm.bean.injection.BeanContext;
import main.java.rm.bean.injection.Beans;
import main.java.rm.bean.injection.TreeMapContext;
import main.java.rm.exceptions.NameAlreadyExistsException;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        startInit();
        Parent root = FXMLLoader.load(getClass().getResource("/itemListExample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void startInit() throws NameAlreadyExistsException {
        Map<Integer, TeacherInfo> teachers = new HashMap<>(5);

        BeanContext mainContext = new TreeMapContext();

        TeacherInfo newTeacher = new TeacherInfo("Vlad");
        newTeacher.createId();
        teachers.put(newTeacher.getId(), newTeacher);

        newTeacher = new TeacherInfo("Dima");
        newTeacher.createId();
        teachers.put(newTeacher.getId(), newTeacher);

        newTeacher = new TeacherInfo("Egor");
        newTeacher.createId();
        teachers.put(newTeacher.getId(), newTeacher);

        newTeacher = new TeacherInfo("Denis");
        newTeacher.createId();
        teachers.put(newTeacher.getId(), newTeacher);

        newTeacher = new TeacherInfo("Danil");
        newTeacher.createId();
        teachers.put(newTeacher.getId(), newTeacher);

        mainContext.set("allTeachers", teachers);
        Beans.setContext("main", mainContext);
    }
}
