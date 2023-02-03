package io.github.silverandro._221e.impl.patches;

import nilloader.api.ASMTransformer;
import nilloader.api.lib.asm.Opcodes;
import nilloader.api.lib.asm.tree.ClassNode;

public class _221EPatcher implements ASMTransformer {
	@Override
	public boolean transform(ClassLoader classLoader, ClassNode node) {
		node.methods.forEach(methodNode -> {
			methodNode.instructions.forEach(abstractInsnNode -> {
				if (abstractInsnNode.getOpcode() == Opcodes.INVOKESTATIC) {

				}
			});
		});

		return false;
	}

	@Override
	public boolean canTransform(ClassLoader loader, String className) {
		return !className.startsWith("java");
	}
}
