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
     * Reading information from housings.
     *
     * @param housings empty data structure to save stored data tables housings
     */
    public void getHousings(Map<Integer, HousingInfo> housings)
            throws SQLException {

        ResultSet resultSet = getProvider().execute("SELECT * FROM housings");

        while (resultSet.next()) {
            Integer key = resultSet.getInt("Id");

            HousingInfo value = new HousingInfo(resultSet.getString("Name"));
            value.setId(key);

            housings.put(key, value);

        }
        logger.debug("SQL, SELECT * FROM housings");

    }

    /**
     * Reading information from teachers.
     *
     * @param teachers empty data structure to save stored data tables teachers
     */
    public void getTeachers(Map<Integer, TeacherInfo> teachers)
            throws SQLException {

        ResultSet resultSet = getProvider().execute("SELECT * FROM teachers");

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
     * Reading information from rooms.
     *
     * @param rooms empty data structure to save stored data tables rooms
     */
    public void getRooms(Map<Integer, RoomInfo> rooms) throws SQLException {

        ResultSet resultSet = getProvider().execute("SELECT * FROM rooms");

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
     * Reading information from rooms.
     *
     * @param access empty variable to save stored data tables rooms
     */
    public void getRtAccess(ConnectionsList access) throws SQLException {

        ResultSet resultSet = getProvider().execute("SELECT * FROM rtaccess");

        while (resultSet.next()) {

            access.setConnection(resultSet.getInt("TeacherId"),
                    resultSet.getInt("RoomId"));

        }
        logger.debug("SQL, SELECT * FROM rtaccess");

    }
}
