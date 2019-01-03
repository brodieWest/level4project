package main.model;

import java.util.List;

public class FileModel {

    private List<ComponentParametersModel> components;

    private List<WireModel> wires;

    public FileModel(List<ComponentParametersModel> components, List<WireModel> wires) {
        this.components = components;
        this.wires = wires;
    }
}
