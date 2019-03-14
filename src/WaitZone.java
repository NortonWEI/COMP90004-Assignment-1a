public class WaitZone {

    private String type = "";

    public WaitZone(String type) {
        this.type = type;
    }

    public void depart() {

    }

    public void arrive(Ship ship) {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
