package com.grenade.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** The pinned grenade. Right-click pulls the pin and turns it into an armed grenade. */
public class GrenadeItem extends Item implements HeldOut {
	public GrenadeItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (!level.isClientSide) {
			level.playSound(null, player.getX(), player.getY(), player.getZ(),
					SoundEvents.TRIPWIRE_CLICK_ON, SoundSource.PLAYERS, 1.0F, 1.6F);

			ItemStack armed = new ItemStack(ModItems.GRENADE_PINLESS);
			if (stack.getCount() == 1) {
				// Swap the single grenade in hand for the armed one.
				stack = armed;
			} else {
				stack.shrink(1);
				if (!player.getInventory().add(armed)) {
					player.drop(armed, false);
				}
			}

			popPin(level, player);

			// Small delay so a fast double-click can't pull the pin and throw in the same instant.
			player.getCooldowns().addCooldown(ModItems.GRENADE_PINLESS, 10);
		}

		return InteractionResultHolder.consume(stack);
	}

	/** Flicks the pin out of the grenade as a real item entity that arcs up and falls to the ground. */
	private static void popPin(Level level, Player player) {
		Vec3 look = player.getLookAngle();
		Vec3 start = player.getEyePosition().add(look.scale(0.5)).add(0.0, -0.35, 0.0);

		ItemEntity pin = new ItemEntity(level, start.x, start.y, start.z, new ItemStack(ModItems.GRENADE_PIN));
		RandomSource random = level.getRandom();
		pin.setDeltaMovement(
				look.x * 0.1 + (random.nextDouble() - 0.5) * 0.12,
				0.25 + random.nextDouble() * 0.05,
				look.z * 0.1 + (random.nextDouble() - 0.5) * 0.12);
		pin.setPickUpDelay(30);
		level.addFreshEntity(pin);
	}
}
