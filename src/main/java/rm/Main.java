package rm;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;
import rm.database.ConcurrentQueryProvider;
import rm.database.mySql.RTGetSQL;
import rm.model.*;
import rm.database.mySql.RTModifySQL;
import rm.saving.*;
import rm.service.Beans;

/**
 * Main class
 */
public class Main extends Application {
    private static final Logger logger = Logger.getLogger(Main.class);
    private static final String DATABASE_PATH = "database.xml";

    /**
     * Creates main window and gives management to controller {@link rm.controller.LoginController}
     * @param stage window object
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        /*
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            logger.fatal(Objects.requireNonNullElse(e.getMessage(),
                    e.toString()));
            Platform.exit();
        });

         */
        Parent root = FXMLLoader.load(getClass().getResource(
                "/adminPanel.fxml"));
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

        ConcurrentQueryProvider provider = new ConcurrentQueryProvider();
        Datasource datasource = new Datasource();
        User user = new User();
        XmlSavingsHandler dataSavings = new XmlSavingsHandler();
        Notifications notifications = new Notifications();
        RTGetSQL databaseQueries = new RTModifySQL();
        provider.setDatasource(datasource);
        provider.setUser(user);
        databaseQueries.setProvider(provider);
        HousingSaving housingSaving = new HousingSaving();
        RTKeysSaving rtKeysSaving = new RTKeysSaving();
        DatabaseCheckSaving checkSaving = new DatabaseCheckSaving();
        ObjectProperty<Room> selectedRoom =
                new SimpleObjectProperty<>(null);
        ObjectProperty<Teacher> selectedTeacher =
                new SimpleObjectProperty<>(null);
        ObjectProperty<HousingInfo> selectedHousing =
                new SimpleObjectProperty<>(null);

        dataSavings.propertiesForPath(DATABASE_PATH,
                new DatabaseSaving(datasource, user));
        if(!dataSavings.existsForPath(DATABASE_PATH)) {
            dataSavings.write();
        } else {
            dataSavings.read();
        }

        Beans.context().set("databaseQueries", databaseQueries);
        Beans.context().set("selectedRoom", selectedRoom);
        Beans.context().set("selectedTeacher", selectedTeacher);
        Beans.context().set("selectedHousing", selectedHousing);
        Beans.context().set("notifications", notifications);
        Beans.context().set("dataSavings", dataSavings);
        Beans.context().set("housingSaving", housingSaving);
        Beans.context().set("rtKeysSaving", rtKeysSaving);
        Beans.context().set("checkSaving", checkSaving);
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