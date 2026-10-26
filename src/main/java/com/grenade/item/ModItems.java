package com.grenade.item;

import com.grenade.GrenadeMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public final class ModItems {
	/** The grenade with its pin in. Right-click to pull the pin. */
	public static final Item GRENADE = register("grenade", new GrenadeItem(new Item.Properties().stacksTo(16)));
	/** The armed grenade. Right-click to throw it. */
	public static final Item GRENADE_PINLESS = register("grenade_pinless", new ArmedGrenadeItem(new Item.Properties().stacksTo(16)));
	/** The pin that pops out when the grenade is armed. */
	public static final Item GRENADE_PIN = register("grenade_pin", new Item(new Item.Properties()));

	private static Item register(String name, Item item) {
		return Registry.register(BuiltInRegistries.ITEM, GrenadeMod.id(name), item);
	}

	/** Forces this class to load so the items above get registered. */
	public static void init() {
	}

	private ModItems() {
	}
}
