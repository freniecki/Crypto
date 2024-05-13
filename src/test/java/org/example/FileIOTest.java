package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileIOTest {

    @Test
    void read() {
    }

    @Test
    void write() {
        String message = "dupadupa";
        byte[] messageBytes = message.getBytes();

        byte[] file = FileIO.getFile("./plik.txt").readBytesFromFile();
        printBytes(file);
        byte[] encrypted = FileIO.getFile("./encrypted.txt").readBytesFromFile();
        printBytes(encrypted);
        byte[] decrypted = FileIO.getFile("./decrypted.txt").readBytesFromFile();
        printBytes(decrypted);

        Assertions.assertArrayEquals(messageBytes, file);

        Assertions.assertArrayEquals(file, decrypted);
    }

    void printBytes(byte[] bytes) {
        System.out.println("-----test----");
        for (byte b : bytes) {
            System.out.println(Helper.byteToBinaryString(b));
        }
        System.out.println("---------");
    }
}

/*
java -jar ./build/libs/krypto-1.0-SNAPSHOT.jar encrypt file ./image.bmp ./encrypted.bmp 133457799BBCDFF1;
java -jar ./build/libs/krypto-1.0-SNAPSHOT.jar decrypt file ./encrypted.bmp ./decrypted.bmp 133457799BBCDFF1
 */