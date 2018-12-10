package fileIO;

import ui.component.model.component.ComponentParameters;
import ui.simulation.SimulationController;
import model.Coordinates;
import model.PortIdentifier;
import net.sourceforge.jeval.Evaluator;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class LoadTest {

    private final String file = loadTextFromFile("/fileExamplesForTesting/filejson");

    private final JSONObject testWireJson = new JSONObject(loadTextFromFile("/fileExamplesForTesting/wirejson"));

    private final JSONObject testComponentJson = new JSONObject(loadTextFromFile("/fileExamplesForTesting/componentjson"));

    @Test
    public void loadFromFileError() {
        SimulationController mocksim = mock(SimulationController.class);

        assertFalse(Load.loadFromFile(mocksim, "notAFilePath"));
    }

    @Test
    public void load() {
        SimulationController mocksim = mock(SimulationController.class);

        when(mocksim.addComponent(any(ComponentParameters.class))).thenReturn(true);
        when(mocksim.addWire(anyString(),any(PortIdentifier.class),any(ArrayList.class))).thenReturn(true);



        Load.load(mocksim,file);

        ArgumentCaptor<ComponentParameters> inputComponentParametersArgumentCaptor = ArgumentCaptor.forClass(ComponentParameters.class);
        ArgumentCaptor<ComponentParameters> notComponentParametersArgumentCaptor = ArgumentCaptor.forClass(ComponentParameters.class);

        //verify(mocksim).addComponent(inputComponentParametersArgumentCaptor.capture());
        verify(mocksim, times(2)).addComponent(notComponentParametersArgumentCaptor.capture());

        verify(mocksim).resetSimulation();
        verify(mocksim).wireDelay();

        assertEquals(new ComponentParameters(new Coordinates(0,0), "input1", "input", new ArrayList<>()), notComponentParametersArgumentCaptor.getValue());
        //assertEquals(new ComponentParameters(new Coordinates(100,900), "not1", "not", 1, 1), notComponentParametersArgumentCaptor.getValue());

        ArgumentCaptor<PortIdentifier> inputPort = ArgumentCaptor.forClass(PortIdentifier.class);
        ArgumentCaptor<ArrayList<PortIdentifier>> outputPorts = ArgumentCaptor.forClass(ArrayList.class);

        verify(mocksim).addWire(eq("wire1"), inputPort.capture(),outputPorts.capture());

        assertEquals(new PortIdentifier("input1",0), inputPort.getValue());
        assertEquals(new PortIdentifier("not1",10), outputPorts.getValue().get(0));
    }


    @Test
    public void loadWithComponentError() {
        SimulationController mocksim = mock(SimulationController.class);

        when(mocksim.addComponent(any(ComponentParameters.class))).thenReturn(false);


        Load.load(mocksim,file);

        verify(mocksim, times(2)).clear();
    }

    @Test
    public void loadWithWireError() {
        SimulationController mocksim = mock(SimulationController.class);

        when(mocksim.addComponent(any(ComponentParameters.class))).thenReturn(true);
        //when(mocksim.addWire(anyString(),anyInt(),anyString(),anyInt())).thenReturn(false);
        when(mocksim.addWire(anyString(), any(PortIdentifier.class),any(ArrayList.class))).thenReturn(false);

        Load.load(mocksim,file);

        verify(mocksim, times(2)).clear();
    }

    @Test
    public void loadComponents() {
        SimulationController mocksim = mock(SimulationController.class);

        when(mocksim.addComponent(any(ComponentParameters.class))).thenReturn(true);

        Evaluator evaluator = new Evaluator();

        Load.loadComponents(testComponentJson, mocksim, evaluator);

        ArgumentCaptor<Coordinates> notCoordinatesCaptor = ArgumentCaptor.forClass(Coordinates.class);
        ArgumentCaptor<Coordinates> inputCoordinatesCaptor = ArgumentCaptor.forClass(Coordinates.class);

        ArgumentCaptor<ComponentParameters> notComponentParametersArgumentCaptor = ArgumentCaptor.forClass(ComponentParameters.class);

        verify(mocksim, times(2)).addComponent(notComponentParametersArgumentCaptor.capture());

        assertEquals(new ComponentParameters(new Coordinates(100,900), "not1", "not", new ArrayList<>()), notComponentParametersArgumentCaptor.getValue());
    }

    @Test
    public void loadWires() {
        SimulationController mocksim = mock(SimulationController.class);
        when(mocksim.addWire(anyString(), any(PortIdentifier.class),any(ArrayList.class))).thenReturn(true);

        Evaluator evaluator = new Evaluator();

        evaluator.putVariable("size","50");
        evaluator.putVariable("multiplier", "2");

        Load.loadWires(testWireJson, mocksim, evaluator);

        ArgumentCaptor<PortIdentifier> inputPort = ArgumentCaptor.forClass(PortIdentifier.class);
        ArgumentCaptor<ArrayList<PortIdentifier>> outputPorts = ArgumentCaptor.forClass(ArrayList.class);


        verify(mocksim).addWire(eq("wire2"), inputPort.capture(),outputPorts.capture());
        verify(mocksim).addWire(eq("wire1"), inputPort.capture(),outputPorts.capture());

        assertEquals(new PortIdentifier("input1",0), inputPort.getValue());
        assertEquals(new PortIdentifier("not1",0), outputPorts.getValue().get(0));
        assertEquals(new PortIdentifier("not4",6), outputPorts.getValue().get(1));

    }


    private static String loadTextFromFile(String fileName) {
        InputStream inputStream = LoadTest.class.getResourceAsStream(fileName);
        if(inputStream == null) return null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines().collect(Collectors.joining());
    }
}