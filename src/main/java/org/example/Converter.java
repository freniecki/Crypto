package org.example;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import static java.lang.Boolean.valueOf;

public class Converter {
    private String clearText;
    private String key;

    public Converter(String clearText, String key) {
        this.clearText = clearText;
        this.key = key;
    }

    /**
     * converts clear text and string to bitset
     */
    public List<BitSet> convertMessage() {
        List<BitSet> output = new ArrayList<>();
        for (int i = 0; i < clearText.length()-1; i++) {
            BitSet block = new BitSet();
            char letter = clearText.charAt(i);
            for (int j = 0; j < 8; j++) {
                block.set(i, letter%2);
                letter /= 2;
            }
            if (block.size() == 64) {
                output.add(block);
            }
        }
        return output;
    }
    public BitSet convertStringToBitSet() {
        BitSet output = new BitSet(64);
        for (int i = 0; i < 64; i++) {
            output.set(i, key.charAt(i) == 1);
        }
        return output;
    }

    public String convertToString(List<BitSet> binary) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < binary.size(); i++) {
            for (int j = 0; j < binary.get(i).size(); j++) {
                //out.append(toString((binary.get(i).get(j)) * 2));  //nie konwertuje z bool do int i nie widzę metody konwertacji
            }
        }
        boolean k = true;
        return out.toString();
    }
}
