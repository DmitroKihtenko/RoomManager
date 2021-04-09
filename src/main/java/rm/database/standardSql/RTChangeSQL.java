package main.java.rm.database.standardSql;

import main.java.rm.bean.ConnectionsList;
import main.java.rm.bean.HousingInfo;
import main.java.rm.bean.RoomInfo;
import main.java.rm.bean.TeacherInfo;
import main.java.rm.database.QueryProvider;
import main.java.rm.database.RTDatabaseTables;

import java.sql.SQLException;
import java.util.List;
import java.util.TreeMap;

public class RTChangeSQL extends QueryProvider implements RTDatabaseTables {
    private TreeMap<Integer, RoomInfo> newRooms;
    private TreeMap<Integer, RoomInfo> updatedRooms;
    private TreeMap<Integer, RoomInfo> removedRooms;

    private TreeMap<Integer, TeacherInfo> newTeachers;
    private TreeMap<Integer, TeacherInfo> updatedTeachers;
    private TreeMap<Integer, TeacherInfo> removedTeachers;

    private TreeMap<Integer, HousingInfo> newHousings;
    private TreeMap<Integer, HousingInfo> updatedHousings;
    private TreeMap<Integer, HousingInfo> removedHousings;

    private ConnectionsList addedRtAccess;
    private ConnectionsList removedRtAccess;

    public void newRoomQuery(RoomInfo newRoom) {

    }

    public void newTeacherQuery(TeacherInfo newTeacher) {

    }

    public void newHousingQuery(HousingInfo newHousing) {

    }

    public void changeRoomQuery(RoomInfo changedRoom) {

    }

    public void changeTeacherQuery(TeacherInfo changedTeacher) {

    }

    public void changeHousingQuery(HousingInfo changedHousing) {

    }

    public void removeRoomQuery(RoomInfo removedRoom) {

    }

    public void removeTeacherQuery(TeacherInfo removedTeacher) {

    }

    public void removeHousingQuery(HousingInfo removedHousing,
                                   HousingInfo defaultHousing) {

    }

    public void changeConnectionQuery(int teacherId, int roomId,
                                      boolean isAdded) {

    }

    public void provideChanges() throws SQLException {

    }
}
