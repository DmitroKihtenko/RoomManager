package main.java.rm.controller;

public class TestRoom {
    private String name;
    private String status;
    private String access;
    private String condition;
    private String lastKey;

    public TestRoom(String name, String status, String access, String condition, String lastKey){
        this.name = name;
        this.status = status;
        this. access= access;
        this.condition = condition;
        this.lastKey = lastKey;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getAccess() {
        return access;
    }

    public String getCondition() {
        return condition;
    }

    public String getLastKey() {
        return lastKey;
    }
}
