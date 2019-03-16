public class WaitZone {

    private String name = "";
    //    private int waitingShipNum = 0;
    private Ship ship;

    WaitZone(String name) {
        this.name = name;
    }

    synchronized void depart() {

    }

    synchronized void arrive(Ship ship) {
        while (this.getShip() != null) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        notify();
        this.ship = ship;
        System.out.println(ship + " arrives at arrival zone.");
    }

    synchronized void acquireShip(Pilot pilot) {
        pilot.setCurrentShip(getShip());
        setShip(null);
        if (pilot.getCurrentShip() != null) {
            System.out.println("pilot " + pilot.getPid() + " acquires " + pilot.getCurrentShip() + ".");
        }
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    Ship getShip() {
        return ship;
    }

    void setShip(Ship ship) {
        this.ship = ship;
    }
}
