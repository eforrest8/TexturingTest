package com.sgolc.utils;

import org.apache.commons.math3.special.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

/**
 * Several methods in this file are implementations of tests described in
 * "A Statistical Test Suite for Random and Pseudorandom Number Generators for Cryptographic Applications"
 * by the NIST.
 */
class BadHashTest {

    private static List<Long> generateHashes() {
        List<Long> generated = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            generated.add(BadHash.hash(i));
        }
        return generated;
    }

    private static LongStream instanceHashStream() {
        BadHash hasher = new BadHash(0);
        return LongStream.generate(hasher::nextLong);
    }

    @Test
    void hashFirst100NoSeed() {
        //System.out.println(generateHashes());
        Assertions.assertTrue(true);
    }

    @Test
    void nistStatisticalTests() {
        double threshold = 0.01;
        StringBuilder bitString = new StringBuilder();
        for (long l : instanceHashStream().limit(100).toArray()) {
            bitString.append(Long.toBinaryString(l));
        }
        String bits = bitString.toString();
        double actualFrequency = nistFrequency(bits);
        Assertions.assertTrue(actualFrequency >= threshold,
                "Frequency test failed.\n" +
                "P-value was " + actualFrequency + " which did not meet the threshold of " + threshold);
        System.out.println("Frequency test: P-value was: " + actualFrequency);
        double actualBlockFrequency = nistBlockFrequency(bits);
        Assertions.assertTrue(actualBlockFrequency >= threshold,
                "Block frequency test failed.\n" +
                        "P-value was " + actualBlockFrequency + " which did not meet the threshold of " + threshold);
        System.out.println("Block frequency test: P-value was: " + actualBlockFrequency);
    }

    private static double nistFrequency(String epsilon) {
        int n = epsilon.length();
        int Sn = Arrays.stream(epsilon.split("")).map(s -> s.equals("1") ? 1 : -1).reduce(0, Integer::sum);
        double Sobs = Math.abs(Sn) / Math.sqrt(n);
        return Erf.erfc(Sobs / Math.sqrt(2));
    }

    private static double nistBlockFrequency(String epsilon) {
        int M = 64;
        int n = epsilon.length();
        String[] split = new String[n/M];
        double[] pii = new double[split.length];
        for (int i = 0; i < split.length; i++) {
            try {
                split[i] = epsilon.substring(i * M, (i+1) * M);
                pii[i] = split[i].replace("0", "").length() / (double) M;
            } catch (StringIndexOutOfBoundsException ignored) {}
        }
        double chi = 4*M*Arrays.stream(pii).reduce(0, (acc, v) -> acc + Math.pow(v-0.5, 2));
        return Gamma.regularizedGammaP(split.length/2d, chi/2d);
    }
}