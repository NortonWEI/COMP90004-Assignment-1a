/**
 * The pilot that executes the entire simulation from acquiring ships until getting off ships.
 *
 * @author Wenzhou Wei (903836)
 */
public class Pilot extends Thread {

    // the id of the pilot
    private int pid;

    // arrival zone of the system
    private WaitZone arrivalZone;

    // departure zone of the system
    private WaitZone departureZone;

    // tug controller that maintains the number of tugs available
    private Tugs tugs;

    // the berth of USS Emafor
    private Berth berth;

    // the ship that acquired by the pilot (can be null)
    private Ship currentShip;

    // a flag indicating whether the tugs for dock have been required
    private boolean isAcquireDockTugs = false;

    // a flag indicating whether the tugs for undock have been required
    private boolean isAcquireUndockTugs = false;

    // a flag indicating whether the tugs for dock have been released
    private boolean isReleaseDockTugs = false;

    // a flag indicating whether the tugs for undock have been released
    private boolean isReleaseUndockTugs = false;

    // a flag indicating whether the ship is docked
    private boolean isDocked = false;

    // a flag indicating whether the ship is undocked
    private boolean isUndocked = false;

    // a flag indicating whether the ship has left arrival zone
    private boolean isDepartArrivalZone = false;

    // a flag indicating whether the ship has got departure zone
    private boolean isArriveDepartureZone = false;

    /**
     * @param pid The id of the pilot
     * @param arrivalZone Arrival zone of the system
     * @param departureZone Departure zone of the system
     * @param tugs Tug controller that maintains the number of tugs available
     * @param berth The berth of USS Emafor
     * Create a new pilot
     */
    Pilot(int pid, WaitZone arrivalZone, WaitZone departureZone, Tugs tugs, Berth berth) {
        this.pid = pid;
        this.arrivalZone = arrivalZone;
        this.departureZone = departureZone;
        this.tugs = tugs;
        this.berth = berth;
    }

    /**
     * Simulate the entire task of the pilot here
     * Every successive step is located at the "else" portion of its preceding step
     */
    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                // start the task after acquirement
                if (this.getCurrentShip() != null) {
                    if (!isAcquireDockTugs) {
                        // acquire the tugs of docking
                        getTugs().acquireDockTugs(this);
                    } else {
                        if (!isDepartArrivalZone) {
                            // depart the ship from arrival zone
                            getArrivalZone().departFromArrivalZone(this);
                        } else {
                            // travel to the vicinity of the berth
                            sleep(Params.TRAVEL_TIME);
                            // dock at the berth of USS Emafor
                            if (!isDocked) {
                                // dock the ship to berth
                                getBerth().dock(this);
                                // simulate the docking time
                                sleep(Params.DOCKING_TIME);
                            } else {
                                if (!isReleaseDockTugs) {
                                    // release the tugs while unloading for others to use
                                    getTugs().releaseDockTugs(this);
                                } else {
                                    if (this.getCurrentShip().isLoaded()) {
                                        // unload the ship
                                        getBerth().unload(this);
                                        // simulate the unloading time
                                        sleep(Params.UNLOADING_TIME);
                                    } else {
                                        if (!isAcquireUndockTugs) {
                                            // acquire the tugs of undocking
                                            getTugs().acquireUndockTugs(this);
                                        } else {
                                            if (!isUndocked) {
                                                // undock from the berth of USS Emafor
                                                getBerth().undock(this);
                                                // simulate the undocking time
                                                sleep(Params.UNDOCKING_TIME);
                                            } else {
                                                // travel to departure zone
                                                sleep(Params.TRAVEL_TIME);
                                                if (!isArriveDepartureZone) {
                                                    // park the ship into departure zone
                                                    getDepartureZone().arriveAtDepartureZone(this);
                                                } else {
                                                    if (!isReleaseUndockTugs) {
                                                        // release the tugs on arrival at departure zone
                                                        getTugs().releaseUndockTugs(this);
                                                    } else {
                                                        // get off the ship to departure zone
                                                        getDepartureZone().releaseShip(this);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // acquire the ship from arrival zone
                    getArrivalZone().acquireShip(this);
                }
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }

    // getters and setters
    public int getPid() {
        return pid;
    }

    public void setPid(int id) {
        this.pid = pid;
    }

    public WaitZone getArrivalZone() {
        return arrivalZone;
    }

    public void setArrivalZone(WaitZone arrivalZone) {
        this.arrivalZone = arrivalZone;
    }

    public WaitZone getDepartureZone() {
        return departureZone;
    }

    public void setDepartureZone(WaitZone departureZone) {
        this.departureZone = departureZone;
    }

    public Tugs getTugs() {
        return tugs;
    }

    public void setTugs(Tugs tugs) {
        this.tugs = tugs;
    }

    public Berth getBerth() {
        return berth;
    }

    public void setBerth(Berth berth) {
        this.berth = berth;
    }

    public Ship getCurrentShip() {
        return currentShip;
    }

    public void setCurrentShip(Ship currentShip) {
        this.currentShip = currentShip;
    }

    public boolean isAcquireDockTugs() {
        return isAcquireDockTugs;
    }

    public void setAcquireDockTugs(boolean acquireDockTugs) {
        isAcquireDockTugs = acquireDockTugs;
    }

    public boolean isAcquireUndockTugs() {
        return isAcquireUndockTugs;
    }

    public void setAcquireUndockTugs(boolean acquireUndockTugs) {
        isAcquireUndockTugs = acquireUndockTugs;
    }

    public boolean isReleaseDockTugs() {
        return isReleaseDockTugs;
    }

    public void setReleaseDockTugs(boolean releaseDockTugs) {
        isReleaseDockTugs = releaseDockTugs;
    }

    public boolean isReleaseUndockTugs() {
        return isReleaseUndockTugs;
    }

    public void setReleaseUndockTugs(boolean releaseUndockTugs) {
        isReleaseUndockTugs = releaseUndockTugs;
    }

    public boolean isDocked() {
        return isDocked;
    }

    public void setDocked(boolean docked) {
        isDocked = docked;
    }

    public boolean isUndocked() {
        return isUndocked;
    }

    public void setUndocked(boolean undocked) {
        isUndocked = undocked;
    }

    public boolean isDepartArrivalZone() {
        return isDepartArrivalZone;
    }

    public void setDepartArrivalZone(boolean departArrivalZone) {
        isDepartArrivalZone = departArrivalZone;
    }

    public boolean isArriveDepartureZone() {
        return isArriveDepartureZone;
    }

    public void setArriveDepartureZone(boolean arriveDepartureZone) {
        isArriveDepartureZone = arriveDepartureZone;
    }
}
