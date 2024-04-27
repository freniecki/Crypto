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
                    encrypt/decrypt text/file inoutFileName outputFileName key(in HEX)
                    """;

            logger.info("use 5 arguments");
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

    static void runEncryptionText(String inputFileName, String outputFileName, String key) {
        FileIO fileReader = FileIOFactory.getFile(inputFileName);
        byte[] byteArray = fileReader.read();

        BitSet keyBitSet = DES.convertStringToBitSet(getKeyBitSet(key), 64);

        byte[] encrypted = encryptionCycle(byteArray, keyBitSet);

        FileIO fileWriter = FileIOFactory.getFile(outputFileName);
        fileWriter.write(encrypted);
    }

    private static void runDecryptionText(String inputFileName, String outputFileName, String key) {
        FileIO fileReader = FileIOFactory.getFile(inputFileName);
        byte[] byteArray = fileReader.read();

        BitSet keyBitSet = DES.convertStringToBitSet(getKeyBitSet(key), 64);

        byte[] decrypted = decryptionCycle(byteArray, keyBitSet);

        FileIO fileWriter = FileIOFactory.getFile(outputFileName);
        fileWriter.write(decrypted);
    }

    static void runEncryptionFile(String inputFileName, String outputFileName, String key) {
        FileIO fileReader = FileIOFactory.getFile(inputFileName);
        byte[] byteArray = fileReader.read();

        BitSet keyBitSet = DES.convertStringToBitSet(getKeyBitSet(key), 64);

        byte[] encrypted = encryptionCycle(byteArray, keyBitSet);

        FileIO fileWriter = FileIOFactory.getFile(outputFileName);
        fileWriter.write(encrypted);
    }

    private static void runDecryptionFile(String inputFileName, String outputFileName, String key) {
        FileIO fileReader = FileIOFactory.getFile(inputFileName);
        byte[] byteArray = fileReader.read();
        logger.info(String.valueOf(byteArray.length));

        BitSet keyBitSet = DES.convertStringToBitSet(getKeyBitSet(key), 64);

        byte[] decrypted = decryptionCycle(byteArray, keyBitSet);

        FileIO fileWriter = FileIOFactory.getFile(outputFileName);
        fileWriter.write(decrypted);
    }

    static byte[] encryptionCycle(byte[] byteArray, BitSet keyBitSet) {
        List<byte[]> messageArray = byteArrayToByteList(byteArray);
        byte[] aByte;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        for (byte[] bytes : messageArray) {
            BitSet messageBitSet = BitSet.valueOf(bytes);
            aByte = DES.bitSetToByteArray(des.encryption(messageBitSet, keyBitSet));
            try {
                outputStream.write(aByte);
            } catch (IOException e) {
                logger.info("io exception: encryption cycle");
            }
        }
        return outputStream.toByteArray();
    }

    static byte[] decryptionCycle(byte[] byteArray, BitSet keyBitSet) {
        List<byte[]> messageArray = byteArrayToByteList(byteArray);
        byte[] aByte;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        for (byte[] bytes : messageArray) {
            BitSet messageBitSet = BitSet.valueOf(bytes);
            aByte = DES.bitSetToByteArray(des.decryption(messageBitSet, keyBitSet));
            try {
                outputStream.write(aByte);
            } catch (IOException e) {
                logger.info("io exception: decryption cycle");
            }
        }
        return outputStream.toByteArray();
    }

    static List<byte[]> byteArrayToByteList(byte[] byteArray) {
        byte[] clearByteArray = byteArrayFillZeros(byteArray);
        int length = clearByteArray.length / 8;
        List<byte[]> list = new ArrayList<>();
        byte[] byteArrayToList = new byte[8];

        for (int i = 0; i < length; i++) {
            System.arraycopy(clearByteArray, i * 8, byteArrayToList, 0, 8);
            list.add(byteArrayToList);
        }

        return list;
    }

    static byte[] byteArrayFillZeros(byte[] byteArray) {
        if ( byteArray.length % 8 != 0) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] zeroArray = new byte[8 - byteArray.length % 8 ];
            try {
                outputStream.write(byteArray);
                outputStream.write(zeroArray);
            } catch (IOException e) {
                logger.info("io exception: filling with 0's");
            }
            return outputStream.toByteArray();
        } else {
            return byteArray;
        }
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
