package io.github.silverandro._221e.nil.patches;

import io.github.silverandro._221e.impl.MatchEngine;
import io.github.silverandro._221e.impl.Util;
import io.github.silverandro._221e.nil._221EMain;
import nilloader.api.ASMTransformer;
import nilloader.api.lib.asm.ClassWriter;
import nilloader.api.lib.asm.Opcodes;
import nilloader.api.lib.asm.tree.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;

public class _221EPatcher implements ASMTransformer {
    @Override
    public boolean transform(ClassLoader classLoader, ClassNode node) {
        AtomicBoolean didModify = new AtomicBoolean(false);

        node.methods.forEach(methodNode -> {
			AtomicBoolean shouldDrop = new AtomicBoolean(false);
			AtomicBoolean isInGuard = new AtomicBoolean(false);
			InsnList newInstructions = new InsnList();

            methodNode.instructions.forEach(abstractInsnNode -> {
                launderInsnNode(abstractInsnNode);

                if (abstractInsnNode.getOpcode() == Opcodes.INVOKESTATIC) {
                    MethodInsnNode invocation = (MethodInsnNode) abstractInsnNode;
                    if (invocation.owner.equals("io/github/silverandro/_221e/_221E")) {
                        didModify.set(true);

                        if (invocation.name.equals("guardStart")) {
                            isInGuard.set(true);
                            AbstractInsnNode last = newInstructions.getLast();
                            newInstructions.remove(last);
                            if (last instanceof LdcInsnNode && ((LdcInsnNode)last).cst instanceof String) {
                                _221EMain.log.info("Found match string: " + ((LdcInsnNode) last).cst);
                                if (!MatchEngine.isMatch((String) ((LdcInsnNode)last).cst)) {
                                    shouldDrop.set(true);
                                }
                            } else {
                                Util.tooComplex("Non-trivial match string");
                            }
                        } else if (invocation.name.equals("guardEnd")) {
                            if (!isInGuard.get()) {
                                Util.badFormat("Guard end without opening guard");
                            }
                            shouldDrop.set(false);
                            isInGuard.set(false);
                        }
                    } else {
                        if (!shouldDrop.get()) {
                            if (((MethodInsnNode)abstractInsnNode).name.contains("guard")) {
                                Util.badFormat("Guard leak!");
                            }
                            newInstructions.add(abstractInsnNode);
                        }
                    }
                } else {
                    if (!shouldDrop.get()) {
                        if (abstractInsnNode instanceof MethodInsnNode && ((MethodInsnNode)abstractInsnNode).name.contains("guard")) {
                            Util.badFormat("Guard leak!");
                        }
                        newInstructions.add(abstractInsnNode);
                    }
                }
            });
            if (didModify.get()) {
                newInstructions.forEach(insnNode -> {
                    if (insnNode instanceof MethodInsnNode && ((MethodInsnNode) insnNode).name.contains("guard")) {
                        Util.badFormat("Guard leak!");
                    }
                });
            }
            methodNode.instructions = newInstructions;
        });

        if (didModify.get()) {
            dumpToFile(node);
        }

        return didModify.get();
    }

    @Override
    public boolean canTransform(ClassLoader loader, String className) {
        return !className.startsWith("java");
    }

    private void dumpToFile(ClassNode node) {
        _221EMain.log.info("Dumping...");
        File output = new File(node.name.replace("/", "_") + ".class");
        try {
            //noinspection ResultOfMethodCallIgnored
            output.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ClassWriter writer = new ClassWriter(0);
        node.accept(writer);
        try {
            //noinspection resource
            new FileOutputStream(output).write(writer.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void launderInsnNode(AbstractInsnNode node) {
        try {
            Field prevF = AbstractInsnNode.class.getDeclaredField("previousInsn");
            Field nextF = AbstractInsnNode.class.getDeclaredField("nextInsn");
            prevF.setAccessible(true);
            nextF.setAccessible(true);
            prevF.set(node, null);
            nextF.set(node, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
