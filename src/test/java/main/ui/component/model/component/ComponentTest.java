package main.ui.component.model.component;

import main.ui.component.model.component.gates.AndGate;
import main.ui.component.model.component.gates.NotGate;
import main.model.Coordinates;
import main.model.Direction;
import main.model.PortParameters;
import main.model.PortType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ComponentTest {

    @Before
    public void setup() {
    }


    @Test
    public void addNewInput() {
        Component and = new AndGate(new ComponentParameters(new Coordinates(1,1), "testand","and",0, new ArrayList<>()));

        assertEquals(50,and.getInput(0).getOffset().getX());
        assertEquals(40,and.getInput(0).getOffset().getY());

        assertEquals(50,and.getInput(1).getOffset().getX());
        assertEquals(60,and.getInput(1).getOffset().getY());
    }

    @Test
    public void addNewOutput() {
        Component not = new NotGate(new ComponentParameters(new Coordinates(1,1), "testnot","not",0, new ArrayList<>()));

        assertEquals(50,not.getOutput(0).getOffset().getX());
        assertEquals(50,not.getOutput(0).getOffset().getY());

    }

    @Test
    public void addPort() {
        List<PortParameters> portParameters = new ArrayList<>();

        portParameters.add(new PortParameters(Direction.EAST, PortType.OUTPUT,1));
        portParameters.add(new PortParameters(Direction.WEST, PortType.INPUT,1));
        portParameters.add(new PortParameters(Direction.WEST, PortType.INPUT,1));
        portParameters.add(new PortParameters(Direction.SOUTH, PortType.INPUT,1));
        portParameters.add(new PortParameters(Direction.SOUTH, PortType.INPUT,1));

        Component and = new AndGate(new ComponentParameters(new Coordinates(0,0), "testand4","and",0,portParameters ));

        assertEquals(50,and.getInput(0).getOffset().getX());
        assertEquals(40,and.getInput(0).getOffset().getY());

        assertEquals(50,and.getInput(1).getOffset().getX());
        assertEquals(60,and.getInput(1).getOffset().getY());

        assertEquals(40,and.getInput(2).getOffset().getX());
        assertEquals(50,and.getInput(2).getOffset().getY());

        assertEquals(60,and.getInput(3).getOffset().getX());
        assertEquals(50,and.getInput(3).getOffset().getY());
    }

    @Test
    public void getPortLocations() {
        Component and = new AndGate(new ComponentParameters(new Coordinates(100,200), "testand","and",0, new ArrayList<>()));

        Map<String,Integer> map = and.getPortLocations();

        assertEquals(150, (int)map.get("testand.inputPorts1.x"));
        assertEquals(240, (int)map.get("testand.inputPorts0.y"));
        assertEquals(150, (int)map.get("testand.outputPorts0.x"));
    }
}