package main.java.rm.database;

import main.java.rm.bean.DatabaseInfo;

public class DatabaseChangesInfo extends DatabaseInfo {
    private long changesVersion;

    public DatabaseChangesInfo(String url, String port, String subprotocol) {
        super(url, port, subprotocol);
        changesVersion = 0;
    }

    public long getChangesVersion() {
        return changesVersion;
    }

    public void setChangesVersion(long changesVersion) {
        this.changesVersion = changesVersion;
    }
}
