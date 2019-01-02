package main.ui.port;

import main.model.*;
import main.ui.component.model.component.Component;
import main.ui.wire.Wire;

public class Port {

    private Coordinates offset;

    private Coordinates endCoordinates;

    private Wire wire;

    private Word word;

    private int size;

    private PortType portType;

    private Component component;

    private Direction direction;

    private PortController portController;

    private int portNo;

    public Port(Component component, PortParameters portParameters, int portNo) {
        this.component = component;
        this.size = portParameters.getSize();
        this.portType = portParameters.getPortType();
        this.direction = portParameters.getDirection();
        this.word = new Word(portParameters.getSize());
        this.portNo = portNo;

        portController = new PortController(this);
    }

    public PortParameters getParameters() {
        return new PortParameters(direction,portType,size);
    }

    public Coordinates getOffset() {
        return offset;
    }

    public Coordinates getPosition() {
        return component.getCoordinates().addOffset(offset);
    }

    public void setOffset(Coordinates offset) {
        this.offset = offset;

        if(direction == Direction.NORTH) {
            endCoordinates = new Coordinates(offset.getX(), 0);
        } else if(direction == Direction.SOUTH) {
            endCoordinates = new Coordinates(offset.getX(), component.getHEIGHT());
        } else if(direction == Direction.EAST) {
            endCoordinates = new Coordinates(component.getWIDTH(),offset.getY());
        } else {
            endCoordinates = new Coordinates(0,offset.getY());
        }
    }

    public Wire getWire() {
        return wire;
    }

    public void setWire(Wire wire) {
        this.wire = wire;
        portController.hidePort();
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

    public PortController getPortController() {
        return portController;
    }

    Coordinates getEndCoordinates() {
        return endCoordinates;
    }

    Coordinates getEndPosition() {
        return component.getCoordinates().addOffset(endCoordinates);
    }

    int getPortNo() {
        return portNo;
    }

    public void setPortNo(int portNo) {
        this.portNo = portNo;
    }
}
