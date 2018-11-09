package javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import utils.FxmlLoaderUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Descriptions {

    private static Map<String, Parent> descriptions = new HashMap<>();

    private static String DESCRIPTION_PATH = "componentDescriptions/";
    private static String CHARACTER_AFTER_TYPE = "D";

    static {
        File folder = new File(Descriptions.class.getClassLoader().getResource(DESCRIPTION_PATH).getFile());
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles != null) {
            for(File file : listOfFiles) {
                String fileName = file.getName();
                String type = fileName.substring(0,fileName.lastIndexOf(CHARACTER_AFTER_TYPE));
                Parent description = FxmlLoaderUtils.loadFxml(DESCRIPTION_PATH + fileName).getNode();
                descriptions.put(type, description);
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
