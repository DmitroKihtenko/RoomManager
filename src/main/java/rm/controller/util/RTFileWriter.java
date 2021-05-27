package rm.controller.util;

import org.apache.log4j.Logger;
import rm.bean.Room;
import rm.bean.Teacher;
import rm.service.Assertions;
import rm.service.StringLogic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

public class RTFileWriter {
    private static final Logger logger =
            Logger.getLogger(RTFileWriter.class);
    private String filePath;

    public void setFilePath(String filePath) {
        Assertions.isNotNull(filePath, "Save data file", logger);
        StringLogic.isVisible(filePath, "Save data file", logger);

        this.filePath = filePath;
    }

    public void writeUsedRooms(Map<Integer, Teacher> teachers) {
        File file = new File(filePath);
        logger.debug("Writing used rooms info into file " +
                file.getAbsolutePath());

        if(file.exists()) {
            if(file.canWrite()) {
                StringBuilder fileContent = new StringBuilder();
                Teacher teacher;
                for(Integer id : teachers.keySet()) {
                    teacher = teachers.get(id);
                    if(teacher.usesRoom()) {
                        fileContent.append(teacher.getId()).
                                append(":").append(teacher.
                                getUsedRoomId()).append(":");
                    }
                }
                try(FileOutputStream fos =
                            new FileOutputStream(file)) {
                    fos.write(fileContent.toString().getBytes());
                } catch (Exception e) {
                    logger.warn("Unknown error while " +
                            "writing data to file");
                }
            } else {
                logger.warn("Unable to write in file " +
                        file.getAbsolutePath());
            }
        } else {
            logger.warn("File " + file.getAbsolutePath() +
                    "does not exist");
        }
    }

    public void readUsedRooms(Map<Integer, Teacher> teachers,
                      Map<Integer, Room> rooms) {
        File file = new File(filePath);
        logger.debug("Reading used rooms info from file " +
                file.getAbsolutePath());

        if(file.exists()) {
            if(file.canRead()) {
                String fileContent = null;
                try(FileInputStream fis =
                            new FileInputStream(file)) {
                    fileContent = new String(fis.readAllBytes());
                } catch (Exception e) {
                    logger.warn("Unknown error while " +
                            "reading data from file");
                }
                try {
                    String[] ids = fileContent.split(":");
                    Integer teacherId = null;
                    int roomId;
                    for(String id : ids) {
                        if(teacherId == null) {
                            teacherId = Integer.valueOf(id);
                        } else {
                            roomId = Integer.parseInt(id);
                            if(teachers.containsKey(teacherId) &&
                                rooms.containsKey(roomId)) {
                                teachers.get(teacherId).
                                        setUsedRoom(roomId);
                                rooms.get(roomId).
                                        setOccupiedBy(teacherId);
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.warn("Invalid file content format");
                }
            } else {
                logger.warn("Unable to read from file " +
                        file.getAbsolutePath());
            }
        } else {
            logger.warn("File " + file.getAbsolutePath() +
                    "does not exist");
        }
    }
}
