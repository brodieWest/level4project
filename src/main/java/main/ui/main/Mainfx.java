package main.ui.main;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class Mainfx extends Application {

    private static Stage stage;

    private static Logger logger = LogManager.getLogger(Mainfx.class);

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/logicsimMain.fxml"));
        primaryStage.setTitle("Logic Simulator");
        Scene mainScene = new Scene(root, 800, 500);
        mainScene.getStylesheets().add(getClass().getResource("css/css").toExternalForm());
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static File openFileLoadWindow() {
        FileChooser fileChooser = new FileChooser();
        try {
            String currentPath = Paths.get("./examples").toAbsolutePath().normalize().toString();
            File defaultDirectory = new File(currentPath);
            fileChooser.setInitialDirectory(defaultDirectory);
            return fileChooser.showOpenDialog(stage);
        } catch (RuntimeException e) {
            FileChooser fileChooserWithoutDefault = new FileChooser();
            return fileChooserWithoutDefault.showOpenDialog(stage);
        }
    }

    public static void openFileSaveWindow(String fileString) {
        File file;
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            String currentPath = Paths.get("./examples").toAbsolutePath().normalize().toString();
            File defaultDirectory = new File(currentPath);
            fileChooser.setInitialDirectory(defaultDirectory);
            file = fileChooser.showSaveDialog(stage);
        } catch (RuntimeException e) {
            FileChooser fileChooserWithoutDefault = new FileChooser();
            fileChooserWithoutDefault.setTitle("Save File");
            file = fileChooserWithoutDefault.showSaveDialog(stage);
        }

        if (file != null) {
            try {
                PrintWriter writer;
                writer = new PrintWriter(file);
                writer.println(fileString);
                writer.close();
            } catch (IOException e) {
                logger.error("No file path chosen.");
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
