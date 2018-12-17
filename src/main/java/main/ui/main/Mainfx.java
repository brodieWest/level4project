package main.ui.main;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Mainfx extends Application {

    private static Stage stage;

    //private static Stage reusableInternalStage = new Stage();

   // private static FileChooser fileChooser = new FileChooser();

    //private static AnchorPane root = new AnchorPane();

    //private static Scene newScene = new Scene(root, 800, 500);

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        //System.out.println(getClass().getResource("fxml/logicsimMain.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("fxml/logicsimMain.fxml"));
        primaryStage.setTitle("Hello World");
        Scene mainScene = new Scene(root, 800, 500);
        mainScene.getStylesheets().add(getClass().getResource("css/css").toExternalForm());
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static File openFileWindow() {
        FileChooser fileChooser = new FileChooser();
        try {
            File defaultDirectory = new File(Mainfx.class.getClassLoader().getResource("main/ui/main/fileExamples/").getFile());
            fileChooser.setInitialDirectory(defaultDirectory);
            return fileChooser.showOpenDialog(stage);
        } catch (RuntimeException e) {
            FileChooser fileChooserWithoutDefault = new FileChooser();
            return fileChooserWithoutDefault.showOpenDialog(stage);
        }
    }

    public static void newWindow(Parent internal, String title) {
        /*reusableInternalStage.setTitle(title);
        root.getChildren().clear();
        root.getChildren().add(internal);

        newScene.getStylesheets().add(Mainfx.class.getResource("/css/css").toExternalForm());
        reusableInternalStage.setScene(newScene);
        reusableInternalStage.show();*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}