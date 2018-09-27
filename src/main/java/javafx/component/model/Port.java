package javafx.component.model;

import javafx.component.Wire.Wire;

public class Port {

    private Coordinates offset;

    private Wire wire;

    private Logic state;

    public Port(Coordinates offset) {
        this.offset = offset;
    }

    public Coordinates getOffset() {
        return offset;
    }

    public void setOffset(Coordinates offset) {
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
