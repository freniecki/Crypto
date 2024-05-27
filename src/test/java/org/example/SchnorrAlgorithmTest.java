package org.example;

import org.junit.jupiter.api.*;

import java.math.BigInteger;
import java.util.Objects;
import java.util.logging.Logger;

public class SchnorrAlgorithmTest {

    static Logger logger = Logger.getLogger(SchnorrAlgorithmTest.class.getName());
    Schnorr tested = new Schnorr(BigInteger.valueOf(100));
    @Test
    void testQInitializer() {
        logger.info(String.valueOf(tested.getQ().bitLength()));
        Assertions.assertTrue(tested.getQ().bitLength() >= 140);
    }

    @Test
    void testpInitializer() {
        logger.info(String.valueOf(tested.getP().bitLength()));
        Assertions.assertTrue(tested.getP().bitLength() >= 512);
    }

    @Test
    void testConstructor() {
        logger.info(String.valueOf(tested.getA()));
        Assertions.assertEquals(BigInteger.valueOf(100), tested.getA());
    }

}
