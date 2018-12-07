package javafx.component.model.component;

import model.Coordinates;
import model.PortParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ComponentParameters {
    private Coordinates coordinates;
    private String uuid;
    private String type;
    private List<PortParameters> portParameters;

    public ComponentParameters(Coordinates coordinates, String uuid, String type, List<PortParameters> portParameters) {
        this.coordinates = coordinates;
        this.uuid = uuid;
        this.type = type;
        this.portParameters = portParameters;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    List<PortParameters> getPortParameters() {
        return portParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentParameters that = (ComponentParameters) o;
        return Objects.equals(coordinates, that.coordinates) &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, uuid, type, portParameters);
    }
}
