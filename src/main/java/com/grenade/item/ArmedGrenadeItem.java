package com.grenade.item;

import com.grenade.entity.GrenadeProjectile;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** The armed (pinless) grenade. Right-click throws it; it explodes where it hits. */
public class ArmedGrenadeItem extends Item implements HeldOut {
	public ArmedGrenadeItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		level.playSound(null, player.getX(), player.getY(), player.getZ(),
				SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.6F,
				0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

		if (!level.isClientSide) {
			GrenadeProjectile grenade = new GrenadeProjectile(level, player);
			// Same launch values as a vanilla splash potion: -20 degree upward offset, speed 0.5, spread 1.0.
			grenade.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);
			level.addFreshEntity(grenade);
		}

		if (!player.getAbilities().instabuild) {
			stack.shrink(1);
		}

		// Cooldown on the pinned grenade too, so you can't instantly arm the next one.
		player.getCooldowns().addCooldown(ModItems.GRENADE, 20);

		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}
}
