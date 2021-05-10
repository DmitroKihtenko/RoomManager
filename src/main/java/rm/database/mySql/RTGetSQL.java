package main.java.rm.database.mySql;

import main.java.rm.bean.*;
import main.java.rm.database.QueryExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

public class RTGetSQL extends QueryExecutor {
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

        ResultSet resultSet = getProvider().execute();

        while (resultSet.next()) {
            Integer key = resultSet.getInt("Id");

            HousingInfo value = new HousingInfo(resultSet.getString("Name"));

            housings.put(key, value);


        }

    }
    /**
     *
     */
    public void getTeachers(Map<Integer, TeacherInfo> teachers)
            throws SQLException {

        ResultSet resultSet = getProvider().execute();

        while (resultSet.next()) {

            Integer key = resultSet.getInt("Id");

            TeacherInfo value = new TeacherInfo(resultSet.getString("Name"));
            value.setSurname(resultSet.getString("Surname"));
            value.setPatronymic(resultSet.getString("Patronymic"));

            teachers.put(key, value);

        }
    }
    /**
     *
     */
    public void getRooms(Map<Integer, RoomInfo> rooms) throws SQLException {

        ResultSet resultSet = getProvider().execute();

        while (resultSet.next()) {

            Integer key = resultSet.getInt("Id");

            RoomInfo value = new RoomInfo(resultSet.getString("Number"));
            value.setHousingId(resultSet.getInt("HousingId"));
            value.setNotUsedReason(resultSet.getString("NotUsedReason"));

            rooms.put(key, value);

        }
    }
    /**
     *
     */
    public void getRtAccess(ConnectionsList access) throws SQLException {

        ResultSet resultSet = getProvider().execute();

        while (resultSet.next()) {

            access.setConnection(resultSet.getInt("TeacherId"),
                    resultSet.getInt("RoomId"));


        }
    }
}
