package main.ui.component.controllers;

import main.ui.component.model.component.ComponentParameters;
import main.ui.simulation.SimulationController;
import main.model.Coordinates;
import main.model.Direction;
import main.model.PortParameters;
import main.model.PortType;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ComponentControllerFactoryTest extends ApplicationTest{

    @Test
    public void getComponentControllerNot() {
        ComponentController testNotController = ComponentControllerFactory.getComponentController(mock(SimulationController.class),new ComponentParameters(new Coordinates(0,0), "not1", "not",0,new ArrayList<>()));
        assertNotNull(testNotController);
    }

    @Test
    public void getComponentControllerDff() {
        ComponentController testDffController = ComponentControllerFactory.getComponentController(mock(SimulationController.class),new ComponentParameters(new Coordinates(0,0), "not1","dff",0,new ArrayList<>()));
        assertNotNull(testDffController);
    }

    @Test
    public void getComponentControllerNand() {
        List<PortParameters> portParameters = new ArrayList<>();

        portParameters.add(new PortParameters(Direction.EAST, PortType.OUTPUT, 1));
        portParameters.add(new PortParameters(Direction.WEST,PortType.INPUT, 1));
        portParameters.add(new PortParameters(Direction.WEST,PortType.INPUT, 1));

        ComponentController testMuxController = ComponentControllerFactory.getComponentController(mock(SimulationController.class),new ComponentParameters(new Coordinates(0,0), "not1","mux1",0,portParameters));
        assertNotNull(testMuxController);
    }

    @Test
    public void getComponentControllerError() {
        ComponentController testError = ComponentControllerFactory.getComponentController(mock(SimulationController.class),new ComponentParameters(new Coordinates(0,0), "not1","incorrect",0,new ArrayList<>()));
        assertNull(testError);
        // factory should return null if an a reusable component is not found
    }
}