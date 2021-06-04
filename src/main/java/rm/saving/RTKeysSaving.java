package rm.saving;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import rm.model.Room;
import rm.model.Teacher;
import rm.service.Assertions;

import java.util.HashMap;
import java.util.Map;

/**
 * Class used to save keys from rooms received by teachers
 */
public class RTKeysSaving implements XmlSaving {
    private static final Logger logger =
            Logger.getLogger(RTKeysSaving.class);
    private Map<Integer, Room> rooms;
    private Map<Integer, Teacher> teachers;

    /**
     * Default constructor that creates empty maps of rooms and teachers
     */
    public RTKeysSaving() {
        rooms = new HashMap<>();
        teachers = new HashMap<>();
    }

    /**
     * Getter for rooms map
     * @return map of rooms, not null
     */
    public Map<Integer, Room> getRooms() {
        return rooms;
    }

    /**
     * Setter for rooms map
     * @param rooms map of rooms, not null
     * @param teachers map of teachers, not null
     */
    public void setKeysData(Map<Integer, Room> rooms,
                            Map<Integer, Teacher> teachers) {
        Assertions.isNotNull(rooms, "Rooms map", logger);
        Assertions.isNotNull(teachers, "Teachers map", logger);

        this.rooms = rooms;
        this.teachers = teachers;
    }

    /**
     * Getter for teachers map
     * @return map of teachers, not null
     */
    public Map<Integer, Teacher> getTeachers() {
        return teachers;
    }

    /**
     * Reads data about keys from rooms received by teachers
     * @param element xml document element
     * @throws DocumentException if any error occurred while reading data
     */
    @Override
    public void read(Element element) throws DocumentException {
        logger.debug("Reading info about ordered keys of rooms");

        Element keysTag = element.element("keys");
        if(keysTag.element("none") == null) {
            Element keyTag;
            int teacherId;
            int roomId;
            Teacher teacher;
            Room room;
            for(Object current : keysTag.elements()) {
                keyTag = (Element) current;
                if(current != null && (keyTag.getName().
                        equals("key"))) {
                    try {
                        teacherId = Integer.parseInt(keyTag.
                                element("teacher").getText());
                        roomId = Integer.parseInt(keyTag.
                                element("room").getText());
                    } catch (NumberFormatException e) {
                        throw new DocumentException("Teacher value " +
                                "must be number");
                    }
                    teacher = teachers.get(teacherId);
                    room = rooms.get(roomId);
                    if(teacher != null && room != null) {
                        teacher.setUsedRoom(roomId);
                        room.setOccupiedBy(teacherId);
                    }
                }
            }
        }
    }

    /**
     * Writes data about keys from rooms received by teachers
     * @param element xml document element
     */
    @Override
    public void write(Element element) {
        logger.debug("Writing info about ordered keys of rooms");

        Element keysTag = element.addElement("keys");
        Element currentTag;
        boolean wasAdded = false;
        for(Teacher teacher : teachers.values()) {
            if(teacher.getUsesRoom()) {
                currentTag = keysTag.addElement("key");
                currentTag = currentTag.addElement("teacher");
                currentTag.setText(String.valueOf(teacher.getId()));
                currentTag = currentTag.getParent();
                currentTag = currentTag.addElement("room");
                currentTag.setText(String.valueOf(teacher.
                        getUsedRoomId()));
                wasAdded = true;
            }
        }
        if(!wasAdded) {
            keysTag.addElement("none");
        }
    }
}
