package com.grenade.entity;

import com.grenade.item.ModItems;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/** A thrown grenade. Explodes at the exact spot it hits a block or an entity. */
public class GrenadeProjectile extends ThrowableItemProjectile {
	/** Explosion radius. TNT is 4.0. */
	private static final float EXPLOSION_POWER = 3.0F;

	public GrenadeProjectile(EntityType<? extends GrenadeProjectile> type, Level level) {
		super(type, level);
	}

	public GrenadeProjectile(Level level, LivingEntity owner) {
		super(ModEntities.GRENADE, owner, level);
	}

	@Override
	protected Item getDefaultItem() {
		// What the flying grenade looks like: the armed (pinless) model.
		return ModItems.GRENADE_PINLESS;
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);

		if (this.level().isClientSide) {
			return;
		}

		Vec3 pos = result.getLocation();
		if (result instanceof BlockHitResult blockHit) {
			// Nudge the blast out of the surface so it doesn't start buried in the block.
			Direction face = blockHit.getDirection();
			pos = pos.add(face.getStepX() * 0.1, face.getStepY() * 0.1, face.getStepZ() * 0.1);
		}

		this.level().explode(this, pos.x, pos.y, pos.z, EXPLOSION_POWER, Level.ExplosionInteraction.TNT);
		this.discard();
	}
}
