package com.grenade.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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
				giveOrDrop(player, armed);
			}

			// The pin pops out.
			giveOrDrop(player, new ItemStack(ModItems.GRENADE_PIN));

			// Small delay so a fast double-click can't pull the pin and throw in the same instant.
			player.getCooldowns().addCooldown(ModItems.GRENADE_PINLESS, 10);
		}

		return InteractionResultHolder.consume(stack);
	}

	private static void giveOrDrop(Player player, ItemStack stack) {
		if (!player.getInventory().add(stack)) {
			player.drop(stack, false);
		}
	}
}
