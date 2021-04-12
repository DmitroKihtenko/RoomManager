package main.java.rm.threads;

import jdk.dynalink.Operation;
import org.apache.log4j.Logger;

import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantLock;

public class ServiceThread extends Thread {
    private static final Logger logger =
            Logger.getLogger(ServiceThread.class);
    private TreeSet<ThreadOperation> operations;
    private ReentrantLock lock;

    public ServiceThread() {

    }

    void addOperation(Operation operation) {

    }

    void removeOperation(Operation operation) {

    }

    boolean existsOperation(Operation operation) {
        return true;
    }

    @Override
    public void run() {
        super.run();
    }
}
