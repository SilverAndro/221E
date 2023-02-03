package io.github.silverandro._221e.impl.patches;

import io.github.silverandro._221e.api._221E;
import io.github.silverandro._221e.impl._221EMain;
import io.github.silverandro._221e.impl._221EMatchEngine;
import nilloader.api.ASMTransformer;
import nilloader.api.lib.asm.Opcodes;
import nilloader.api.lib.asm.tree.ClassNode;
import nilloader.api.lib.asm.tree.InsnList;
import nilloader.api.lib.asm.tree.MethodInsnNode;

import java.util.Objects;

public class _221EGetMcEnv implements ASMTransformer {
    private boolean didInit = false;

    @Override
    public boolean transform(ClassLoader classLoader, ClassNode classNode) {
        _221EMain.log.info("Hooking " + classNode.name + " as it seems to be the main class for this install");
        classNode.methods.forEach(methodNode -> {
            if (Objects.equals(methodNode.name, "<clinit>")) {
                _221EMain.log.info("Found clinit, patching...");
                InsnList instructions = methodNode.instructions;
                instructions.insertBefore(
                        instructions.getFirst(),
                        new MethodInsnNode(
                                Opcodes.INVOKESTATIC,
                                "io/github/silverandro/_221e/impl/patches/_221EGetMcEnv$Hooks",
                                "onClinit",
                                "()V"
                        )
                );
            }
        });
        _221EMain.log.info("Done!");
        didInit = true;
        return true;
    }

    @Override
    @SuppressWarnings("RedundantIfStatement")
    public boolean canTransform(ClassLoader loader, String className) {
        if (didInit) return false;
        if (className.contains("$")) return false;

        if (className.startsWith("net/minecraft/client/main/Main")) return true;
        if (className.startsWith("net/minecraft/client/MinecraftApplet")) return true;
        if (className.startsWith("com/mojang/minecraft/MinecraftApplet")) return true;
        if (className.startsWith("net/minecraft/server/Main")) return true;
        if (className.startsWith("com/mojang/minecraft/server/MinecraftServer")) return true;
        if (className.startsWith("net/minecraft/server/MinecraftServer")) return true;
        if (className.startsWith("net/minecraft/bundler/Main")) return true;
        return false;
    }

    @SuppressWarnings("unused")
    public static class Hooks {
        static final String LOADER = "loader";

        public static void onClinit() {
            determineLoader();
            _221EMain.log.info("Detected loader is: " + _221EMatchEngine.matchMap.get(LOADER));
        }

        public static void determineLoader() {

            _221EMain.log.info("Attempting to determine current loader...");

            // Determine the current loader, if any
            try {
                Class.forName("org.quiltmc.loader.api.QuiltLoader");
                _221E.registerMatchEngineParam(LOADER, "quilt");
                return;
            } catch (ClassNotFoundException e) {
                // Nothing
            }

            try {
                Class.forName("net.fabricmc.loader.api.FabricLoader");
                _221E.registerMatchEngineParam(LOADER, "fabric");
                return;
            } catch (ClassNotFoundException e) {
                // Nothing
            }

            _221E.registerMatchEngineParam(LOADER, "none");
        }
    }
}
