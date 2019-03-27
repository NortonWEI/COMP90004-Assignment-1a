/**
 * A monitor class
 *
 * The WaitZone is instantiated into an arrival zone an departure zone.
 * Arrival zone accepts new ships from Producer, passes ships as pilots' acquirement and releases ships for following steps.
 * Departure zone accepts ships that finishes unload, lets pilots get off the ships and releases ships to Consumer.
 *
 * @author Wenzhou Wei (903836)
 */
public class WaitZone {

    // the name of the wait zone, enumeratively, "arrival" and "departure"
    private String name = "";

    // the ship that stay at the wait zone (assume the wait zone has a fixed capacity of 1)
    private Ship ship;

    /**
     * @param name The name (type) of the wait zone
     * Create a new wait zone.
     */
    WaitZone(String name) {
        this.name = name;
        this.ship = null;
    }

    /**
     * @param ship The ship that arrives at arrival zone
     * Add the ship produced to arrival zone
     */
    synchronized void arrive(Ship ship) {
        while (this.ship != null) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        this.ship = ship;
        System.out.println(ship + " arrives at arrival zone.");

        notifyAll();
    }

    /**
     * Remove the ship from departure zone
     */
    synchronized void depart() {
        while (getShip() == null || getShip().isAcquired()) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        System.out.println(getShip() + " departs departure zone.");
        setShip(null);

        notifyAll();
    }

    /**
     * @param pilot The pilot that acquires the ship from arrival zone
     * Allocate the ship at arrival zone to the pilot acquired
     */
    synchronized void acquireShip(Pilot pilot) {
        while (getShip() == null || getShip().isAcquired()) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        pilot.setCurrentShip(getShip());
        pilot.getCurrentShip().setAcquired(true);
        System.out.println("pilot " + pilot.getPid() + " acquires " + pilot.getCurrentShip() + ".");
    }

    /**
     * @param pilot The pilot that releases the ship from arrival zone
     * Leave the ship at arrival zone from the pilot released
     */
    synchronized void releaseShip(Pilot pilot) {
        pilot.getCurrentShip().setAcquired(false);
        System.out.println("pilot " + pilot.getPid() + " releases " + pilot.getCurrentShip() + ".");

        // reinitialize the pilot that just finishes the task
        pilot.setCurrentShip(null);
        pilot.setAcquireDockTugs(false);
        pilot.setReleaseDockTugs(false);
        pilot.setAcquireUndockTugs(false);
        pilot.setReleaseUndockTugs(false);
        pilot.setDocked(false);
        pilot.setUndocked(false);
        pilot.setDepartArrivalZone(false);
        pilot.setArriveDepartureZone(false);

        notifyAll();
    }

    /**
     * @param pilot The pilot that departs from arrival zone with his/her ship
     * Remove the ship from arrival zone
     */
    synchronized void departFromArrivalZone(Pilot pilot) {
        pilot.setDepartArrivalZone(true);
        setShip(null);

        notifyAll();
    }

    /**
     * @param pilot The pilot that arrives at departure zone with his/her ship
     * Add the ship to departure zone
     */
    synchronized void arriveAtDepartureZone(Pilot pilot) {
        while (getShip() != null) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        setShip(pilot.getCurrentShip());
        pilot.setArriveDepartureZone(true);

        notifyAll();
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
}
