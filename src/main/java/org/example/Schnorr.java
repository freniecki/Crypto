package org.example;

import org.apache.commons.lang3.builder.EqualsBuilder;

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

    private BigInteger X;
    private BigInteger Z;

    private byte[] MX;
    private byte[] MZ;

    private byte[] M;

    /**
     * Private key
     **/
    private BigInteger a;

    private BigInteger r;


    /**
     * Results
     **/
    private BigInteger s1;

    private static final Random random = new Random();
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

    /**
     * Generators of required numbers
     **/

    private BigInteger generateQ() { // According to algorithm, q > 2^140
        return BigInteger.probablePrime(140, random);
    }

//    private boolean isANaturalNumber(BigInteger observed) {
//        return observed.compareTo(BigInteger.ZERO) > 0 && (observed.remainder(BigInteger.ONE).compareTo(BigInteger.ZERO) == 0);
//        // Checking if (p-q)/p belongs to N. It does if this stuff is greater than zero and mod 1 == 0
//    } not useful so far

    private BigInteger geneateP() { // According to algorithm, p > 2^512
        BigInteger initializeP;
        do {
            initializeP = ((BigInteger.probablePrime(512, random)).subtract(BigInteger.ONE));
            initializeP = initializeP.subtract(initializeP.remainder(q));
        } while (!initializeP.isProbablePrime(5));
        return initializeP;
    }


    private BigInteger generateR() { // According to algorithm 0 < r < q-1
        BigInteger initializeR;
        int lentgth = (q.subtract(BigInteger.ONE)).bitLength();
        do {
            initializeR = new BigInteger(lentgth, random);
        } while (initializeR.compareTo(BigInteger.ZERO) < 0 && initializeR.compareTo(q) > 0);
        return initializeR;
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


    /**
     * Concatenation
     **/

    public byte[] concatenation(byte[] firstRow, byte[] secondRow) {
        byte[] result = new byte[firstRow.length + secondRow.length];
        int counter = 0;
        int j = 0;

        for(int i = 0; i < firstRow.length; i++) {
            result[i] = firstRow[i];
            counter = i;
        }

        for (int i = counter; i < counter + secondRow.length; i++) {
            result[i] = secondRow[j];
            j++;
        }

        return result;
    }

    public void createMX(){
        MX = concatenation(M, X.toByteArray());
    }

    public void createMZ() {
        MZ = concatenation(M, Z.toByteArray());
    }


    /**
     * Hash-functions
     **/

    public BigInteger hashCodeMX() {
        return BigInteger.valueOf(new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37).append(MX).toHashCode());
    }

    public BigInteger hashCodeMZ() {
        return BigInteger.valueOf(new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37).append(MZ).toHashCode());
    }
}
