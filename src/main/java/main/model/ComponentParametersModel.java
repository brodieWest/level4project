package main.model;

import java.util.List;

public class ComponentParametersModel {

    private List<PortParameters> inputPorts;
    private List<PortParameters> outputPorts;
    private Coordinates coordinates;
    private String uuid;
    private String type;

    public ComponentParametersModel(Coordinates coordinates, String uuid, String type, List<PortParameters> inputPorts, List<PortParameters> outputPorts) {
        this.inputPorts = inputPorts;
        this.outputPorts = outputPorts;
        this.coordinates = coordinates;
        this.uuid = uuid;
        this.type = type;
    }
}
