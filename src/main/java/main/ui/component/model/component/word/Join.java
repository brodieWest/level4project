package main.ui.component.model.component.word;

import main.ui.component.WordComponent;
import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;
import main.ui.port.Port;
import main.model.Word;

public class Join extends Component implements WordComponent {
    public Join(ComponentParameters componentParameters) {
        super(componentParameters);
    }

    @Override
    public void processGateDelay() {
    }

    @Override
    public int getDefaultInputs() {
        return  4;
    }

    @Override
    public int getDefaultOutputs() {
        return 1;
    }

    @Override
    public void wireDelay() {
        Word outputWord = getOutput(0).getWord();

        for(int i=0;i<getInputSize();i++) {
            Port input = getInput(i);
            outputWord.get(i).copy(input.getLogic());
        }
    }
}
