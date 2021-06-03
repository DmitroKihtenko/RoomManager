package rm;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rm.bean.*;
import rm.database.mySql.RTModifySQL;
import rm.properties.DatasourceProperty;
import rm.properties.HousingProperty;
import rm.properties.XmlDataHandler;
import rm.service.Beans;
import rm.service.Context;

public class Main extends Application {
    public static Stage stage = null;
    private static final XmlDataHandler dataHandler =
            new XmlDataHandler();

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        this.stage = stage;
        stage.show();
    }

    @Override
    public void init() {
        Notifications notifications = new Notifications();
        Datasource datasource = new Datasource();
        RTModifySQL databaseQueries = new RTModifySQL();
        databaseQueries.getProvider().setDatasource(datasource);
        ObjectProperty<RoomInfo> selectedRoom =
                new SimpleObjectProperty<>(null);
        ObjectProperty<TeacherInfo> selectedTeacher =
                new SimpleObjectProperty<>(null);
        ObjectProperty<HousingInfo> selectedHousing =
                new SimpleObjectProperty<>(null);

        dataHandler.propertiesForPath("datasource.xml",
                new DatasourceProperty(datasource));
        dataHandler.propertiesForPath("properties.xml",
                new HousingProperty());
        dataHandler.read();

        Beans.context().set("databaseQueries", databaseQueries);
        Beans.context().set("selectedRoom", selectedRoom);
        Beans.context().set("selectedTeacher", selectedTeacher);
        Beans.context().set("selectedHousing", selectedHousing);
        Beans.context().set("notifications", notifications);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}