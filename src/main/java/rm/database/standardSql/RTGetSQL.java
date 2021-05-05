package main.java.rm.database.standardSql;

import main.java.rm.bean.*;
import main.java.rm.database.QueryProvider;
import main.java.rm.database.RTDatabaseTables;

import java.sql.SQLException;
import java.util.Map;

public class RTGetSQL extends QueryProvider implements RTDatabaseTables {
    public Integer getDatabaseChanges() throws SQLException {
        return null;
    }

    public Map<Integer, HousingInfo> getHousings()
            throws SQLException{
        return null;
    }

    public Map<Integer, TeacherInfo> getTeachers()
            throws SQLException {
        return null;
    }

    public Map<Integer, RoomInfo> getRooms() throws SQLException {
        return null;
    }

    public ConnectionsList getRtAccess() throws SQLException {
        return null;
    }
}
