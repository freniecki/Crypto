package org.example;

import java.util.Arrays;

import static java.lang.System.arraycopy;

public class Main {

    private byte[] key;

    private byte[] plainText;

    void manglerFunction() {

    }

    public final byte[][] s1matrix = {
            {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
            {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
            {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
            {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
    };

    private final byte[][] permutationFunctionMatrix = {
            {16, 7, 20, 21, 29, 12, 28, 17},
            {1, 15, 23, 26, 5, 18, 31, 10},
            {2, 8, 24, 14, 32, 27, 3, 9},
            {19, 13, 30, 6, 22, 11, 4, 25}
    };

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
     *
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

    /**
     * Performs XOR on expanded key and round key
     *
     * @param expanded bits from previous half of the text
     * @param roundKey bits of round key specific for given round
     * @return Xor'ed key in 48 bits
     */
    byte[] expandedXorRoundKey(byte[] expanded, byte[] roundKey) {
        byte[] innerXor = new byte[48];

        for (int i = 0; i < 48; i++) {
            innerXor[i] = (byte) (expanded[i] + roundKey[i]);
            innerXor[i] %= 2;
        }

        return innerXor;
    }

    byte[] changeToArray(int decimal) {
        String binaryString = Integer.toBinaryString(decimal);

        while (binaryString.length() < 4) {
            binaryString = "0" + binaryString;
        }

        byte[] sBoxBin = new byte[4];

        for (int i = 0; i < 4; i++) {
            sBoxBin[i] = (byte) (binaryString.charAt(i) - '0');
        }

        return sBoxBin;
    }

    /**
     * Gets the bits version of
     * @param sBox
     * @param innerXor
     * @param sMatrix
     * @return
     */
    byte[] sBox(byte[] sBox, byte[] innerXor, byte[][] sMatrix) {
        String stringColumn = String.valueOf(sBox[0]) + String.valueOf(sBox[5]);
        int columnIndex = Integer.parseInt(stringColumn, 2);

        String stringRow = String.valueOf(sBox[1]) + String.valueOf(sBox[2])
                + String.valueOf(sBox[3]) + String.valueOf(sBox[4]);

        int rowIndex = Integer.parseInt(stringRow, 2);

        byte[][] innerXor2D = changeToTwoDimension(innerXor, 4, 8);

        return changeToArray(innerXor2D[columnIndex][rowIndex]);
    }

    /**
     * @param innerXor
     * @return
     */
    byte[] keySubstitution(byte[] innerXor) {
        // dostaje klucz 48 bitowy -> zwraca 32 bitowy

        byte[] sBoxSix = new byte[8];
        byte[] sBoxFour;
        byte[] substituteKey = new byte[32];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                sBoxSix[j] = innerXor[i * 6 + j];
            }

            sBoxFour = sBox(sBoxSix, innerXor, s1matrix);

            for (int j = 0; j < 4; j++) {
                substituteKey[i * 4 + j] = sBoxFour[j];
            }
        }

        return substituteKey;
    }

    byte[] permutationBox(byte[] substitutedKey) {
        byte[] permutatedKey = new byte[32];

        for (int i = 0; i < 32; i++) {
            int index = permutationFunctionMatrix[i / 4][i % 4];
            permutatedKey[i] = substitutedKey[index];
        }
        return permutatedKey;
    }

//    byte[] manglerFunction(byte[] rightHalf, byte[] leftHalf) {
//        byte[] newRightHalf;
//
//
//        return newRightHalf[];
//    }

    // todo: CREATE Mangler Function:
    // todo: create S-boxes
    // todo: create 8to6 boxes
    // todo: create transposition-box

}