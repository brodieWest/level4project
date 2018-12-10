package model;

import ui.component.model.component.Component;
import ui.wire.Wire;

public class Port {

    private Coordinates offset;

    private Wire wire;

    private Word word;

    private Component component;

    private Direction direction;

    public Port(Component component, PortParameters portParameters) {
        this.component = component;
        this.direction = portParameters.getDirection();
        this.word = new Word(portParameters.getSize());
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
        return word.get(0);
    }

    public Component getComponent() {
        return component;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}
