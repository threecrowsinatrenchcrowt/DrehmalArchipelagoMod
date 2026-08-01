package net.threecrows.drehmal_archipelago;


import net.deadlydiamond98.koalalib.config.KoalaConfigCreator;
import net.deadlydiamond98.koalalib.updater.KoalaUpdateChecker;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.threecrows.drehmal_archipelago.archipelago.ArchipelagoServerConnector;
import net.threecrows.drehmal_archipelago.archipelago.items.SavedArchipelagoItems;
import net.threecrows.drehmal_archipelago.archipelago.items.dataloader.APItemDataLoader;
import net.threecrows.drehmal_archipelago.events.common.*;
import net.threecrows.drehmal_archipelago.init.APAdvancements;
import net.threecrows.drehmal_archipelago.init.APEffects;
import net.threecrows.drehmal_archipelago.init.APItems;
import net.threecrows.drehmal_archipelago.init.APSounds;
import net.threecrows.drehmal_archipelago.networking.APNetworking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class APMod implements ModInitializer {
	public static final String MOD_ID = "drehmal_archipelago";
	public static final String VALID_WORLD_VERSION = "1.0.2";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		KoalaConfigCreator.addModConfig(MOD_ID, APModConfigs.Main.class);
		KoalaUpdateChecker.addModUpdateChecker(MOD_ID);
		ArchipelagoServerConnector.readLastConnectedServer();

		// Register Persistent State Items
		SavedArchipelagoItems.register();

		// Registry
		APItems.register();
		APEffects.register();
		APAdvancements.register();
		APSounds.register();
		APNetworking.registerC2SReceivers();

		// Events
		APServerWorldEvents.register();
		APSeverCommandEvents.register();
		APServerChatEvents.register();
		APServerPlayConnectionEvents.register();
		APServerTickEvents.register();
		APDeathEvents.register();
		APPlayerDeathEvents.register();

		// Data Pack Loader
		APItemDataLoader.register();
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

	public static boolean isModLoaded(String modid) {
		return FabricLoader.getInstance().isModLoaded(modid);
	}
}