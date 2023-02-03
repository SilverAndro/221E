package io.github.silverandro._221e.impl;

import io.github.silverandro._221e.impl.patches._221EGetMcEnv;
import io.github.silverandro._221e.impl.patches._221EPatcher;
import nilloader.api.ClassTransformer;
import nilloader.api.NilLogger;

@SuppressWarnings("unused")
public class _221EMain implements Runnable {
	public static final NilLogger log = NilLogger.get("221E");
	
	@Override
	public void run() {
		log.info("221E is live!");
		ClassTransformer.register(new _221EPatcher());
		ClassTransformer.register(new _221EGetMcEnv());
	}
}
