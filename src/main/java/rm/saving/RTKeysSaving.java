package rm.saving;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import rm.model.Room;
import rm.model.Teacher;
import rm.service.Assertions;

import java.util.Map;

public class RTKeysSaving implements XmlSaving {
    private static final Logger logger =
            Logger.getLogger(RTKeysSaving.class);
    private Map<Integer, Room> rooms;
    private Map<Integer, Teacher> teachers;

    public Map<Integer, Room> getRooms() {
        return rooms;
    }

    public void setKeysData(Map<Integer, Room> rooms,
                            Map<Integer, Teacher> teachers) {
        Assertions.isNotNull(rooms, "Rooms map", logger);
        Assertions.isNotNull(teachers, "Teachers map", logger);

        this.rooms = rooms;
        this.teachers = teachers;
    }

    public Map<Integer, Teacher> getTeachers() {
        return teachers;
    }

    @Override
    public void read(Element element) throws DocumentException {
        logger.debug("Reading info about ordered keys of rooms");

        Element keysTag = element.element("keys");
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
                    throw new DocumentException("Teacher value must " +
                            "be number");
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

    @Override
    public void write(Element element) {
        logger.debug("Writing info about ordered keys of rooms");

        Element keysTag = element.addElement("keys");
        Element currentTag;
        for(Teacher teacher : teachers.values()) {
            if(teacher.usesRoom()) {
                currentTag = keysTag.addElement("key");
                currentTag = currentTag.addElement("teacher");
                currentTag.setText(String.valueOf(teacher.getId()));
                currentTag = currentTag.getParent();
                currentTag = currentTag.addElement("room");
                currentTag.setText(String.valueOf(teacher.
                        getUsedRoomId()));
            }
        }
    }
}
