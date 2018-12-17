package main.model;

public enum PortType {
    INPUT(Direction.WEST,"inputPorts"), OUTPUT(Direction.EAST,"outputPorts");

    private Direction direction;
    private String identifier;

    PortType(Direction direction, String identifier) {
        this.direction = direction;
        this.identifier = identifier;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getIdentifier() {
        return identifier;
    }
}
