package main.java.rm.database.mySql;

import main.java.rm.bean.*;
import main.java.rm.database.QueryExecutor;

import java.sql.SQLException;
import java.util.Map;

public class RTGetSQL extends QueryExecutor {
    public Integer getDatabaseChanges() throws SQLException {
        return null;
    }

    public void getHousings(Map<Integer, HousingInfo> housings)
            throws SQLException {
    }

    public void getTeachers(Map<Integer, TeacherInfo> teachers)
            throws SQLException {
    }

    public void getRooms(Map<Integer, RoomInfo> rooms) throws SQLException {
    }

    public void getRtAccess(ConnectionsList access) throws SQLException {
    }
}
