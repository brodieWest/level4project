package javafx.component.controllers;

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
        ComponentController testNotController = ComponentControllerFactory.getComponentController(mock(SimulationController.class),"not",new Coordinates(0,0), "not1",1,1);
        assertNotNull(testNotController);
    }

    @Test
    public void getComponentControllerDff() {
        ComponentController testDffController = ComponentControllerFactory.getComponentController(mock(SimulationController.class),"dff",new Coordinates(0,0), "not1",1,1);
        assertNotNull(testDffController);
    }

    @Test
    public void getComponentControllerNand() {
        ComponentController testNandController = ComponentControllerFactory.getComponentController(mock(SimulationController.class),"nand",new Coordinates(0,0), "not1",2,1);
        assertNotNull(testNandController);
    }

    @Ignore
    @Test
    public void getComponentControllerError() {
        ComponentController testError = ComponentControllerFactory.getComponentController(mock(SimulationController.class),"incorrect",new Coordinates(0,0), "not1",1,1);
        assertNull(testError);
        // factory should return null if an a reusable component is not found
    }
}