package main.model;

import java.util.ArrayList;

public class Word extends ArrayList<Logic> {

    Word(int size) {
        super();
        for(int i=0;i<size;i++) {
            this.add(new Logic());
        }
    }

    public void setTo0() {
        for(Logic logic : this) {
            logic.setValue(false);
        }
    }

    public void setValue(int value) {
        String binary = Integer.toBinaryString(value);
        setUndefined(false);
        setTo0();

        for(int i=0;i<binary.length();i++) {
            String bit = binary.substring(i,i+1);
            this.get(binary.length()-i-1).setValue(bit.equals("1"));
        }
    }

    public void setUndefined(Boolean undefined) {
        for(Logic logic : this) {
            logic.setUndefined(undefined);
        }
    }

    public void copy(Word word) {
        for(int i=0;i<size();i++) {
            this.get(i).copy(word.get(i));
        }
    }

    @Override
    public String toString() {
        int value = 0;
        for(int i=0;i<size();i++) {
            Logic logic = this.get(i);

            if(logic.isUndefined()) return "U";

            if(logic.value()) value += Math.pow(2,i);
        }
        return Integer.toString(value,16);
    }
}
