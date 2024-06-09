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

    private BigInteger s2;

    private static final Random random = new Random();
    public Schnorr(BigInteger a, byte[] message) {
        q = generateQ();
        p = generateP();
        h = generateH();
        r = generateR();
        X = generateX();
        M = message;
        if((a.compareTo(BigInteger.ONE)) > 0 && a.compareTo(p.subtract(BigInteger.ONE)) < 0) {
            this.a = a;
        }
        else {
            this.a = BigInteger.ZERO;
        }
        v = generateV();
    }

    public Schnorr(BigInteger p, BigInteger q, BigInteger h, BigInteger v, BigInteger a, BigInteger r, byte[] message) {
        this.p = p;
        this.q = q;
        this.h = h;
        this.v = v;
        this.a = a;
        this.r = r;
        M = message;
    }

    public Schnorr() {
    }

    private BigInteger generateX() {
        return h.modPow(r, p);
    }

    /**
     * Generators of required numbers
     **/

    private BigInteger generateQ() { // According to algorithm, q > 2^140
        return BigInteger.probablePrime(140, random);
    }

    private boolean MillerRabinPrimalityTest(BigInteger observedNumber) {
        /**
         Checks if given number possibly prime.
          @param primeNumbers: first 100 prime numbers [https://prime-numbers.info/list/first-100-primes]
         */
        int[] primeNumbers = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43,
                47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113,
                127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193,
                197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271,
                277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359,
                367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443,
                449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541};

        if (observedNumber.compareTo(BigInteger.ONE) < 0 ) {
            return false;
        }

        for (int number : primeNumbers) {
            if (observedNumber.compareTo(BigInteger.valueOf(number)) == 0) {
                return true;
            }
            if (observedNumber.mod(BigInteger.valueOf(number)).compareTo(BigInteger.ZERO) == 0) {
                return false;
            }
        }

        BigInteger d = observedNumber.subtract(BigInteger.ONE);
        int s = 0;
        while (d.mod(BigInteger.TWO).compareTo(BigInteger.TWO) == 0) {
            d = d.divide(BigInteger.TWO);
            s++;
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < primeNumbers.length - 1; j++) {
                BigInteger a = BigInteger.valueOf(primeNumbers[j]);
                if (!millerRabinPass(a, s, d, observedNumber)) return false;
            }
        }
        return true;
    }

    private boolean millerRabinPass(BigInteger a, int s, BigInteger d, BigInteger observedNumber) {
        BigInteger aPowD = a.modPow(d, observedNumber);
        if (aPowD.equals(BigInteger.ONE)) return true;
        for (int r = 0; r < s; r++) {
            if (aPowD.equals(observedNumber.subtract(BigInteger.ONE))) return true;
            aPowD = aPowD.modPow(BigInteger.TWO, observedNumber);
        }
        return false;
    }

    private BigInteger generateP() { // According to algorithm, p > 2^512
        BigInteger initializeP;
        do {
            initializeP = BigInteger.probablePrime(512, random);
            initializeP = (initializeP.subtract(BigInteger.ONE)).subtract(initializeP.remainder(q));
        } while (!MillerRabinPrimalityTest(initializeP));
        return initializeP;
    }


    private BigInteger generateR() { // According to algorithm 0 < r < q-1
        BigInteger initializeR;
        int length = (q.subtract(BigInteger.ONE)).bitLength();
        do {
            initializeR = new BigInteger(length, random);
        } while (initializeR.compareTo(BigInteger.ZERO) < 0 && initializeR.compareTo(q) > 0);
        return initializeR;
    }

    private BigInteger generateH() {
        BigInteger initializeH;
        initializeH = new BigInteger(510, random);
        initializeH = initializeH.modPow(p.subtract(BigInteger.ONE).divide(q), p);
        return initializeH;
    }

    private BigInteger generateV() {
        return (h.modPow(a, p)).modInverse(p);
    }

    /**
     * Getters. Just for satisfaction.
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

    public BigInteger getS1() {
        return s1;
    }

    public BigInteger getS2() {
        return s2;
    }

    public BigInteger getR() {
        return r;
    }

    /**
     * Setters
     **/
    public void setP(BigInteger p) {
        this.p = p;
    }

    public void setQ(BigInteger q) {
        this.q = q;
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

    public Schnorr(byte[] m) {
        M = m;
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

    /**
     * Generating signature
     **/
    public BigInteger[] sign() {
        BigInteger[] signature = new BigInteger[2];
        createMX();
        s1 = hashCodeMX();
        s2 = (r.add(a.multiply(s1))).mod(q);
        signature[0] = s1;
        signature[1] = s2;
        return signature;
    }

    public boolean verifySignature(BigInteger[] signature, BigInteger givenH, BigInteger givenV, BigInteger givenP) {
        s1 = signature[0];
        s2 = signature[1];
        Z = (givenH.modPow(s2, givenP).multiply(givenV.modPow(s1, givenP)).mod(givenP));
        createMZ();
        return s1.compareTo(hashCodeMZ()) == 0;
    }
}
