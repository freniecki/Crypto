package org.example;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class Main {
    static Logger logger = Logger.getLogger(Main.class.getName());

    static DES des = new DES();

    public static void main(String[] args) {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        logger.addHandler(consoleHandler);

        if (args.length != 4 && args.length != 5 ) {
            String info = """ 
                    proper usage:
                    encrypt/decrypt text inputFileName outputFileName key(in HEX)
                    encrypt/decrypt file inputFileName outputFileName key(in HEX)
                    """;
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
        FileIO fileReader = FileIO.getFile(inputFileName);
        byte[] byteArray = fileReader.readTextFileToBytes();

        BitSet keyBitSet = DES.convertStringToBitSet(Helper.getKeyBitSet(key), 64);

        byte[] encrypted = des.encrypt(byteArray, keyBitSet);

        FileIO fileWriter = FileIO.getFile(outputFileName);
        fileWriter.writeBytesToFile(encrypted);
    }

    private static void runDecryptionText(String inputFileName, String outputFileName, String key) {
        FileIO fileReader = FileIO.getFile(inputFileName);
        byte[] byteArray = fileReader.readBytesFromFile();

        BitSet keyBitSet = DES.convertStringToBitSet(Helper.getKeyBitSet(key), 64);

        byte[] decrypted = des.decrypt(byteArray, keyBitSet);

        FileIO fileWriter = FileIO.getFile(outputFileName);
        fileWriter.writeBytesAsASCII(decrypted);
    }

    static void runEncryptionFile(String inputFileName, String outputFileName, String key) {
        FileIO fileReader = FileIO.getFile(inputFileName);
        byte[] byteArray = fileReader.readBytesFromFile();

        BitSet keyBitSet = DES.convertStringToBitSet(Helper.getKeyBitSet(key), 64);

        byte[] encrypted = des.encrypt(byteArray, keyBitSet);

        FileIO fileWriter = FileIO.getFile(outputFileName);
        fileWriter.writeBytesToFile(encrypted);
    }

    private static void runDecryptionFile(String inputFileName, String outputFileName, String key) {
        FileIO fileReader = FileIO.getFile(inputFileName);
        byte[] byteArray = fileReader.readBytesFromFile();

        BitSet keyBitSet = DES.convertStringToBitSet(Helper.getKeyBitSet(key), 64);

        byte[] decrypted = des.decrypt(byteArray, keyBitSet);

        FileIO fileWriter = FileIO.getFile(outputFileName);
        fileWriter.writeBytesToFile(decrypted);
    }
}
