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

/**
 * Main class
 */
public class Main extends Application {
    private static final Logger logger = Logger.getLogger(Main.class);
    private static final String DATASOURCE_FILE = "datasource.xml";

    /**
     * Creates main window and gives management to controller {@link rm.controller.LoginController}
     * @param stage window object
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            logger.fatal(Objects.requireNonNullElse(e.getMessage(),
                    e.toString()));
            Platform.exit();
        });
        Parent root = FXMLLoader.load(getClass().getResource(
                "/login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    /**
     * Reads data from file and creates all objects needed for other controllers
     */
    @Override
    public void init() {
        logger.info("Program started");

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

        dataSavings.propertiesForPath(DATASOURCE_FILE,
                new DatasourceSaving(datasource));
        if(!dataSavings.existsForPath(DATASOURCE_FILE)) {
            dataSavings.write();
        } else {
            dataSavings.read();
        }

        Beans.context().set("databaseQueries", databaseQueries);
        Beans.context().set("selectedRoom", selectedRoom);
        Beans.context().set("selectedTeacher", selectedTeacher);
        Beans.context().set("selectedHousing", selectedHousing);
        Beans.context().set("notifications", notifications);
    }

    /**
     * Method that called when program finishes
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        ((RTModifySQL) Beans.context().get("databaseQueries")).
                getProvider().disconnect();
        logger.info("Program finished");
        super.stop();
    }

    /**
     * Main method, program entry point
     * @param args program arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}