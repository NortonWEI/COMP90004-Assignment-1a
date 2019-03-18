public class Pilot extends Thread {

    private int pid;
    private WaitZone arrivalZone;
    private WaitZone departureZone;
    private Tugs tugs;
    private Berth berth;
    private Ship currentShip;
    private boolean isAcquireTugs = false;

    Pilot(int pid, WaitZone arrivalZone, WaitZone departureZone, Tugs tugs, Berth berth) {
        this.pid = pid;
        this.arrivalZone = arrivalZone;
        this.departureZone = departureZone;
        this.tugs = tugs;
        this.berth = berth;
    }

    public void run() {
        while (!isInterrupted()) {
            if (this.getCurrentShip() != null) {
                if (!isAcquireTugs) {
                    getTugs().acquireTugs(this);
                } else {

                }
            } else {
                if (getArrivalZone().getShip() != null) {
                    getArrivalZone().acquireShip(this);
//                } else {
//                    synchronized (this) {
//                        try {
//                            wait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
                }
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

    public boolean isAcquireTugs() {
        return isAcquireTugs;
    }

    public void setAcquireTugs(boolean acquireTugs) {
        isAcquireTugs = acquireTugs;
    }
}
