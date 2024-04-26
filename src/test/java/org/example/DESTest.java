package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

class DESTest {

    @Test
    void decimalToBits() {
        DES des = new DES();
        Assertions.assertEquals("{}", des.decimalToBitSet(0).toString());
        Assertions.assertEquals("{0, 1, 2, 3}", des.decimalToBitSet(15).toString());
    }

    @Test
    void expansionPermutation() {
        BitSet rightHalf = new BitSet(32);
        rightHalf.set(0);
        rightHalf.set(1);
        rightHalf.set(2);
        rightHalf.set(3);

        BitSet expected = new BitSet();
        expected.set(1);
        expected.set(2);
        expected.set(3);
        expected.set(4);
        expected.set(6);
        expected.set(47);

        DES des = new DES();
        Assertions.assertEquals(expected, des.expansionPermutation(rightHalf));
    }

    @Test
    void sBox() {
        DES des = new DES();

        String str = "110110";
        // 10 -> 2
        // 1011 -> 11
        Assertions.assertEquals(des.sBoxMatrix[0][2][11], des.sBox(str, 0));
    }

    @Test
    void keySubstitution() {
        DES des = new DES();
        BitSet innerXor = new BitSet();
        innerXor.set(0);
        innerXor.set(1);
        innerXor.set(3);
        innerXor.set(4);
        BitSet bitSet = des.keySubstitution(innerXor);
        Assertions.assertEquals(
                "{1, 2, 3, 4, 5, 6, 7, 8, 10, 13, 14, 15, 18, 20, 21, 25, 28, 29, 31}",
                bitSet.toString());
    }

    @Test
    void permutationFunction() {
        DES des = new DES();
        BitSet subKey = new BitSet();
        subKey.set(0);
        subKey.set(1);
        subKey.set(2);
        subKey.set(3);

        BitSet expected = new BitSet(32);
        expected.set(8);
        expected.set(16);
        expected.set(22);
        expected.set(30);
        Assertions.assertEquals(expected.toString(),
                des.permutationFunction(subKey).toString());
    }

    @Test
    void bitsToString() {
        BitSet bitSet = new BitSet(6);
        bitSet.set(1);
        bitSet.set(3);
        bitSet.set(4);

        Assertions.assertEquals("010110", DES.bitsToString(bitSet, 6));
    }

    @Test
    void convertStringToBits() {
        String binaryMessage =
                "0000000100100011010001010110011110001001101010111100110111101111";
        String binaryKey =
                "0001001100110100010101110111100110011011101111001101111111110001";
        BitSet key = DES.convertStringToBitSet(binaryKey, 64);
        String getStringKeyBack = DES.bitsToString(key, 64);

        Assertions.assertEquals(binaryKey, getStringKeyBack);
    }

    @Test
    void bitSetToByteArray() {
        BitSet bitSet = new BitSet(64);
        bitSet.set(0,5);
        bitSet.set(17);

        byte[] bytes = DES.bitSetToByteArray(bitSet);

        for (byte aByte : bytes) {
            System.out.println(DES.byteToBinaryString(aByte));
        }
    }

    @Test
    void keyMethods() {
        DES des = new DES();
        String binaryKey =
                "0001001100110100010101110111100110011011101111001101111111110001";
        String keyAfterPC1 =
                "11110000110011001010101011110101010101100110011110001111";
        BitSet startKey = DES.convertStringToBitSet(binaryKey, 64);
        BitSet effectiveKey = des.getEffectiveKey(startKey);

        String effectiveKeyString = DES.bitsToString(effectiveKey, 56);
        Assertions.assertEquals(keyAfterPC1, effectiveKeyString);

        BitSet firstShift = des.keyShift(effectiveKey, 0);

        String firstShiftString =
                        "1110000110011001010101011111" + "1010101011001100111100011110";
        Assertions.assertEquals(firstShiftString, DES.bitsToString(firstShift, 56));

        BitSet firstRoundKey = des.getRoundKey(firstShift);

        String firstRoundKeyString = "000110110000001011101111111111000111000001110010";
        Assertions.assertEquals(firstRoundKeyString, DES.bitsToString(firstRoundKey, 48));
    }

    @Test
    void messageMethods() {
        DES des = new DES();
        String hexMessage = "0123456789ABCDEF";
        String binaryMessageEntry =
                "0000 0001 0010 0011 0100 0101 0110 0111 1000 1001 1010 1011 1100 1101 1110 1111";
        String binaryMessage =
                "0000000100100011010001010110011110001001101010111100110111101111";

        BitSet message = DES.convertStringToBitSet(binaryMessage, 64);
        Assertions.assertEquals(binaryMessage, DES.bitsToString(message, 64));

        // ------initial permutation-------------
        BitSet initialPermutation = des.initialPermutation(message);

        String afterInitialPermutation =
                "1100110000000000110011001111111111110000101010101111000010101010";
        Assertions.assertEquals(afterInitialPermutation, DES.bitsToString(initialPermutation, 64));

        // -----left & right half--------
        BitSet leftHalf = new BitSet(32);
        BitSet rightHalf = new BitSet(32);

        for (int i = 0; i < 32; i++) {
            leftHalf.set(i, initialPermutation.get(i));
            rightHalf.set(i, initialPermutation.get(i + 32));
        }

        String leftZero =
                "11001100000000001100110011111111";
        String rightZero =
                "11110000101010101111000010101010";

        Assertions.assertEquals(leftZero, DES.bitsToString(leftHalf, 32));
        Assertions.assertEquals(rightZero, DES.bitsToString(rightHalf, 32));

        // -----expanded right---------
        BitSet expanded = des.expansionPermutation(rightHalf);

        String expandedString =
                "011110100001010101010101011110100001010101010101";
        Assertions.assertEquals(expandedString, DES.bitsToString(expanded, 48));

        // -----xor expanded with key----
        String firstRoundKeyString =
                "000110110000001011101111111111000111000001110010";
        BitSet roundKey = DES.convertStringToBitSet(firstRoundKeyString, 48);

        String xorKey =
                "011000010001011110111010100001100110010100100111";
        expanded.xor(roundKey);
        Assertions.assertEquals(xorKey, DES.bitsToString(expanded, 48));

        // -----keySubstitution
        BitSet keySubstitution = des.keySubstitution(expanded);

        String keySubstitutionString =
                "01011100100000101011010110010111";
        Assertions.assertEquals(keySubstitutionString, DES.bitsToString(keySubstitution, 32));

        // ----permutation function----
        BitSet permutated = des.permutationFunction(keySubstitution);

        String permutatedString =
                "00100011010010101010100110111011";
        Assertions.assertEquals(permutatedString, DES.bitsToString(permutated, 32));

        // ----xor with left
        permutated.xor(leftHalf);

        String newRightString =
                "11101111010010100110010101000100";
        Assertions.assertEquals(newRightString, DES.bitsToString(permutated, 32));

    }

    @Test
    void manglerFunction() {
        DES des = new DES();

        String leftZero =
                "11001100000000001100110011111111";
        String rightZero =
                "11110000101010101111000010101010";

        BitSet leftHalf = DES.convertStringToBitSet(leftZero, 32);
        BitSet rightHalf = DES.convertStringToBitSet(rightZero, 32);

        String firstShiftString =
                "1110000110011001010101011111" + "1010101011001100111100011110";
        BitSet roundKey = DES.convertStringToBitSet(firstShiftString, 56);

        BitSet mangler = des.manglerFunction(rightHalf, leftHalf, roundKey);

        String newRightString =
                "11101111010010100110010101000100";
        Assertions.assertEquals(newRightString, DES.bitsToString(mangler, 32));
    }

    @Test
    void encryption() {
        DES des = new DES();
        String binaryMessage =
                "0000000100100011010001010110011110001001101010111100110111101111";
        String binaryKey =
                "0001001100110100010101110111100110011011101111001101111111110001";

        BitSet message = DES.convertStringToBitSet(binaryMessage, 64);
        BitSet key = DES.convertStringToBitSet(binaryKey, 64);

        BitSet encrypted = des.encryption(message, key);

        String encryptedString =
                "1000010111101000000100110101010000001111000010101011010000000101";
        Assertions.assertEquals(encryptedString, DES.bitsToString(encrypted, 64));


        String mainString =
                "1000010111101000000100110101010000001111000010101011010000000101";

        Assertions.assertEquals(mainString, encryptedString);
    }

    @Test
    void decryption() {
        String encryptedString =
                "1000010111101000000100110101010000001111000010101011010000000101";
        String binaryKey =
                "0001001100110100010101110111100110011011101111001101111111110001";
        BitSet message = DES.convertStringToBitSet(encryptedString, 64);
        BitSet key = DES.convertStringToBitSet(binaryKey, 64);
        // --------------------------------
        DES des = new DES();
        BitSet decrypted = des.decryption(message, key);
        // --------------------------------
        String binaryMessage =
                "0000000100100011010001010110011110001001101010111100110111101111";
        BitSet expected = DES.convertStringToBitSet(binaryMessage, 64);
        Assertions.assertEquals(expected, decrypted);
    }
}