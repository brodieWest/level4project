package main.model;

import java.util.List;

public class FileModel {

    private List<ComponentParametersModel> components;

    private List<WireModel> wires;

    private List<ExternalPortMapping> portMapping;

    public FileModel(List<ComponentParametersModel> components, List<WireModel> wires) {
        this.components = components;
        this.wires = wires;
    }

    public void setPortMappings(List<ExternalPortMapping> portMappings) {
        this.portMapping = portMappings;
    }
}
