package io.github.silverandro._221e.impl;

import io.github.silverandro._221e.impl.patches._221EPatcher;
import nilloader.api.ClassTransformer;
import nilloader.api.NilLogger;

@SuppressWarnings("unused")
public class _221EMain implements Runnable {
	public static final NilLogger log = NilLogger.get("221E");
	
	@Override
	public void run() {
		log.info("Hello from premain!");
		ClassTransformer.register(new _221EPatcher());
	}
}
