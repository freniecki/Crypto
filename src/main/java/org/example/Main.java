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

        if (args.length != 6) {
            String info = """ 
                    proper usage:
                    algorithm (DFS or Schnorr signature) encrypt/decrypt(for DFS) | create/verify(for Schnorr) text inputFileName outputFileName key(in HEX)
                    algorithm (DFS or Schnorr signature) encrypt/decrypt(for DFS) | create/verify(for Schnorr) file inputFileName outputFileName key(in HEX)
                    """;
            logger.info(info);
            return;
        }

        //Probably redundant asking for an algorithm, as only dfs en/decrypts and only schnorr creates/verifies signature. Requires review.
        if (args[0].equalsIgnoreCase("DFS")) {
            logger.info("DFS has been chosen");
            if (args[1].equals("encrypt")) {
                if (args[2].equals("text")) {
                    runEncryptionText(args[3], args[4], args[5]);
                } else if (args[2].equals("file")) {
                    runEncryptionFile(args[3], args[4], args[5]);
                } else {
                    logger.info("text or file?");
                }
            } else if (args[1].equals("decrypt")) {
                if (args[2].equals("text")) {
                    runDecryptionText(args[3], args[4], args[5]);
                } else if (args[2].equals("file")) {
                    runDecryptionFile(args[3], args[4], args[5]);
                } else {
                    logger.info("text or file?");
                }
            } else {
                logger.info("use proper command");
            }
        } else if (args[0].equalsIgnoreCase("Schnorr")) {
            logger.info("Schnorr algorithm has been chosen. Implemenration comming soon");
        } else {
            logger.info("Please, read proper usage");
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
