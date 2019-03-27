/**
 * A cargo ship, with a unique id, that arrives at
 * the space station to deliver its cargo.
 *
 * @author ngeard@unimelb.edu.au (edited by: Wenzhou Wei (903836))
 *
 */
public class Ship {

    // a unique identifier for this cargo ship
    private int id;

    // the next ID to be allocated
    private static int nextId = 1;

    // a flag indicating whether the ship is currently loaded
    private boolean loaded;

    //
    private boolean isAcquired;

    // create a new vessel with a given identifier
    private Ship(int id) {
        this.id = id;
        this.loaded = true;
        this.isAcquired = false;
    }

    // get a new Ship instance with a unique identifier
    static Ship getNewShip() {
        return new Ship(nextId++);
    }

    // produce an identifying string for the cargo ship
    public String toString() {
        return "ship [" + id + "]";
    }

    // getters and setters
    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isAcquired() {
        return isAcquired;
    }

    public void setAcquired(boolean acquired) {
        isAcquired = acquired;
    }
}
