package main.java.rm.service;


import org.apache.log4j.Logger;

public class Assertions {
    /**
     * Checking string empty or no.
     *
     * @param object    Variable to test
     * @param parameter The name is a changeling
     * @param logger    Variable to log
     */
    public static void isNotNull(Object object, String parameter,
                                 Logger logger) {

        if (object == null) {

            logger.error("Passed an empty string as an argument.");

            throw new IllegalArgumentException(
                    "Float " + parameter + " equals " + null
            );

        }
    }

    /**
     * Check a number for a positive
     *
     * @param number    Variable to test
     * @param parameter The name is a changeling
     * @param logger    Variable to log
     */
    public static void isPositive(float number, String parameter,
                                  Logger logger) {

        if (number <= 0) {

            logger.error("Passed a negative number as an argument.");

            throw new IllegalArgumentException(
                    "Float " + parameter + " equals " + null
            );

        }
    }
}
