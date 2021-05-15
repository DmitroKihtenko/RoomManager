package main.java.rm.database.mySql;

import main.java.rm.bean.ConnectionsList;
import main.java.rm.bean.HousingInfo;
import main.java.rm.bean.RoomInfo;
import main.java.rm.bean.TeacherInfo;
import main.java.rm.database.DbChangesInfo;
import main.java.rm.database.QueryExecutor;
import org.apache.log4j.Logger;

import java.sql.JDBCType;
import java.sql.SQLException;

public class RTModifySQL extends QueryExecutor {
    private static final Logger logger =
            Logger.getLogger(RTModifySQL.class);
    /**
     * Adds teachers to database
     * @param teachers list with the details of the TeacherInfo’s class.
     */
    public void addTeachers(Iterable<TeacherInfo> teachers)
            throws SQLException {
        logger.debug("Adding teachers to database");

        for (TeacherInfo teacherInfo : teachers) {
            try {
                getProvider().prepare("INSERT INTO teachers " +
                        "(Id, Name, Surname, Patronymic) Values (?, ?, ?, ?)");

                getProvider().setPrepareArguments(1,
                        teacherInfo.getId(), JDBCType.INTEGER);
                getProvider().setPrepareArguments(2,
                        teacherInfo.getName(), JDBCType.VARCHAR);
                getProvider().setPrepareArguments(3,
                        teacherInfo.getSurname(), JDBCType.VARCHAR);
                getProvider().setPrepareArguments(4,
                        teacherInfo.getPatronymic(), JDBCType.VARCHAR);

                getProvider().execute();
            } catch (SQLException e) {
                logger.warn("Error while adding teachers to database");
                throw e;
            }
        }
    }

    /**
     * Deleting teachers from database
     * @param teachers list with the details of the TeacherInfo’s class
     */
    public void removeTeachers(Iterable<TeacherInfo> teachers)
            throws SQLException {
        for (TeacherInfo teacherInfo : teachers) {
            try {
                getProvider().prepare("DELETE FROM teachers " +
                        "WHERE Id = ?");

                getProvider().setPrepareArguments(1,
                        teacherInfo.getId(), JDBCType.INTEGER);

                getProvider().execute();
            } catch (SQLException e) {
                logger.warn("Error while adding teachers to database");
                throw e;
            }
        }
    }

    /**
     * Method of Adding Data RoomInfo to table rooms
     * @param rooms list with the details of the RoomInfo’s class.
     */
    public void addRooms(Iterable<RoomInfo> rooms) throws SQLException {
        logger.debug("Adding rooms to database");

        for (RoomInfo roomInfo : rooms) {
            try {
                getProvider().prepare("INSERT INTO rooms (Id, " +
                        "Number, HousingId, NotUsedReason) Values " +
                        "(?, ?, ?, ?)");

                getProvider().setPrepareArguments(1,
                        roomInfo.getId(), JDBCType.INTEGER);
                getProvider().setPrepareArguments(2,
                        roomInfo.getNumber(), JDBCType.VARCHAR);
                getProvider().setPrepareArguments(3,
                        roomInfo.getHousingId(), JDBCType.INTEGER);
                getProvider().setPrepareArguments(4,
                        roomInfo.getNotUsedReason(), JDBCType.VARCHAR);

                getProvider().execute();
            } catch (SQLException e) {
                logger.warn("Error while adding rooms to database");
                throw e;
            }
        }
    }

    /**
     * Deletes rooms from database
     * @param rooms list with rooms to delete
     */
    public void removeRooms(Iterable<RoomInfo> rooms) throws SQLException {
        logger.debug("Deleting rooms from database");

        for (RoomInfo roomInfo : rooms) {
            try {
                getProvider().prepare("DELETE FROM rooms" +
                        " WHERE Id = ?");

                getProvider().setPrepareArguments(1, roomInfo.getId(),
                        JDBCType.INTEGER);

                getProvider().execute();
            } catch (SQLException e) {
                logger.warn("Error while deleting rooms from " +
                        "database");
                throw e;
            }
        }
    }

    /**
     * Adds housings to database
     * @param housings list of housings to add
     */
    public void addHousings(Iterable<HousingInfo> housings) throws SQLException {
        logger.debug("Adding housings to database");

        for (HousingInfo housingInfo : housings) {
            try {
                getProvider().prepare("INSERT INTO housings " +
                        "(Id, Name) Values (?, ?)");

                getProvider().setPrepareArguments(1,
                        housingInfo.getId(), JDBCType.INTEGER);
                getProvider().setPrepareArguments(2,
                        housingInfo.getName(), JDBCType.VARCHAR);

                getProvider().execute();
            } catch (SQLException e) {
                logger.warn("Error while adding housings to database");
                throw e;
            }
        }
    }

    /**
     * Removes housings from database
     * @param housings list with the details of the HousingInfo’s class
     */
    public void removeHousings(Iterable<HousingInfo> housings) throws SQLException {
        logger.debug("Deleting housings from database");

        for (HousingInfo housingInfo : housings) {
            try {
                getProvider().prepare("DELETE FROM housings " +
                        "WHERE Id = ?");

                getProvider().setPrepareArguments(1,
                        housingInfo.getId(), JDBCType.INTEGER);

                getProvider().execute();
            } catch (SQLException e) {
                logger.warn("Error while deleting housings from" +
                        " database");
                throw e;
            }
        }
    }

    /**
     * Adds new connections of access between teachers and rooms
     * @param access list of access connections
     */
    public void addAccess(ConnectionsList access) throws SQLException {
        logger.debug("Adding access connections to database");

        for (Integer teacherId : access.getFirstIds()) {
            for (Integer roomId : access.getFirstConnections(
                    teacherId)) {
                try {
                    getProvider().prepare("INSERT INTO rtaccess" +
                            "(TeacherId, RoomId) Values (?, ?)");

                    getProvider().setPrepareArguments(1,
                            teacherId,
                            JDBCType.INTEGER);
                    getProvider().setPrepareArguments(2,
                            roomId,
                            JDBCType.INTEGER);

                    getProvider().execute();
                } catch (SQLException e) {
                    logger.warn("Error while adding access" +
                            "connections to database");
                    throw e;
                }
            }
        }
    }

    /**
     * Deletes connections of access between teachers and rooms
     * @param access list of access connections
     */
    public void removeAccess(ConnectionsList access)
            throws SQLException {
        logger.debug("Deleting access connections from database");

        for (Integer teacherId : access.getFirstIds()) {
            for (Integer roomId : access.getFirstConnections(
                    teacherId)) {
                try {
                    getProvider().prepare("DELETE FROM rtaccess" +
                            "WHERE TeacherId = ? and RoomId = ?");

                    getProvider().setPrepareArguments(1,
                            teacherId,
                            JDBCType.INTEGER);
                    getProvider().setPrepareArguments(2,
                            roomId,
                            JDBCType.INTEGER);

                    getProvider().execute();
                } catch (SQLException e) {
                    logger.warn("Error while deleting access" +
                            "connections from database");
                    throw e;
                }
            }
        }
    }

    /**
     *
     */
    public void provideChanges(DbChangesInfo dbChanges) {

    }
}
