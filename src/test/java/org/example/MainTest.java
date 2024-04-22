package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void stringToByteArray() {
        String message = "0123456789ABCDEF";
        String expected = "0000000100100011010001010110011110001001101010111100110111101111";
        byte[] hexByte = Main.stringHexToByteArray(message);
        String hexBinary = Main.byteHexToBinaryString(hexByte);
        Assertions.assertEquals(expected, hexBinary);

        BitSet bitSet = Encryption.convertStringToBitSet(hexBinary, 64);

    }

    @Test
    void getMessage() {
        String newS = "0123456789ABCDEF0123456789ABCDE";

        System.out.println(newS.length());

        String[] string = Main.getMessage(newS);
        for (String str : string) {
            System.out.println(str);
            System.out.println(str.length());
        }
    }

    @Test
    void byteArrayToBitSetArray() {

    }

}