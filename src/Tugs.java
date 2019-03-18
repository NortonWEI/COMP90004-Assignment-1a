public class Tugs {
    private int num;

    Tugs(int num) {
        this.num = num;
    }

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

    synchronized void releaseDockTugs(Pilot pilot) {
        this.num += Params.DOCKING_TUGS;
        pilot.setReleaseDockTugs(true);
        System.out.println("pilot " + pilot.getPid() + " releases " + Params.DOCKING_TUGS + " tugs (" + getNum() + " available).");
        notify();
    }

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
