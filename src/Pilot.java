/**
 * @author Wenzhou Wei (903836)
 */
public class Pilot extends Thread {

    private int pid;
    private WaitZone arrivalZone;
    private WaitZone departureZone;
    private Tugs tugs;
    private Berth berth;
    private Ship currentShip;
    private boolean isAcquireDockTugs = false;
    private boolean isAcquireUndockTugs = false;
    private boolean isReleaseDockTugs = false;
    private boolean isReleaseUndockTugs = false;
    private boolean isDocked = false;
    private boolean isUndocked = false;

    Pilot(int pid, WaitZone arrivalZone, WaitZone departureZone, Tugs tugs, Berth berth) {
        this.pid = pid;
        this.arrivalZone = arrivalZone;
        this.departureZone = departureZone;
        this.tugs = tugs;
        this.berth = berth;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                // start the task after acquirement
                if (this.getCurrentShip() != null) {
                    // acquire the tugs of docking
                    if (!isAcquireDockTugs) {
                        getTugs().acquireDockTugs(this);
                        // travel to the vicinity of the berth
                        sleep(Params.TRAVEL_TIME);
                    } else {
                        // dock at the berth of USS Emafor
                        if (!isDocked) {
                            getBerth().dock(this);
                            // simulate the docking time
                            sleep(Params.DOCKING_TIME);
                        } else {
                            // release the tugs while unloading for others to use
                            if (!isReleaseDockTugs) {
                                getTugs().releaseDockTugs(this);
                            } else {
                                // unload the ship
                                if (this.getCurrentShip().isLoaded()) {
                                    getBerth().unload(this);
                                    // simulate the unloading time
                                    sleep(Params.UNLOADING_TIME);
                                } else {
                                    // acquire the tugs of undocking
                                    if (!isAcquireUndockTugs) {
                                        getTugs().acquireUndockTugs(this);
                                    } else {
                                        // undock from the berth of USS Emafor
                                        if (!isUndocked) {
                                            getBerth().undock(this);
                                            // simulate the undocking time
                                            sleep(Params.UNDOCKING_TIME);
                                        } else {
                                            // travel to departure zone
                                            sleep(Params.TRAVEL_TIME);
                                            // release the tugs on arrival at departure zone
                                            if (!isReleaseUndockTugs) {
                                                getTugs().releaseUndockTugs(this);
                                            } else {
                                                // get off the ship to departure zone
                                                if (getDepartureZone().getShip() == null) {
                                                    getDepartureZone().releaseShip(this);
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
                    if (getArrivalZone().getShip() != null) {
                        getArrivalZone().acquireShip(this);
                    }
                }
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }

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

    Ship getCurrentShip() {
        return currentShip;
    }

    void setCurrentShip(Ship currentShip) {
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
}
