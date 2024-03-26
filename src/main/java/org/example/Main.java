package org.example;

import java.util.Arrays;

import static java.lang.System.arraycopy;

public class Main {

    private byte[] key;

    private byte[] plainText;

    void manglerFunction() {

    }

    byte[] changeToOneDimension(byte[][] twoDimension) {
        int columnLength = twoDimension.length; // evaluate [x][]
        int rowLength = twoDimension[0].length; // evaluate [][x]

        byte[] oneDimension = new byte[columnLength * rowLength]; // create 1D to return

        for (int i = 0; i < columnLength; i++) {
            arraycopy(twoDimension[i], 0, oneDimension, i * rowLength, rowLength);
        }
        return oneDimension;
    }

    byte[][] changeToTwoDimension(byte[] oneDimension, int col, int row) {
        byte[][] twoDimension = new byte[col][row];

        for (int i = 0; i < col; i++) {
            arraycopy(oneDimension, i * row, twoDimension[i], 0, row);
        }

        return twoDimension;
    }

    /**
     * Create expanded right half of text.
     * @param rightHalf 32 bits of text
     * @return 48 bits of expanded text
     */
    byte[] expansionPermutation(byte[] rightHalf) {
        byte[][] expandedDraft = new byte[8][6];

        // places 1:1 right half of text
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                expandedDraft[i][j + 1] = rightHalf[i * 4 + j];
            }
        }

        // fill 1st column with 4th column, but with moved by one numbers
        for (int i = 0; i < 7; i++) {
            expandedDraft[i + 1][0] = expandedDraft[i][4];
        }
        expandedDraft[0][0] = expandedDraft[7][4];

        // fill 6th column with 2nd column, but with moved by one numbers
        for (int i = 1; i < 8; i++) {
            expandedDraft[i - 1][5] = expandedDraft[i][1];
        }
        expandedDraft[7][5] = expandedDraft[0][1];

        return changeToOneDimension(expandedDraft);
    }

    // todo: CREATE Mangler Function:
    // todo: create XOR on Expanded and Key
    // todo: create S-boxes
    // todo: create 8to6 boxes
    // todo: create transposition-box

}