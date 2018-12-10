package utils.fxml;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import ui.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class FxmlLoaderUtils {
    private static Logger logger = LogManager.getLogger(FxmlLoaderUtils.class);

    public static Fxml loadFxml(String fileName) {

        FXMLLoader fxmlLoader = new FXMLLoader(FxmlLoaderUtils.class.getClassLoader().getResource(fileName));

        Parent componentNode;

        try {
            componentNode = fxmlLoader.load();
        } catch (IOException | ExceptionInInitializerError e) {
            logger.error(String.format("Failed to get component Node from FxmlLoader. FilePath: %s", fileName));
            return null;
        }

        Controller controller = fxmlLoader.getController();

        return new Fxml(controller, componentNode);
    }

    public static void loadFxml(String fileName, Controller controller) {

        FXMLLoader fxmlLoader = new FXMLLoader(FxmlLoaderUtils.class.getClassLoader().getResource(fileName));

        fxmlLoader.setController(controller);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            logger.error(String.format("Failed to load fxml given controller. FilePath: %s. Controller type: %s", fileName, controller.getClass().getName()));
        }
    }
}
