package rm.service;

/**
 * Class with static field used to get singleton object of context
 */
public class Beans {
    private static Context instance;

    /**
     * Gets singleton object of context
     * @return singleton object
     */
    public static Context context() {
        if(instance == null) {
            instance = new Context();
        }
        return instance;
    }
}
