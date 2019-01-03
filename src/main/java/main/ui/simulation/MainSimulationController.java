package main.ui.simulation;

import com.google.gson.Gson;
import javafx.scene.Group;
import javafx.scene.Parent;
import main.model.ComponentParametersModel;
import main.model.Coordinates;
import main.model.FileModel;
import main.ui.component.controllers.ComponentController;
import main.ui.component.controllers.DffController;
import main.ui.component.controllers.InputController;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;
import main.ui.main.MainController;
import main.ui.main.Mainfx;
import main.ui.port.PortController;
import main.ui.simulation.model.Simulator;
import main.ui.wire.WireBuilderController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainSimulationController extends SimulationController {

    private MainController mainController;

    private int gateDelayCount = 0;

    private int clockTickCount = 0;

    private Simulator simulator = new Simulator();

    private WireBuilderController wireBuilderController = new WireBuilderController();

    public MainSimulationController(MainController mainController) {
        super();
        this.mainController = mainController;

        simulationPane.getChildren().add(wireBuilderController.getPath());
        wireBuilderController.getPath().toBack();
        backGround.toBack();
    }

    @Override
    public void gateDelay() {
        super.gateDelay();
        updateGateDelayCount();
    }

    @Override
    public void clockTick() {
        super.clockTick();
        updateClockTickCount();

        if(mainController.getLongClockTick()) {
            for (int i = 0; i < simulator.getPathDepth(); i++) {
                gateDelay();
            }
        }
    }

    @Override
    public void resetSimulation() {
        super.resetSimulation();
        clearGateDelayCount();
    }

    @Override
    public void clear() {
        super.clear();
        clearClockTickCount();
    }

    @Override
    public void displayText(Parent parent) {
        mainController.displayText(parent);
    }


    private void updateGateDelayCount() {
        gateDelayCount++;

        mainController.setGateDelayCount(gateDelayCount);
    }

    private void clearGateDelayCount() {
        gateDelayCount = 0;

        mainController.setGateDelayCount(gateDelayCount);
    }

    private void updateClockTickCount() {
        clockTickCount++;

        mainController.setClockTickCount(clockTickCount);
    }

    private void clearClockTickCount() {
        clockTickCount = 0;

        mainController.setClockTickCount(clockTickCount);
    }

    public void calculatePathDepth() {
        List<Component> inputs = new ArrayList<>();

        Collection<ComponentController> newComponentControllers = componentControllers.values();

        for(ComponentController componentController : newComponentControllers) {
            if(componentController instanceof InputController || componentController instanceof DffController) {
                inputs.add(componentController.getComponentModel());
            }

        }
        simulator.calculatePathDepth(inputs);
        mainController.setPathDepth(simulator.getPathDepth());
    }

    public void saveFile() {
        List<ComponentParametersModel> parameters = new ArrayList<>();
        for (ComponentController componentController : componentControllers.values()) {
            parameters.add(componentController.getComponentParameters());
        }

        FileModel fileModel = new FileModel(parameters);

        Gson gson = new Gson();

        String fileString = gson.toJson(fileModel);

        Mainfx.openFileSaveWindow(fileString);

    }

    public PortController getWireBuilderStartPort() {
        return wireBuilderController.getWireBuilderStartPort();
    }

    public void setWireBuilderStartPort(PortController wireBuilderStartPort) {
        wireBuilderController.setWireBuilderStartPort(wireBuilderStartPort);
    }

    public void startWireBuilder(Coordinates coordinates) {
        wireBuilderController.startLine(coordinates);
    }

    public void endWireBuilder(Coordinates coordinates) {
        wireBuilderController.displayLine(coordinates);
    }

    public void clearWireBuilder() {
        wireBuilderController.clear();
    }

    //public void addWireBuilder(Group lines) {
    //    simulationPane.getChildren().add(lines);
    //    lines.toBack();
    //    backGround.toBack();
    //}

}
