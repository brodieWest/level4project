package main.ui.component.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import main.model.Direction;
import main.ui.component.model.component.Component;
import main.ui.simulation.SimulationController;

abstract class ResizableComponentController extends ComponentController {
    private static int DEFAULT_SIZE = 100;
    private static double MAX_PORTS = 4.0;

    @FXML
    private Rectangle rectangle;

    ResizableComponentController(SimulationController simulationController, Component componentModel) {
        super(simulationController, componentModel);

        int maxEastWest = Math.max(componentModel.getPortsByDirection(Direction.EAST).size(),componentModel.getPortsByDirection(Direction.WEST).size());

        int height = Math.max(DEFAULT_SIZE,(int)Math.ceil(maxEastWest/MAX_PORTS) * DEFAULT_SIZE);

        int maxNorthSouth = Math.max(componentModel.getPortsByDirection(Direction.NORTH).size(),componentModel.getPortsByDirection(Direction.SOUTH).size());

        int width = Math.max(DEFAULT_SIZE,(int)Math.ceil(maxNorthSouth/MAX_PORTS) * DEFAULT_SIZE);

        componentModel.setHEIGHT(height);
        componentModel.setWIDTH(width);

        componentModel.positionPorts();

        component.prefHeight(height);

        rectangle.setTranslateY(componentModel.getPORT_OFFSET());

        rectangle.setHeight(height - componentModel.getPORT_OFFSET() * 2);

        component.prefWidth(width);

        rectangle.setWidth(width - componentModel.getDefaultPortOffset() * 2);
    }

}
