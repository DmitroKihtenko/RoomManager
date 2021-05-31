package rm.database.mySql;

import rm.bean.ConnectionsList;
import rm.bean.HousingInfo;
import rm.bean.RoomInfo;
import rm.bean.TeacherInfo;
import rm.database.DbChangesInfo;
import org.apache.log4j.Logger;

import java.sql.JDBCType;
import java.sql.SQLException;

public class RTModifySQL extends RTGetSQL {
    private static final Logger logger =
            Logger.getLogger(RTModifySQL.class);
    /**
     * Adds teachers to database
     * @param teachers list with the details of the TeacherInfo’s class.
     */
    public void addTeachers(Iterable<TeacherInfo> teachers)
            throws SQLException {
        logger.debug("Adding teachers to database");

        getProvider().prepare("INSERT INTO teachers " +
                "(Id, Name, Surname, Patronymic) Values (?, ?, ?, ?)");
        for (TeacherInfo teacherInfo : teachers) {
            try {
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
        logger.debug("Deleting teachers from database");

        getProvider().prepare("DELETE FROM teachers " +
                "WHERE Id = ?");
        for (TeacherInfo teacherInfo : teachers) {
            try {
                getProvider().setPrepareArguments(1,
                        teacherInfo.getId(), JDBCType.INTEGER);

                getProvider().execute();
            } catch (SQLException e) {
                logger.warn("Error while adding teachers to database");
                throw e;
            }
        }
    }

    public void updateTeachers(Iterable<TeacherInfo> teachers)
            throws SQLException {
        logger.debug("Updating teachers in database");

        getProvider().prepare("UPDATE teachers " +
                "SET Name = ?, SET Surname = ?, SET " +
                "Patronymic = ? WHERE Id = ?");
        for (TeacherInfo teacherInfo : teachers) {
            try {
                getProvider().setPrepareArguments(1,
                        teacherInfo.getName(), JDBCType.VARCHAR);
                getProvider().setPrepareArguments(2,
                        teacherInfo.getSurname(), JDBCType.VARCHAR);
                getProvider().setPrepareArguments(3,
                        teacherInfo.getPatronymic(), JDBCType.VARCHAR);
                getProvider().setPrepareArguments(4,
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

        getProvider().prepare("INSERT INTO rooms (Id, " +
                "Number, HousingId, NotUsedReason) Values " +
                "(?, ?, ?, ?)");
        for (RoomInfo roomInfo : rooms) {
            try {
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

        getProvider().prepare("DELETE FROM rooms" +
                " WHERE Id = ?");
        for (RoomInfo roomInfo : rooms) {
            try {
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

    public void updateRooms(Iterable<RoomInfo> rooms)
            throws SQLException {
        logger.debug("Updating rooms in database");

        getProvider().prepare("UPDATE rooms SET Number = ?, " +
                "HousingId = ?, NotUsedReason = ? WHERE Id = ?");
        for (RoomInfo roomInfo : rooms) {
            try {
                getProvider().setPrepareArguments(1,
                        roomInfo.getNumber(),
                        JDBCType.VARCHAR);
                getProvider().setPrepareArguments(2,
                        roomInfo.getHousingId(),
                        JDBCType.INTEGER);
                getProvider().setPrepareArguments(3,
                        roomInfo.getNotUsedReason(),
                        JDBCType.VARCHAR);
                getProvider().setPrepareArguments(4,
                        roomInfo.getId(),
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

        getProvider().prepare("INSERT INTO housings " +
                "(Id, Name) Values (?, ?)");
        for (HousingInfo housingInfo : housings) {
            try {
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

        getProvider().prepare("DELETE FROM housings " +
                "WHERE Id = ?");
        for (HousingInfo housingInfo : housings) {
            try {
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

    public void updateHousings(Iterable<HousingInfo> housings)
            throws SQLException {
        logger.debug("Updating housings in database");

        getProvider().prepare("UPDATE housings SET Name = ? " +
                "WHERE Id = ?");
        for (HousingInfo housingInfo : housings) {
            try {
                getProvider().setPrepareArguments(1,
                        housingInfo.getId(), JDBCType.VARCHAR);
                getProvider().setPrepareArguments(2,
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

        getProvider().prepare("INSERT INTO rtaccess" +
                "(TeacherId, RoomId) Values (?, ?)");
        for (Integer teacherId : access.getFirstIds()) {
            for (Integer roomId : access.getFirstConnections(
                    teacherId)) {
                try {
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

        getProvider().prepare("DELETE FROM rtaccess" +
                "WHERE TeacherId = ? and RoomId = ?");
        for (Integer teacherId : access.getFirstIds()) {
            for (Integer roomId : access.getFirstConnections(
                    teacherId)) {
                try {
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
     * Saves data about database edit version
     * @param dbChanges Object that contains changes version
     */
    public void provideChanges(DbChangesInfo dbChanges)
            throws SQLException {
        logger.debug("Saving database edit version");

        try {
            getProvider().execute("UPDATE info SET Value = " +
                    "Value + 1 WHERE Data = 'ChangesVersion'");
        } catch (SQLException e) {
            logger.warn("Error while saving database edit version");
            throw e;
        }
    }
}
