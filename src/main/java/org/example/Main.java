package org.example;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

         */
        if (args.length != 5) {
            String info = """ 
                    proper usage:
                    1. encrypt text message(in HEX) fileName key(in HEX)
                    2. decrypt text message(in HEX) fileName key(in HEX)
                    3. encrypt file fileName fileName key(in HEX)
                    4. decrypt file fileName fileName key(in HEX)
                    """;

            logger.info("use 3 arguments");
            logger.info(info);
            return;
        }

        if (args[0].equals("encrypt")) {
            if (args[1].equals("text")) {
                runEncryptionText(args[2], args[3], args[4]);
            } else if (args[1].equals("file")) {
                runEncryptionFile(args[2], args[3], args[4]);
            } else {
                logger.info("text or file?");
            }
        } else if (args[0].equals("decrypt")) {
            if (args[1].equals("text")) {
                runDecryptionText(args[2], args[3], args[4]);
            } else if (args[1].equals("file")) {
                runDecryptionFile(args[2], args[3], args[4]);
            } else {
                logger.info("text or file?");
            }
        } else {
            logger.info("use proper command");
        }
    }

    static void runEncryptionText(String message, String fileName, String key) {
        String[] messageArray = getMessage(message);
        String keyString = getKeyBitSet(key);
        BitSet keyBitSet = DES.convertStringToBitSet(keyString, 64);

        List<byte[]> encrypted = encryptionCycle(messageArray, keyBitSet);

        FileIO fileWriter = FileIOFactory.getFile(fileName);
        fileWriter.write(combineByteArray(encrypted));
    }

    private static void runDecryptionText(String message, String fileName, String key) {
        String[] messageArray = getMessage(message);
        String keyString = getKeyBitSet(key);
        BitSet keyBitSet = DES.convertStringToBitSet(keyString, 64);

        List<byte[]> decrypted = decryptionCycle(messageArray, keyBitSet);

        FileIO fileWriter = FileIOFactory.getFile(fileName);
        fileWriter.write(combineByteArray(decrypted));
    }

    static void runEncryptionFile(String inputFileName, String outputFileName, String key) {
        FileIO fileReader = FileIOFactory.getFile(inputFileName);
        byte[] byteArray = fileReader.read();

        String[] messageArray = getMessage(byteHexToBinaryString(byteArray));
        BitSet keyBitSet = DES.convertStringToBitSet(getKeyBitSet(key), 64);

        List<byte[]> encrypted = encryptionCycle(messageArray, keyBitSet);

        FileIO fileWriter = FileIOFactory.getFile(outputFileName);
        fileWriter.write(combineByteArray(encrypted));
    }

    private static void runDecryptionFile(String inputFileName, String outputFileName, String key) {
        FileIO fileReader = FileIOFactory.getFile(inputFileName);
        byte[] byteArray = fileReader.read();

        String[] messageArray = getMessage(byteHexToBinaryString(byteArray));
        BitSet keyBitSet = DES.convertStringToBitSet(getKeyBitSet(key), 64);

        List<byte[]> decrypted = decryptionCycle(messageArray, keyBitSet);

        FileIO fileWriter = FileIOFactory.getFile(outputFileName);
        fileWriter.write(combineByteArray(decrypted));
    }

    static List<byte[]> encryptionCycle(String[] messageArray, BitSet keyBitSet) {
        List<byte[]> encrypted = new ArrayList<>();
        byte[] aByte;
        for (String s : messageArray) {
            BitSet messageBitSet = DES.convertStringToBitSet(s, 64);
            aByte = DES.bitSetToByteArray(des.encryption(messageBitSet, keyBitSet));
            encrypted.add(aByte);
        }
        return encrypted;
    }

    static List<byte[]> decryptionCycle(String[] messageArray, BitSet keyBitSet) {
        List<byte[]> decrypted = new ArrayList<>();
        byte[] aByte;
        for (String s : messageArray) {
            BitSet messageBitSet = DES.convertStringToBitSet(s, 64);
            aByte = DES.bitSetToByteArray(des.decryption(messageBitSet, keyBitSet));
            decrypted.add(aByte);
        }
        return decrypted;
    }

    static byte[] combineByteArray(List<byte[]> byteArray) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        for (byte[] bytes : byteArray) {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                logger.info("io exception while combining bytes");
            }
        }

        return outputStream.toByteArray();
    }

    /**
     * Turns binary-string message to string[]
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
