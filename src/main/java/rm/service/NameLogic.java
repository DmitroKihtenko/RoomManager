package main.java.rm.service;

import org.apache.log4j.Logger;

public class NameLogic {

    private static final Logger logger = Logger.getLogger(NameLogic.class);

    /**
     * Indicates whether the name does not contain only those symbols that can't be visible on screen
     *
     * @param name string of name
     * @return true if contains only of symbols that can't be visible on screen
     */
    static public boolean isVisibleName(String name) {

        boolean checkFlag1 = true, checkFlag2 = true, checkFlag3 = true, retur = true;

        for (int i = 0; i < name.length(); i++) {

            if (name.charAt(i) != ' ')
                checkFlag1 = false;
            if (name.charAt(i) == '\n')
                checkFlag2 = false;
            if (name.charAt(i) == '\t')
                checkFlag3 = false;

        }
        if (checkFlag1 || !checkFlag2 || !checkFlag3
                || name.isEmpty() || name == null) {

            retur = false;

            logger.error("The name does not contain only those symbols that can't be visible on screen\n" +
                    " or parameter has null value");

            throw new IllegalArgumentException(
                    "The name does not contain only those symbols that can't be visible on screen\n" +
                            " or parameter has null value"
            );
        }

        return retur;
    }

    /**
     * Indicates whether the phrase consists of only one whole word
     *
     * @param phrase phrase string
     * @return true if the phrase consists of only one whole word
     */
    static public boolean isWholeWord(String phrase) {

        boolean checkFlag1 = true, checkFlag2 = true, checkFlag3 = true, retur = true;

        for (int i = 0; i < phrase.length(); i++) {

            if (phrase.charAt(i) == ' ')
                checkFlag1 = false;
            if (phrase.charAt(i) == '\n')
                checkFlag2 = false;
            if (phrase.charAt(i) == '\t')
                checkFlag3 = false;

        }
        if (!checkFlag1 || !checkFlag2 || !checkFlag3
                || phrase.isEmpty() || phrase == null) {

            retur = false;

            logger.error("The phrase consists of more than one whole word\n" +
                    " or parameter has null value");

            throw new IllegalArgumentException(
                    "The phrase consists of more than one whole word\n" +
                            " or parameter has null value"
            );
        }

        return retur;
    }

    /**
     * Indicates whether the phrase contains at least one letter
     *
     * @param phrase phrase string
     * @return true if the phrase contains at least one letter
     */
    static public boolean containsLetter(String phrase) {

        boolean checkFlag = true, retur = true;
        short count = 0;

        for (int i = 0; i < phrase.length(); i++) {

            count++;

            if (!Character.isLetter(phrase.charAt(i)))
                checkFlag = false;

        }
        if (!checkFlag || count != 1 || phrase == null) {

            retur = false;

            logger.error("The phrase contains at more than one letter\n" +
                    " or parameter has null value");

            throw new IllegalArgumentException(
                    "The phrase contains at more than one letter\n" +
                            " or parameter has null value"
            );
        }

        return retur;
    }
}
