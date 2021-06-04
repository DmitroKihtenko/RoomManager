package rm.saving;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import rm.database.mySql.RTGetSQL;
import rm.model.Notifications;
import rm.service.Assertions;
import rm.threads.DatabaseCheck;

/**
 * Class used to save info about check operation of database version
 */
public class DatabaseCheckSaving implements XmlSaving {
    private static final Logger logger =
            Logger.getLogger(DatabaseCheckSaving.class);
    private DatabaseCheck databaseCheck;

    /**
     * Default constructor, creates database check operation object default parameters
     */
    public DatabaseCheckSaving() {
        databaseCheck = new DatabaseCheck(new Notifications(),
                new RTGetSQL());
    }

    /**
     * Getter for database check operation object
     * @return database check operation object
     */
    public DatabaseCheck getDatabaseCheck() {
        return databaseCheck;
    }

    /**
     * Setter for database check operation object
     * @param databaseCheck database check operation object
     */
    public void setDatabaseCheck(DatabaseCheck databaseCheck) {
        Assertions.isNotNull(databaseCheck,
                "Database check operation", logger);

        this.databaseCheck = databaseCheck;
    }

    /**
     * Reads data about check-interval parameter in database check object
     * @param element xml document element
     * @throws DocumentException if check-interval is not number or interval setter throws any exception
     */
    @Override
    public void read(Element element) throws DocumentException {
        Element checkTag = element.element("database-check");
        Element currentTag = checkTag.element("enabled");
        databaseCheck.setEnabled(Boolean.parseBoolean(
                currentTag.getText()));
        currentTag = checkTag.element("interval");
        try {
            databaseCheck.setCheckInterval(Integer.
                    parseInt(currentTag.getText()));
        } catch (NumberFormatException e) {
            throw new DocumentException("Value of database " +
                    "check interval must be integer number");
        } catch (IllegalArgumentException e) {
            throw new DocumentException(e.getMessage());
        }
    }

    /**
     * Writes data about check-interval parameter in database check object
     * @param element xml document element
     */
    @Override
    public void write(Element element) {
        Element checkTag = element.addElement("database-check");
        Element currentTag = checkTag.addElement("enabled");
        currentTag.setText(String.valueOf(databaseCheck.getEnabled()));
        currentTag = element.addElement("database-check");
        currentTag.setText(String.valueOf(databaseCheck.
                getCheckInterval()));
    }
}
