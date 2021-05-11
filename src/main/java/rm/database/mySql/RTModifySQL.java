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
import java.util.function.Consumer;

public class RTModifySQL extends QueryExecutor {
    private static final Logger logger =
            Logger.getLogger(RTModifySQL.class);

    /**
     *
     */
    public void addTeachers(Iterable<TeacherInfo> teachers) throws SQLException {

        teachers.forEach(new Consumer<TeacherInfo>() {
            @Override
            public void accept(TeacherInfo teacherInfo) {

                if (!getProvider().isConnected()) {
                    throw new IllegalStateException(
                            "Error  No connection to Database"
                    );
                }

                try {
                    getProvider().prepare("INSERT INTO teachers (Id, Name, Surname, Patronymic) Values (?, ?, ?, ?)");

                    getProvider().setPrepareArguments(1, teacherInfo.getId(), JDBCType.INTEGER);
                    getProvider().setPrepareArguments(2, teacherInfo.getName(), JDBCType.VARCHAR);
                    getProvider().setPrepareArguments(3, teacherInfo.getSurname(), JDBCType.VARCHAR);
                    getProvider().setPrepareArguments(4, teacherInfo.getPatronymic(), JDBCType.VARCHAR);
                    getProvider().execute();

                    logger.debug("SQL, INSERT INTO teachers (Id, Name, Surname, Patronymic) Values (?, ?, ?, ?)");

                } catch (SQLException throwables) {

                    throw new IllegalStateException(
                            "Error  INSERT INTO teachers " +
                                    throwables
                    );
                }


            }
        });

    }

    /**
     *
     */
    public void removeTeachers(Iterable<TeacherInfo> teachers) {

        teachers.forEach(new Consumer<TeacherInfo>() {
            @Override
            public void accept(TeacherInfo teacherInfo) {

                if (!getProvider().isConnected()) {
                    throw new IllegalStateException(
                            "Error  No connection to Database"
                    );
                }

                try {
                    getProvider().prepare("DELETE FROM teachers WHERE Id = ?");

                    getProvider().setPrepareArguments(1, teacherInfo.getId(), JDBCType.INTEGER);
                    getProvider().execute();

                    logger.debug("SQL, DELETE FROM teachers WHERE Id = ?");

                } catch (SQLException throwables) {

                    throw new IllegalStateException(
                            "Error  DELETE FROM teachers " +
                                    throwables
                    );
                }


            }
        });
    }

    /**
     *
     */
    public void addRooms(Iterable<RoomInfo> rooms) {

        rooms.forEach(new Consumer<RoomInfo>() {
            @Override
            public void accept(RoomInfo roomInfo) {

                if (!getProvider().isConnected()) {
                    throw new IllegalStateException(
                            "Error  No connection to Database"
                    );
                }

                try {
                    getProvider().prepare("INSERT INTO rooms (Id, Number, HousingId, NotUsedReason) Values (?, ?, ?, ?)");

                    getProvider().setPrepareArguments(1, roomInfo.getId(), JDBCType.INTEGER);
                    getProvider().setPrepareArguments(2, roomInfo.getNumber(), JDBCType.VARCHAR);
                    getProvider().setPrepareArguments(3, roomInfo.getHousingId(), JDBCType.INTEGER);
                    getProvider().setPrepareArguments(4, roomInfo.getNotUsedReason(), JDBCType.VARCHAR);
                    getProvider().execute();

                    logger.debug("SQL, INSERT INTO rooms (Id, Number, HousingId, NotUsedReason) Values (?, ?, ?, ?)");

                } catch (SQLException throwables) {

                    throw new IllegalStateException(
                            "Error  INSERT INTO rooms " +
                                    throwables
                    );
                }

            }
        });
    }

    /**
     *
     */
    public void removeRooms(Iterable<RoomInfo> rooms) {

        rooms.forEach(new Consumer<RoomInfo>() {
            @Override
            public void accept(RoomInfo roomInfo) {

                if (!getProvider().isConnected()) {
                    throw new IllegalStateException(
                            "Error  No connection to Database"
                    );
                }

                try {
                    getProvider().prepare("DELETE FROM rooms WHERE Id = ?");

                    getProvider().setPrepareArguments(1, roomInfo.getId(), JDBCType.INTEGER);
                    getProvider().execute();

                    logger.debug("SQL, DELETE FROM rooms WHERE Id = ?");

                } catch (SQLException throwables) {

                    throw new IllegalStateException(
                            "Error  DELETE FROM rooms " +
                                    throwables
                    );
                }
            }
        });
    }

    /**
     *
     */
    public void addHousings(Iterable<HousingInfo> housings) {

        housings.forEach(new Consumer<HousingInfo>() {
            @Override
            public void accept(HousingInfo housingInfo) {

                if (!getProvider().isConnected()) {
                    throw new IllegalStateException(
                            "Error  No connection to Database"
                    );
                }

                try {
                    getProvider().prepare("INSERT INTO housings (Id, Name) Values (?, ?)");

                    getProvider().setPrepareArguments(1, housingInfo.getId(), JDBCType.INTEGER);
                    getProvider().setPrepareArguments(2, housingInfo.getName(), JDBCType.VARCHAR);
                    getProvider().execute();

                    logger.debug("SQL, INSERT INTO housings (Id, Name) Values (?, ?)");

                } catch (SQLException throwables) {

                    throw new IllegalStateException(
                            "Error  INSERT INTO housings " +
                                    throwables
                    );
                }
            }
        });
    }

    /**
     *
     */
    public void removeHousings(Iterable<HousingInfo> housings) {

        housings.forEach(new Consumer<HousingInfo>() {
            @Override
            public void accept(HousingInfo housingInfo) {

                if (!getProvider().isConnected()) {
                    throw new IllegalStateException(
                            "Error  No connection to Database"
                    );
                }

                try {
                    getProvider().prepare("DELETE FROM housings WHERE Id = ?");

                    getProvider().setPrepareArguments(1, housingInfo.getId(), JDBCType.INTEGER);
                    getProvider().execute();

                    logger.debug("SQL, DELETE FROM housings WHERE Id = ?");

                } catch (SQLException throwables) {

                    throw new IllegalStateException(
                            "Error  DELETE FROM housings " +
                                    throwables
                    );
                }
            }
        });
    }

    /**
     *
     */
    public void addAccess(ConnectionsList access) {

        access.getFirstIds().forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                if (!getProvider().isConnected()) {
                    throw new IllegalStateException(
                            "Error  No connection to Database"
                    );
                }

                try {
                    getProvider().prepare("INSERT INTO rtaccess (TeacherId, RoomId) Values (?, ?)");

                    getProvider().setPrepareArguments(1, access.getFirstConnections(integer), JDBCType.INTEGER);
                    getProvider().execute();

                    logger.debug("SQL, INSERT INTO rtaccess (TeacherId, RoomId) Values (?)");

                } catch (SQLException throwables) {

                    throw new IllegalStateException(
                            "Error  INSERT INTO rtaccess (TeacherId) " +
                                    throwables
                    );
                }
            }
        });

        access.getSecondIds().forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {

                if (!getProvider().isConnected()) {
                    throw new IllegalStateException(
                            "Error  No connection to Database"
                    );
                }

                try {
                    getProvider().prepare("INSERT INTO rtaccess (TeacherId, RoomId) Values (?, ?)");

                    getProvider().setPrepareArguments(2, access.getSecondConnections(integer), JDBCType.INTEGER);
                    getProvider().execute();

                    logger.debug("SQL, INSERT INTO rtaccess (TeacherId, RoomId) Values (, ?)");

                } catch (SQLException throwables) {

                    throw new IllegalStateException(
                            "Error  INSERT INTO rtaccess (RoomId) " +
                                    throwables
                    );
                }
            }
        });
    }

    /**
     *
     */
    public void removeAccess(ConnectionsList access) {

        access.getFirstIds().forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {

                if (!getProvider().isConnected()) {
                    throw new IllegalStateException(
                            "Error  No connection to Database"
                    );
                }

                try {
                    getProvider().prepare("DELETE FROM rtaccess WHERE TeacherId = ?");

                    getProvider().setPrepareArguments(1, integer, JDBCType.INTEGER);
                    getProvider().execute();

                    logger.debug("SQL, DELETE FROM rtaccess WHERE TeacherId = ?");

                } catch (SQLException throwables) {

                    throw new IllegalStateException(
                            "Error  DELETE FROM rtaccess WHERE TeacherId " +
                                    throwables
                    );
                }
            }
        });

        access.getSecondIds().forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {

                if (!getProvider().isConnected()) {
                    throw new IllegalStateException(
                            "Error  No connection to Database"
                    );
                }

                try {
                    getProvider().prepare("DELETE FROM rtaccess WHERE RoomId = ?");

                    getProvider().setPrepareArguments(1, integer, JDBCType.INTEGER);
                    getProvider().execute();

                    logger.debug("SQL, DELETE FROM rtaccess WHERE RoomId = ?");

                } catch (SQLException throwables) {

                    throw new IllegalStateException(
                            "Error  DELETE FROM rtaccess WHERE RoomId " +
                                    throwables
                    );
                }
            }
        });
    }

    /**
     *
     */
    public void provideChanges(DbChangesInfo dbChanges) {

    }
}
