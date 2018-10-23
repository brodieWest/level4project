package javafx.main;

import javafx.application.Application;
import javafx.component.model.component.ComponentFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Mainfx extends Application {

    private static Stage stage;

    private static FileChooser fileChooser = new FileChooser();

    private static Desktop desktop = Desktop.getDesktop();

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        primaryStage.setTitle("Hello World");
        Scene mainScene = new Scene(root, 800, 500);
        mainScene.getStylesheets().add(getClass().getResource("/fileExamples/css/css").toExternalForm());
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static File openFileWindow() {
        return fileChooser.showOpenDialog(stage);
    }


    public static void main(String[] args) {
        ComponentFactory.initialise();
        launch(args);
    }
}
