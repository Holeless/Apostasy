package com.grenade;

import com.grenade.entity.ModEntities;
import com.grenade.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;

public class GrenadeMod implements ModInitializer {
	public static final String MOD_ID = "grenade";

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		ModItems.init();
		ModEntities.init();

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> {
			entries.accept(ModItems.GRENADE);
			entries.accept(ModItems.GRENADE_PINLESS);
		});
	}
}
