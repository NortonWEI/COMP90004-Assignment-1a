/**
 * A monitor class
 *
 * Maintain the quantity of tugs that can be used for dock and undock.
 * Allocate and Deallocate the tugs for each dock and undock process.
 *
 * @author Wenzhou Wei (903836)
 */
public class Tugs {

    // the number of tugs available
    private int num;

    /**
     * @param num The number of tugs available
     * Create a new tug controller
     */
    Tugs(int num) {
        this.num = num;
    }

    /**
     * @param pilot The pilot that acquires available tugs of docking
     * Allocate current available tugs of docking as the pilot acquired
     */
    synchronized void acquireDockTugs(Pilot pilot) {

        // lock a pilot thread when the number of available docking tugs is less than 0
        while (this.num - Params.DOCKING_TUGS < 0) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        // acquire docking tugs
        this.num -= Params.DOCKING_TUGS;
        pilot.setAcquireDockTugs(true);
        System.out.println("pilot " + pilot.getPid() + " acquires " + Params.DOCKING_TUGS + " tugs (" + getNum() + " available).");
    }

    /**
     * @param pilot The pilot that releases available tugs of docking
     * Deallocate current available tugs of docking as the pilot acquired
     */
    synchronized void releaseDockTugs(Pilot pilot) {

        // release docking tugs
        this.num += Params.DOCKING_TUGS;
        pilot.setReleaseDockTugs(true);
        System.out.println("pilot " + pilot.getPid() + " releases " + Params.DOCKING_TUGS + " tugs (" + getNum() + " available).");

        // unlock a pilot thread when docking tugs are released
        notifyAll();
    }

    /**
     * @param pilot pilot The pilot that acquires available tugs of undocking
     * Allocate current available tugs of undocking as the pilot acquired
     */
    synchronized void acquireUndockTugs(Pilot pilot) {

        // lock a pilot thread when the number of available undocking tugs is less than 0
        while (this.num - Params.UNDOCKING_TUGS < 0) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }

        // acquire undocking tugs
        this.num -= Params.UNDOCKING_TUGS;
        pilot.setAcquireUndockTugs(true);
        System.out.println("pilot " + pilot.getPid() + " acquires " + Params.UNDOCKING_TUGS + " tugs (" + getNum() + " available).");
    }

    /**
     * @param pilot The pilot that releases available tugs of undocking
     * Deallocate current available tugs of undocking as the pilot acquired
     */
    synchronized void releaseUndockTugs(Pilot pilot) {

        // release undocking tugs
        this.num += Params.UNDOCKING_TUGS;
        pilot.setReleaseUndockTugs(true);
        System.out.println("pilot " + pilot.getPid() + " releases " + Params.UNDOCKING_TUGS + " tugs (" + getNum() + " available).");

        // unlock a pilot thread when undocking tugs are released
        notifyAll();
    }

    // getters and setters
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
