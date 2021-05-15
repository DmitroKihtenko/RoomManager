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
import java.util.Iterator;
import java.util.function.Consumer;

public class RTModifySQL extends QueryExecutor {

    private static final Logger logger =
            Logger.getLogger(RTModifySQL.class);

    /**
     * Method of Adding Data TeacherInfo to table teachers
     *
     * @param teachers list with the details of the TeacherInfo’s class.
     */
    public void addTeachers(Iterable<TeacherInfo> teachers) throws SQLException {

        for (TeacherInfo teacherInfo : teachers) {

            try {
                getProvider().prepare("INSERT INTO teachers (Id, Name, Surname, Patronymic) Values (?, ?, ?, ?)");

                getProvider().setPrepareArguments(1, teacherInfo.getId(), JDBCType.INTEGER);
                getProvider().setPrepareArguments(2, teacherInfo.getName(), JDBCType.VARCHAR);
                getProvider().setPrepareArguments(3, teacherInfo.getSurname(), JDBCType.VARCHAR);
                getProvider().setPrepareArguments(4, teacherInfo.getPatronymic(), JDBCType.VARCHAR);

                getProvider().execute();

            } catch (SQLException e) {
                logger.warn("SQL query execution error - INSERT INTO teachers ");
                throw e;

            }
        }
    }

    /**
     * Deleting labels of the table teachers
     *
     * @param teachers list with the details of the TeacherInfo’s class
     */
    public void removeTeachers(Iterable<TeacherInfo> teachers) throws SQLException {

        for (TeacherInfo teacherInfo : teachers) {

            try {
                getProvider().prepare("DELETE FROM teachers WHERE Id = ?");

                getProvider().setPrepareArguments(1, teacherInfo.getId(), JDBCType.INTEGER);

                getProvider().execute();

            } catch (SQLException e) {
                logger.warn("SQL query execution error - DELETE FROM teachers ");
                throw e;

            }
        }
    }

    /**
     * Method of Adding Data RoomInfo to table rooms
     *
     * @param rooms list with the details of the RoomInfo’s class.
     */
    public void addRooms(Iterable<RoomInfo> rooms) throws SQLException {

        for (RoomInfo roomInfo : rooms) {

            try {
                getProvider().prepare("INSERT INTO rooms (Id, Number, HousingId, NotUsedReason) Values (?, ?, ?, ?)");

                getProvider().setPrepareArguments(1, roomInfo.getId(), JDBCType.INTEGER);
                getProvider().setPrepareArguments(2, roomInfo.getNumber(), JDBCType.VARCHAR);
                getProvider().setPrepareArguments(3, roomInfo.getHousingId(), JDBCType.INTEGER);
                getProvider().setPrepareArguments(4, roomInfo.getNotUsedReason(), JDBCType.VARCHAR);

                getProvider().execute();

            } catch (SQLException e) {
                logger.warn("SQL query execution error - INSERT INTO rooms ");
                throw e;

            }
        }
    }

    /**
     * Deleting labels of the table rooms
     *
     * @param rooms list with the details of the RoomInfo’s class
     */
    public void removeRooms(Iterable<RoomInfo> rooms) throws SQLException {

        for (RoomInfo roomInfo : rooms) {

            try {
                getProvider().prepare("DELETE FROM rooms WHERE Id = ?");

                getProvider().setPrepareArguments(1, roomInfo.getId(), JDBCType.INTEGER);

                getProvider().execute();

            } catch (SQLException e) {
                logger.warn("SQL query execution error - DELETE FROM rooms ");
                throw e;

            }
        }
    }

    /**
     * Method of Adding Data HousingInfo to table housings
     *
     * @param housings list with the details of the HousingInfo’s class.
     */
    public void addHousings(Iterable<HousingInfo> housings) throws SQLException {

        for (HousingInfo housingInfo : housings) {

            try {
                getProvider().prepare("INSERT INTO housings (Id, Name) Values (?, ?)");

                getProvider().setPrepareArguments(1, housingInfo.getId(), JDBCType.INTEGER);
                getProvider().setPrepareArguments(2, housingInfo.getName(), JDBCType.VARCHAR);

                getProvider().execute();

            } catch (SQLException e) {
                logger.warn("SQL query execution error - INSERT INTO housings ");
                throw e;

            }
        }
    }

    /**
     * Deleting labels of the table housings
     *
     * @param housings list with the details of the HousingInfo’s class
     */
    public void removeHousings(Iterable<HousingInfo> housings) throws SQLException {

        for (HousingInfo housingInfo : housings) {

            try {
                getProvider().prepare("DELETE FROM housings WHERE Id = ?");

                getProvider().setPrepareArguments(1, housingInfo.getId(), JDBCType.INTEGER);

                getProvider().execute();

            } catch (SQLException e) {
                logger.warn("SQL query execution error - DELETE FROM housings ");
                throw e;

            }
        }
    }

    /**
     * Method of Adding Data ConnectionsList to table rtaccess
     *
     * @param access variable ConnectionsList’s class.
     */
    public void addAccess(ConnectionsList access) throws SQLException {

        Iterator<Integer> iterator1 = access.getFirstIds().iterator();
        Iterator<Integer> iterator2 = access.getSecondIds().iterator();

        while (iterator1.hasNext() && iterator2.hasNext()) {

            try {
                getProvider().prepare("INSERT INTO rtaccess (TeacherId, RoomId) Values (?, ?)");

                getProvider().setPrepareArguments(1, access.getFirstConnections(iterator1.next()), JDBCType.INTEGER);
                getProvider().setPrepareArguments(2, access.getSecondConnections(iterator2.next()), JDBCType.INTEGER);

                getProvider().execute();

            } catch (SQLException e) {
                logger.warn("SQL query execution error - INSERT INTO rtaccess (TeacherId) ");
                throw e;

            }
        }
    }

    /**
     * Deleting labels of the table rtaccess
     *
     * @param access variable ConnectionsList’s class.
     */
    public void removeAccess(ConnectionsList access) throws SQLException {

        Iterator<Integer> iterator1 = access.getFirstIds().iterator();
        Iterator<Integer> iterator2 = access.getSecondIds().iterator();

        while (iterator1.hasNext()) {

            try {
                getProvider().prepare("DELETE FROM rtaccess WHERE TeacherId = ?");

                getProvider().setPrepareArguments(1, iterator1.next(), JDBCType.INTEGER);

                getProvider().execute();

            } catch (SQLException e) {
                logger.warn("SQL query execution error - DELETE FROM rtaccess (TeacherId) ");
                throw e;

            }
        }
    }

    /**
     *
     */
    public void provideChanges(DbChangesInfo dbChanges) {
    }

}
