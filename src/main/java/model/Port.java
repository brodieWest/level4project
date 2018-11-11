package model;

import javafx.component.model.component.Component;
import javafx.wire.Wire;

public class Port {

    private Coordinates offset;

    private Wire wire;

    private Logic logic = new Logic();

    private Component component;

    public Port(Component component) {
        this.component = component;
    }

    public Coordinates getOffset() {
        return offset;
    }

    public Coordinates getPosition() {
        return component.getCoordinates().addOffset(offset);
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

    public Component getComponent() {
        return component;
    }
}
