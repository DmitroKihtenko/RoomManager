package main.java.rm.bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

    }

    public void setUrl(String url) {

    }

    public void setPort(String port) {

    }

    public String getUrl() {
        return null;
    }

    public String getProtocol() {
        return null;
    }

    public String getSource() {
        return null;
    }

    public StringProperty urlProperty() {
        return null;
    }

    public StringProperty portProperty() {
        return null;
    }

    public String getPort() {
        return null;
    }
}
