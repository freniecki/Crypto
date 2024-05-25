package org.example;

import java.math.BigInteger;
import java.util.Random;

/**
 * That algorithm was implemented according to lecture materials
 **/

public class Schnorr {
    /**
     * Parameters of a public key
     **/
    private BigInteger p; // p > 2^512
    private BigInteger q; // q > 2^140
    private BigInteger h;
    private BigInteger v;


    /**
     * Private key
     **/
    private BigInteger a;

    private static Random random = new Random();
    public Schnorr(BigInteger a) {
        q = generateQ();
        if((a.compareTo(BigInteger.ONE)) > 0 && a.compareTo(p.subtract(BigInteger.ONE)) < 0) {
            this.a = a;
        }
        else {
            this.a = BigInteger.ZERO;
        }
    }

    public static BigInteger generateQ() { // According to algorithm, q > 2^140
        BigInteger initializeQ = BigInteger.ZERO;
        while (initializeQ.compareTo(BigInteger.valueOf(2).pow(140)) > 0) {
            initializeQ = BigInteger.probablePrime(512, random);
        }
        return initializeQ;
    }

//    private BigInteger geneateP() {
//        BigInteger initializeP = 0;
//
//        return initializeP;
//    }

    /**
     * Getters. Just for satisfaction [REMOVE IF UNUSED!]
     **/
    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getH() {
        return h;
    }

    public BigInteger getV() {
        return v;
    }

    public BigInteger getA() {
        return a;
    }

    /**
     * Setters [THE SAME SH]
     **/
    public void setP(BigInteger p) {
        this.p = p;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public void setH(BigInteger h) {
        this.h = h;
    }

    public void setV(BigInteger v) {
        this.v = v;
    }

    public void setA(BigInteger a) {
        this.a = a;
    }


}
