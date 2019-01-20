package main.ui.component.model.component;

import main.ui.component.controllers.TextController;

public class TextComponent extends Component {

    private String oldText = "";

    public TextComponent(ComponentParameters componentParameters) {
        super(componentParameters);

        this.uuid = componentParameters.getUuid().substring(0,21);
        if(componentParameters.getUuid().length() > 21) {
            oldText = componentParameters.getUuid().substring(21);
        }
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

    @Override
    public String saveFileUuid() {
        return uuid + ((TextController)componentController).getText();
    }

    public String getOldText() {
        return oldText;
    }
}
