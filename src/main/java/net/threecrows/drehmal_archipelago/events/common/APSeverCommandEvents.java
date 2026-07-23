package net.threecrows.drehmal_archipelago.events.common;

import com.mojang.brigadier.arguments.StringArgumentType;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.threecrows.drehmal_archipelago.archipelago.Archipelago;
import net.threecrows.drehmal_archipelago.common.world.APPersistentState;
import net.threecrows.drehmal_archipelago.events.common.commands.APConnectionCommands;
import net.threecrows.drehmal_archipelago.events.common.commands.APDeathlinkCommand;
import net.threecrows.drehmal_archipelago.events.common.commands.APTextClientCommands;
import net.threecrows.drehmal_archipelago.init.APDamageTypes;
import net.threecrows.drehmal_archipelago.util.APServerUtil;

public class APSeverCommandEvents {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            APConnectionCommands.connectionCommands(dispatcher);
            APTextClientCommands.textClientCommands(dispatcher);
            APDeathlinkCommand.deathlinkCommand(dispatcher);

            // Say Command (used for commands that aren't covered by what I added or for sending general messages)
            dispatcher.register(CommandManager.literal("archipelago").then(CommandManager.literal("say")
                    .then(CommandManager.argument("message", StringArgumentType.string())
                            .executes(context -> Archipelago.runCommand(archipelago -> {
                                archipelago.sendChat(StringArgumentType.getString(context, "message"));
                            }, () -> {
                                APServerUtil.sendMessage(Text.translatable("drehmal_archipelago.connection.no_connection"));
                            }))
                    )
            ));

            // Command for granting or revoking checks, only enabled in the Dev Environment
            if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
                dispatcher.register(CommandManager.literal("apDebug")
                        .then(CommandManager.argument("check", StringArgumentType.string())
                                .executes(context -> {
                                    APPersistentState.get().triggerCheck(StringArgumentType.getString(context, "check"));
                                    return 1;
                                })
                        )
                );
            }
        });
    }
}
