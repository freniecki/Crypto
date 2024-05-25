package org.example;

import java.math.BigInteger;
import java.util.Random;

/**
 * That algorithm was implemented according to lecture materials
 **/
//a.compareTo(b);  возвращает (-1 если a < b), (0 если a == b), (1 если a > b)
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
        p = geneateP();
        if((a.compareTo(BigInteger.ONE)) > 0 && a.compareTo(p.subtract(BigInteger.ONE)) < 0) {
            this.a = a;
        }
        else {
            this.a = BigInteger.ZERO;
        }
    }

    private BigInteger generateQ() { // According to algorithm, q > 2^140
        BigInteger initializeQ = BigInteger.ZERO;
        while (initializeQ.compareTo(BigInteger.valueOf(2).pow(140)) > 0) {
            initializeQ = BigInteger.probablePrime(140, random);
        }
        return initializeQ;
    }

    private boolean isANaturalNumber(BigInteger observed) {
        return observed.compareTo(BigInteger.ZERO) > 0 && (observed.remainder(BigInteger.ONE).equals(BigInteger.ZERO));
        // Checking if (p-q)/p belongs to N. It does if this stuff is greater than zero and mod 1 == 0
    }

    private BigInteger geneateP() { // According to algorithm, p > 2^512
        BigInteger initializeP = BigInteger.ZERO;
        while (initializeP.compareTo(BigInteger.valueOf(2).pow(512)) > 0 && isANaturalNumber((p.subtract(BigInteger.ONE)).divide(q))){
            initializeP = BigInteger.probablePrime(512, random);
        }
        return initializeP;
    }

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
