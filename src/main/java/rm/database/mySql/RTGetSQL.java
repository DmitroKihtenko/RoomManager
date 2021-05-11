package main.java.rm.database.mySql;

import main.java.rm.bean.*;
import main.java.rm.database.QueryExecutor;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class RTGetSQL extends QueryExecutor {
    private static final Logger logger =
            Logger.getLogger(RTGetSQL.class);

    /**
     *
     */
    public Integer getDatabaseChanges() throws SQLException {

        return null;
    }

    /**
     *
     */
    public void getHousings(Map<Integer, HousingInfo> housings)
            throws SQLException {

        if (!getProvider().isConnected()) {
            throw new IllegalStateException(
                    "Error  No connection to Database"
            );
        }

        getProvider().prepare("SELECT * FROM housings");
        ResultSet resultSet = getProvider().execute();

        while (resultSet.next()) {
            Integer key = resultSet.getInt("Id");

            HousingInfo value = new HousingInfo(resultSet.getString("Name"));
            value.setId(key);

            housings.put(key, value);
        }

        logger.debug("SQL, SELECT * FROM housings");
    }

    /**
     *
     */
    public void getTeachers(Map<Integer, TeacherInfo> teachers)
            throws SQLException {

        if (!getProvider().isConnected()) {
            throw new IllegalStateException(
                    "Error  No connection to Database"
            );
        }

        getProvider().prepare("SELECT * FROM teachers");
        ResultSet resultSet = getProvider().execute();

        while (resultSet.next()) {

            Integer key = resultSet.getInt("Id");

            TeacherInfo value = new TeacherInfo(resultSet.getString("Name"));
            value.setSurname(resultSet.getString("Surname"));
            value.setPatronymic(resultSet.getString("Patronymic"));
            value.setId(key);
            teachers.put(key, value);

        }

        logger.debug("SQL, SELECT * FROM teachers");
    }

    /**
     *
     */
    public void getRooms(Map<Integer, RoomInfo> rooms) throws SQLException {

        if (!getProvider().isConnected()) {
            throw new IllegalStateException(
                    "Error  No connection to Database"
            );
        }

        getProvider().prepare("SELECT * FROM rooms");
        ResultSet resultSet = getProvider().execute();

        while (resultSet.next()) {

            Integer key = resultSet.getInt("Id");

            RoomInfo value = new RoomInfo(resultSet.getString("Number"));
            value.setHousingId(resultSet.getInt("HousingId"));
            value.setNotUsedReason(resultSet.getString("NotUsedReason"));
            value.setId(key);

            rooms.put(key, value);

        }

        logger.debug("SQL, SELECT * FROM rooms");
    }

    /**
     *
     */
    public void getRtAccess(ConnectionsList access) throws SQLException {

        if (!getProvider().isConnected()) {
            throw new IllegalStateException(
                    "Error  No connection to Database"
            );
        }

        getProvider().prepare("SELECT * FROM rtaccess");
        ResultSet resultSet = getProvider().execute();

        while (resultSet.next()) {

            access.setConnection(resultSet.getInt("TeacherId"),
                    resultSet.getInt("RoomId"));

        }

        logger.debug("SQL, SELECT * FROM rtaccess");
    }
}
