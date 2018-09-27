package javafx.component.model;

public class Port {

    private Coordinate offset;

    private Wire wire;

    private Logic state;

    public Port(Coordinate offset) {
        this.offset = offset;
    }

    public Coordinate getOffset() {
        return offset;
    }

    public void setOffset(Coordinate offset) {
        this.offset = offset;
    }

    public Wire getWire() {
        return wire;
    }

    public void setWire(Wire wire) {
        this.wire = wire;
    }

    public Logic getState() {
        return state;
    }

    public void setState(Logic state) {
        this.state = state;
    }
}
