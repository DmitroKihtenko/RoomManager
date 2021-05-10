package main.java.rm.service;


import org.apache.log4j.Logger;

public class Assertions {
    /**
     * Checking string? empty or no.
     * @param object Object, string parameter, Logger logger
     */
    public static void isNotNull(Object object, String parameter,
                                 Logger logger) {
        if(object==null){
            logger.error("Passed an empty string as an argument.");

            throw new IllegalArgumentException(
                    "Float "+parameter+" equals " + null
            );
        }

    }
    /**
     * Checks whether the transmitted number is negative or no
     * @param number float, String parameter,  Logger logger
     */
    public static void isPositive(float number, String parameter,
                                  Logger logger) {
        if(number<=0){
            logger.error("Passed a negative number as an argument.");

            throw new IllegalArgumentException(
                    "Float "+parameter+" equals " + null
            );
        }
    }
}
