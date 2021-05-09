package main.java.rm.database;

import main.java.rm.bean.Datasource;

public class DbChangesInfo extends Datasource {
    private long changesVersion;

    public DbChangesInfo() {
        changesVersion = -1;
    }

    /**
     * Returns number of current data changes version got from database
     * @return Returns number of current data changes version got from database
     */
    public long getChangesVersion() {
        return changesVersion;
    }

    /**
     * Sets version of current data got from database
     * @param changesVersion number of current data changes version
     */
    public void setChangesVersion(long changesVersion) {
        this.changesVersion = changesVersion;
    }
}
