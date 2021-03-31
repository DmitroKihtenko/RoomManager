package main.java.rm.bean.idControl;

public class CustomId implements Comparable<CustomId> {
    private int id;

    public CustomId(int customId) {
        this.id = customId;
    }

    @Override
    public int compareTo(CustomId o) {
        if(this.id > o.id) {
            return 1;
        } else if (this.id < o.id) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        return id;
    }
}
