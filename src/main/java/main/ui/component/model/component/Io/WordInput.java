package main.ui.component.model.component.Io;

import main.ui.component.model.component.Component;
import main.ui.component.model.component.ComponentParameters;
import main.model.Word;

public class WordInput extends Component {

    private int wordValue = 0;

  @Override
    public void processGateDelay() {
    }

    @Override
    public int getDefaultInputs() {
        return 0;
    }

    @Override
    public int getDefaultOutputs() {
        return 1;
    }

    @Override
    public int defaultOutputSize() {
      return 4;
    }

    @Override
    public void reset() {
        Word outputWord = getOutput(0).getWord();
        outputWord.setUndefined(false);

    }


    public WordInput(ComponentParameters componentParameters) {
        super(componentParameters);
        this.getOutput(0).getWord().setTo0();
        this.getOutput(0).getLogic().setUndefined(false);
    }

    public int getWordValue() {
        return wordValue;
    }

    public void setWordValue(int wordValue) {
        this.wordValue = wordValue;
        getOutput(0).getWord().setValue(wordValue);
    }
}
