package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileIOTest {

    @Test
    void read() {
    }

    @Test
    void write() {
        String message = "dupa";
        byte[] plain = message.getBytes();
        byte[] file = FileIO.getFile("./plik.txt").read();
        byte[] encrypted = FileIO.getFile("./encrypted.txt").read();
        byte[] decrypted = FileIO.getFile("./decrypted.txt").read();

        printBytes(plain);
        printBytes(file);
        printBytes(encrypted);
        printBytes(decrypted);
    }

    void printBytes(byte[] bytes) {
        for (byte b : bytes) {
            System.out.println(Helper.byteToBinaryString(b));
        }
        System.out.println("---------");
    }
}