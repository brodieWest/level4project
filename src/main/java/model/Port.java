package model;

import javafx.wire.Wire;

public class Port {

    private Coordinates offset;

    private Wire wire;

    private Logic logic = new Logic();

    public Port() {
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

    public Logic getLogic() {
        return logic;
    }
}
