import java.util.Random;

/**
 * The customisable parameters of the system.
 * The random generator of arrival, departure and debris lapse.
 *
 * @author ngeard@unimelb.edu.au (edited by: Wenzhou Wei (903836))
 */
class Params {
    // number of total pilots
    static final int NUM_PILOTS = 3;

    // number of total tugs
    static final int NUM_TUGS = 5;

    // number of docking tugs required
    static final int DOCKING_TUGS = 3;

    // number of undocking tugs required
    static final int UNDOCKING_TUGS = 2;

    // docking time in millisecond
    static final int DOCKING_TIME = 800;

    // undocking time in millisecond
    static final int UNDOCKING_TIME = 400;

    // unloading time in millisecond
    static final int UNLOADING_TIME = 1200;

    // travel time from arrival zone to berth; travel time from berth to departure zone (in millisecond)
    static final int TRAVEL_TIME = 800;

    // the duration that the shield is activated for (in millisecond)
    static final int DEBRIS_TIME = 1800;

    // the maximum time interval for a new ship to arrive
    private static final int MAX_ARRIVAL_INTERVAL = 400;

    // the maximum time interval for a new ship to depart
    private static final int MAX_DEPARTURE_INTERVAL = 1000;

    // the maximum time interval for 2 debris hit
    private static final int MAX_DEBRIS_INTERVAL = 2400;

    /**
     * Generate random debris lapse
     * @return The random time interval for 2 debris hit
     */
    static int debrisLapse() {
        Random rnd = new Random();
        return rnd.nextInt(MAX_DEBRIS_INTERVAL);
    }

    /**
     * Generate random arrival lapse
     * @return The random time interval for a new ship to arrive
     */
    static int arrivalLapse() {
        Random rnd = new Random();
        return rnd.nextInt(MAX_ARRIVAL_INTERVAL);
    }

    /**
     * Generate random departure lapse
     * @return The random time interval for a new ship to depart
     */
    static int departureLapse() {
        Random rnd = new Random();
        return rnd.nextInt(MAX_DEPARTURE_INTERVAL);
    }
}
