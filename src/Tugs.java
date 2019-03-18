public class Tugs {
    private int num;

    Tugs(int num) {
        this.num = num;
    }

    synchronized void acquireTugs(Pilot pilot) {
        while (this.num - Params.DOCKING_TUGS < 0) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        this.num -= Params.DOCKING_TUGS;
        pilot.setAcquireTugs(true);
        System.out.println("pilot " + pilot.getPid() + " acquires " + Params.DOCKING_TUGS + " tugs (" + getNum() + " available).");
    }

    synchronized void releaseTugs(Pilot pilot) {
        this.num += Params.UNDOCKING_TUGS;
        pilot.setAcquireTugs(false);
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
