package rm.threads;

import javafx.application.Platform;
import rm.database.mySql.RTGetSQL;
import rm.model.Notifications;
import org.apache.log4j.Logger;
import rm.service.Assertions;

import java.sql.SQLException;

/**
 * Implementor of class {@link ThreadOperation} that checks database edit version
 */
public class DatabaseCheck implements ThreadOperation {
    private static final Logger logger =
            Logger.getLogger(DatabaseCheck.class);

    private int checkInterval;
    private Notifications notifications;
    private RTGetSQL getQueries;
    private int secondsTimeLabel;
    private Integer changesVersion;
    private boolean isEnabled;

    /**
     * Constructor, sets as default specified in parameters objects
     * @param notifications notification object
     * @param getQueries query executor object
     */
    public DatabaseCheck(Notifications notifications, RTGetSQL getQueries) {
        setGetQueries(getQueries);
        setNotifications(notifications);
        setEnabled(true);
        checkInterval = 30;
        changesVersion = null;
        secondsTimeLabel = (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * Enables or disables database edit version check
     * @param enabled true if enabled, false if disabled
     */
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    /**
     * Indicated whether there are enabled or disabled database version check
     * @return true if enabled, false if disabled
     */
    public boolean getEnabled() {
        return isEnabled;
    }

    /**
     * Indicated whether there are enabled or disabled database version check
     * @return true if enabled, false if disabled
     */
    public void setNotifications(Notifications notifications) {
        Assertions.isNotNull(notifications,
                "Notifications", logger);

        this.notifications = notifications;
    }

    /**
     * Setter for database queries executor
     * @param getQueries queries executor object
     */
    public void setGetQueries(RTGetSQL getQueries) {
        Assertions.isNotNull(getQueries,
                "Queries executor", logger);

        this.getQueries = getQueries;
    }

    /**
     * Setter for check interval parameter
     * @param interval check interval in seconds
     */
    public void setCheckInterval(int interval) {
        Assertions.isPositive(interval, "Database check interval",
                logger);

        this.checkInterval = interval;
    }

    /**
     * Getter for check interval parameter
     * @return check interval in seconds
     */
    public int getCheckInterval() {
        return checkInterval;
    }

    /**
     * Getter for database changes version parameter
     * @return database changes version parameter, can be null
     */
    public Integer getChangesVersion() {
        return changesVersion;
    }

    /**
     * Makes database edit version check, if it has been changed pushes message into notifications
     */
    @Override
    public void make() {
        if(isEnabled && System.currentTimeMillis() / 1000
                > secondsTimeLabel + checkInterval) {
            logger.info("Attempt to check database edit version");
            try {
                int receivedValue;
                getQueries.getProvider().connect();
                receivedValue = getQueries.getDatabaseChanges();
                if(changesVersion == null) {
                    changesVersion = receivedValue;
                } else {
                    if(receivedValue != changesVersion) {
                        changesVersion = receivedValue;
                        logger.info("Database edit version has " +
                                        "been changed");
                        Platform.runLater(() -> notifications.
                                push("Database has been " +
                                "changed. Please update your data"));
                    }
                }
            } catch (SQLException e) {
                logger.warn("Checking database version error: "
                        + e.getMessage());
            } finally {
                getQueries.getProvider().disconnect();
                secondsTimeLabel = (int)
                        (System.currentTimeMillis() / 1000);
            }
        }
    }
}
