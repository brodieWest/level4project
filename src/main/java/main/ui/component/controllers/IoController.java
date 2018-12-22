package main.ui.component.controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import main.ui.component.model.component.Component;
import main.ui.simulation.SimulationController;

class IoController extends ComponentController{

    static String LOGIC_0 = "0";
    static String LOGIC_1 = "1";
    static String LOGIC_UNDEFINED = "U";
    //static String LOGIC_0_COLOUR = "0xe1974c";
    static String LOGIC_0_COLOUR = "0x7293cb";
    static String LOGIC_1_COLOUR = "0xd35e60";
    static String LOGIC_UNDEFINED_COLOUR = "lightgrey";

    @FXML
    private StackPane stackPane;

    @FXML
    private SVGPath svgPath;

    IoController(SimulationController simulationController, Component componentModel) {
        super(simulationController, componentModel);
    }


    void ioShowValue(String textString, String colour) {
        text.setText(textString);
        svgPath.setFill(Paint.valueOf(colour));
    }

    @Override
    public Parent getComponent() {
        return stackPane;
    }
}
