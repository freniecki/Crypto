package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class EncryptionTest {

    @Test
    void changeToOneDimension() {
        Encryption main = new Encryption();

        byte[][] twoDim = {
                {1, 2, 3, 4, 5, 6, 7, 8},
                {9, 10, 11, 12, 13, 14, 15, 16},
                {17, 18, 19, 20, 21, 22, 23, 24},
                {25, 26, 27, 28, 29, 30, 31, 32}
        };

        byte[] oneDim = {
                1, 2, 3, 4, 5, 6, 7, 8,
                9, 10, 11, 12, 13, 14, 15, 16,
                17, 18, 19, 20, 21, 22, 23, 24,
                25, 26, 27, 28, 29, 30, 31, 32
        };

        Assertions.assertArrayEquals(oneDim, main.changeToOneDimension(twoDim));
    }

    @Test
    void changeToTwoDimension() {
        Encryption main = new Encryption();

        byte[] oneDim = {
                1, 2, 3, 4, 5, 6, 7, 8,
                9, 10, 11, 12, 13, 14, 15, 16,
                17, 18, 19, 20, 21, 22, 23, 24,
                25, 26, 27, 28, 29, 30, 31, 32
        };

        byte[][] twoDim = {
                {1, 2, 3, 4, 5, 6, 7, 8},
                {9, 10, 11, 12, 13, 14, 15, 16},
                {17, 18, 19, 20, 21, 22, 23, 24},
                {25, 26, 27, 28, 29, 30, 31, 32}
        };

        Assertions.assertArrayEquals(twoDim, main.changeToTwoDimension(oneDim, 4, 8));
    }

    @Test
    void expansionPermutation() {
        Encryption main = new Encryption();

        byte[] rightHalf = {
                1, 2, 3, 4, 5, 6, 7, 8,
                9, 10, 11, 12, 13, 14, 15, 16,
                17, 18, 19, 20, 21, 22, 23, 24,
                25, 26, 27, 28, 29, 30, 31, 32
        };

        byte[] expanded = {
                32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8,
                9, 10, 11, 12, 13, 12, 13, 14, 15, 16,
                17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24,
                25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1
        };

        Assertions.assertArrayEquals(expanded, main.expansionPermutation(rightHalf));
    }

    @Test
    void expandedXorRoundKey() {
        Encryption main = new Encryption();
        byte[] exp = new byte[48];
        byte[] key = new byte[48];
        exp[0] = 0;
        exp[1] = 1;
        key[0] = 1;
        key[1] = 1;

        System.out.println(Arrays.toString(main.xorOperation(exp, key)));
    }

    @Test
    void changeToArray() {
        Encryption main = new Encryption();
        byte[] expected = {0,1,1,0};
        byte[] actual = main.changeToArray(6);

        System.out.println(Arrays.toString(expected));
        System.out.println(Arrays.toString(actual));

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void sBox() {
        Encryption main = new Encryption();

        byte[] sBox6 = {
                1, 0, 0, 1, 0, 1
        };

        byte[] sBox6v2 = {
                1, 0, 0, 1, 1, 1
        };


        //col - 4 // row - 2

        byte[] innerXor = {
                1, 2, 3, 4, 5, 6, 7, 8,
                9, 10, 11, 12, 13, 14, 15, 16,
                17, 18, 19, 20, 21, 22, 23, 24,
                25, 26, 7, 5, 29, 30, 31, 32
        };

        byte[] sBox4 = {
                1,1,1,1
        };

        byte[] sBox4v2 = {
                0,1,1,1
        };

        Assertions.assertArrayEquals(sBox4, main.sBox(sBox6, innerXor));

        System.out.println(Arrays.toString(sBox4v2));
        System.out.println(Arrays.toString(main.sBox(sBox6v2, innerXor)));

        Assertions.assertArrayEquals(sBox4v2, main.sBox(sBox6v2, innerXor));
    }

    @Test
    void keySubstitution() {
        Encryption main = new Encryption();

        byte[] afterXor48bits = {
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1,
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1,
                0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1
        };

        byte[] returnKey = {
                1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0,
                1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1
        };

        Assertions.assertArrayEquals(returnKey, main.keySubstitution(afterXor48bits));
    }

    @Test
    void permutationBox() {
    }
}