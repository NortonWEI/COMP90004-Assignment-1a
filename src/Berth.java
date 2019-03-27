/**
 * A monitor class
 *
 * Dock and undock the cargo ships to and from the berth of USS Emafor.
 * Unload the ships.
 * Activate and deactivate the shield.
 *
 * @author Wenzhou Wei (903836)
 */
public class Berth {

    // The name of the berth
    private String name = "";

    // a flag indicating whether the berth is occupied by a ship already
    private boolean isOccupied = false;

    // a flag indicating whether the shield of the berth is activated or not
    private boolean isShieldActivated = false;

    /**
     * @param name The name of the berth, namely "berth"
     * Create a new berth
     */
    Berth(String name) {
        this.name = name;
    }

    /**
     * @param pilot The pilot that requests to dock after travel
     * Dock the ship at the berth
     */
    synchronized void dock(Pilot pilot) {
        while (isOccupied || isShieldActivated) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        isOccupied = true;
        pilot.setDocked(true);
        System.out.println(pilot.getCurrentShip() + " docks at berth.");
    }

    /**
     * @param pilot The pilot that requests to undock after travel
     * Undock the ship from the berth
     */
    synchronized void undock(Pilot pilot) {
        while (isShieldActivated) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        isOccupied = false;
        pilot.setUndocked(true);
        System.out.println(pilot.getCurrentShip() + " undocks from berth.");

        notifyAll();
    }

    /**
     * @param pilot The pilot that requests to unload
     * Unload at the berth of USS Emafor
     */
    synchronized void unload(Pilot pilot) {
        pilot.getCurrentShip().setLoaded(false);
        System.out.println(pilot.getCurrentShip() + " being unloaded.");
    }

    /**
     * Activate the shield
     */
    synchronized void activateShield() {
        isShieldActivated = true;
        System.out.println("Shield is activated.");
    }

    /**
     * Deactivate the shield
     */
    synchronized void deactivateShield() {
        isShieldActivated = false;
        System.out.println("Shield is deactivated.");

        notifyAll();
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public boolean isShieldActivated() {
        return isShieldActivated;
    }

    public void setShieldActivated(boolean shieldActivated) {
        isShieldActivated = shieldActivated;
    }
}
