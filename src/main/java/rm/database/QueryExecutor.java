package rm.database;

import rm.service.Assertions;
import org.apache.log4j.Logger;

/**
 * Abstract class that describes common logic of class that executes queries to database
 */
public abstract class QueryExecutor {
    private static final Logger logger =
            Logger.getLogger(QueryExecutor.class);
    private QueryProvider queryProvider;

    public QueryExecutor() {
        queryProvider = new QueryProvider();
    }

    public void setProvider(QueryProvider provider) {
        Assertions.isNotNull(provider, "Query provider", logger);

        queryProvider = provider;
    }

    public QueryProvider getProvider() {
        return queryProvider;
    }
}
