package main.ui.main;

import javafx.scene.control.Button;
import main.fileIO.Load;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import main.model.Coordinates;
import main.ui.Controller;
import main.ui.component.controllers.ComponentControllerFactory;
import main.ui.component.model.component.ComponentParameters;
import main.ui.simulation.MainSimulationController;
import main.ui.toolbar.ToolbarButtonController;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.UUID;


public class MainController implements Controller {

    private MainSimulationController simulationController;

    @FXML
    private VBox componentDescription;

    @FXML
    private VBox simulationVBox;

    @FXML
    private MenuItem load;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label gateDelayCount;

    @FXML
    private Label clockTickCount;

    @FXML
    private CheckMenuItem checkMenuItem;

    @FXML
    private Label pathDepthLabel;

    @FXML
    private VBox toolbox;

    @FXML
    protected void loadFile() {
        simulationController.clear();
        Load.loadWithFileChooser(simulationController);
        simulationController.resetSimulation();
        simulationController.wireDelay();
        simulationController.calculatePathDepth();
    }

    @FXML
    private void saveFile() {
        simulationController.saveFile();
    }

    @FXML
    private void clear() {
        simulationController.clear();
    }

    @FXML
    private void gateDelay() {
        simulationController.gateDelay();
    }

    @FXML
    private void clockTick() {
        simulationController.clockTick();
    }

    @FXML
    private void zoomIn() {
        simulationController.zoomIn();
    }

    @FXML
    private void zoomOut() {
        simulationController.zoomOut();
    }

    public void initialize(){

        this.simulationController = new MainSimulationController(this);

        borderPane.setCenter(simulationController.getScrollPane());

        initialiseToolbar();
    }

    private void initialiseToolbar() {
        Set<String> componentTypes = ComponentControllerFactory.getComponentTypes();

        for(String type : componentTypes) {
            Button button = new ToolbarButtonController(this,type).getToolbarButton();

            toolbox.getChildren().add(button);
        }
    }

    public void setGateDelayCount(int count) {
        gateDelayCount.setText(String.valueOf(count));
    }

    public void setClockTickCount(int count) {
        clockTickCount.setText(String.valueOf(count));
    }

    public void setPathDepth(int pathDepth) {
        pathDepthLabel.setText(String.format("Path Depth: %s", String.valueOf(pathDepth)));
    }

    public void displayText(Parent parent) {
        borderPane.setRight(parent);
    }

    public boolean getLongClockTick() {
        return checkMenuItem.isSelected();
    }

    public void addComponent(String type) {
        String uuid ="a"+UUID.randomUUID().toString();
        simulationController.addComponent(new ComponentParameters(new Coordinates(0,0), uuid,type,new ArrayList<>()));
    }
}
