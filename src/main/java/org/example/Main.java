package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.BitSet;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class Main {
    static Logger logger = Logger.getLogger(Main.class.getName());

    static DES des = new DES();

    public static void main(String[] args) throws IOException {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        logger.addHandler(consoleHandler);

        if (args.length != 5) {
            String info = """ 
                    proper usage:
                    encrypt/decrypt(for DFS) text inputFileName outputFileName key(in HEX)
                    encrypt/decrypt(for DFS) file inputFileName outputFileName key(in HEX)
                                        
                    create/verify(for Schnorr) text inputFileName outputFileName true/false
                    create/verify(for Schnorr) file inputFileName outputFileName true/false
                    """;
            logger.info(info);
            return;
        }

        //Probably redundant asking for an algorithm, as only dfs en/decrypts and only schnorr creates/verifies signature. Requires review.
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
        } else if (args[0].equalsIgnoreCase("create")) {
            if (args[1].equalsIgnoreCase("text")) {
                logger.warning("Do NOT expose your private key in any occasion!");
//                run generating of signature of text file
                runSignCreationOfAFile(args[2], args[3], args[4]);
            } else if (args[1].equalsIgnoreCase("file")) {
                logger.warning("Do NOT expose your private key in any occasion!");
//                run generating of signature of binary file
                runSignCreationOfAFile(args[2], args[3], args[4]);
            } else {
                logger.info("text or file?");
            }
        } else if (args[0].equalsIgnoreCase("verify")) {
            if (args[1].equalsIgnoreCase("text")) {
                logger.warning("ATTENTION! Do NOT expose your private key in any occasion!");
//                run verification of signature located in text file

            } else if (args[1].equalsIgnoreCase("file")) {
                logger.warning("ATTENTION! Do NOT expose your private key in any occasion!");
//                run verification of signature located in binary file
            } else {
                logger.info("text or file?");
            }
        } else {
            logger.info("use proper command");
        }

    }

    /**
     DES run functions
     **/

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

    /**
     Schnorr digital signature run functions
    **/

    static void runSignCreationOfAFile (String inputFileName, String outputFileName, String hasKeys) throws FileNotFoundException, IOException, RuntimeException {
        FileIO fileReader = FileIO.getFile(inputFileName);
        byte[] message = fileReader.readBytesFromFile();
        BigInteger privateKey = BigInteger.ZERO;
        logger.warning("Please, provide your private key [do not expose it to third persons]:");
        Scanner input = new Scanner(System.in);
        privateKey = input.nextBigInteger();
        logger.warning("Private key:" + privateKey.toString());

        Schnorr signer = new Schnorr(privateKey, message);

        BigInteger[] signature = signer.sign();

        Signature signature1 = new Signature(signature[0], signature[1]);

        FileIO.getFile(outputFileName).writeObject(signature1);
    }

    static void runVerification (String locationOfSignature, String privateKey, String givenH, String givenV, String givenP) throws FileNotFoundException, IOException, RuntimeException {
        FileIO fileReader = FileIO.getFile(locationOfSignature);

        Signature signature = (Signature) fileReader.readObject();

        BigInteger H = new BigInteger(givenH);
        BigInteger V = new BigInteger(givenV);
        BigInteger P = new BigInteger(givenP);

        BigInteger[] signatureForValidation = new BigInteger[2];
        signatureForValidation[0] = signature.getS1();
        signatureForValidation[1] = signature.getS2();

        Schnorr verifier = new Schnorr();

        boolean isValid = verifier.verifySignature(signatureForValidation, H, V, P);
        logger.info(isValid ? "Signature is valid" : "Signature is invalid");

    }

}