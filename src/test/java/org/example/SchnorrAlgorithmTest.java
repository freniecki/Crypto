package org.example;

import org.junit.jupiter.api.*;

import java.math.BigInteger;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Tests of Schnorr digital signature generator. Sometimes they pass, sometimes not
 **/

public class SchnorrAlgorithmTest {

    static Logger logger = Logger.getLogger(SchnorrAlgorithmTest.class.getName());
    String binaryMessage =
            "0000000100100011010001010110011110001001101010111100110111101111"; // Taken from previous DES tests
    byte[] message = binaryMessage.getBytes();
    Schnorr tested = new Schnorr(BigInteger.valueOf(100), message);
    @Test
    void testQInitializer() {
        logger.info(String.valueOf(tested.getQ()));
        logger.info(String.valueOf(tested.getQ().bitLength()));
        Assertions.assertTrue(tested.getQ().bitLength() >= 140);
    }

    @Test
    void testPInitializer() {
        logger.info(String.valueOf(tested.getP()));
        logger.info(String.valueOf(tested.getP().bitLength()));
        Assertions.assertTrue(tested.getP().bitLength() >= 512);
    }

    @Test
    void testConstructor() {
        logger.info(String.valueOf(tested.getA()));
        Assertions.assertEquals(BigInteger.valueOf(100), tested.getA());
    }

    @Test
    void testCreatingAndValidatingOfSignature() {
        logger.info(String.valueOf(tested.getH()));
        logger.info(String.valueOf(tested.getV()));
        BigInteger[] signature = tested.sign();
        BigInteger hForValidation = tested.getH();
        BigInteger vForValidation = tested.getV();
        Assertions.assertTrue(tested.verifySignature(signature, hForValidation, vForValidation));
    }

}
