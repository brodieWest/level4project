package main.model;

import java.util.List;

public class WireIdentifier {
    private Port port;

    private List<Coordinates> corners;

    public WireIdentifier(Port port, List<Coordinates> corners) {
        this.port = port;
        this.corners = corners;
    }

    public Port getPort() {
        return port;
    }

    public List<Coordinates> getCorners() {
        return corners;
    }
}
