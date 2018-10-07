package javafx.component;

import javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.component.model.component.Component;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.simulation.SimulationController;
import model.Logic;

public class ComponentController implements Controller {

    private Component componentModel;

    private SimulationController simulationController;

    private static String LOGIC_0 = "0";
    private static String LOGIC_1 = "1";
    private static String LOGIC_UNDEFINED = "U";
    private static String LOGIC_0_COLOUR = "white";
    private static String LOGIC_1_COLOUR = "yellow";
    private static String LOGIC_UNDEFINED_COLOUR = "lightgrey";

    @FXML
    private Text text;

    @FXML
    private Parent component;

    @FXML
    private VBox componentVBox;

    @FXML
    private Group svgGroup;

    public void initialiseComponent(Component componentModel, SimulationController simulationController) {
        this.componentModel = componentModel;
        this.simulationController = simulationController;
    }

    public Component getComponentModel() {
        return componentModel;
    }

    public void deleteImageAsExample() {
        //Shape shape = (Shape)svgGroup.getChildren().get(0);
        //shape.setFill(Paint.valueOf("yellow"));
    }

    public void switchInputValue() {
        Component input = this.componentModel;
        Logic inputLogic = input.getOutput(0).getLogic();

        simulationController.resetSimulation();

        if(inputLogic.value()) {
            inputLogic.setValue(false);
            ioShowValue(LOGIC_0, LOGIC_0_COLOUR);
        } else {
            inputLogic.setValue(true);
            ioShowValue(LOGIC_1, LOGIC_1_COLOUR);
        }
        simulationController.wireDelay();
    }

    public void showOutputValue() {
        Component output = this.componentModel;
        Logic inputLogic = output.getInput(0).getLogic();


        if(inputLogic.isUndefined()) {
            ioShowValue(LOGIC_UNDEFINED, LOGIC_UNDEFINED_COLOUR);
        } else if(inputLogic.value()) {
            ioShowValue(LOGIC_1, LOGIC_1_COLOUR);
        } else {
            ioShowValue(LOGIC_0, LOGIC_0_COLOUR);
        }
    }

    private void ioShowValue(String textString, String colour) {
        Shape shape = (Shape)svgGroup.getChildren().get(0);
        text.setText(textString);
        shape.setFill(Paint.valueOf(colour));
    }

}
