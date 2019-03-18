public class Operator extends Thread {

    private Berth berth;

    public Operator(Berth berth) {
        this.berth = berth;
    }

    public void run() {
        while (!isInterrupted()) {
            try {
                sleep(Params.debrisLapse());
                getBerth().activateShield();
                sleep(Params.DEBRIS_TIME);
                getBerth().deactivateShield();
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }

    public Berth getBerth() {
        return berth;
    }

    public void setBerth(Berth berth) {
        this.berth = berth;
    }

}
