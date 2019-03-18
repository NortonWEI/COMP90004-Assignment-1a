public class Berth {

    private String name = "";
    private boolean isOccupied = false;
    private boolean isShieldActivated = false;

    Berth(String name) {
        this.name = name;
    }

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

    synchronized void undock(Pilot pilot) {
        while (isShieldActivated) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        isOccupied = false;
        pilot.setUndocked(true);
        System.out.println(pilot.getCurrentShip() + " undocks from berth.");
        notify();
    }

    synchronized void unload(Pilot pilot) {
        pilot.getCurrentShip().setLoaded(false);
        System.out.println(pilot.getCurrentShip() + " being unloaded.");
    }

    synchronized void activateShield() {
        isShieldActivated = true;
        System.out.println("Shield is activated.");
    }

    synchronized void deactivateShield() {
        isShieldActivated = false;
        System.out.println("Shield is deactivated.");
        notify();
    }

    String getName() {
        return name;
    }

    void setName(String name) {
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
