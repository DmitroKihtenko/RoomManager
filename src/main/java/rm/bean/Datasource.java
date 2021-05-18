package main.java.rm.bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.rm.service.Assertions;
import main.java.rm.service.StringLogic;
import org.apache.log4j.Logger;

public class Datasource {
    private static final Logger logger =
            Logger.getLogger(Datasource.class);

    private static final String protocol = "jdbc";
    private final StringProperty url;
    private final StringProperty port;
    private String source;

    public Datasource() {
        url = new SimpleStringProperty("@localhost");
        port = new SimpleStringProperty("1521");
        source = "oracle:thin";
    }

    public void setSource(String source) {
        Assertions.isNotNull(source, "Data source", logger);
        StringLogic.isVisible(source, "Source", logger);
        StringLogic.isWholeWord(source, "Source", logger);

        this.source = source;
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

    public String getPort() {
        return port.get();
    }

    public StringProperty urlProperty() {
        return url;
    }

    public StringProperty portProperty() {
        return port;
    }
}