package rm.threads;

import rm.model.Notifications;
import rm.database.DbChangesInfo;
import org.apache.log4j.Logger;

public class DatabaseCheck implements ThreadOperation {
    private static final Logger logger =
            Logger.getLogger(DatabaseCheck.class);

    public enum CheckInterval {
        ONE_MINUTE(60),
        FIVE_MINUTES(300),
        HALF_AN_HOUR(1800),
        HOUR(3600),
        THREE_HOURS(10800);

        private final int timeInMinutes;

        public int time() {
            return timeInMinutes;
        }

        CheckInterval(int time) {
            timeInMinutes = time;
        }
    }
    private CheckInterval checkInterval;
    private DbChangesInfo databaseChanges;
    private Notifications notifications;

    public DatabaseCheck(DbChangesInfo databaseChanges,
                         Notifications notifications) {

    }

    public void setDatabaseChanges(DbChangesInfo databaseChanges) {

    }

    public void setNotifications(Notifications notifications) {

    }

    public void setCheckInterval(CheckInterval interval) {
        checkInterval = interval;
    }

    public CheckInterval getCheckInterval() {
        return checkInterval;
    }

    @Override
    public void make() {
    }
}
