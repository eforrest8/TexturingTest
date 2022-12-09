package com.sgolc.utils;

import java.util.random.RandomGenerator;

public class BadHash implements RandomGenerator.ArbitrarilyJumpableGenerator {
    public static long hash(long t, long seed) {
        long PRIME_A = 0xED52C7C96A30F583L;
        long PRIME_B = 0xC664F4C404E3F683L;
        long PRIME_C = 0xF09DD526511F9DC3L;

        long result = t ^ (t >> 32);
        result *= PRIME_A;
        result = result ^ (result >> 32);
        result += seed;
        result *= PRIME_B;
        result = result ^ (result >> 32);
        result *= PRIME_C;
        return result;
    }

    public static long hash(long t) {
        return hash(t, 0);
    }

    private long step = 0;
    private double stride = 1;
    private long seed = 0;

    public BadHash(long seed) {
        this.seed = seed;
    }

    @Override
    public ArbitrarilyJumpableGenerator copy() {
        return null;
    }

    @Override
    public double jumpDistance() {
        return stride;
    }

    @Override
    public double leapDistance() {
        return stride;
    }

    @Override
    public void jumpPowerOfTwo(int logDistance) {
        stride = Math.scalb(2, logDistance);
    }

    @Override
    public void jump(double distance) {
        stride = distance;
    }

    @Override
    public long nextLong() {
        long result = hash(step, seed);
        step += Double.doubleToLongBits(stride);
        return result;
    }
}
