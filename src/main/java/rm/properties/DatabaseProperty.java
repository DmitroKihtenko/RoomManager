package rm.properties;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import rm.bean.Datasource;
import rm.bean.User;
import rm.service.Assertions;

public class DatabaseProperty extends DatasourceProperty {
    private final static Logger logger =
            Logger.getLogger(DatabaseProperty.class);

    private User user;

    public DatabaseProperty(Datasource datasource, User user) {
        super(datasource);
        setUser(user);
    }

    public DatabaseProperty() {
        user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        Assertions.isNotNull(user, "User", logger);

        this.user = user;
    }

    @Override
    public void read(Element element) throws DocumentException {
        super.read(element);
        logger.debug("Reading user info");

        Element sourceTag = element.element("user");
        Element currentTag;
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

    @Override
    public void write(Element element) {
        super.write(element);
        logger.debug("Writing user info");

        Element sourceTag = element.addElement("user");
        Element current = sourceTag.addElement("name");
        current.addText(user.getName());
        current = sourceTag.addElement("password");
        current.addText(user.getPassword());
    }
}
