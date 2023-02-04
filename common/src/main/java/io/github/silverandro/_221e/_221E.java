package io.github.silverandro._221e;

import io.github.silverandro._221e.impl.MatchEngine;
import io.github.silverandro._221e.impl.Util;

public class _221E {
    private _221E() {
        Util.noImpl();
    }

    public static void registerMatchEngineParam(String id, String envValue) {
        MatchEngine.register(id, envValue);
    }

    public static void match(String matchString, Runnable code) {
        Util.noImpl();
    }

    public static void guardStart(String matchString) {
        Util.noImpl();
    }

    public static void guardEnd() {
        Util.noImpl();
    }
}
