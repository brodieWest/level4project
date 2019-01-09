package main.ui.main;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import main.fileIO.Load;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import main.fxml.Fxml;
import main.fxml.FxmlLoaderUtils;
import main.model.Coordinates;
import main.model.SimulationMode;
import main.ui.Controller;
import main.ui.component.controllers.ComponentControllerFactory;
import main.ui.component.model.component.ComponentParameters;
import main.ui.simulation.MainSimulationController;
import main.ui.toolbar.ToolbarButtonController;

import java.io.File;
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
    private Button startSimulation;

    @FXML
    private HBox simulationButtons;

    @FXML
    private HBox bottomButtons;

    @FXML
    private HBox buildButtons;

    @FXML
    private VBox leftPane;

    @FXML
    private ScrollPane leftScrollPane;

    @FXML
    private Button deleteWires;

    @FXML
    private Button deleteComponents;

    private static String REUSABLE_PATH = "fileExamples/reusable/";

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

        bottomButtons.getChildren().remove(simulationButtons);
    }

    private void initialiseToolbar() {

        for(Node node : toolbox.getChildren()) {
            if(node instanceof ToolbarButtonController) {
                ((ToolbarButtonController) node).setMainController(this);
            }
        }

        File folder = new File(Mainfx.class.getResource(REUSABLE_PATH).getFile());
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles != null) {
            for(File file : listOfFiles) {
                String type = file.getName();
                ToolbarButtonController button = new ToolbarButtonController();
                button.setMainController(this);
                button.setType(type);
                button.setButtonText(type);
                toolbox.getChildren().add(button);
            }
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
        String uuid = type + Long.toHexString(Double.doubleToLongBits(Math.random()));
        simulationController.addComponent(new ComponentParameters(new Coordinates(0,0), uuid,type,new ArrayList<>()));
    }

    public void startSimulation() {
        if(!simulationController.canSimulate()) return;
        simulationController.setSimulationMode(SimulationMode.SIMULATE);
        bottomButtons.getChildren().remove(buildButtons);
        bottomButtons.getChildren().add(simulationButtons);
        simulationController.resetSimulation();
        simulationController.wireDelay();
        setGateDelayCount(0);
        leftPane.getChildren().remove(toolbox);
        leftScrollPane.setPrefWidth(20);

    }

    public void buildMode() {
        //simulationController.resetSimulation();
        simulationController.setSimulationMode(SimulationMode.BUILD);
        bottomButtons.getChildren().add(buildButtons);
        bottomButtons.getChildren().remove(simulationButtons);
        leftPane.getChildren().add(toolbox);
        leftScrollPane.setPrefWidth(200);
    }

    public void deleteWires() {
        simulationController.makeWiresDeletable();
        startSimulation.setDisable(true);
        deleteComponents.setDisable(true);
        leftPane.getChildren().remove(toolbox);
        leftScrollPane.setPrefWidth(20);
        deleteWires.setText("Finish");
        deleteWires.setOnMouseClicked(event -> {
            leftPane.getChildren().add(toolbox);
            leftScrollPane.setPrefWidth(200);
            deleteWires.setText("Delete Wires");
            startSimulation.setDisable(false);
            deleteComponents.setDisable(false);
            simulationController.wiresNotDeletable();
            deleteWires.setOnMouseClicked(event1 -> deleteWires());
        });
    }

    public void deleteComponents() {
        simulationController.makeComponentsDeletable();
        startSimulation.setDisable(true);
        deleteWires.setDisable(true);
        leftPane.getChildren().remove(toolbox);
        leftScrollPane.setPrefWidth(20);
        deleteComponents.setText("Finish");
        deleteComponents.setOnMouseClicked(event -> {
            leftPane.getChildren().add(toolbox);
            leftScrollPane.setPrefWidth(200);
            deleteComponents.setText("Delete Components");
            startSimulation.setDisable(false);
            deleteWires.setDisable(false);
            simulationController.componentsNotDeletable();
            deleteComponents.setOnMouseClicked(event1 -> deleteComponents());
        });
    }
}
