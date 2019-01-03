package main.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PortIdentifier {

    private String component;
    private int port;
    private List<Coordinates> corner = new ArrayList<>();

    public PortIdentifier(String component, int port) {
        this.component = component;
        this.port = port;
    }

    public String getComponent() {
        return component;
    }

    public int getPort() {
        return port;
    }

    public List<Coordinates> getCorner() {
        return corner;
    }

    public void addCorner(Coordinates coordinates) {
        corner.add(coordinates);
    }

    public void addCorners(List<Coordinates> corners) {
        this.corner.addAll(corners);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortIdentifier that = (PortIdentifier) o;
        return port == that.port &&
                Objects.equals(component, that.component);
    }

    @Override
    public int hashCode() {
        return Objects.hash(component, port);
    }
}
