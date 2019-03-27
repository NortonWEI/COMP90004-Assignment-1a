/**
 * @author Wenzhou Wei (903836)
 */
public class Tugs {
    private int num;

    Tugs(int num) {
        this.num = num;
    }

    /**
     * @param pilot The pilot that acquires available tugs of docking
     * Allocate current available tugs of docking as the pilot acquired
     */
    synchronized void acquireDockTugs(Pilot pilot) {
        while (this.num - Params.DOCKING_TUGS < 0) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        this.num -= Params.DOCKING_TUGS;
        pilot.setAcquireDockTugs(true);
        System.out.println("pilot " + pilot.getPid() + " acquires " + Params.DOCKING_TUGS + " tugs (" + getNum() + " available).");
    }

    /**
     * @param pilot The pilot that releases available tugs of docking
     * Deallocate current available tugs of docking as the pilot acquired
     */
    synchronized void releaseDockTugs(Pilot pilot) {
        this.num += Params.DOCKING_TUGS;
        pilot.setReleaseDockTugs(true);
        System.out.println("pilot " + pilot.getPid() + " releases " + Params.DOCKING_TUGS + " tugs (" + getNum() + " available).");
        notify();
    }

    /**
     * @param pilot pilot The pilot that acquires available tugs of undocking
     * Allocate current available tugs of undocking as the pilot acquired
     */
    synchronized void acquireUndockTugs(Pilot pilot) {
        while (this.num - Params.UNDOCKING_TUGS < 0) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        this.num -= Params.UNDOCKING_TUGS;
        pilot.setAcquireUndockTugs(true);
        System.out.println("pilot " + pilot.getPid() + " acquires " + Params.UNDOCKING_TUGS + " tugs (" + getNum() + " available).");
    }

    /**
     * @param pilot The pilot that releases available tugs of undocking
     * Deallocate current available tugs of undocking as the pilot acquired
     */
    synchronized void releaseUndockTugs(Pilot pilot) {
        this.num += Params.UNDOCKING_TUGS;
        pilot.setReleaseUndockTugs(true);
        System.out.println("pilot " + pilot.getPid() + " releases " + Params.UNDOCKING_TUGS + " tugs (" + getNum() + " available).");
        notify();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
