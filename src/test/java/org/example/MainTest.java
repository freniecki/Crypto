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
        byte[] hexByte = Main.stringHexToByteArray(message);
        String hexBinary = Main.byteHexToBinaryString(hexByte);
        Assertions.assertEquals(expected, hexBinary);

        BitSet bitSet = DES.convertStringToBitSet(hexBinary, 64);

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

    @Test
    void combineByteArray() {
        List<byte[]> list = new ArrayList<>();
        byte[] byte1 = new byte[8];
        byte1[0] = (byte) 17;
        list.add(byte1);
        for (byte aByte : byte1) {
            System.out.println(DES.byteToBinaryString(aByte));
        }

        byte[] byte2 = new byte[8];
        byte2[0] = (byte) 17;
        list.add(byte2);
        for (byte aByte : byte2) {
            System.out.println(DES.byteToBinaryString(aByte));
        }

        byte[] newList;
        newList = Main.combineByteArray(list);


        System.out.println("----------------------------");
        for (byte aByte : newList) {
            System.out.println(DES.byteToBinaryString(aByte));
        }
    }

}