package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

class EncryptionTest {

//    @Test
//    void changeToOneDimension() {
//        Encryption main = new Encryption();
//
//        byte[][] twoDim = {
//                {1, 2, 3, 4, 5, 6, 7, 8},
//                {9, 10, 11, 12, 13, 14, 15, 16},
//                {17, 18, 19, 20, 21, 22, 23, 24},
//                {25, 26, 27, 28, 29, 30, 31, 32}
//        };
//
//        byte[] oneDim = {
//                1, 2, 3, 4, 5, 6, 7, 8,
//                9, 10, 11, 12, 13, 14, 15, 16,
//                17, 18, 19, 20, 21, 22, 23, 24,
//                25, 26, 27, 28, 29, 30, 31, 32
//        };
//
//        Assertions.assertArrayEquals(oneDim, main.changeToOneDimension(twoDim));
//    }
//
//    @Test
//    void changeToTwoDimension() {
//        Encryption main = new Encryption();
//
//        byte[] oneDim = {
//                1, 2, 3, 4, 5, 6, 7, 8,
//                9, 10, 11, 12, 13, 14, 15, 16,
//                17, 18, 19, 20, 21, 22, 23, 24,
//                25, 26, 27, 28, 29, 30, 31, 32
//        };
//
//        byte[][] twoDim = {
//                {1, 2, 3, 4, 5, 6, 7, 8},
//                {9, 10, 11, 12, 13, 14, 15, 16},
//                {17, 18, 19, 20, 21, 22, 23, 24},
//                {25, 26, 27, 28, 29, 30, 31, 32}
//        };
//
//        Assertions.assertArrayEquals(twoDim, main.changeToTwoDimension(oneDim, 4, 8));
//    }
//
//    @Test
//    void expansionPermutation() {
//        Encryption main = new Encryption();
//
//        byte[] rightHalf = {
//                1, 2, 3, 4, 5, 6, 7, 8,
//                9, 10, 11, 12, 13, 14, 15, 16,
//                17, 18, 19, 20, 21, 22, 23, 24,
//                25, 26, 27, 28, 29, 30, 31, 32
//        };
//
//        byte[] expanded = {
//                32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8,
//                9, 10, 11, 12, 13, 12, 13, 14, 15, 16,
//                17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24,
//                25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1
//        };
//
//        Assertions.assertArrayEquals(expanded, main.expansionPermutation(rightHalf));
//    }
//
//    @Test
//    void expandedXorRoundKey() {
//        Encryption main = new Encryption();
//        byte[] exp = new byte[48];
//        byte[] key = new byte[48];
//        exp[0] = 0;
//        exp[1] = 1;
//        key[0] = 1;
//        key[1] = 1;
//
//        //System.out.println(Arrays.toString(main.xorOperation(exp, key)));
//    }

    @Test
    void changeToArray() {
        Encryption main = new Encryption();
        BitSet expected = new BitSet(4);

        expected.set(2);
        expected.set(1);
        BitSet actual = main.decimalToBitSet(6);

        expected.stream().forEach(System.out::println);
        System.out.println(expected.size());
        System.out.println(actual.size());
        System.out.println(actual.length());
    }

    @Test
    void sBox() {
        Encryption main = new Encryption();

        String str = "110110";
        Assertions.assertEquals(14, main.sBox(str, 0));
    }

    @Test
    void decimalToBits() {
        Encryption main = new Encryption();
        Assertions.assertEquals("{}", main.decimalToBitSet(0).toString());
        Assertions.assertEquals("{0, 1, 2, 3}", main.decimalToBitSet(15).toString());
    }
//
//    @Test
//    void keySubstitution() {
//        Encryption main = new Encryption();
//
//        byte[] afterXor48bits = {
//                0, 0, 0, 0, 0, 0, 0, 0,
//                1, 1, 1, 1, 1, 1, 1, 1,
//                0, 0, 0, 0, 0, 0, 0, 0,
//                1, 1, 1, 1, 1, 1, 1, 1,
//                0, 0, 0, 0, 0, 0, 0, 0,
//                1, 1, 1, 1, 1, 1, 1, 1
//        };
//
//        byte[] returnKey = {
//                1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0,
//                1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1
//        };
//
//        Assertions.assertArrayEquals(returnKey, main.keySubstitution(afterXor48bits));
//    }
//
//    @Test
//    void permutationBox() {
//    }
//
    @Test
    void printBits() {
        BitSet bitSet = new BitSet(6);
        bitSet.set(1);
        bitSet.set(3);
        bitSet.set(4);

        System.out.println(Encryption.bitsToString(bitSet, 6));
    }
//
//    @Test
//    void testShift() {
//        Encryption encryption = new Encryption();
//
//        byte[] right = {
//                0,0,17,0
//        };
//
//        byte[] expanded = {
//                0,0,0,0,0,0
//        };
//
//        int index = 20 / 8;
//        int shift = 20 % 8;
//        int i = (right[index] >> shift) & 1;
//
//        System.out.println(i);
//    }
}