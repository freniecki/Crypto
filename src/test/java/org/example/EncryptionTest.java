package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

class EncryptionTest {

    @Test
    void decimalToBits() {
        Encryption main = new Encryption();
        Assertions.assertEquals("{}", main.decimalToBitSet(0).toString());
        Assertions.assertEquals("{0, 1, 2, 3}", main.decimalToBitSet(15).toString());
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

        Encryption encryption = new Encryption();
        Assertions.assertEquals(expected, encryption.expansionPermutation(rightHalf));
    }

    @Test
    void sBox() {
        Encryption main = new Encryption();

        String str = "110110";
        // 10 -> 2
        // 1011 -> 11
        Assertions.assertEquals(main.sBoxMatrix[0][2][11], main.sBox(str, 0));
    }

    @Test
    void keySubstitution() {
        Encryption main = new Encryption();
        BitSet innerXor = new BitSet();
        innerXor.set(0);
        innerXor.set(1);
        innerXor.set(3);
        innerXor.set(4);
        BitSet bitSet = main.keySubstitution(innerXor);
        Assertions.assertEquals(
                "{1, 2, 3, 4, 5, 6, 7, 8, 10, 13, 14, 15, 18, 20, 21, 25, 28, 29, 31}",
                bitSet.toString());
    }

    @Test
    void permutationFunction() {
        Encryption main = new Encryption();
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
                main.permutationFunction(subKey).toString());
    }

    @Test
    void bitsToString() {
        BitSet bitSet = new BitSet(6);
        bitSet.set(1);
        bitSet.set(3);
        bitSet.set(4);

        System.out.println(Encryption.bitsToString(bitSet, 6));
    }

}