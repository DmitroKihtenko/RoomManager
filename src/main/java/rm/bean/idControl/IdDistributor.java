package main.java.rm.bean.idControl;

interface IdDistributor {
    int getUniqueId();
    void addUnusedId(int id);
}
