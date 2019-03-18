public class Berth {

    private String name = "";
    private boolean isOccupied = false;

    Berth(String name) {
        this.name = name;
    }

    synchronized void dock(Pilot pilot) {
        while (isOccupied) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        isOccupied = true;
        pilot.setDocked(true);
        System.out.println(pilot.getCurrentShip() + " docks at berth.");
    }

    synchronized void undock(Pilot pilot) {
        isOccupied = false;
        pilot.setUndocked(true);
        System.out.println(pilot.getCurrentShip() + " undocks from berth.");
        notify();
    }

    synchronized void unload(Pilot pilot) {
        pilot.getCurrentShip().setLoaded(false);
        System.out.println(pilot.getCurrentShip() + " being unloaded.");
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

}
