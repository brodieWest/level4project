package model;

public class PortParameters {
    private Direction direction;

    private PortType portType;

    public PortParameters(Direction direction, PortType portType) {
        this.direction = direction;
        this.portType = portType;
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
}
