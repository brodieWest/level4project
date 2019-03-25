package main.ui.descriptions;

import javafx.scene.Parent;
import main.fxml.Fxml;
import main.fxml.FxmlLoaderUtils;
import main.ui.main.Mainfx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Descriptions {

    private static Map<String, Parent> descriptions = new HashMap<>();

    private static String DESCRIPTION_PATH = "componentDescriptions/%sDescription.fxml";

    private static Logger logger = LogManager.getLogger(Descriptions.class);

    private static String[] components = {"input", "output", "and", "or", "not", "xor", "dff", "wordInput", "wordOutput", "split", "join", "add4", "demux1", "demux2", "fullAdd", "halfAdd", "mux1", "mux1w", "mux2w", "reg1", "reg4"};

    private static List<String> descriptionNames = new ArrayList<>(Arrays.asList(components));

    static {
        for (String fileName : descriptionNames) {
            Fxml fxml = FxmlLoaderUtils.loadFxml(Mainfx.class.getResource(String.format(DESCRIPTION_PATH, fileName)));
            if (fxml != null) {
                Parent description = fxml.getNode();
                descriptions.put(fileName, description);
            } else {
                logger.error(String.format("Could not load description for %s", fileName));
            }
        }
    }

    public static Parent getDescription(String type) {
        return descriptions.get(type);
    }

    public static boolean hasDescription(String type) {
        return descriptions.containsKey(type);
    }
}
