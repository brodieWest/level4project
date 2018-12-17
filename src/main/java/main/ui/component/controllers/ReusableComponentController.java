package main.ui.component.controllers;

import main.fileIO.Load;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.text.Text;
import main.ui.component.Synchronous;
import main.ui.component.model.component.ReusableComponent;
import main.ui.main.Mainfx;
import main.ui.simulation.InternalController;
import main.ui.simulation.SimulationController;
import main.ui.wire.Wire;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main.fxml.FxmlLoaderUtils;

import java.io.FileNotFoundException;
import java.util.List;

public class ReusableComponentController extends ComponentController implements Synchronous {

    private Logger logger = LogManager.getLogger(ReusableComponentController.class);

    private InternalController internalSimulation;

    private static String REUSABLE_FILE_PATH = "/main/ui/main/fileExamples/reusable/";
    private static String REUSABLE = "reusable";

    @FXML
    private Text text;

    public ReusableComponentController(SimulationController  simulationController, ReusableComponent componentModel) throws FileNotFoundException{
        super(simulationController, componentModel);

        this.internalSimulation = new InternalController();

        if(!Load.loadFromFile(internalSimulation, REUSABLE_FILE_PATH + componentModel.getStringIdentifier())) {
            logger.error(String.format("Failed to build internal simulation for %s", componentModel.getStringIdentifier()));
            throw new FileNotFoundException();
        }

        List<Wire> inputWires = internalSimulation.getInputWires();

        List<Wire> outputWires = internalSimulation.getOutputWires();

        componentModel.initialiseWires(inputWires,outputWires);

        text.setText(componentModel.getStringIdentifier());

        simulationController.addSynchronous(this);
    }

    @Override
    public void loadFxml() {
        FxmlLoaderUtils.loadFxml(getClass().getResource(String.format(COMPONENT_PATH,REUSABLE)), this);
    }

    @FXML
    private void showContextMenu(ContextMenuEvent contextMenuEvent) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem showContents = new MenuItem("Show contents in new window");
        showContents.setOnAction(event -> Mainfx.newWindow(internalSimulation.getScrollPane(), this.getComponentModel().getStringIdentifier()));

        contextMenu.getItems().add(showContents);

        contextMenu.show(svgGroup, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
    }

    @Override
    public void processClockTick() {
        internalSimulation.clockTick();
    }

    @Override
    public void processGateDelay() {
        internalSimulation.wireDelay();
        internalSimulation.gateDelay();
    }

    @Override
    public void reset() {
        super.reset();
        internalSimulation.resetSimulation();
    }

}
