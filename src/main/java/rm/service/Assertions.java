package rm.service;


import org.apache.log4j.Logger;

public class Assertions {
    /**
     * Checks some object for null value
     * @param object object to test
     * @param parameter parameter name
     * @param logger logger object
     */
    public static void isNotNull(Object object, String parameter,
                                 Logger logger) {
        if (object == null) {
            logger.error(parameter + " object has null value");

            throw new IllegalArgumentException(
                    parameter + " object has null value"
            );
        }
    }

    /**
     * Check a number for a positive     *
     * @param number variable to test
     * @param parameter parameter name
     * @param logger logger object
     */
    public static void isPositive(float number, String parameter,
                                  Logger logger) {
        if (number <= 0) {
            logger.error(parameter + " has non-positive value " +
                    number);

            throw new IllegalArgumentException(
                    parameter + " has non-positive value " + number
            );
        }
    }
}
