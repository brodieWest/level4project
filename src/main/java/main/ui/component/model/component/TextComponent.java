package main.ui.component.model.component;

public class TextComponent extends Component {
    public TextComponent(ComponentParameters componentParameters) {
        super(componentParameters);
    }

    @Override
    public void processGateDelay() {

    }

    @Override
    public int getDefaultInputs() {
        return 0;
    }

    @Override
    public int getDefaultOutputs() {
        return 0;
    }
}
