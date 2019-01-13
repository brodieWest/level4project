package main.ui.simulation;

import com.google.gson.Gson;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import main.model.*;
import main.ui.component.InputControllerInterface;
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
import main.ui.wire.WireController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MainSimulationController extends SimulationController {

    private MainController mainController;

    private int gateDelayCount = 0;

    private int clockTickCount = 0;

    private Simulator simulator = new Simulator();

    private WireBuilderController wireBuilderController;

    private SimulationMode simulationMode;

    public MainSimulationController(MainController mainController) {
        super();
        this.mainController = mainController;

        wireBuilderController = new WireBuilderController(this);
        simulationPane.getChildren().add(wireBuilderController.getPath());
        wireBuilderController.getPath().toBack();
        backGround.toBack();
        simulationMode = SimulationMode.BUILD;
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
        if(simulationMode.equals(SimulationMode.SIMULATE)) {
            super.resetSimulation();
            clearGateDelayCount();
        }
    }

    @Override
    public void clear() {
        super.clear();
        simulationPane.getChildren().add(wireBuilderController.getPath());
        clearClockTickCount();
    }

    @Override
    public void displayText(Parent parent) {
        mainController.displayText(parent);
    }

    @Override
    public void wireDelay() {
        if(simulationMode == SimulationMode.SIMULATE) {
            super.wireDelay();
        }
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

        //Collection<ComponentController> newComponentControllers = componentControllers.values();

        for(InputControllerInterface inputController : inputControllers.values()) {
            //if(componentController instanceof InputController || componentController instanceof DffController) {
                inputs.add(((ComponentController)inputController).getComponentModel());
            //}

        }
        //simulator.calculatePathDepth(inputs);
        mainController.setPathDepth(simulator.getPathDepth());
    }

    public void saveFile() {
        List<ComponentParametersModel> parameters = new ArrayList<>();
        for (ComponentController componentController : componentControllers.values()) {
            parameters.add(componentController.getComponentParameters());
        }

        List<WireModel> wireModels = new ArrayList<>();
        for(WireController wireController : wireControllers.values()) {
            wireModels.add(wireController.getWireModel());
        }

        FileModel fileModel = new FileModel(parameters,wireModels);

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

    public List<Coordinates> getWireBuilderCorners() {
        return wireBuilderController.getCorners();
    }

    public void startWireBuilder(Coordinates coordinates) {
        wireBuilderController.startNewPath(coordinates);
    }

    public void displayLine(Coordinates coordinates) {
        wireBuilderController.displayLine(coordinates);
    }

    public void newLine(Coordinates coordinates) {
        wireBuilderController.startLine(coordinates);
    }

    public void clearWireBuilder() {
        wireBuilderController.clear();
        removeBuildIcons();
    }

    public void showBuildIcons() {
        for(WireController wireController : wireControllers.values()) {
            wireController.showBuildIcons();
        }
    }

    public SimulationMode getSimulationMode() {
        return simulationMode;
    }

    public void setSimulationMode(SimulationMode simulationMode) {
        this.simulationMode = simulationMode;
    }

    public boolean canSimulate() {
        for(ComponentController componentController : componentControllers.values()) {
            if(!componentController.isConnected()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cannot simulate while components are unconnected.");
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

                alert.showAndWait();

                return false;
            }
        }
        return true;
    }

    public void makeWiresDeletable() {
        for(WireController wireController : wireControllers.values()) {
            wireController.setDeletable(true);
        }
    }

    public void wiresNotDeletable() {
        for(WireController wireController : wireControllers.values()) {
            wireController.setDeletable(false);
        }
    }

    public void makeComponentsDeletable() {
        for(ComponentController componentController : componentControllers.values()) {
            componentController.setDeletable(true);
        }
    }

    public void componentsNotDeletable() {
        for(ComponentController componentController : componentControllers.values()) {
            componentController.setDeletable(false);
        }
    }
}
