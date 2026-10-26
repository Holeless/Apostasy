package com.grenade.entity;

import com.grenade.GrenadeMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class ModEntities {
	public static final EntityType<GrenadeProjectile> GRENADE = Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			GrenadeMod.id("grenade"),
			EntityType.Builder.<GrenadeProjectile>of(GrenadeProjectile::new, MobCategory.MISC)
					.sized(0.25F, 0.25F)
					.clientTrackingRange(4)
					.updateInterval(10)
					.build("grenade"));

	/** Forces this class to load so the entity above gets registered. */
	public static void init() {
	}

	private ModEntities() {
	}
}
