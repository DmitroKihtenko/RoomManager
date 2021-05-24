package rm.bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import rm.service.Assertions;
import rm.service.StringLogic;
import org.apache.log4j.Logger;

public class Datasource {
    private static final Logger logger =
            Logger.getLogger(Datasource.class);

    private static final String protocol = "jdbc";
    private final StringProperty url;
    private final StringProperty port;
    private String source;
    private String databaseName;

    public Datasource() {
        url = new SimpleStringProperty("@localhost");
        port = new SimpleStringProperty("1521");
        source = "oracle:thin";
        databaseName = null;
    }

    public void setSource(String source) {
        Assertions.isNotNull(source, "Data source", logger);
        StringLogic.isVisible(source, "Source", logger);
        StringLogic.isWholeWord(source, "Source", logger);

        this.source = source;
    }

    public void setDatabaseName(String databaseName) {
        if(databaseName != null) {
            StringLogic.isVisible(databaseName, "Database name", logger);
            StringLogic.isWholeWord(databaseName, "Database name", logger);
        }

        this.databaseName = databaseName;
    }

    public void setUrl(String url) {
        Assertions.isNotNull(url, "Data url", logger);
        StringLogic.isVisible(url, "Data url", logger);
        StringLogic.isWholeWord(url, "Data url", logger);

        this.url.set(url);
    }

    public void setPort(String port) {
        Assertions.isNotNull(port, "Data port", logger);
        StringLogic.isVisible(port, "Data port", logger);
        StringLogic.isWholeWord(port, "Data port", logger);

        this.port.set(port);
    }

    public String getUrl() {
        return url.get();
    }

    public String getProtocol() {
        return protocol;
    }

    public String getSource() {
        return source;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getPort() {
        return port.get();
    }

    public StringProperty urlProperty() {
        return url;
    }

    public StringProperty portProperty() {
        return port;
    }

    public String getAddress() {
        String address = protocol + ":" + source + ":" + url.get() +
                ":" + port.get();
        if(databaseName != null) {
            address += "/" + databaseName;
        }
        return address;
    }
}