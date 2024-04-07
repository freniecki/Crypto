package org.example;

import static java.lang.System.arraycopy;

public class Encryption {

    public final byte[][] sMatrix = {
            {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7,
                    0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8,
                    4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0,
                    15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13},

            {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10,
                    3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5,
                    0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15,
                    13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9},

            {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8,
                    13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1,
                    13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7,
                    1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12},

            {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15,
                    13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9,
                    10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4,
                    3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14},

            {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9,
                    14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6,
                    4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14,
                    11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3},

            {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11,
                    10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8,
                    9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6,
                    4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13},

            {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1,
                    13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6,
                    1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2,
                    6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12},

            {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7,
                    1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2,
                    7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8,
                    2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
    };


    private final byte[][] pBlockMatrix = {
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

    byte[] xorOperation(byte[] param1, byte[] param2) {
        int array_size = param1.length;
        byte[] returnValue = new byte[array_size];

        for (int i = 0; i < 48; i++) {
            returnValue[i] = (byte) (param1[i] + param2[i]);
            returnValue[i] %= 2;
        }
        return returnValue;
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

    /*-----------------Mangler methods--------------------*/


    // todo: reconsider byte[] implementation - what about bits?? - sth off

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

    byte[] sBox(byte[] sBox, byte[] innerXor) {
        String stringColumn = String.valueOf(sBox[0]) + String.valueOf(sBox[5]);
        int columnIndex = Integer.parseInt(stringColumn, 2);

        String stringRow = String.valueOf(sBox[1]) + String.valueOf(sBox[2])
                + String.valueOf(sBox[3]) + String.valueOf(sBox[4]);

        int rowIndex = Integer.parseInt(stringRow, 2);

//        byte[][] innerXor2D = changeToTwoDimension(innerXor, 4, 8);
//        return changeToArray(innerXor2D[columnIndex][rowIndex]);

        return changeToArray(innerXor[columnIndex * 4 + rowIndex]);
    }

    byte[] keySubstitution(byte[] innerXor) {
        // dostaje klucz 48 bitowy -> zwraca 32 bitowy

        byte[] sBoxSix = new byte[8];
        byte[] sBoxFour;
        byte[] substituteKey = new byte[32];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                sBoxSix[j] = innerXor[i * 6 + j];
            }

            sBoxFour = sBox(sBoxSix, innerXor);

            for (int j = 0; j < 4; j++) {
                substituteKey[i * 4 + j] = sBoxFour[j];
            }
        }

        return substituteKey;
    }

    byte[] permutationBox(byte[] substitutedKey) {
        byte[] permutatedKey = new byte[32];

        for (int i = 0; i < 32; i++) {
            int index = pBlockMatrix[i / 4][i % 4];
            permutatedKey[i] = substitutedKey[index];
        }
        return permutatedKey;
    }

    byte[] manglerFunction(byte[] rightHalf, byte[] leftHalf, byte[] roundKey) {
        byte[] newRightHalf = permutationBox(keySubstitution(xorOperation(expansionPermutation(rightHalf), roundKey)));

        newRightHalf = xorOperation(newRightHalf, leftHalf);

        return newRightHalf;
    }


    /*-----------------Key methods--------------------*/
    byte[] getEffectiveKey(byte[] inputKey) {
        byte[] effectiveKey = new byte[7];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                effectiveKey[i * 7 + j] = inputKey[i * 8 + j];
            }
        }
        return effectiveKey;
    }

    byte[] getEffectiveKey(String inputKey) {
        byte[] effectiveKey = new byte[7];


    }
}