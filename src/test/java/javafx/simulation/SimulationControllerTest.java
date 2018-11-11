package javafx.simulation;

import javafx.scene.Group;
import model.Coordinates;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class SimulationControllerTest extends ApplicationTest {

    @Test
    public void addWireErrors() {
        SimulationController simulationController = new SimulationController();

        simulationController.addComponent("and",new Coordinates(0,0), "and1", 2,1);
        simulationController.addComponent("and",new Coordinates(0,0), "and2", 2,1);

        assertFalse(simulationController.addWire("and1", 1, "and2", 1));
        assertFalse(simulationController.addWire("error",0,"and2",0));
    }

    @Test
    public void addWire() {
        SimulationController simulationController = new SimulationController();

        simulationController.addComponent("and",new Coordinates(0,0), "and1", 2,1);
        simulationController.addComponent("and",new Coordinates(0,0), "and2", 2,1);

        assertTrue(simulationController.addWire("and1", 0, "and2", 1));

        assertEquals("and1", simulationController.getWireController("wire0").getWire().getInput().getComponent().getUuid());
        assertEquals("and2", simulationController.getWireController("wire0").getWire().getOutput(0).getComponent().getUuid());
    }

    @Test
    public void addComponent() {
        SimulationController simulationController = new SimulationController();

        simulationController.addComponent("and",new Coordinates(0,0), "and1", 2,1);

        //assertEquals(0,lookup("#and").queryAs(Group.class).getLayoutX());

        assertEquals("and1", simulationController.getComponentController("and1").getUuid());
        assertEquals("and", simulationController.getComponentController("and1").getComponentModel().getStringIdentifier());
    }
}