package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PortIdentifier {

    private String componentName;
    private int portNo;
    private List<Coordinates> corners = new ArrayList<>();

    public PortIdentifier(String componentName, int portNo) {
        this.componentName = componentName;
        this.portNo = portNo;
    }

    public String getComponent() {
        return componentName;
    }

    public int getPortNo() {
        return portNo;
    }

    public List<Coordinates> getCorners() {
        return corners;
    }

    public void addCorner(Coordinates coordinates) {
        corners.add(coordinates);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortIdentifier that = (PortIdentifier) o;
        return portNo == that.portNo &&
                Objects.equals(componentName, that.componentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(componentName, portNo);
    }
}
