package main.java.rm.bean;

import javafx.beans.property.StringProperty;
public abstract class DatabaseInfo {
    private StringProperty url;
    private StringProperty port;
    private String protocol;
    private String subprotocol;

    public DatabaseInfo(String url, String port, String subprotocol) {
        protocol = "jdbc";
    }

    public void setSubprotocol(String subprotocol) {

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

    public String getSubprotocol() {
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
