package javafx.component.controllers;

import javafx.component.model.component.ComponentParameters;
import javafx.simulation.SimulationController;
import model.Coordinates;
import org.junit.Ignore;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ComponentControllerFactoryTest extends ApplicationTest{

    @Test
    public void getComponentControllerNot() {
        ComponentController testNotController = ComponentControllerFactory.getComponentController(mock(SimulationController.class),new ComponentParameters(new Coordinates(0,0), "not1", "not",1,1));
        assertNotNull(testNotController);
    }

    @Test
    public void getComponentControllerDff() {
        ComponentController testDffController = ComponentControllerFactory.getComponentController(mock(SimulationController.class),new ComponentParameters(new Coordinates(0,0), "not1","dff",1,1));
        assertNotNull(testDffController);
    }

    @Test
    public void getComponentControllerNand() {
        ComponentController testNandController = ComponentControllerFactory.getComponentController(mock(SimulationController.class),new ComponentParameters(new Coordinates(0,0), "not1","nand",2,1));
        assertNotNull(testNandController);
    }

    @Test
    public void getComponentControllerError() {
        ComponentController testError = ComponentControllerFactory.getComponentController(mock(SimulationController.class),new ComponentParameters(new Coordinates(0,0), "not1","incorrect",1,1));
        assertNull(testError);
        // factory should return null if an a reusable component is not found
    }
}