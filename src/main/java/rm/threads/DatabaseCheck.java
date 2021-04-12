package main.java.rm.threads;

import main.java.rm.bean.Notifications;
import main.java.rm.database.DatabaseChangesInfo;
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
    private DatabaseChangesInfo databaseChanges;
    private Notifications notifications;

    public DatabaseCheck(DatabaseChangesInfo databaseChanges,
                         Notifications notifications) {

    }

    public void setDatabaseChanges(DatabaseChangesInfo databaseChanges) {

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
