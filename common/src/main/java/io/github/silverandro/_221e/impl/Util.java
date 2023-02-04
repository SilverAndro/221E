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
}
