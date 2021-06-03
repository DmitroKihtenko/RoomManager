package rm.service;

import org.apache.log4j.Logger;

public class StringLogic {
    /**
     * Checks whether the name does not contain only those symbols that can't be visible on screen
     * @param parameter string of name
     */
    static public void isVisible(String parameter,
                                 String name, Logger logger) {

        boolean checkFlag1 = true, checkFlag2 = true,
                checkFlag3 = true;

        for (int i = 0; i < parameter.length(); i++) {
            if (parameter.charAt(i) != ' ') {
                checkFlag1 = false;
            }
            if (parameter.charAt(i) == '\n') {
                checkFlag2 = false;
            }
            if (parameter.charAt(i) == '\t') {
                checkFlag3 = false;
            }
        }

        if (checkFlag1 || !checkFlag2 || !checkFlag3 ||
                parameter.isEmpty()) {
            logger.warn(name + " parameter does not contains " +
                    "visible symbols");

            throw new IllegalArgumentException(
                    name + " parameter must contain visible symbols"
            );
        }
    }

    /**
     * Checks whether the phrase consists of only one whole word
     * @param parameter phrase string
     */
    static public void isWholeWord(String parameter,
                                   String name, Logger logger) {
        boolean checkFlag1 = true, checkFlag2 = true,
                checkFlag3 = true;

        for (int i = 0; i < parameter.length(); i++) {
            if (parameter.charAt(i) == ' ') {
                checkFlag1 = false;
            }
            if (parameter.charAt(i) == '\n') {
                checkFlag2 = false;
            }
            if (parameter.charAt(i) == '\t') {
                checkFlag3 = false;
            }
        }

        if (!checkFlag1 || !checkFlag2 || !checkFlag3 || parameter.isEmpty()) {
            logger.warn(name + " parameter is not whole word");

            throw new IllegalArgumentException(
                    name + " parameter must be whole word"
            );
        }
    }

    /**
     * Checks whether the phrase contains at least one letter
     * @param parameter phrase string
     */
    static public void containsLetter(String parameter,
                                      String name, Logger logger) {
        boolean checkFlag = true;

        for (int i = 0; i < parameter.length(); i++) {
            if (Character.isLetter(parameter.charAt(i))) {
                checkFlag = false;
                break;
            }
        }

        if (checkFlag) {
            logger.warn(name + " parameter does not contain letters");

            throw new IllegalArgumentException(
                    name + " parameter does not contain letters"
            );
        }
    }
}
