package io.github.silverandro._221e.impl.patches;

import nilloader.api.ClassTransformer;
import nilloader.api.lib.asm.ClassReader;
import nilloader.api.lib.asm.Opcodes;
import nilloader.api.lib.asm.tree.ClassNode;

public class _221EPatcher implements ClassTransformer {
	@Override
	public byte[] transform(String className, byte[] originalData) {
		if (className.startsWith("java")) return originalData;

		ClassReader reader = new ClassReader(originalData);

		ClassNode node = new ClassNode();
		reader.accept(node, ClassReader.SKIP_DEBUG);

		node.methods.forEach(methodNode -> {
			methodNode.instructions.forEach(abstractInsnNode -> {
				if (abstractInsnNode.getOpcode() == Opcodes.INVOKESTATIC) {

				}
			});
		});

		return new byte[0];
	}
}
