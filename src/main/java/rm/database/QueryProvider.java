package rm.database;

import rm.model.Datasource;
import rm.model.User;
import rm.service.Assertions;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Objects;

/**
 * A class that encapsulates the logic of working with the JDBC, leaving only the query execution methods for the database
 */
public class QueryProvider {
    private static final Logger logger =
            Logger.getLogger(QueryProvider.class);

    private Statement statement;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Datasource datasource;
    private User user;

    /**
     * Default constructor, creates default objects of database and user
     */
    public QueryProvider() {
        datasource = new Datasource();
        user = new User();
    }

    /**
     * Getter for datasource object
     * @return datasource object
     */
    public Datasource getDatasource() {
        return datasource;
    }

    /**
     * Getter for user object
     * @return user object
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter for datasource object
     * @param datasource datasource object, not null
     */
    public void setDatasource(Datasource datasource) {
        Assertions.isNotNull(datasource, "Datasource", logger);

        this.datasource = datasource;
    }

    /**
     * Setter for user object
     * @param user user object, not null
     */
    public void setUser(User user) {
        Assertions.isNotNull(user, "User", logger);

        this.user = user;
    }

    /**
     * Creates connection with specified database for specified user and his password
     * @param address database uri address
     * @param user username
     * @param password user password
     * @throws SQLException
     */
    public void connect(String address, String user, String password)
            throws SQLException {
        Assertions.isNotNull(address, "Database address", logger);
        Assertions.isNotNull(user, "User string", logger);
        Assertions.isNotNull(password, "Password string", logger);

        if(connection != null) {
            disconnect();
        }
        try {
            connection = DriverManager.getConnection(address,
                    user, password);
            logger.debug("Connected to " + address);
        } catch (SQLException e) {
            logger.error("Failed connection to " + address +
                    " with error: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Creates connection with specified database object for specified user object
     * @throws SQLException
     */
    public void connect() throws SQLException {
        connect(datasource.getAddress(), user.getName(),
                Objects.requireNonNullElse(user.getPassword(), ""));
    }

    /**
     * Executes some sql query
     * @param sql sql query
     * @return result object or null if the query does not return a result
     * @throws SQLException
     */
    public ResultSet execute(String sql) throws SQLException {
        Assertions.isNotNull(sql, "Sql statement", logger);

        if(connection == null) {
            throw new SQLException("Connection is not set");
        }
        statement = connection.createStatement();
        statement.execute(sql);
        return statement.getResultSet();
    }

    /**
     * Executes created earlier prepared statement
     * @return result object or null if the query does not return a result
     * @throws IllegalStateException if prepared statement was not created
     * @throws SQLException
     */
    public ResultSet execute() throws SQLException {
        if(preparedStatement == null) {
            throw new IllegalStateException(
                    "Prepared statement is not created"
            );
        }
        preparedStatement.execute();
        return preparedStatement.getResultSet();
    }

    /**
     * Creates new prepared statement for database
     * @param sql sql query
     * @throws SQLException
     */
    public void prepare(String sql) throws SQLException {
        Assertions.isNotNull(sql, "Sql statement", logger);

        preparedStatement = connection.prepareStatement(sql);
    }

    /**
     * Sets arguments for prepared statement
     * @param index number of argument in statement
     * @param parameter value of argument
     * @param type type of argument in database
     * @throws SQLException
     */
    public void setPrepareArguments(int index, Object parameter,
                                    SQLType type) throws SQLException {
        if(preparedStatement != null) {
            preparedStatement.setObject(index, parameter, type);
        }
    }

    /**
     * Closes connection with database if it exists
     */
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

    public static void setDriver(String driverClass)
            throws ClassNotFoundException {
        Assertions.isNotNull(driverClass,
                "Driver class string", logger);

        Class.forName(driverClass);
    }
}
