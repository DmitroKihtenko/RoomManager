package main.java.rm.database;

import main.java.rm.bean.DatabaseInfo;
import main.java.rm.bean.User;

import java.sql.*;

public class QueryProvider {
    private Statement statement;
    private Connection connection;

    public void connectInto(DatabaseInfo database, User user)
            throws SQLException {
        connection = DriverManager.getConnection(database.getProtocol()
        + ":" + database.getSubprotocol() + ":" + database.getUrl() +
                ":" + database.getPort(), user.getName(),
                user.getPassword());
    }

    public ResultSet execute(String sql) throws SQLException {
        statement = connection.createStatement();
        statement.execute(sql);
        return statement.getResultSet();
    }

    public void disconnect() throws SQLException {
        statement = null;
        connection.close();
        connection = null;
    }
}
