package main.java.rm.database;

import main.java.rm.bean.Datasource;
import main.java.rm.bean.User;
import main.java.rm.service.Assertions;
import org.apache.log4j.Logger;

import java.sql.*;

public class QueryProvider {
    private static final Logger logger =
            Logger.getLogger(QueryProvider.class);

    private Statement statement;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Datasource datasource;
    private User user;

    public Datasource getDatasource() {
        return datasource;
    }

    public User getUser() {
        return user;
    }

    public void setDatasource(Datasource datasource) {
        Assertions.isNotNull(datasource, "Datasource", logger);

        this.datasource = datasource;
    }

    public void setUser(User user) {
        Assertions.isNotNull(user, "User", logger);

        this.user = user;
    }

    public void connect(String address, String user, String password)
            throws SQLException {
        Assertions.isNotNull(address, "Database address", logger);
        Assertions.isNotNull(user, "User string", logger);
        Assertions.isNotNull(password, "Password string", logger);

        if(connection != null) {
            throw new IllegalStateException(
                    "Connection already exists"
            );
        }
        connection = DriverManager.getConnection(address,
                user, password);
        logger.debug("Connected to " + address);
    }

    public void connect() throws SQLException {
        StringBuilder connectionString = new StringBuilder();
        if(datasource.getProtocol() != null) {
            connectionString.append(datasource.getProtocol());
        }
        if(datasource.getSource() != null) {
            connectionString.append(":").append(datasource.
                    getSource());
        }
        if(datasource.getUrl() != null) {
            connectionString.append(":").append(datasource.getUrl());
        }
        if(datasource.getPort() != null) {
            connectionString.append(":").append(datasource.getPort());
        }
        if(user.getName() != null) {
            connectionString.append(":").append(user.getName());
            if(user.getPassword() != null) {
                connectionString.append(":").
                        append(user.getPassword());
            }
        }
        connect(connectionString.toString(), user.getName(),
                user.getPassword());
    }

    public ResultSet execute(String sql) throws SQLException {
        Assertions.isNotNull(sql, "Sql statement", logger);

        statement = connection.createStatement();
        statement.execute(sql);
        return statement.getResultSet();
    }

    public ResultSet execute() throws SQLException {
        if(preparedStatement == null) {
            throw new IllegalStateException(
                    "Prepared statement is not created"
            );
        }
        return preparedStatement.executeQuery();
    }

    public void prepare(String sql) throws SQLException {
        Assertions.isNotNull(sql, "Sql statement", logger);

        preparedStatement = connection.prepareStatement(sql);
    }

    public void setPrepareArguments(int index, Object parameter,
                                    SQLType type) throws SQLException {
        if(preparedStatement != null) {
            preparedStatement.setObject(index, parameter, type);
        }
    }

    public void disconnect() {
        statement = null;
        try {
            if(connection != null) {
                connection.close();

                logger.debug("Disconnected");
            }
        } catch (SQLException e) {
            logger.warn("Disconnection error: " + e.getMessage());
        } finally {
            connection = null;
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public static void setDriver(String driverClass)
            throws ClassNotFoundException {
        Assertions.isNotNull(driverClass,
                "Driver class string", logger);

        Class.forName(driverClass);
    }
}
