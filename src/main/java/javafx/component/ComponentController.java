package javafx.component;

import javafx.Controller;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.component.model.component.Component;
import javafx.simulation.model.Simulator;

import java.io.InputStream;

public class ComponentController implements Controller {

    private Component componentModel;

    @FXML
    private Parent component;

    @FXML
    private VBox componentVBox;

    @FXML
    private ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    public void initialiseComponent(Component componentModel) {
        this.componentModel = componentModel;
        setImage();
    }

    private void setImage() {
        InputStream inputstream = Simulator.class.getResourceAsStream(componentModel.getImageLocation());
        Image image = new Image(inputstream);
        imageView.setImage(image);
    }

    public Component getComponentModel() {
        return componentModel;
    }

    public void deleteImageAsExample() {
        imageView.setImage(null);
    }

}
