package main.java.rm.bean;

import org.apache.log4j.Logger;

public class IdHandler {
    private static final Logger logger =
            Logger.getLogger(IdHandler.class);
    public final static int DEFAULT_ID = -1;

    private int maximalId;

    public IdHandler() {
        maximalId = 0;
    }

    public void setUsedId(int id) {
        if(id > maximalId) {
            maximalId = id;
        }
    }

    public int getUniqueId() {
        if(maximalId == Integer.MAX_VALUE) {
            logger.fatal("Can not get unique " +
                    "identifier. The identifiers of one of the " +
                    "classes are in a too scattered order. Required" +
                    "redistribution of identifiers");

            throw new IllegalStateException("Can not get unique " +
                    "identifier. The identifiers of one of the " +
                    "classes are in a too scattered order");
        }
        return ++maximalId;
    }
}
