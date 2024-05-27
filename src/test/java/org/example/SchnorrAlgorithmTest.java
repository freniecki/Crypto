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
        logger.info(String.valueOf(tested.getQ().bitCount()));
        Assertions.assertTrue(tested.getQ().bitCount() >= 140);
    }

    @Test
    void testConstructor() {
        logger.info(String.valueOf(tested.getA()));
        Assertions.assertEquals(BigInteger.valueOf(100), tested.getA());
    }

}
