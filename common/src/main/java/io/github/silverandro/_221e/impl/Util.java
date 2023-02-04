package io.github.silverandro._221e.impl;

public class Util {
    private Util() {
        noImpl();
    }

    public static void noImpl() {
        throw new AbstractMethodError("This does not have an implementation");
    }

    public static void badArg(String msg) {
        throw new IllegalArgumentException(msg);
    }

    public static void tooComplex(String msg) {
        throw new IllegalStateException("Too complex: " + msg);
    }

    public static void badFormat(String msg) {
        throw new IllegalStateException("Bad format: " + msg);
    }
}
