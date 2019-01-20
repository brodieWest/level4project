package main.fileIO;

import com.google.gson.JsonArray;
import main.ui.component.controllers.ReusableComponentController;
import main.ui.component.model.component.ComponentParameters;
import main.ui.main.Mainfx;
import main.model.*;
import main.ui.simulation.InternalController;
import main.ui.simulation.SimulationController;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Load {

    private static final String PORT_MAPPING = "portMapping";
    private static final String EXTERNAL_PORT = "externalPort";
    private static final String INTERNAL_PORT = "internalPort";
    private static final String ROTATION = "rotation";
    private static String COMPONENTS = "components";
    private static String COMPONENT = "component";
    private static String TYPE = "type";
    private static String WIRES = "wires";
    private static String INPUT = "input";
    private static String OUTPUT = "output";
    private static String PORT = "port";
    private static String COORDINATES = "coordinates";
    private static String X = "x";
    private static String Y = "y";
    //private static String XCOORD = "xCoord";
    //private static String YCOORD = "yCoord";
    private static String UUID = "uuid";
    private static String INPUTPORTS = "inputPorts";
    private static String OUTPUTPORTS = "outputPorts";
    private static String CORNER = "corner";
    private static String CONSTANTS = "constants";
    private static String DIRECTION = "direction";
    private static String SIZE = "size";

    private static Logger logger = LogManager.getLogger(Load.class);

    public static void loadWithFileChooser(SimulationController simulationController) {

        String jsonText = loadTextWithFileChooser();
        if(jsonText == null) {
            return;
        }
        load(simulationController,jsonText);
    }

    public static boolean loadFromFile(SimulationController simulationController, String fileName) {
        String file = loadTextFromFile(fileName);
        if(file == null) {
            logger.error(String.format("failed to load from: %s", fileName));
            return false;
        }

        load(simulationController, file);
        return true;
    }

    public static boolean loadInternal(SimulationController simulationController, ReusableComponentController reusableController, String fileName) {
        String file = loadTextFromPath(fileName);
        if(file == null) {
            logger.error(String.format("failed to load from: %s", fileName));
            return false;
        }

        load(simulationController, file);

        loadInterface(simulationController, reusableController, file);

        return true;
    }

    static String loadTextFromFile(String fileName) {
        InputStream inputStream = Mainfx.class.getResourceAsStream(fileName);
        if(inputStream == null) return null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines().collect(Collectors.joining());
    }

    static String loadTextFromPath(String filePath) {
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            return reader.lines().collect(Collectors.joining());
        } catch (FileNotFoundException e) {
            return null;
        }

    }

    static void load(SimulationController simulationController, String file) {
        simulationController.clear();

        JSONObject circuit = new JSONObject(file);

        Evaluator evaluator = new Evaluator();

        boolean loadConstantsCorrect = loadConstants(circuit, evaluator, simulationController);

        boolean loadComponentsCorrect = loadComponents(circuit, simulationController, evaluator);

        addPortVariables(evaluator,simulationController);

        boolean loadWiresCorrect = loadWires(circuit, simulationController, evaluator);

        if(!loadConstantsCorrect || !loadComponentsCorrect || !loadWiresCorrect) {
            logger.info("clearing screen as loading failed");
            simulationController.clear();
            return;
        }

        simulationController.resetSimulation();
        simulationController.wireDelay();

    }

    private static boolean loadConstants(JSONObject circuit, Evaluator evaluator, SimulationController simulationController) {
        if(!circuit.has(CONSTANTS)) return true;
        JSONObject variableJson = circuit.getJSONObject(CONSTANTS);

        for(String key : variableJson.keySet()) {
            evaluator.putVariable(key, Integer.toString(variableJson.getInt(key)));
        }
        return true;
    }

    private static void addPortVariables(Evaluator evaluator, SimulationController simulationController) {
        var portLocations = simulationController.getPortLocations();

        for(String key : portLocations.keySet()) {
            evaluator.putVariable(key, Integer.toString(portLocations.get(key)));
        }
    }

    static boolean loadComponents(JSONObject circuit, SimulationController simulationController, Evaluator evaluator) {
        JSONArray components = circuit.getJSONArray(COMPONENTS);

        for(Object componentObject : components) {
            JSONObject component = (JSONObject)componentObject;
            String uuid = component.getString(UUID);

            JSONObject coordinatesJson = component.getJSONObject(COORDINATES);

            int xCoord;
            int yCoord;
            try {
                xCoord = parseEval(coordinatesJson, X, evaluator);
                yCoord = parseEval(coordinatesJson, Y, evaluator);
            } catch (EvaluationException e) {
                logger.error("failed to parse expression");
                return false;
            }

            Coordinates coordinates = new Coordinates(xCoord,yCoord);

            List<PortParameters> portParametersList = new ArrayList<>();

            portParametersList.addAll(loadPort(component,PortType.INPUT));

            portParametersList.addAll(loadPort(component,PortType.OUTPUT));

            int rotation;
            if(component.has(ROTATION)) {
                rotation = component.getInt(ROTATION);
            } else {
                rotation = 0;
            }

            ComponentParameters componentParameters = new ComponentParameters(coordinates, uuid, component.getString(TYPE), rotation, portParametersList);

            if (!simulationController.addComponent(componentParameters)) {
                logger.error("failed to load components");
                return false;
            }
        }
        return true;
    }

    private static List<PortParameters> loadPort(JSONObject component, PortType portType) {
        List<PortParameters> portParametersList = new ArrayList<>();
        String identifier = portType.getIdentifier();
        if(component.has(identifier)) {
            if(component.get(identifier) instanceof Integer) {
                int outputPorts = component.getInt(identifier);
                for (int i = 0; i < outputPorts; i++) {
                    portParametersList.add(new PortParameters(portType.getDirection(), portType));
                }
            } else {
                JSONArray outputsJson = component.getJSONArray(identifier);
                for (Object inputObject : outputsJson) {
                    JSONObject inputJson = (JSONObject) inputObject;
                    int size=1;
                    if(inputJson.has(SIZE)) {
                        size = inputJson.getInt(SIZE);
                    }
                    Direction direction = portType.getDirection();
                    if(inputJson.has(DIRECTION)) {
                        direction = Direction.valueOf(inputJson.getString(DIRECTION));
                    }
                    portParametersList.add(new PortParameters(direction, portType, size));
                }
            }
        }
        return portParametersList;
    }

    static boolean loadWires(JSONObject circuit, SimulationController simulationController, Evaluator evaluator) {
        JSONArray wires = circuit.getJSONArray(WIRES);

        for(Object wireObject : wires) {
            JSONObject wireJson = (JSONObject)wireObject;
            JSONObject inputJson = wireJson.getJSONObject(INPUT);
            JSONArray outputsJson = wireJson.getJSONArray(OUTPUT);

            PortIdentifier inputPort = new PortIdentifier(inputJson.getString(COMPONENT), inputJson.getInt(PORT));

            ArrayList<PortIdentifier> outputPorts = new ArrayList<>();

            for(Object object : outputsJson) {
                JSONObject outputJson = (JSONObject) object;

                PortIdentifier portIdentifier = new PortIdentifier(outputJson.getString(COMPONENT), outputJson.getInt(PORT));

                if(outputJson.has(CORNER)) {
                    JSONArray cornersJson = outputJson.getJSONArray(CORNER);

                    for(Object cornerObject : cornersJson) {
                        JSONObject cornerJson = (JSONObject) cornerObject;

                        int xCoord;
                        int yCoord;
                        try {
                            xCoord = parseEval(cornerJson, X, evaluator);
                            yCoord = parseEval(cornerJson, Y, evaluator);
                        } catch (EvaluationException e) {
                            logger.error("failed to parse expression in wires");
                            e.printStackTrace();
                            return false;
                        }

                        portIdentifier.addCorner(new Coordinates(xCoord, yCoord));
                    }
                }

                outputPorts.add(portIdentifier);
            }

            if (!simulationController.addWire(wireJson.getString(UUID), inputPort, outputPorts)) {
                logger.error(String.format("wire loading failed at input %s %d", inputJson.getString(COMPONENT), inputJson.getInt(PORT)));
                return false;
            }
        }
        return true;
    }

    private static void loadInterface(SimulationController simulationController, ReusableComponentController reusableController, String file) {
        JSONObject fileJson = new JSONObject(file);

        JSONArray interfaceArray = fileJson.getJSONArray(PORT_MAPPING);

        for(Object interfaceObject : interfaceArray) {
            JSONObject interfaceJson = (JSONObject) interfaceObject;
            Direction direction = Direction.valueOf(interfaceJson.getString(DIRECTION));
            String internalPort = interfaceJson.getString(INTERNAL_PORT);
            reusableController.addExternalPort(internalPort, direction);
        }

    }

    private static String loadTextWithFileChooser() {
        File file = Mainfx.openFileLoadWindow();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            return reader.lines().collect(Collectors.joining());
        } catch (FileNotFoundException | NullPointerException e) {
            logger.error("No file was chosen");
        }

        return null;
    }

    private static int parseEval(JSONObject jsonObject, String key, Evaluator evaluator) throws EvaluationException {
        int result;
        if(jsonObject.get(key) instanceof Integer) {
            result = jsonObject.getInt(key);
        } else {
            result = (int)Math.floor(Float.valueOf(evaluator.evaluate(jsonObject.getString(key))));
        }
        return result;
    }
}
