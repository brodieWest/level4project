package fileIO;

import javafx.main.Mainfx;
import model.Coordinates;
import javafx.simulation.SimulationController;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.stream.Collectors;

public class Load {

    private static String COMPONENTS = "components";
    private static String COMPONENT = "component";
    private static String TYPE = "type";
    private static String WIRES = "wires";
    private static String INPUT = "input";
    private static String OUTPUT = "output";
    private static String PORT = "port";
    private static String XCOORD = "xCoord";
    private static String YCOORD = "yCoord";

    public static void loadFromFile(SimulationController simulationController) {
        simulationController.clear();

        String JsonText = loadTextFromFile();
        if(JsonText == null) {
            //TODO show user error message
            return;
        }
        JSONObject circuit = new JSONObject(JsonText);

        loadComponents(circuit, simulationController);

        loadWires(circuit, simulationController);

        simulationController.resetSimulation();
        simulationController.wireDelay();

    }

    private static void loadComponents(JSONObject circuit, SimulationController simulationController) {
        JSONArray components = circuit.getJSONArray(COMPONENTS);

        for(Object componentObject : components) {
            JSONObject component = (JSONObject)componentObject;
            Coordinates coordinates = new Coordinates(component.getInt(XCOORD), component.getInt(YCOORD));
            simulationController.addComponent(component.getString(TYPE), coordinates);
        }
    }

    private static void loadWires(JSONObject circuit, SimulationController simulationController) {
        JSONArray wires = circuit.getJSONArray(WIRES);

        for(Object wireOject : wires) {
            JSONObject wireJson = (JSONObject)wireOject;
            JSONObject inputJson = wireJson.getJSONObject(INPUT);
            JSONObject outputJson = wireJson.getJSONObject(OUTPUT);
            simulationController.addWire(inputJson.getString(COMPONENT), inputJson.getInt(PORT), outputJson.getString(COMPONENT), outputJson.getInt(PORT) );

            //wire.setInput()
        }
    }

    private static String loadTextFromFile() {
        File file = Mainfx.openFileWindow();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            return reader.lines().collect(Collectors.joining());
        } catch (FileNotFoundException | NullPointerException e) {
            //TODO show error to user
            e.printStackTrace();
        }

        return null;
    }
}
