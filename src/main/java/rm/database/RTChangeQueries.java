package main.java.rm.database;

import java.util.List;

public abstract class RTChangeQueries implements EmptyQueries {
    protected abstract List<String> createQueryList();

    public void newRoomQuery() {

    }

    public void newTeacherQuery() {

    }

    public void changeRoomQuery() {

    }

    public void changeTeacherQuery() {

    }

    public void removeRoomQuery() {

    }

    public void removeTeacherQuery() {

    }

    public void changeConnectionQuery() {

    }

    @Override
    public Iterable<String> getQueriesList() {
        return null;
    }
}
