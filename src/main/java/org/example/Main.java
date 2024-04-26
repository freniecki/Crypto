package org.example;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class Main {
    static Logger logger = Logger.getLogger(Main.class.getName());

    static DES des = new DES();

    public static void main(String[] args) {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        logger.addHandler(consoleHandler);
        /*
            encryption -jar encrypt message(in HEX) key(in HEX)
            encryptF fileName key(in HEX)
         */
        if (args.length != 3) {
            logger.info("use 3 arguments");
            return;
        }

        if (args[0].equals("encrypt")) {
            runEncryptionText(args[1], args[2]);

        } else if (args[0].equals("encryptF")) {
            runEncryptionFile(args[1], args[2]);

        } else {
            logger.info("use proper command");
        }
    }

    static void runEncryptionText(String message, String key) {
        String[] messageArray = getMessage(message);
        String keyString = getKeyBitSet(key);
        BitSet keyBitSet = DES.convertStringToBitSet(keyString, 64);

        List<String> encrypted = encryptionCycle(messageArray, keyBitSet);

        System.out.println(encrypted);
    }

    static void runEncryptionFile(String message, String key) {
        FileIO fileReader = FileIOFactory.getFile(message);
        byte[] byteArray = fileReader.read();

        String[] messageArray = getMessage(byteHexToBinaryString(byteArray));
        BitSet keyBitSet = DES.convertStringToBitSet(getKeyBitSet(key), 64);

        List<String> encrypted = encryptionCycle(messageArray, keyBitSet);

        FileIO fileWriter = FileIOFactory.getFile("encryption.bmp");
        fileWriter.write(encrypted);
    }

    static List<String> encryptionCycle(String[] messageArray, BitSet keyBitSet) {
        List<String> encrypted = new ArrayList<>();
        String str;
        for (String s : messageArray) {
            BitSet messageBitSet = DES.convertStringToBitSet(s, 64);
            str = DES.bitsToString(des.encryption(messageBitSet, keyBitSet), 64);
            encrypted.add(binaryStringToHexString(str));
        }
        return encrypted;
    }

    /**
     * Turns binary-string message to string[]
     *
     * @param message Binary-string message
     * @return 64-bits string array filled with 0 if not 64k
     */
    public static String[] getMessage(String message) {
        byte[] messageByte = stringHexToByteArray(message);
        String messageString = byteHexToBinaryString(messageByte);
        if (messageString.length() % 64 != 0) {
            messageString = messageString + "0".repeat(messageString.length() % 64);
        }
        int size = messageString.length() / 64;
        String[] messageArray = new String[size];

        for (int i = 0; i < size; i++) {
            messageArray[i] = messageString.substring(i * 64, i * 64 + 64);
        }

        return messageArray;
    }

    public static byte[] stringHexToByteArray(String plainText) {
        byte[] byteHex = new byte[plainText.length() / 2];
        for (int i = 0; i < byteHex.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(plainText.substring(index, index + 2), 16);
            byteHex[i] = (byte) j;
        }
        return byteHex;
    }

    public static String byteHexToBinaryString(byte[] byteArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : byteArray) {
            stringBuilder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        return stringBuilder.toString();
    }

    public static String getKeyBitSet(String key) {
        byte[] hexByte = stringHexToByteArray(key);
        return byteHexToBinaryString(hexByte);
    }

    public static String binaryStringToHexString(String binary) {
        StringBuilder stringBuilder = new StringBuilder();
        String hexString;
        int hexDecimal;
        for (int i = 0; i < 16; i++) {
            hexString = binary.substring(i * 4, i * 4 + 4);
            hexDecimal = Integer.parseInt(hexString, 2);
            stringBuilder.append(Integer.toHexString(hexDecimal));
        }
        return stringBuilder.toString();
    }
}
