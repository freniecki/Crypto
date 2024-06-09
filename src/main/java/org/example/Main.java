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

    public static void main(String args[]) {

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
     Schnorr digital signature run functions.
     Function 1: Create and write a signature to a concrete file.
     */
    static void runSignCreationOfAFile (String inputFileName, String outputFileName, String hasKeys) throws FileNotFoundException, IOException, RuntimeException {
        FileIO fileReader = FileIO.getFile(inputFileName);
        byte[] message = fileReader.readBytesFromFile();
        BigInteger privateKey;
        logger.warning("Please, provide your private key [do not expose it to third persons]:");
        Scanner input = new Scanner(System.in);
        privateKey = input.nextBigInteger();
        logger.warning("Do NOT expose your private key in any occasion!");
        logger.warning("Private key:" + privateKey.toString());

        Schnorr signer = new Schnorr(privateKey, message);

        BigInteger[] signature = signer.sign();
        BigInteger h = signer.getH();
        BigInteger v = signer.getV();
        BigInteger p = signer.getP();

        Signature signature1 = new Signature(signature[0], signature[1], h, v, p);

        FileIO.getFile(outputFileName).writeObject(signature1);
    }

    /**
     Function 2: Read the signature and its parameters of a public key.
     Then, verify the signature.
     */
    static void runVerification (String locationOfSignature) throws IOException, RuntimeException {
        FileIO fileReader = FileIO.getFile(locationOfSignature);
        Signature signature = (Signature) fileReader.readObject();


        BigInteger h = signature.getH();
        BigInteger v = signature.getV();
        BigInteger p = signature.getP();

        BigInteger[] signatureForValidation = new BigInteger[2];
        signatureForValidation[0] = signature.getS1();
        signatureForValidation[1] = signature.getS2();

        Schnorr verifier = new Schnorr();

        boolean isValid = verifier.verifySignature(signatureForValidation, h, v, p);
        logger.info(isValid ? "Signature is valid" : "Signature is invalid");

    }

}