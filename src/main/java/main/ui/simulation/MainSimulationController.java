package main.ui.simulation;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import main.model.*;
import main.ui.component.InputControllerInterface;
import main.ui.component.OutputControllerInterface;
import main.ui.component.controllers.*;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;
import main.ui.main.MainController;
import main.ui.main.Mainfx;
import main.ui.port.PortController;
import main.ui.simulation.model.Simulator;
import main.ui.wire.WireBuilderController;
import main.ui.wire.WireController;

import java.util.*;

public class MainSimulationController extends SimulationController {

    private MainController mainController;

    private int gateDelayCount = 0;

    private int clockTickCount = 0;

    private Simulator simulator = new Simulator();

    private WireBuilderController wireBuilderController;

    private SimulationMode simulationMode;

    @FXML
    private Group wireBuilderGroup;

    private int SCREEN_SIZE = 10000;
    private int SCREEN_PADDING = 50;

    public MainSimulationController(MainController mainController) {
        super();
        this.mainController = mainController;

        wireBuilderController = new WireBuilderController(this);
        wireBuilderGroup.getChildren().add(wireBuilderController.getPath());
        //wireBuilderController.getPath().toBack();
        //backGround.toBack();
        simulationMode = SimulationMode.BUILD;
    }

    @Override
    public void gateDelay() {
        super.gateDelay();
        updateGateDelayCount();
    }

    @Override
    public void clockTick() {
        if(mainController.getLongClockTick()) {
            for (int i = 0; i < simulator.getPathDepth(); i++) {
                gateDelay();
            }
        }

        super.clockTick();
        updateClockTickCount();
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
        //simulationPane.getChildren().add(wireBuilderController.getPath());
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
        List<Component> outputs = new ArrayList<>();

        for(OutputControllerInterface outputController : outputControllers.values()) {
            outputs.add(((ComponentController)outputController).getComponentModel());
        }

        for(ReusableComponentController reusable : reusableControllers.values()) {
            outputs.addAll(reusable.getOutputs());
        }

        for(DffController dffController : dffControllers.values()) {
            outputs.add(dffController.getComponentModel());
        }
        simulator.calculatePathDepth(outputs, this);

        mainController.setPathDepth(simulator.getPathDepth());
    }

    private FileModel getFileModel() {
        List<ComponentParametersModel> parameters = new ArrayList<>();
        for (ComponentController componentController : componentControllers.values()) {
            parameters.add(componentController.getComponentParameters());
        }

        List<WireModel> wireModels = new ArrayList<>();
        for(WireController wireController : wireControllers.values()) {
            wireModels.add(wireController.getWireModel());
        }

        return new FileModel(parameters,wireModels);

    }

    public void saveFile() {
       openFileWindow(getFileModel());
    }

    public void saveAsComponent(List<ExternalPortMapping> portMappings) {
        FileModel fileModel = getFileModel();
        fileModel.setPortMappings(portMappings);
        openFileWindow(fileModel);
    }

    private void openFileWindow(FileModel fileModel) {
        Gson gson = new Gson();

        String fileString = gson.toJson(fileModel);

        Mainfx.openFileSaveWindow(fileString);
    }

    private Iterator<IoController> iterator;

    private IoController current;

    public boolean startIterator() {
        List<IoController> componentControllers = new ArrayList<>();

        for(InputControllerInterface inputControllerInterface : inputControllers.values()) {
            componentControllers.add((IoController)inputControllerInterface);
        }

        for(OutputControllerInterface outputControllerInterface : outputControllers.values()) {
            componentControllers.add((IoController)outputControllerInterface);
        }

        iterator = componentControllers.iterator();

        if(iterator.hasNext()) {
            current = iterator.next();
            current.ioShowValue("", "yellow");
            return true;
        }
        return false;
    }

    public boolean hasNextIo() {
        return iterator.hasNext();
    }

    public String getNextIo() {
        IoController old = current;
        old.ioShowValue("0","white");

        if(iterator.hasNext()) {
            current = iterator.next();
            current.ioShowValue("", "yellow");
        }
        return old.getUuid();
    }

    public PortController getWireBuilderStartPort() {
        return wireBuilderController.getWireBuilderStartPort();
    }

    public void setWireBuilderStartPort(PortController wireBuilderStartPort) {
        wireBuilderController.setWireBuilderStartPort(wireBuilderStartPort);
        if(wireBuilderStartPort != null) {
            ComponentController componentController = wireBuilderStartPort.getComponentController();
            componentController.getComponent().toBack();
        }
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

    public Coordinates getScrollPosition() {
        int x = (int)Math.ceil((scrollPane.getHvalue()*(SCREEN_SIZE-scrollPane.getWidth()+SCREEN_PADDING))/ComponentController.HALF_COMPONENT_HEIGHT)*ComponentController.HALF_COMPONENT_HEIGHT;
        int y = (int)Math.ceil((scrollPane.getVvalue()*(SCREEN_SIZE-scrollPane.getHeight()+SCREEN_PADDING))/ComponentController.HALF_COMPONENT_HEIGHT)*ComponentController.HALF_COMPONENT_HEIGHT;
        return new Coordinates(x,y);

    }

    public MainController getMainController() {
        return mainController;
    }
}
