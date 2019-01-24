package main.ui.component.controllers;

import javafx.scene.Scene;
import main.fileIO.Load;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.text.Text;
import main.model.Direction;
import main.ui.ComponentInternalWindow;
import main.ui.component.Synchronous;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.ReusableComponent;
import main.ui.main.Mainfx;
import main.ui.port.Port;
import main.ui.simulation.InternalController;
import main.ui.simulation.SimulationController;
import main.ui.wire.Wire;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import main.fxml.FxmlLoaderUtils;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.List;

public class ReusableComponentController extends ResizableComponentController implements Synchronous {

    private Logger logger = LogManager.getLogger(ReusableComponentController.class);

    private InternalController internalSimulation;

    private Scene newWindowScene;

    private static String REUSABLE_FILE_PATH = Paths.get("./examples/reusables").toAbsolutePath().normalize().toString() + "/";
    private static String REUSABLE = "reusable";

    @FXML
    private Text text;

    public ReusableComponentController(SimulationController  simulationController, ReusableComponent componentModel) throws FileNotFoundException{
        super(simulationController, componentModel);

        this.internalSimulation = new InternalController();

        if(!Load.loadInternal(internalSimulation,this, REUSABLE_FILE_PATH + componentModel.getStringIdentifier())) {
            logger.error(String.format("Failed to build internal simulation for %s", componentModel.getStringIdentifier()));
            throw new FileNotFoundException();
        }

        internalSimulation.removeInputs();


        if(componentModel.isPortsAdded()) {
            displayPorts();
        }

        text.setText(componentModel.getStringIdentifier());

        simulationController.addSynchronous(this);

        simulationController.addReusableController(this);

        newWindowScene = new Scene(internalSimulation.getScrollPane(),800,500);
        newWindowScene.getStylesheets().add(Mainfx.class.getResource("css/css").toExternalForm());
    }

    @Override
    public void loadFxml() {
        FxmlLoaderUtils.loadFxml(Mainfx.class.getResource(String.format(COMPONENT_PATH,REUSABLE)), this);
    }

    public void addExternalPort(String internalPort, Direction direction) {
        Component internalComponent = internalSimulation.getComponent(internalPort).getComponentModel();

        ((ReusableComponent)componentModel).addExternalPort(internalComponent,direction);
    }

    @FXML
    private void showContextMenu(ContextMenuEvent contextMenuEvent) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem showContents = new MenuItem("Show contents in new window");
        showContents.setOnAction(event -> ComponentInternalWindow.newWindow(newWindowScene, this.getComponentModel().getStringIdentifier()));

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

    public void wireDelay() {
        internalSimulation.wireDelay();
    }

}
