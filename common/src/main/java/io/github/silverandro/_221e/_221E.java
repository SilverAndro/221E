package io.github.silverandro._221e;

public class _221E {
    public static void registerMatchEngineParam(String id, String envValue) {
        MatchEngine.register(id, envValue);
    }

    public static void match(String matchString, Runnable code) {
        Util.noImpl();
    }
}
