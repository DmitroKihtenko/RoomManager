package rm.threads;

import org.apache.log4j.Logger;
import rm.service.Assertions;

import java.util.HashSet;

/**
 * Class successor of class {@link Thread} that used to make some parallel operations
 */
public class ServiceThread extends Thread {
    private static final Logger logger =
            Logger.getLogger(ServiceThread.class);
    HashSet<ThreadOperation> operations;

    /**
     * Default constructor, sets thread as daemon with min priority
     */
    public ServiceThread() {
        operations = new HashSet<>();
        setDaemon(true);
        setName("service");
        this.setPriority(MIN_PRIORITY);
    }

    /**
     * Adds new operation for parallel execution
     * @param operation operation object
     */
    void addOperation(ThreadOperation operation) {
        Assertions.isNotNull(operation,
                "Thread operation", logger);

        operations.add(operation);
    }

    /**
     * Removed operation for parallel execution that was contained in class
     * @param operation operation object
     */
    void removeOperation(ThreadOperation operation) {
        Assertions.isNotNull(operation,
                "Thread operation", logger);

        operations.remove(operation);
    }

    /**
     * Indicates whether the operation exists in this thread
     * @param operation operation object
     */
    boolean existsOperation(ThreadOperation operation) {
        return operations.contains(operation);
    }

    /**
     * Makes all operations contained in this thread
     */
    @Override
    public void run() {
        while(true) {
            for(ThreadOperation operation : operations) {
                operation.make();
            }
        }
    }
}
