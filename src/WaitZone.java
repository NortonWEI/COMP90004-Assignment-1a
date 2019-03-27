/**
 * @author Wenzhou Wei (903836)
 */
public class WaitZone {

    private String name = "";
    private Ship ship;

    WaitZone(String name) {
        this.name = name;
    }

    /**
     * @param ship The ship that arrives at arrival zone
     * Add the ship produced to arrival zone
     */
    synchronized void arrive(Ship ship) {
        while (getShip() != null) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        this.ship = ship;
        System.out.println(ship + " arrives at arrival zone.");
    }

    /**
     * Remove the ship from departure zone
     */
    synchronized void depart() {
        while (getShip() == null) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        System.out.println(getShip() + " departs departure zone.");
        this.ship = null;
    }

    /**
     * @param pilot The pilot that acquires the ship from arrival zone
     * Allocate the ship at arrival zone to the pilot acquired
     */
    synchronized void acquireShip(Pilot pilot) {
        pilot.setCurrentShip(getShip());
        setShip(null);
        if (pilot.getCurrentShip() != null) {
            System.out.println("pilot " + pilot.getPid() + " acquires " + pilot.getCurrentShip() + ".");
        }
        notify();
    }

    /**
     * @param pilot The pilot that releases the ship from arrival zone
     * Leave the ship at arrival zone from the pilot released
     */
    synchronized void releaseShip(Pilot pilot) {
        setShip(pilot.getCurrentShip());

        if (pilot.getCurrentShip() != null) {
            System.out.println("pilot " + pilot.getPid() + " releases " + pilot.getCurrentShip() + ".");
        }

        // reinitialize the pilot that just finishes the task
        pilot.setCurrentShip(null);
        pilot.setAcquireDockTugs(false);
        pilot.setReleaseDockTugs(false);
        pilot.setAcquireUndockTugs(false);
        pilot.setReleaseUndockTugs(false);
        pilot.setDocked(false);
        pilot.setUndocked(false);

        notify();
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    Ship getShip() {
        return ship;
    }

    void setShip(Ship ship) {
        this.ship = ship;
    }
}
