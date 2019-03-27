/**
 * @author Wenzhou Wei (903836)
 */
public class Operator extends Thread {

    private Berth berth;

    public Operator(Berth berth) {
        this.berth = berth;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                // activate and deactivate the shield periodically
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
