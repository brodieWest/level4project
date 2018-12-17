package main.model;

public class PortParameters {
    private Direction direction;

    private PortType portType;

    private int size;

    public PortParameters(Direction direction, PortType portType, int size) {
        this.direction = direction;
        this.portType = portType;
        this.size = size;
    }

    public PortParameters(Direction direction, PortType portType) {
        this.direction = direction;
        this.portType = portType;
        this.size = 1;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public PortType getPortType() {
        return portType;
    }

    public void setPortType(PortType portType) {
        this.portType = portType;
    }

    public int getSize() {
        return size;
    }
}
