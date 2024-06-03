package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;

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

    static void printBytes(byte[] bytes) {
        System.out.println("-----test----");
        for (byte b : bytes) {
            System.out.println(Helper.byteToBinaryString(b));
        }
        System.out.println("---------");
    }

    @Test
    void testSerializationOfAnObjectWrite() {
        BigInteger temp = new BigInteger("3");
        BigInteger temp1 = new BigInteger("12");
        Signature signature = new Signature(BigInteger.ONE, BigInteger.TWO, BigInteger.TEN, temp, temp1);
        Assertions.assertDoesNotThrow(() -> FileIO.getFile("oaoammm.txt").writeObject(signature));
    }

    @Test
    void testSerializationOfAnObjectRead() throws FileNotFoundException, IOException, RuntimeException {
        BigInteger temp = new BigInteger("3");
        BigInteger temp1 = new BigInteger("12");
        Signature signature = null;
        signature = (Signature) FileIO.getFile("oaoammm.txt").readObject();
        System.out.println(signature.getS1().toString());
        System.out.println(signature.getS2().toString());
        System.out.println(signature.getH().toString());
        System.out.println(signature.getV().toString());
        System.out.println(signature.getP().toString());
        assertEquals(0, signature.getS1().compareTo(BigInteger.ONE));
        assertEquals(0, signature.getS2().compareTo(BigInteger.TWO));
        assertEquals(0, signature.getH().compareTo(BigInteger.TEN));
        assertEquals(0, signature.getV().compareTo(temp));
        assertEquals(0, signature.getP().compareTo(temp1));
    }

    @Test
    void testSerializationOfAnObjectReadNonExistentFile() {
        Signature signature = null;
        Assertions.assertThrows(FileNotFoundException.class, () -> FileIO.getFile("nonexistentfile.txt").readObject());
    }
}

/*
java -jar ../build/libs/krypto-1.0-SNAPSHOT.jar encrypt file ./image.bmp ./encrypted.bmp 133457799BBCDFF1;
java -jar ../build/libs/krypto-1.0-SNAPSHOT.jar decrypt file ./encrypted.bmp ./decrypted.bmp 133457799BBCDFF1
 */