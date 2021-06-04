package rm;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;
import rm.model.*;
import rm.database.mySql.RTModifySQL;
import rm.saving.DatasourceSaving;
import rm.saving.XmlSavingsHandler;
import rm.service.Beans;

import java.util.Objects;

public class Main extends Application {
    private static final Logger logger = Logger.getLogger(Main.class);

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    @Override
    public void init() {
        XmlSavingsHandler dataSavings = new XmlSavingsHandler();
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

        dataSavings.propertiesForPath("datasource.xml",
                new DatasourceSaving(datasource));
        dataSavings.read();

        Beans.context().set("databaseQueries", databaseQueries);
        Beans.context().set("selectedRoom", selectedRoom);
        Beans.context().set("selectedTeacher", selectedTeacher);
        Beans.context().set("selectedHousing", selectedHousing);
        Beans.context().set("notifications", notifications);
    }

    @Override
    public void stop() throws Exception {
        ((RTModifySQL) Beans.context().get("databaseQueries")).
                getProvider().disconnect();
        super.stop();
    }

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            logger.fatal(Objects.requireNonNullElse(e.getMessage(),
                    e.toString()));
            Platform.exit();
        }
    }
}