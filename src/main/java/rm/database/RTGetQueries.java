package main.java.rm.database;

import java.sql.ResultSet;
import java.util.List;

public abstract class RTGetQueries implements ResultingQueries {
    protected abstract List<String> createQueries();

    public void infoGetQuery() {

    }

    @Override
    public Iterable<String> getQueriesList() {
        return null;
    }

    @Override
    public void setResultsList(Iterable<ResultSet> results) {

    }

    @Override
    public Iterable<ResultSet> getResultsList() {
        return null;
    }
}
