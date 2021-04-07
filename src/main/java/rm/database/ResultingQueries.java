package main.java.rm.database;

import java.sql.ResultSet;

public interface ResultingQueries extends EmptyQueries {
    void setResultsList(Iterable<ResultSet> results);
    Iterable<ResultSet> getResultsList();
}
