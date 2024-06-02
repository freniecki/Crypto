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

    public Signature(BigInteger s1, BigInteger s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public BigInteger getS1() {
        return s1;
    }
    public BigInteger getS2() {
        return s2;
    }
}
