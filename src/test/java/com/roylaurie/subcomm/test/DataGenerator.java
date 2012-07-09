package com.roylaurie.subcomm.test;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class DataGenerator {
    private static final SecureRandom mRandom = new SecureRandom();
    
    /**
     * Makes random 32-character dummy data.
     * @return String
     */
    public static String generate() {
        return new BigInteger(130, mRandom).toString(32);
    }
}
