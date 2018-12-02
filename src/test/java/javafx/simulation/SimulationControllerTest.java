package javafx.simulation;

import javafx.component.model.component.ComponentParameters;
import javafx.scene.Group;
import model.Coordinates;
import model.PortIdentifier;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class SimulationControllerTest extends ApplicationTest {

    @Test
    public void addWireErrors() {
        SimulationController simulationController = new SimulationController();

        simulationController.addComponent(new ComponentParameters(new Coordinates(0,0), "and1", "and",2,1));
        simulationController.addComponent(new ComponentParameters(new Coordinates(0,0), "and2", "and",2,1));

        ArrayList<PortIdentifier> outputs = new ArrayList<>();
        outputs.add(new PortIdentifier("and2", 1));

        assertFalse(simulationController.addWire("wire1", new PortIdentifier("and1", 1), outputs));
        assertFalse(simulationController.addWire("wire2", new PortIdentifier("error",0), outputs));
    }

    @Test
    public void addWire() {
        SimulationController simulationController = new SimulationController();

        simulationController.addComponent(new ComponentParameters(new Coordinates(0,0), "and1","and", 2,1));
        simulationController.addComponent(new ComponentParameters(new Coordinates(0,0), "and2","and", 2,1));

        ArrayList<PortIdentifier> outputs = new ArrayList<>();
        outputs.add(new PortIdentifier("and2", 1));

        assertTrue(simulationController.addWire("wire0", new PortIdentifier("and1", 0),outputs));

        assertEquals("and1", simulationController.getWireController("wire0").getWire().getInput().getComponent().getUuid());
        assertEquals("and2", simulationController.getWireController("wire0").getWire().getOutput(0).getComponent().getUuid());
    }

    @Test
    public void addWireMultipleOutputs() {
        SimulationController simulationController = new SimulationController();

        simulationController.addComponent(new ComponentParameters(new Coordinates(0,0), "and1", "and",2,1));
        simulationController.addComponent(new ComponentParameters(new Coordinates(0,0), "and2", "and",2,1));
        simulationController.addComponent(new ComponentParameters(new Coordinates(0,0), "and3", "and",2,1));

        ArrayList<PortIdentifier> outputs = new ArrayList<>();
        outputs.add(new PortIdentifier("and2", 1));
        outputs.add(new PortIdentifier("and3", 0));

        assertTrue(simulationController.addWire("wire0", new PortIdentifier("and1", 0),outputs));

        assertEquals("and1", simulationController.getWireController("wire0").getWire().getInput().getComponent().getUuid());
        assertEquals("and2", simulationController.getWireController("wire0").getWire().getOutput(0).getComponent().getUuid());
        assertEquals("and3", simulationController.getWireController("wire0").getWire().getOutput(1).getComponent().getUuid());
    }

    @Test
    public void addComponent() {
        SimulationController simulationController = new SimulationController();

        simulationController.addComponent(new ComponentParameters(new Coordinates(0,0), "and1", "and", 2,1));

        //assertEquals(0,lookup("#and").queryAs(Group.class).getLayoutX());

        assertEquals("and1", simulationController.getComponentController("and1").getUuid());
        assertEquals("and", simulationController.getComponentController("and1").getComponentModel().getStringIdentifier());
    }
}