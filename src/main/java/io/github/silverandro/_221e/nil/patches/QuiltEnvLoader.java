package io.github.silverandro._221e.nil.patches;

import nilloader.api.lib.asm.Opcodes;
import nilloader.api.lib.asm.tree.FieldInsnNode;
import nilloader.api.lib.asm.tree.IntInsnNode;
import nilloader.api.lib.asm.tree.LdcInsnNode;
import nilloader.api.lib.asm.tree.MethodInsnNode;
import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("org.quiltmc.loader.impl.game.minecraft.MinecraftGameProvider")
public class QuiltEnvLoader extends MiniTransformer {
    @Patch.Method("locateGame(Lorg.quiltmc.loader.impl.launch.common.QuiltLauncher;[Ljava.lang.String;)Z")
    public void exfilGameVersion(PatchContext ctx) {
        ctx.jumpToLastReturn();
        ctx.add(
                // Loader is quilt
                new LdcInsnNode("loader"),
                new LdcInsnNode("quilt"),
                new MethodInsnNode(Opcodes.INVOKESTATIC,
                        "io/github/silverandro/_221e/api/_221E",
                        "registerMatchEngineParam",
                        "(Ljava/lang/String;Ljava/lang/String;)V"
                ),
                new FieldInsnNode(Opcodes.GETSTATIC,
                        "io/github/silverandro/_221e/nil/_221EMain",
                        "log",
                        "Lnilloader/api/NilLogger;"
                ),
                new LdcInsnNode("Got game loader: quilt"),
                new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                        "nilloader/api/NilLogger",
                        "info",
                        "(Ljava/lang/String;)V"
                ),

                // Extract
                new LdcInsnNode("version"),
                new IntInsnNode(Opcodes.ALOAD, 0),
                new FieldInsnNode(Opcodes.GETFIELD,
                        "org/quiltmc/loader/impl/game/minecraft/MinecraftGameProvider",
                        "versionData",
                        "Lorg/quiltmc/loader/impl/game/minecraft/McVersion;"
                ),
                new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                        "org/quiltmc/loader/impl/game/minecraft/McVersion",
                        "getNormalized",
                        "()Ljava/lang/String;"
                ),
                new MethodInsnNode(Opcodes.INVOKESTATIC,
                        "io/github/silverandro/_221e/api/_221E",
                        "registerMatchEngineParam",
                        "(Ljava/lang/String;Ljava/lang/String;)V"
                ),

                // Log
                new FieldInsnNode(Opcodes.GETSTATIC,
                        "io/github/silverandro/_221e/nil/_221EMain",
                        "log",
                        "Lnilloader/api/NilLogger;"
                ),
                new LdcInsnNode("Extracted game version from quilt loader: "),
                new IntInsnNode(Opcodes.ALOAD, 0),
                new FieldInsnNode(Opcodes.GETFIELD,
                        "org/quiltmc/loader/impl/game/minecraft/MinecraftGameProvider",
                        "versionData",
                        "Lorg/quiltmc/loader/impl/game/minecraft/McVersion;"
                ),
                new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                        "org/quiltmc/loader/impl/game/minecraft/McVersion",
                        "getNormalized",
                        "()Ljava/lang/String;"
                ),
                new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                        "java/lang/String",
                        "concat",
                        "(Ljava/lang/String;)Ljava/lang/String;"
                ),
                new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                        "nilloader/api/NilLogger",
                        "info",
                        "(Ljava/lang/String;)V"
                )
        );
    }
}
