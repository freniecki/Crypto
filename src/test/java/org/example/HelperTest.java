package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HelperTest {

    @Test
    void combineByteArray() {
    }

    @Test
    void byteArrayFillZeros() {
    }

    @Test
    void byteArrayToByteList() {
        byte[] bytes = {
                (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                (byte) 0, (byte) 0, (byte) 0, (byte) 0
        };
        List<byte[]> list = Helper.byteArrayToByteList(bytes);

        Assertions.assertArrayEquals(bytes, list.getFirst());

        byte[] bytes2 = {
                (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                (byte) 0, (byte) 0
        };
        List<byte[]> list2 = Helper.byteArrayToByteList(bytes2);

        Assertions.assertArrayEquals(bytes, list2.getFirst());
    }

    @Test
    void stringHexToByteArray() {
    }

    @Test
    void byteHexToBinaryString() {
    }

    @Test
    void getKeyBitSet() {
    }

    @Test
    void binaryStringToHexString() {
    }

    @Test
    void byteToBinaryString() {
    }
}