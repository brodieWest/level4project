package main.ui;

import javafx.scene.Parent;
import main.fxml.Fxml;
import main.fxml.FxmlLoaderUtils;
import main.ui.main.Mainfx;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Descriptions {

    private static Map<String, Parent> descriptions = new HashMap<>();

    private static String DESCRIPTION_PATH = "componentDescriptions/";
    private static String CHARACTER_AFTER_TYPE = "D";

    static {
        File folder = new File(Mainfx.class.getResource(DESCRIPTION_PATH).getFile());
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles != null) {
            for(File file : listOfFiles) {
                String fileName = file.getName();
                String type = fileName.substring(0,fileName.lastIndexOf(CHARACTER_AFTER_TYPE));
                Fxml fxml = FxmlLoaderUtils.loadFxml(Mainfx.class.getResource(DESCRIPTION_PATH + fileName));
                if(fxml != null) {
                    Parent description = fxml.getNode();
                    descriptions.put(type, description);
                }
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
