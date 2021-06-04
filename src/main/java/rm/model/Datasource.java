package rm.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import rm.service.Assertions;
import rm.service.StringLogic;
import org.apache.log4j.Logger;

/**
 * Class that contains all datasource info for connecting to some database server
 */
public class Datasource {
    private static final Logger logger =
            Logger.getLogger(Datasource.class);

    private static final String protocol = "jdbc";
    private final StringProperty url;
    private final StringProperty port;
    private String source;
    private String databaseName;

    /**
     * Default constructor. Sets as the default data for connecting to mysql database
     */
    public Datasource() {
        url = new SimpleStringProperty("//localhost");
        port = new SimpleStringProperty("3306");
        source = "mysql";
        databaseName = "roommanager";
    }

    /**
     * Setter for driver source or subprotocol
     * @param source fragment of database driver source
     */
    public void setSource(String source) {
        Assertions.isNotNull(source, "Data source", logger);
        StringLogic.isVisible(source, "Data source", logger);
        StringLogic.isWholeWord(source, "Data source", logger);

        this.source = source;
    }

    /**
     * Setter for database name
     * @param databaseName database name for connection, can be null
     */
    public void setDatabaseName(String databaseName) {
        if (databaseName != null) {
            StringLogic.isVisible(databaseName, "Database name", logger);
            StringLogic.isWholeWord(databaseName, "Database name", logger);
        }

        this.databaseName = databaseName;
    }

    /**
     * Setter for database url
     * @param url database url for connection, not null
     */
    public void setUrl(String url) {
        Assertions.isNotNull(url, "Data url", logger);
        StringLogic.isVisible(url, "Data url", logger);
        StringLogic.isWholeWord(url, "Data url", logger);

        this.url.set(url);
    }

    /**
     * Setter for port for connection
     * @param port port value, not null
     */
    public void setPort(String port) {
        Assertions.isNotNull(port, "Data port", logger);
        StringLogic.isVisible(port, "Data port", logger);
        StringLogic.isWholeWord(port, "Data port", logger);

        this.port.set(port);
    }

    /**
     * Getter for connection url
     * @return url for connection, not null
     */
    public String getUrl() {
        return url.get();
    }

    /**
     * Getter for connection protocol
     * @return protocol for connection, not null
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Getter for connection source or subrococl
     * @return source or subrococl for connection, not null
     */
    public String getSource() {
        return source;
    }

    /**
     * Getter for connection database name
     * @return database name for connection, can be null
     */
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * Getter for connection port
     * @return port for connection, not null
     */
    public String getPort() {
        return port.get();
    }

    /**
     * Used to track url property changes
     * @return string property for java fx mvc
     */
    public StringProperty urlProperty() {
        return url;
    }

    /**
     * Used to track port property changes
     * @return string property for java fx mvc
     */
    public StringProperty portProperty() {
        return port;
    }

    /**
     * Created string of connection address to database in jdbc format
     * @return string address for connection
     */
    public String getAddress() {
        String address = protocol + ":" + source + ":" + url.get() +
                ":" + port.get();
        if (databaseName != null) {
            address += "/" + databaseName;
        }
        return address;
    }

    @Override
    public String toString() {
        String result = "";
        result += "Protocol: " + getProtocol() + ", ";
        result += "Url: " + getUrl() + ", ";
        result += "Port: " + getPort() + ", ";
        result += "Source: " + getSource() + ", ";
        result += "DatabaseName: " + getDatabaseName();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Datasource)) {
            return false;
        }
        Datasource guest = (Datasource) obj;
        return getAddress().equals(guest.getSource());
    }
}
