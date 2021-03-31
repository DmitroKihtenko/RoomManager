package main.java.rm.bean.idControl;

public class IdWrapper extends CustomId {
    protected static final IdDistributor idControl;
    private Object content;

    static {
        idControl = new SimpleIdDistributor();
    }

    public IdWrapper(Object content) {
        super(idControl.getUniqueId());
        this.content = content;
    }

    public Object get() {
        return content;
    }

    @Override
    public int compareTo(CustomId o) {
        return super.compareTo(o);
    }
}
