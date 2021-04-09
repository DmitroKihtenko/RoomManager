package main.java.rm.database;

import main.java.rm.bean.DatabaseInfo;
import main.java.rm.bean.User;

import java.sql.*;
import java.util.concurrent.locks.ReentrantLock;

public class QueryProvider {
    private Statement statement;
    private Connection connection;
    private final ReentrantLock lock;

    public QueryProvider() {
        lock = new ReentrantLock();
    }

    /**
     * Creates connection with specified database and user. Locks the object for other threads
     * @param database database object with database info
     * @param user user object with user info
     * @throws SQLException if something wrong with database connection
     */
    final public void connectInto(DatabaseInfo database, User user)
            throws SQLException {
        if(!lock.isHeldByCurrentThread()) {
            lock.lock();
        }
        StringBuilder connectionString = new StringBuilder();
        if(database.getProtocol() != null) {
            connectionString.append(database.getProtocol());
        }
        if(database.getSubprotocol() != null) {
            connectionString.append(":").append(database.
                    getSubprotocol());
        }
        if(database.getUrl() != null) {
            connectionString.append(":").append(database.getUrl());
        }
        if(database.getPort() != null) {
            connectionString.append(":").append(database.getPort());
        }
        if(user.getName() != null) {
            connectionString.append(":").append(user.getName());
            if(user.getPassword() != null) {
                connectionString.append(":").
                        append(user.getPassword());
            }
        }
        connection = DriverManager.
                getConnection(connectionString.toString());
    }

    /**
     * Executes sql statements to connected database
     * @param sql sql statement
     * @return ResultSet object, can be null
     * @throws SQLException if something wrong with statement execution
     */
    public ResultSet execute(String sql) throws SQLException {
        if(!lock.isHeldByCurrentThread()) {
            lock.lock();
        }
        statement = connection.createStatement();
        statement.execute(sql);
        return statement.getResultSet();
    }

    /**
     * Closes created earlier connection with database. Unlocks object for other threads even if throws any exception
     * @throws SQLException if something wrong with closing execution
     */
    final public void disconnect() throws SQLException {
        if(!lock.isHeldByCurrentThread()) {
            lock.lock();
        }
        try {
            if(connection != null) {
                statement = null;
                connection.close();
                connection = null;
            }
        } finally {
            lock.unlock();
        }
    }
}
