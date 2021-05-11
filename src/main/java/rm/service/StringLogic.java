package main.java.rm.service;

import org.apache.log4j.Logger;

public class StringLogic {
    /**
     * Indicates whether the name does not contain only those symbols that can't be visible on screen
     *
     * @param parameter string of name
     */
    static public void isVisible(String parameter,
                                 String name, Logger logger) {

        boolean checkFlag1 = true, checkFlag2 = true, checkFlag3 = true, retur = true;

        for (int i = 0; i < parameter.length(); i++) {

            if (parameter.charAt(i) != ' ')
                checkFlag1 = false;
            if (parameter.charAt(i) == '\n')
                checkFlag2 = false;
            if (parameter.charAt(i) == '\t')
                checkFlag3 = false;

        }
        if (checkFlag1 || !checkFlag2 || !checkFlag3
                || parameter.isEmpty() || parameter == null) {

            retur = false;

            logger.error("The name does not contain only those symbols that can't be visible on screen\n" +
                    " or parameter has null value");

            throw new IllegalArgumentException(
                    "The name does not contain only those symbols that can't be visible on screen\n" +
                            " or parameter has null value"
            );
        }
    }

    /**
     * Indicates whether the phrase consists of only one whole word
     *
     * @param parameter phrase string
     */
    static public void isWholeWord(String parameter,
                                   String name, Logger logger) {

        boolean checkFlag1 = true, checkFlag2 = true, checkFlag3 = true, retur = true;

        for (int i = 0; i < parameter.length(); i++) {

            if (parameter.charAt(i) == ' ')
                checkFlag1 = false;
            if (parameter.charAt(i) == '\n')
                checkFlag2 = false;
            if (parameter.charAt(i) == '\t')
                checkFlag3 = false;

        }
        if (!checkFlag1 || !checkFlag2 || !checkFlag3
                || parameter.isEmpty() || parameter == null) {

            retur = false;

            logger.error("The phrase consists of more than one whole word\n" +
                    " or parameter has null value");

            throw new IllegalArgumentException(
                    "The phrase consists of more than one whole word\n" +
                            " or parameter has null value"
            );
        }
    }

    /**
     * Indicates whether the phrase contains at least one letter
     *
     * @param parameter phrase string
     * @return true if the phrase contains at least one letter
     */
    static public void containsLetter(String parameter,
                                      String name, Logger logger) {

        boolean checkFlag = true, retur = true;
        short count = 0;

        for (int i = 0; i < parameter.length(); i++) {

            count++;

            if (!Character.isLetter(parameter.charAt(i)))
                checkFlag = false;

        }
        if (!checkFlag || count != 1 || parameter == null) {

            retur = false;

            logger.error("The phrase contains at more than one letter\n" +
                    " or parameter has null value");

            throw new IllegalArgumentException(
                    "The phrase contains at more than one letter\n" +
                            " or parameter has null value"
            );
        }
    }
}
