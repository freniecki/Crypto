package org.example;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.Character.getNumericValue;

public class DES {
    Logger logger = Logger.getLogger(getClass().getName());

    public final int[][] initialPermutation = {
            {58, 50, 42, 34, 26, 18, 10, 2},
            {60, 52, 44, 36, 28, 20, 12, 4},
            {62, 54, 46, 38, 30, 22, 14, 6},
            {64, 56, 48, 40, 32, 24, 16, 8},
            {57, 49, 41, 33, 25, 17, 9, 1},
            {59, 51, 43, 35, 27, 19, 11, 3},
            {61, 53, 45, 37, 29, 21, 13, 5},
            {63, 55, 47, 39, 31, 23, 15, 7}
    };
    public final int[][] expansionMatrix = {
            {32, 1, 2, 3, 4, 5, 4, 5},
            {6, 7, 8, 9, 8, 9, 10, 11},
            {12, 13, 12, 13, 14, 15, 16, 17},
            {16, 17, 18, 19, 20, 21, 20, 21},
            {22, 23, 24, 25, 24, 25, 26, 27},
            {28, 29, 28, 29, 30, 31, 32, 1}
    };
    public final int[][][] sBoxMatrix = {
            {
                    {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                    {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                    {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                    {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
            },
            {
                    {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                    {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                    {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                    {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
            },
            {
                    {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
            },
            {
                    {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
            },
            {
                    {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                    {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                    {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
            },
            {
                    {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                    {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                    {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                    {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
            },
            {
                    {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                    {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                    {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                    {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
            },
            {
                    {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
            }
    };

    private final int[][] pBlockMatrix = {
            {16, 7, 20, 21, 29, 12, 28, 17},
            {1, 15, 23, 26, 5, 18, 31, 10},
            {2, 8, 24, 14, 32, 27, 3, 9},
            {19, 13, 30, 6, 22, 11, 4, 25}
    };

    private final int[] keyShiftStep = {
            1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };

    private final int[][] firstKeyPermutation = {
            {57, 49, 41, 33, 25, 17, 9},
            {1, 58, 50, 42, 34, 26, 18},
            {10, 2, 59, 51, 43, 35, 27},
            {19, 11, 3, 60, 52, 44, 36},
            {63, 55, 47, 39, 31, 23, 15},
            {7, 62, 54, 46, 38, 30, 22},
            {14, 6, 61, 53, 45, 37, 29},
            {21, 13, 5, 28, 20, 12, 4}
    };

    private final int[][] secondKeyPermutation = {
            {14, 17, 11, 24, 1, 5, 3, 28},
            {15, 6, 21, 10, 23, 19, 12, 4},
            {26, 8, 16, 7, 27, 20, 13, 2},
            {41, 52, 31, 37, 47, 55, 30, 40},
            {51, 45, 33, 48, 44, 49, 39, 56},
            {34, 53, 46, 42, 50, 36, 29, 32}
    };

    private final int[][] endingPermutation = {
            {40, 8, 48, 16, 56, 24, 64, 32},
            {39, 7, 47, 15, 55, 23, 63, 31},
            {38, 6, 46, 14, 54, 22, 62, 30},
            {37, 5, 45, 13, 53, 21, 61, 29},
            {36, 4, 44, 12, 52, 20, 60, 28},
            {35, 3, 43, 11, 51, 19, 59, 27},
            {34, 2, 42, 10, 50, 18, 58, 26},
            {33, 1, 41, 9, 49, 17, 57, 25}
    };

    /*----------------Feistel's methods-----------------*/


    BitSet initialPermutation(BitSet message) {
        BitSet permutatedMessage = new BitSet(64);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                permutatedMessage.set(i * 8 + j, message.get(initialPermutation[i][j] - 1));
            }
        }
        return permutatedMessage;
    }

    BitSet endingPermutation(BitSet param) {
        BitSet endingPermutationMessage = new BitSet(64);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                endingPermutationMessage.set(i * 8 + j, param.get(endingPermutation[i][j] - 1));
            }
        }
        return endingPermutationMessage;
    }

    /**
     * Expands given half-message to 48 bits
     *
     * @param rightHalf 32 bits set from right half of message
     * @return 48 bits expanded set
     */
    BitSet expansionPermutation(BitSet rightHalf) {
        BitSet expanded = new BitSet(48);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                expanded.set(i * 8 + j, rightHalf.get(expansionMatrix[i][j] - 1));
            }
        }

        return expanded;
    }

    /**
     * I don't know
     *
     * @param decimal Integer value to change in 4 bits string
     * @return 4 bits integer representation
     */
    BitSet decimalToBitSet(int decimal) {
        StringBuilder binaryString = new StringBuilder();
        binaryString.append(Integer.toBinaryString(decimal));

        while (binaryString.length() < 4) {
            binaryString.insert(0, '0');
        }

        BitSet sBoxBin = new BitSet(4);
        for (int i = 0; i < 4; i++) {
            if (getNumericValue(binaryString.charAt(i)) == 1) {
                sBoxBin.set(i);
            }
        }

        return sBoxBin;
    }

    /**
     * Given string with '1' and '0' are changed to positions in sBox matrix
     * and return decimal value present in that position
     *
     * @param sBoxString 6 bits string
     * @param sBoxNumber number of matrix to get value from
     * @return Decimal value from given matrix at counted position
     */
    int sBox(String sBoxString, int sBoxNumber) {
        String stringColumn = sBoxString.charAt(0) + sBoxString.substring(5);
        String stringRow = sBoxString.substring(1, 5);

        int columnIndex = Integer.parseInt(stringColumn, 2);
        int rowIndex = Integer.parseInt(stringRow, 2);

        return sBoxMatrix[sBoxNumber][columnIndex][rowIndex];
    }

    /**
     * Given 48 bits xor product of expanded right half and key creates 32 bits word
     *
     * @param innerXor 48 bits xor product of right half and key
     * @return Shortened 32 bits word
     */
    BitSet keySubstitution(BitSet innerXor) {
        BitSet sBoxSix = new BitSet(6);
        BitSet sBoxFour;
        BitSet substituteKey = new BitSet(32);

        for (int i = 0; i < 8; i++) {
            // clears before getting next 6 bits
            sBoxSix.clear();
            for (int j = 0; j < 6; j++) {
                // sets bits from innerXor to position 0-5
                sBoxSix.set(j, innerXor.get(i * 6 + j));
            }
            // get the 4 bits value of integer pointed by sBoxSix
            sBoxFour = decimalToBitSet(sBox(bitsToString(sBoxSix, 6), i));

            for (int j = 0; j < 4; j++) {
                substituteKey.set(i * 4 + j, sBoxFour.get(j));
            }
        }

        return substituteKey;
    }

    /**
     * Permutate 32 bit word by permutation matrix
     *
     * @param substitutedKey 32 bit result of sBoxing
     * @return 32 bit after round permutation
     */
    BitSet permutationFunction(BitSet substitutedKey) {
        BitSet permutatedKey = new BitSet(32);

        for (int i = 0; i < 32; i++) {
            int index = pBlockMatrix[i / 8][i % 8] - 1;
            permutatedKey.set(i, substitutedKey.get(index));
        }
        return permutatedKey;
    }

    /**
     * Process left and right half to get new rightHalf
     *
     * @param rightHalf  32 bits of right half of message
     * @param leftHalf   32 bits of left half of message
     * @param roundKey56 56 bits from key permutation
     * @return New rightHalf based on mangler algorithm
     */
    BitSet manglerFunction(BitSet rightHalf, BitSet leftHalf, BitSet roundKey56) {
        BitSet expanded = expansionPermutation(rightHalf);
        BitSet roundKey48 = getRoundKey(roundKey56);
        expanded.xor(roundKey48);
        BitSet newRightHalf = permutationFunction(keySubstitution(expanded));

        newRightHalf.xor(leftHalf);

        return newRightHalf;
    }


    /**
     * Main method for encryption.
     *
     * @param message 64-bit message in BitSet(64)
     * @param key     64-bit key in BitSet(64)
     * @return encrypted message in BitSet(64)
     */
    BitSet encryption(BitSet message, BitSet key) {
        BitSet firstStep = initialPermutation(message);

        BitSet leftHalf = new BitSet(32);
        BitSet rightHalf = new BitSet(32);

        for (int i = 0; i < 32; i++) {
            leftHalf.set(i, firstStep.get(i));
            rightHalf.set(i, firstStep.get(i + 32));
        }

        BitSet effectiveKey = getEffectiveKey(key);
        for (int i = 0; i < 16; i++) {
            effectiveKey = keyShift(effectiveKey, i);
            BitSet tmp = rightHalf;
            rightHalf = manglerFunction(rightHalf, leftHalf, effectiveKey);
            leftHalf = tmp;
        }

        // combine halfs after 16th round in one bitset
        // 0-31 from right, 32-63 from left
        for (int i = 0; i < 32; i++) {
            rightHalf.set(i + 32, leftHalf.get(i));
        }

        return endingPermutation(rightHalf);
    }

    /**
     * Main method for decryption
     *
     * @param message Message in 64-bit BitSet
     * @param key     Key in 64-bit BitSet
     * @return Decrypted message in 64-bit BitSet
     */
    BitSet decryption(BitSet message, BitSet key) {
        BitSet lastStep = initialPermutation(message);

        // get halfs
        BitSet leftHalf = new BitSet(32);
        BitSet rightHalf = new BitSet(32);
        for (int i = 0; i < 32; i++) {
            leftHalf.set(i, lastStep.get(i));
            rightHalf.set(i, lastStep.get(i + 32));
        }

        // get effectiveKeys
        BitSet effectiveKey = getEffectiveKey(key);
        BitSet[] roundKeys = new BitSet[16];
        for (int i = 0; i < 16; i++) {
            effectiveKey = keyShift(effectiveKey, i);
            roundKeys[i] = effectiveKey;
        }
        // all 16 rounds, keys in reverse order
        for (int i = 0; i < 16; i++) {
            BitSet tmp = rightHalf;
            rightHalf = manglerFunction(rightHalf, leftHalf, roundKeys[15 - i]);
            leftHalf = tmp;
        }

        // combine halfs
        for (int i = 0; i < 32; i++) {
            rightHalf.set(i + 32, leftHalf.get(i));
        }

        // initial permutation
        return endingPermutation(rightHalf);
    }

    byte[] encrypt(byte[] message, BitSet keyBitSet) {
        List<byte[]> messageArray = Helper.byteArrayToByteList(message);
        byte[] aByte;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        for (byte[] bytes : messageArray) {
            BitSet messageBitSet = BitSet.valueOf(bytes);
            BitSet encrypted = encryption(messageBitSet, keyBitSet);
            aByte = DES.bitSetToByteArray(encrypted);
            encrypted.clear();
            try {
                outputStream.write(aByte);
            } catch (IOException e) {
                logger.info("io exception: encryption cycle");
            }
        }
        return outputStream.toByteArray();
    }

    byte[] decrypt(byte[] message, BitSet keyBitSet) {
        List<byte[]> messageArray = Helper.byteArrayToByteList(message);
        byte[] aByte;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        for (byte[] bytes : messageArray) {
            BitSet messageBitSet = BitSet.valueOf(bytes);
            aByte = DES.bitSetToByteArray(decryption(messageBitSet, keyBitSet));
            try {
                outputStream.write(aByte);
            } catch (IOException e) {
                logger.info("io exception: decryption cycle");
            }
            messageBitSet.clear();
        }
        return Helper.removePadding(outputStream.toByteArray());
    }

    /*-----------------Key methods--------------------*/

    /**
     * Creates effective key based on PC-1 table
     *
     * @param inputKey 64 bits key from input
     * @return 56 secret key
     */
    BitSet getEffectiveKey(BitSet inputKey) {
        BitSet effectiveKey = new BitSet(56);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                effectiveKey.set(i * 7 + j, inputKey.get(firstKeyPermutation[i][j] - 1));
            }
        }
        return effectiveKey;
    }

    BitSet keyShift(BitSet effectiveKey, int roundNumber) {
        BitSet shiftedKey = new BitSet(56);
        if (keyShiftStep[roundNumber] == 1) {
            // 0  ...  27 | 28  ... 55
            boolean getLeftFirst = effectiveKey.get(0);
            for (int i = 0; i < 27; i++) {
                shiftedKey.set(i, effectiveKey.get(i + 1));
            }
            shiftedKey.set(27, getLeftFirst);

            boolean getRightFirst = effectiveKey.get(28);
            for (int i = 28; i < 55; i++) {
                shiftedKey.set(i, effectiveKey.get(i + 1));
            }
            shiftedKey.set(55, getRightFirst);
        } else {
            // 0 1 ... 26 27 | 28 29 ... 54 55
            boolean getLeftFirst = effectiveKey.get(0);
            boolean getLeftSecond = effectiveKey.get(1);
            for (int i = 0; i < 26; i++) {
                shiftedKey.set(i, effectiveKey.get(i + 2));
            }
            shiftedKey.set(26, getLeftFirst);
            shiftedKey.set(27, getLeftSecond);

            boolean getRightFirst = effectiveKey.get(28);
            boolean getRightSecond = effectiveKey.get(29);
            for (int i = 28; i < 54; i++) {
                shiftedKey.set(i, effectiveKey.get(i + 2));
            }
            shiftedKey.set(54, getRightFirst);
            shiftedKey.set(55, getRightSecond);
        }

        return shiftedKey;
    }

    BitSet getRoundKey(BitSet shiftedKey) {
        BitSet roundKey = new BitSet(48);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                roundKey.set(i * 8 + j, shiftedKey.get(secondKeyPermutation[i][j] - 1));
            }
        }
        return roundKey;
    }

    /*-----------------Supporting methods-------------*/

    /**
     * Return clean string of given bits count
     *
     * @param bitSet BitSet object
     * @param length Number of bits to be returned
     * @return Clean string with '0' and '1'
     */
    public static String bitsToString(BitSet bitSet, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (bitSet.get(i)) {
                stringBuilder.append("1");
            } else {
                stringBuilder.append("0");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Creates BitSet base on string
     *
     * @param plainText key or message given in String
     * @return BitSet(64)
     */
    public static BitSet convertStringToBitSet(String plainText, int length) {
        BitSet output = new BitSet(length);
        for (int i = 0; i < length; i++) {
            output.set(i, plainText.charAt(i) == '1');
        }
        return output;
    }

    public static byte[] bitSetToByteArray(BitSet bitSet) {
        byte[] byteArray = new byte[8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j <8; j++) {
                if (bitSet.get(i * 8 + j)) {
                    byteArray[i] |= (byte) (1 << ((i * 8 + j) % 8));
                }
            }
        }

        return byteArray;
    }

    public static String byteToBinaryString(byte b) {
        StringBuilder binaryString = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            binaryString.append((b >> i) & 1);
        }
        return binaryString.toString();
    }
}