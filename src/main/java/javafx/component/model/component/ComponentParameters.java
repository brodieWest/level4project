package javafx.component.model.component;

import model.Coordinates;

public class ComponentParameters {
    private Coordinates coordinates;
    private String uuid;
    private String type;
    private int noInputs;
    private int noOutputs;

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
}
