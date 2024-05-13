package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.example.FileIOTest.printBytes;
import static org.junit.jupiter.api.Assertions.*;

class HelperTest {

    @Test
    void byteArrayFillZeros() {
        byte[] image = FileIO.getFile("./files/image.bmp").readBytesFromFile();
        byte[] decrypted = FileIO.getFile("./files/decrypted.bmp").readBytesFromFile();

        printFirstBytes(image);
        printFirstBytes(decrypted);


        printLastBytes(image);
        printLastBytes(decrypted);

        Assertions.assertArrayEquals(image, decrypted);
    }

    private void printFirstBytes(byte[] image) {
        for (int i = 0; i < 8; i++) {
            System.out.println(Helper.byteToBinaryString(image[i]));
        }
        System.out.println("------------");
    }

    void printLastBytes(byte[] bytes) {
        int bytesLength = bytes.length;
        int rest = bytes.length % 8;
        if (rest !=0) {
            for (int i = bytesLength - rest; i < bytesLength; i++) {
                System.out.println(Helper.byteToBinaryString(bytes[i]));
            }
            System.out.println("---------");
        } else {
            for (int i = bytesLength - 8; i < bytesLength; i++) {
                System.out.println(Helper.byteToBinaryString(bytes[i]));
            }
            System.out.println("---------");
        }

    }
}