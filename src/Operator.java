/**
 * Control the time of shield activation and deactivation.
 *
 * @author Wenzhou Wei (903836)
 */
public class Operator extends Thread {

    // the berth that equipped the shield
    private Berth berth;

    /**
     * @param berth The berth that equipped the shield
     * Create a new operator to control the activation time of the shield
     */
    Operator(Berth berth) {
        this.berth = berth;
    }

    /**
     * The shield activates and deactivates consecutively
     */
    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                // activate and deactivate the shield periodically
                sleep(Params.debrisLapse());
                getBerth().activateShield();    // activate shield
                sleep(Params.DEBRIS_TIME);
                getBerth().deactivateShield();  // deactivate shield
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }

    // getters and setters
    public Berth getBerth() {
        return berth;
    }

    public void setBerth(Berth berth) {
        this.berth = berth;
    }

}
