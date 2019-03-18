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

    public void run() {
        while (!isInterrupted()) {
            try {
                if (this.getCurrentShip() != null) {
                    if (!isAcquireDockTugs) {
                        getTugs().acquireDockTugs(this);
                        sleep(Params.TRAVEL_TIME);
                    } else {
                        if (!isDocked) {
                            getBerth().dock(this);
                            sleep(Params.DOCKING_TIME);
                        } else {
                            if (!isReleaseDockTugs) {
                                getTugs().releaseDockTugs(this);
                            } else {
                                if (this.getCurrentShip().isLoaded()) {
                                    getBerth().unload(this);
                                    sleep(Params.UNLOADING_TIME);
                                } else {
                                    if (!isAcquireUndockTugs) {
                                        getTugs().acquireUndockTugs(this);
                                    } else {
                                        if (!isUndocked) {
                                            getBerth().undock(this);
                                            sleep(Params.UNDOCKING_TIME);
                                        } else {
                                            sleep(Params.TRAVEL_TIME);
                                            if (!isReleaseUndockTugs) {
                                                getTugs().releaseUndockTugs(this);
                                            } else {
                                                getDepartureZone().releaseShip(this);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
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
