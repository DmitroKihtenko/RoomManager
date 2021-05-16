package main.java.rm.controller;

public class TestTeacher {
    private String name;
    private String room;
    private String key;

    public TestTeacher(String name, String room, String key){
        this.name = name;
        this.room = room;
        this.key = key;
    }

    public String getRoom() {
        return room;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
