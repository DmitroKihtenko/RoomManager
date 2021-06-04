package rm.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class successor of class {@link QueryProvider} that allows work with multiple threads
 */
public class ConcurrentQueryProvider extends QueryProvider {
    private ReentrantLock lock;

    /**
     * Creates connection with specified database, username and password. Locks the object for other threads
     * @param address database uri address
     * @param user username
     * @param password user password
     * @throws SQLException
     */
    @Override
    public void connect(String address, String user, String password)
            throws SQLException {
        if(!lock.isHeldByCurrentThread()) {
            lock.lock();
        }
        super.connect(address, user, password);
    }

    /**
     * Creates connection with specified database object and user object. Locks the object for other threads
     * @throws SQLException
     */
    @Override
    public void connect() throws SQLException {
        if(!lock.isHeldByCurrentThread()) {
            lock.lock();
        }
        super.connect();
    }

    /**
     * Executes some sql query. Locks the object for other threads
     * @param sql sql query
     * @return result object or null if the query does not return a result
     * @throws SQLException
     */
    @Override
    public ResultSet execute(String sql) throws SQLException {
        if(!lock.isHeldByCurrentThread()) {
            lock.lock();
        }
        return super.execute(sql);
    }

    /**
     * Executes created earlier prepared statement. Locks the object for other threads
     * @return result object or null if the query does not return a result
     * @throws IllegalStateException if prepared statement was not created
     * @throws SQLException
     */
    @Override
    public ResultSet execute() throws SQLException {
        if(!lock.isHeldByCurrentThread()) {
            lock.lock();
        }
        return super.execute();
    }

    /**
     * Creates new prepared statement for database. Locks the object for other threads
     * @param sql sql query
     * @throws SQLException
     */
    @Override
    public void prepare(String sql) throws SQLException {
        if(!lock.isHeldByCurrentThread()) {
            lock.lock();
        }
        super.prepare(sql);
    }

    /**
     * Sets arguments for prepared statement. Locks the object for other threads
     * @param index number of argument in statement
     * @param parameter value of argument
     * @param type type of argument in database
     * @throws SQLException
     */
    @Override
    public void setPrepareArguments(int index, Object parameter,
                                    SQLType type) throws SQLException {
        if(!lock.isHeldByCurrentThread()) {
            lock.lock();
        }
        super.setPrepareArguments(index, parameter, type);
    }

    /**
     * Closes connection with database if it exists. Locks the object for other threads
     */
    @Override
    public void disconnect() {
        if(!lock.isHeldByCurrentThread()) {
            lock.lock();
        }
        super.disconnect();
        lock.unlock();
    }
}
