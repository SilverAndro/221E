package io.github.silverandro._221e.nil.patches;

import io.github.silverandro._221e.nil._221EMain;
import nilloader.api.ASMTransformer;
import nilloader.api.lib.asm.Opcodes;
import nilloader.api.lib.asm.tree.ClassNode;
import nilloader.api.lib.asm.tree.MethodInsnNode;

public class _221EPatcher implements ASMTransformer {
	@Override
	public boolean transform(ClassLoader classLoader, ClassNode node) {
		node.methods.forEach(methodNode -> methodNode.instructions.forEach(abstractInsnNode -> {
			if (abstractInsnNode.getOpcode() == Opcodes.INVOKESTATIC) {
				MethodInsnNode invocation = (MethodInsnNode)abstractInsnNode;
				_221EMain.log.info(invocation.desc);
			}
		}));

		return false;
	}

	@Override
	public boolean canTransform(ClassLoader loader, String className) {
		return !className.startsWith("java");
	}
}
