package org.example;

import java.io.Serializable;
import java.math.BigInteger;

/**
 Representation of signature with a separated object.
 Needed for serialisation
 **/

public class Signature implements Serializable {
    private final BigInteger s1;
    private final BigInteger s2;
    private final BigInteger H;
    private final BigInteger V;
    private final BigInteger P;

    public Signature(BigInteger s1, BigInteger s2, BigInteger h, BigInteger v, BigInteger p) {
        this.s1 = s1;
        this.s2 = s2;
        this.H = h;
        this.V = v;
        this.P = p;
    }

    public BigInteger getS1() {
        return s1;
    }
    public BigInteger getS2() {
        return s2;
    }
    public BigInteger getH() {
        return H;
    }
    public BigInteger getV() {
        return V;
    }
    public BigInteger getP() {
        return P;
    }
}
