package io.github.silverandro._221e.nil;

import io.github.silverandro._221e.nil.patches.QuiltEnvLoader;
import io.github.silverandro._221e.nil.patches._221EPatcher;
import nilloader.api.ClassTransformer;
import nilloader.api.ModRemapper;
import nilloader.api.NilLogger;

@SuppressWarnings("unused")
public class _221EMain implements Runnable {
	public static final NilLogger log = NilLogger.get("221E");
	
	@Override
	public void run() {
		log.info("221E is live!");
		ModRemapper.setTargetMapping(null);
		ClassTransformer.register(new _221EPatcher());
		ClassTransformer.register(new QuiltEnvLoader());
	}
}
