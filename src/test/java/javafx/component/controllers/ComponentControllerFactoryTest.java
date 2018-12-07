package javafx.component.controllers;

import javafx.component.model.component.ComponentParameters;
import javafx.simulation.SimulationController;
import model.Coordinates;
import model.Direction;
import model.PortParameters;
import model.PortType;
import org.junit.Ignore;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ComponentControllerFactoryTest extends ApplicationTest{

    @Test
    public void getComponentControllerNot() {
        ComponentController testNotController = ComponentControllerFactory.getComponentController(mock(SimulationController.class),new ComponentParameters(new Coordinates(0,0), "not1", "not",new ArrayList<>()));
        assertNotNull(testNotController);
    }

    @Test
    public void getComponentControllerDff() {
        ComponentController testDffController = ComponentControllerFactory.getComponentController(mock(SimulationController.class),new ComponentParameters(new Coordinates(0,0), "not1","dff",new ArrayList<>()));
        assertNotNull(testDffController);
    }

    @Test
    public void getComponentControllerNand() {
        List<PortParameters> portParameters = new ArrayList<>();

        portParameters.add(new PortParameters(Direction.EAST, PortType.OUTPUT));
        portParameters.add(new PortParameters(Direction.WEST,PortType.INPUT));
        portParameters.add(new PortParameters(Direction.WEST,PortType.INPUT));

        ComponentController testNandController = ComponentControllerFactory.getComponentController(mock(SimulationController.class),new ComponentParameters(new Coordinates(0,0), "not1","nand",portParameters));
        assertNotNull(testNandController);
    }

    @Test
    public void getComponentControllerError() {
        ComponentController testError = ComponentControllerFactory.getComponentController(mock(SimulationController.class),new ComponentParameters(new Coordinates(0,0), "not1","incorrect",new ArrayList<>()));
        assertNull(testError);
        // factory should return null if an a reusable component is not found
    }
}