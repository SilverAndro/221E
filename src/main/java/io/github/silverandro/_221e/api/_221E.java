package io.github.silverandro._221e.api;

import io.github.silverandro._221e.impl._221EMatchEngine;
import io.github.silverandro._221e.impl._221EUtil;

public class _221E {
    public static void registerMatchEngineParam(String id, String envValue) {
        String result = _221EMatchEngine.matchMap.put(id, envValue);
        if (result != null) {
            _221EUtil.badArg("Cannot re-register env value for " + id);
        }
    }

    public static void match(String matchString, Runnable code) {
        _221EUtil.noImpl();
    }
}
