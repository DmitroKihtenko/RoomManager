package rm.saving;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import rm.model.Datasource;
import rm.model.User;
import rm.service.Assertions;

/**
 * Class successor of class {@link DatabaseSaving} that saves info about database user
 */
public class DatabaseSaving extends DatasourceSaving {
    private final static Logger logger =
            Logger.getLogger(DatabaseSaving.class);

    private User user;

    /**
     * Constructor, sets as default database and user objects for saving
     * @param datasource datasource object
     * @param user user object
     */
    public DatabaseSaving(Datasource datasource, User user) {
        super(datasource);
        setUser(user);
    }

    /**
     * Default constructor, creates as default objects of datasource and user
     */
    public DatabaseSaving() {
        user = new User();
    }

    /**
     * Getter for user object
     * @return user object, not null
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter for user object
     * @param user user object, not null
     */
    public void setUser(User user) {
        Assertions.isNotNull(user, "User", logger);

        this.user = user;
    }

    /**
     * Reads parameters for user and datasource
     * @param element xml document attribute
     * @throws DocumentException if any error occurred while reading parameters
     */
    @Override
    public void read(Element element) throws DocumentException {
        super.read(element);
        logger.debug("Reading user info");

        Element sourceTag = element.element("user");
        Element currentTag;
        user.setPassword(null);
        for(Object current : sourceTag.elements()) {
            currentTag = (Element) current;
            if(current != null) {
                switch (currentTag.getName()) {
                    case "name":
                        user.setName(currentTag.getText());
                        break;
                    case "password":
                        user.setPassword(currentTag.getText());
                        break;
                    default:
                        throw new DocumentException("Invalid tag" +
                                currentTag.getName() +
                                " in database user property");
                }
            }
        }
    }

    /**
     * Writes parameters of user and database
     * @param element xml document attribute
     */
    @Override
    public void write(Element element) {
        super.write(element);
        logger.debug("Writing user info");

        Element sourceTag = element.addElement("user");
        Element current = sourceTag.addElement("name");
        current.addText(user.getName());
        if(user.getPassword() != null) {
            current = sourceTag.addElement("password");
            current.addText(user.getPassword());
        }
    }
}
