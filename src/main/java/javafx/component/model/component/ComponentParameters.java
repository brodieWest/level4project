package javafx.component.model.component;

import model.Coordinates;

import java.util.Objects;

public class ComponentParameters {
    private Coordinates coordinates;
    private String uuid;
    private String type;
    private int noInputs;
    private int noOutputs;

    public ComponentParameters(Coordinates coordinates, String uuid, String type, int noInputs, int noOutputs) {
        this.coordinates = coordinates;
        this.uuid = uuid;
        this.type = type;
        this.noInputs = noInputs;
        this.noOutputs = noOutputs;
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

    public int getNoInputs() {
        return noInputs;
    }

    public void setNoInputs(int noInputs) {
        this.noInputs = noInputs;
    }

    public int getNoOutputs() {
        return noOutputs;
    }

    public void setNoOutputs(int noOutputs) {
        this.noOutputs = noOutputs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentParameters that = (ComponentParameters) o;
        return noInputs == that.noInputs &&
                noOutputs == that.noOutputs &&
                Objects.equals(coordinates, that.coordinates) &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, uuid, type, noInputs, noOutputs);
    }
}
