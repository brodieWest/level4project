package model;

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

        for(int i=0;i<binary.length();i++) {
            String bit = binary.substring(i,i+1);
            this.get(binary.length()-i-1).setUndefined(false);
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
}
