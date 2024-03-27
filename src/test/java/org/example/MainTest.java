package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class MainTest {

    @Test
    void changeToOneDimension() {
        Main main = new Main();

        byte[][] twoDim = {
                {1,2,3,4,5,6,7,8},
                {9,10,11,12,13,14,15,16},
                {17,18,19,20,21,22,23,24},
                {25,26,27,28,29,30,31,32}
        };

        byte[] oneDim = {
                1,2,3,4,5,6,7,8,
                9,10,11,12,13,14,15,16,
                17,18,19,20,21,22,23,24,
                25,26,27,28,29,30,31,32
        };

        Assertions.assertArrayEquals(oneDim, main.changeToOneDimension(twoDim));
    }

    @Test
    void changeToTwoDimension() {
        Main main = new Main();

        byte[] oneDim = {
                1,2,3,4,5,6,7,8,
                9,10,11,12,13,14,15,16,
                17,18,19,20,21,22,23,24,
                25,26,27,28,29,30,31,32
        };

        byte[][] twoDim = {
                {1,2,3,4,5,6,7,8},
                {9,10,11,12,13,14,15,16},
                {17,18,19,20,21,22,23,24},
                {25,26,27,28,29,30,31,32}
        };

        Assertions.assertArrayEquals(twoDim, main.changeToTwoDimension(oneDim, 4, 8));
    }

    @Test
    void expansionPermutation() {
        Main main = new Main();

        byte[] rightHalf = {
                1,2,3,4,5,6,7,8,
                9,10,11,12,13,14,15,16,
                17,18,19,20,21,22,23,24,
                25,26,27,28,29,30,31,32
        };

        byte[] expanded = {
                32,1,2,3,4,5,4,5,6,7,8,9,8,
                9,10,11,12,13,12,13,14,15,16,
                17,16,17,18,19,20,21,20,21,22,23,24,
                25,24,25,26,27,28,29,28,29,30,31,32,1
        };

        Assertions.assertArrayEquals(expanded, main.expansionPermutation(rightHalf));
    }

    @Test
    void expandedXorRoundKey() {
        Main main = new Main();
        byte[] exp = new byte[48];
        byte[] key = new byte[48];
        exp[0] = 0;
        exp[1] = 1;
        key[0] = 1;
        key[1] = 1;

        System.out.println(Arrays.toString(main.expandedXorRoundKey(exp, key, 48)));
    }

    @Test
    void keySubstitution() {
    }

    @Test
    void sBox() {
        byte[] sBox = {1,0,0,1,0,1};
        String stringColumn = String.valueOf(sBox[0]) + String.valueOf(sBox[5]);
        System.out.println(stringColumn);
        int columnIndex = Integer.parseInt(stringColumn,2);
        System.out.println(columnIndex);
    }

    @Test
    void permutationBox() {
    }
}