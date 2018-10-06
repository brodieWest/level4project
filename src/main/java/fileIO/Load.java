package fileIO;

import javafx.main.Mainfx;
import model.Coordinates;
import javafx.simulation.SimulationController;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.stream.Collectors;

public class Load {

    public static void loadFromFile(SimulationController simulationController) {
        JSONObject circuit = new JSONObject(loadTextFromFile());

        loadComponents(circuit, simulationController);

        loadWires(circuit, simulationController);
    }

    private static void loadComponents(JSONObject circuit, SimulationController simulationController) {
        JSONArray components = circuit.getJSONArray("components");

        for(Object componentObject : components) {
            JSONObject component = (JSONObject)componentObject;
            Coordinates coordinates = new Coordinates(component.getInt("xCoord"), component.getInt("yCoord"));
            simulationController.addComponent(component.getString("type"), coordinates);
        }
    }

    private static void loadWires(JSONObject circuit, SimulationController simulationController) {
        JSONArray wires = circuit.getJSONArray("wires");

        for(Object wireOject : wires) {
            JSONObject wireJson = (JSONObject)wireOject;
            JSONObject inputJson = wireJson.getJSONObject("input");
            JSONObject outputJson = wireJson.getJSONObject("output");
            simulationController.addWire(inputJson.getString("component"), inputJson.getInt("port"), outputJson.getString("component"), outputJson.getInt("port") );

            //wire.setInput()
        }
    }

    private static String loadTextFromFile() {
        File file = Mainfx.openFileWindow();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            return reader.lines().collect(Collectors.joining());
        } catch (FileNotFoundException | NullPointerException e) {
            //TODO show error to user
            e.printStackTrace();
        }

        return reader.lines().collect(Collectors.joining());
    }
}
