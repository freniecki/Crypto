package org.example;

import org.junit.jupiter.api.Test;

public class Testowo {

    @Test
    void wlasne() {
        byte[][] twoDim = new byte[8][6];
        int wholeLen = twoDim.length;
        int firstRow = twoDim[0].length;

        System.out.println(wholeLen);
        System.out.println(firstRow);
    }


}
