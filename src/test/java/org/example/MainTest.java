package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

class MainTest {

    @Test
    void stringToByteArray() {
        String message = "0123456789ABCDEF";
        String expected = "0000000100100011010001010110011110001001101010111100110111101111";
        byte[] hexByte = Helper.stringHexToByteArray(message);
        String hexBinary = Helper.byteHexToBinaryString(hexByte);
        Assertions.assertEquals(expected, hexBinary);

        BitSet bitSet = DES.convertStringToBitSet(hexBinary, 64);

    }

    @Test
    void encodeDecode() {
        byte[] bytes = {
                (byte) 15, (byte) 56, (byte) 0, (byte) 0,
                (byte) 0, (byte) 9, (byte) 0, (byte) 2
        };
        printBytes(bytes);

        String key = "133457799BBCDFF1";
        BitSet keyBitSet = DES.convertStringToBitSet(Helper.getKeyBitSet(key), 64);

        byte[] encrypted = Main.des.encrypt(bytes, keyBitSet);
        //printBytes(encrypted);

        byte[] decrypted = Main.des.decrypt(encrypted, keyBitSet);
        printBytes(decrypted);

        Assertions.assertArrayEquals(bytes, decrypted);
    }

    void printBytes(byte[] bytes) {
        for (byte b : bytes) {
            System.out.println(Helper.byteToBinaryString(b));
        }
        System.out.println("---------");
    }
}