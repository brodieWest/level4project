package main.fileIO;

import main.model.*;
import main.ui.component.model.component.ComponentParameters;
import main.ui.main.MainController;
import main.ui.simulation.MainSimulationController;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class SaveTest extends ApplicationTest {

    @Test
    public void calculatePathDepthOneComponent() {
        MainSimulationController mainSimulationController = new MainSimulationController(mock(MainController.class));

        mainSimulationController.addComponent(new ComponentParameters(new Coordinates(0,0),"input1", "input",0, new ArrayList<>()));

        mainSimulationController.addComponent(new ComponentParameters(new Coordinates(100,0),"not1", "not",0, new ArrayList<>()));

        mainSimulationController.addComponent(new ComponentParameters(new Coordinates(200,0), "output1", "output",0, new ArrayList<>()));

        ArrayList<PortIdentifier> endPorts = new ArrayList<>();
        endPorts.add(new PortIdentifier("not1",0));
        mainSimulationController.addWire("wire1",new PortIdentifier("input1",0),endPorts);

        ArrayList<PortIdentifier> endPorts2 = new ArrayList<>();
        endPorts2.add(new PortIdentifier("output1",0));
        mainSimulationController.addWire("wire2",new PortIdentifier("not1",0),endPorts2);

        FileModel fileModel = mainSimulationController.getFileModel();

        List<ComponentParametersModel> components = fileModel.getComponents();

        List<WireModel> wires = fileModel.getWires();

        assertEquals(3, components.size());

        assertEquals(2, wires.size());
    }
}
