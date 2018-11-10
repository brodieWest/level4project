package fileIO;

import javafx.main.Mainfx;
import model.Coordinates;
import javafx.simulation.SimulationController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static String UUID = "uuid";
    private static String INPUTPORTS = "inputPorts";
    private static String OUTPUTPORTS = "outputPorts";

    private static Logger logger = LogManager.getLogger(Load.class);

    public static void loadWithFileChooser(SimulationController simulationController) {

        String jsonText = loadTextWithFileChooser();
        if(jsonText == null) {
            return;
        }
        load(simulationController,jsonText);
    }

    public static void loadFromFile(SimulationController simulationController, String fileName) {

        InputStream inputStream = Load.class.getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String file = reader.lines().collect(Collectors.joining());

        load(simulationController, file);
    }

    static void load(SimulationController simulationController, String file) {
        simulationController.clear();

        JSONObject circuit = new JSONObject(file);

        if(!loadComponents(circuit, simulationController)) {
            logger.error("failed to load components");
            simulationController.clear();
            return;
        }

        loadWires(circuit, simulationController);

        simulationController.resetSimulation();
        simulationController.wireDelay();

    }

    static boolean loadComponents(JSONObject circuit, SimulationController simulationController) {
        JSONArray components = circuit.getJSONArray(COMPONENTS);

        for(Object componentObject : components) {
            JSONObject component = (JSONObject)componentObject;
            String uuid = component.getString(UUID);
            Coordinates coordinates = new Coordinates(component.getInt(XCOORD), component.getInt(YCOORD));

            int inputPorts, outputPorts;

            if(component.has(INPUTPORTS)) {
                inputPorts = component.getInt(INPUTPORTS);
            } else {
                inputPorts = 0;
            }

            if(component.has(OUTPUTPORTS)) {
                outputPorts = component.getInt(OUTPUTPORTS);
            } else {
                outputPorts = 0;
            }

            if (!simulationController.addComponent(component.getString(TYPE), coordinates, uuid, inputPorts, outputPorts)) return false;
        }
        return true;
    }

    static void loadWires(JSONObject circuit, SimulationController simulationController) {
        JSONArray wires = circuit.getJSONArray(WIRES);

        for(Object wireOject : wires) {
            JSONObject wireJson = (JSONObject)wireOject;
            JSONObject inputJson = wireJson.getJSONObject(INPUT);
            JSONObject outputJson = wireJson.getJSONObject(OUTPUT);
            simulationController.addWire(inputJson.getString(COMPONENT), inputJson.getInt(PORT), outputJson.getString(COMPONENT), outputJson.getInt(PORT) );

            //wire.setInput()
        }
    }

    private static String loadTextWithFileChooser() {
        File file = Mainfx.openFileWindow();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            return reader.lines().collect(Collectors.joining());
        } catch (FileNotFoundException | NullPointerException e) {
            logger.error("No file was chosen");
        }

        return null;
    }
}
