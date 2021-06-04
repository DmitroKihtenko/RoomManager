package rm.database.mySql;

import rm.model.*;
import rm.database.QueryExecutor;
import org.apache.log4j.Logger;
import rm.service.Assertions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Class successor of class {@link QueryExecutor} that has methods for receiving data from database
 */
public class RTGetSQL extends QueryExecutor {
    private static final Logger logger =
            Logger.getLogger(RTGetSQL.class);
    private HousingInfo defaultHousing;
    private RoomInfo defaultRoom;
    private TeacherInfo defaultTeacher;

    /**
     * Setter for default object of housing that used to create another object while getting data from database
     * @param defaultHousing object of housing
     */
    public void setDefaultHousing(HousingInfo defaultHousing) {
        this.defaultHousing = defaultHousing;
    }

    /**
     * Setter for default object of room that used to create another object while getting data from database
     * @param defaultRoom object of room
     */
    public void setDefaultRoom(RoomInfo defaultRoom) {
        this.defaultRoom = defaultRoom;
    }

    /**
     * Setter for default object of teacher that used to create another object while getting data from database
     * @param defaultTeacher object of teacher
     */
    public void setDefaultTeacher(TeacherInfo defaultTeacher) {
        this.defaultTeacher = defaultTeacher;
    }

    /**
     * Creates new object of class {@link TeacherInfo} or clones default teacher object if it has been set
     * @return object of teacher
     * @throws SQLException
     */
    protected TeacherInfo newTeacher() throws SQLException {
        TeacherInfo object;
        if(defaultTeacher == null) {
            object = new TeacherInfo("Teacher");
        } else {
            try {
                object = (TeacherInfo) defaultTeacher.clone();
            } catch (CloneNotSupportedException e) {
                throw new SQLException(e.getMessage());
            }
        }
        return object;
    }

    /**
     * Creates new object of class {@link RoomInfo} or clones default room object if it has been set
     * @return object of teacher
     * @throws SQLException
     */
    protected RoomInfo newRoom() throws SQLException {
        RoomInfo object;
        if(defaultRoom == null) {
            object = new RoomInfo("0");
        } else {
            try {
                object = (RoomInfo) defaultRoom.clone();
            } catch (CloneNotSupportedException e) {
                throw new SQLException(e.getMessage());
            }
        }
        return object;
    }

    /**
     * Creates new object of class {@link HousingInfo} or clones default housing object if it has been set
     * @return object of teacher
     * @throws SQLException
     */
    protected HousingInfo newHousing() throws SQLException {
        HousingInfo object;
        if(defaultHousing == null) {
            object = new HousingInfo("0");
        } else {
            try {
                object = (HousingInfo) defaultHousing.clone();
            } catch (CloneNotSupportedException e) {
                throw new SQLException(e.getMessage());
            }
        }
        return object;
    }

    /**
     * Gets data about database edit version
     * @return number of database edit version
     */
    public int getDatabaseChanges() throws SQLException {
        logger.debug("Getting database version info");
        int result = 0;

        try {
            ResultSet resultSet = getProvider().execute("SELECT FROM" +
                    " info WHERE Data = 'ChangesVersion'");
            if(resultSet.next()) {
                result = resultSet.getInt("Value");
            }
        } catch (SQLException e) {
            logger.debug("Get database version info error: " +
                    e.getMessage());
            throw e;
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

        try {
            ResultSet resultSet = getProvider().execute("SELECT * FROM housings");
            while (resultSet.next()) {
                int key = resultSet.getInt("Id");

                HousingInfo value = newHousing();
                value.setName(resultSet.getString(
                        "Name"));
                value.setId(key);

                housings.put(key, value);
            }
        } catch (SQLException e) {
            logger.error("Get housings data error: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Gets teachers objects from database
     * @param teachers map object for saving new teacher objects
     */
    public void getTeachers(Map<Integer, TeacherInfo> teachers)
            throws SQLException {
        logger.debug("Getting teachers data from database");

        try {
            ResultSet resultSet = getProvider().execute("SELECT * " +
                    "FROM teachers");
            while (resultSet.next()) {
                int key = resultSet.getInt("Id");

                TeacherInfo value = newTeacher();
                value.setSurname(resultSet.getString("Surname"));
                value.setName(resultSet.getString("Name"));
                value.setPatronymic(resultSet.getString("Patronymic"));
                value.setId(key);

                teachers.put(key, value);
            }
        } catch (SQLException e) {
            logger.error("Get teachers data error: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Gets rooms objects from database
     * @param rooms map data structure to save rooms objects
     */
    public void getRooms(Map<Integer, RoomInfo> rooms,
                         HousingInfo roomsHousing) throws SQLException {
        logger.debug("Getting rooms data from database");

        String sql = "SELECT * FROM rooms";
        if(roomsHousing != null) {
            sql += " WHERE HousingId = " + roomsHousing.getId();
        }
        try {
            ResultSet resultSet = getProvider().execute(sql);
            String temp;
            while (resultSet.next()) {
                int key = resultSet.getInt("Id");

                RoomInfo value = newRoom();
                value.setNumber(resultSet.getString("Number"));
                value.setHousingId(resultSet.getInt("HousingId"));
                temp = resultSet.getString("NotUsedReason");
                if(temp != null) {
                    value.setNotUsedReason(temp);
                }
                value.setId(key);

                rooms.put(key, value);
            }
        } catch (SQLException e) {
            logger.error("Get rooms data error: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Gets access connections between teachers and rooms
     * @param access connections list object for saving data
     */
    public void getRtAccess(ConnectionsList access) throws SQLException {
        logger.debug("Getting access connections list from database");

        try {
            ResultSet resultSet = getProvider().execute("SELECT * " +
                    "FROM rtaccess");
            while (resultSet.next()) {
                access.setConnection(resultSet.getInt("TeacherId"),
                        resultSet.getInt("RoomId"));
            }
        } catch (SQLException e) {
            logger.error("Get access connections data error: " +
                    e.getMessage());
            throw e;
        }
    }
}
