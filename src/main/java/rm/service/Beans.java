package rm.service;

public class Beans {
    private static Context instance;

    public static Context context() {
        if(instance == null) {
            instance = new Context();
        }
        return instance;
    }
}
