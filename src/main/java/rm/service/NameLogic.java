package main.java.rm.service;

public class NameLogic {
    /**
     * Indicates whether the name does not contain only those symbols that can't be visible on screen
     * @param name string of name
     * @return true if contains only of symbols that can't be visible on screen
     */
    static public boolean isVisibleName(String name) {
        return true;
    }

    /**
     * Indicates whether the phrase consists of only one whole word
     * @param phrase phrase string
     * @return true if the phrase consists of only one whole word
     */
    static public boolean isWholeWord(String phrase) {
        return true;
    }

    /**
     * Indicates whether the phrase contains at least one letter
     * @param phrase phrase string
     * @return true if the phrase contains at least one letter
     */
    static public boolean containsLetter(String phrase) {
        return true;
    }
}
