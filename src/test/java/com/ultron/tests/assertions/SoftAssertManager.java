package com.ultron.tests.assertions;

import org.testng.asserts.SoftAssert;

public class SoftAssertManager {

    private static final ThreadLocal<SoftAssert> softAssert = new ThreadLocal<>();

    private SoftAssertManager() {
    }

    public static void init() {
        softAssert.set(new SoftAssert());
    }

    public static SoftAssert get() {
        return softAssert.get();
    }

    public static void assertAll() {
        if (softAssert.get() != null) {
            softAssert.get().assertAll();
            softAssert.remove();
        }
    }
}