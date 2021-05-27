package rm.database.mySql;

import rm.bean.*;
import rm.database.QueryExecutor;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class RTGetSQL extends QueryExecutor {
    private static final Logger logger =
            Logger.getLogger(RTGetSQL.class);

    /**
     * Gets data about database edit version
     * @return number of database edit version
     */
    public Integer getDatabaseChanges() throws SQLException {
        logger.debug("Getting database version info");
        Integer result = null;

        ResultSet resultSet = getProvider().execute("SELECT FROM " +
                "info WHERE Data = 'ChangesVersion'");
        if(resultSet.next()) {
            result = resultSet.getInt("Value");
        }
        return result;
    }

    /**
     * Gets housing objects from database
     * @param housings map data structure to save housings objects
     */
    public void getHousings(Map<Integer, HousingInfo> housings)
            throws SQLException {
        logger.debug("Getting housings data from database");

        ResultSet resultSet = getProvider().execute("SELECT * FROM housings");

        while (resultSet.next()) {
            int key = resultSet.getInt("Id");

            HousingInfo value = new HousingInfo(resultSet.getString(
                    "Name"));
            value.setId(key);

            housings.put(key, value);
        }
    }

    /**
     * Gets teachers objects from database
     * @param teachers map object for saving new teacher objects
     */
    public void getTeachers(Map<Integer, TeacherInfo> teachers)
            throws SQLException {
        logger.debug("Getting teachers data from database");

        ResultSet resultSet = getProvider().execute("SELECT * FROM teachers");

        while (resultSet.next()) {
            int key = resultSet.getInt("Id");

            TeacherInfo value = new TeacherInfo(resultSet.getString("Name"));
            value.setSurname(resultSet.getString("Surname"));
            value.setPatronymic(resultSet.getString("Patronymic"));
            value.setId(key);

            teachers.put(key, value);
        }
    }

    /**
     * Gets rooms objects from database
     * @param rooms map data structure to save rooms objects
     */
    public void getRooms(Map<Integer, RoomInfo> rooms,
                         HousingInfo roomsHousing) throws SQLException {
        logger.debug("Getting rooms data from database");

        String sql = "SELECT * FROM housings";
        if(roomsHousing != null) {
            sql += " WHERE HousingId = " + roomsHousing.getId();
        }

        ResultSet resultSet = getProvider().execute(sql);
        String temp;

        while (resultSet.next()) {
            int key = resultSet.getInt("Id");

            RoomInfo value = new RoomInfo(resultSet.getString("Number"));
            value.setHousingId(resultSet.getInt("HousingId"));
            temp = resultSet.getString("NotUsedReason");
            if(temp != null) {
                value.setNotUsedReason(temp);
            }
            value.setId(key);

            rooms.put(key, value);
        }
    }

    /**
     * Gets access connections between teachers and rooms
     * @param access connections list object for saving data
     */
    public void getRtAccess(ConnectionsList access) throws SQLException {
        logger.debug("Getting access connections list from database");

        ResultSet resultSet = getProvider().execute("SELECT * " +
                "FROM rtaccess");

        while (resultSet.next()) {
            access.setConnection(resultSet.getInt("TeacherId"),
                    resultSet.getInt("RoomId"));
        }
    }
}
