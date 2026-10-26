package com.grenade.client.mixin;

import com.grenade.item.HeldOut;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Third-person arm pose: while a grenade is in the main hand, the arm is held out
 * in front of the player, and swings overhand when the grenade is thrown.
 * (First person is handled by the item model's "display" transforms.)
 */
@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin {
	@Shadow @Final public ModelPart head;
	@Shadow @Final public ModelPart rightArm;
	@Shadow @Final public ModelPart leftArm;

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
	private void grenade$holdOut(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
								 float netHeadYaw, float headPitch, CallbackInfo ci) {
		if (!(entity instanceof Player player)) {
			return;
		}
		if (!(player.getMainHandItem().getItem() instanceof HeldOut)) {
			return;
		}
		if (player.isFallFlying() || player.isSwimming() || player.isSleeping() || player.isPassenger()) {
			return;
		}

		boolean rightHanded = player.getMainArm() == HumanoidArm.RIGHT;
		ModelPart arm = rightHanded ? this.rightArm : this.leftArm;

		// Arm straight out in front, following where the player is looking.
		float ready = -1.45F + this.head.xRot * 0.8F;
		float armX = ready;

		// Overhand throw: wind back, whip forward, settle. 'swing' runs 0 -> 1 during the hand swing.
		float swing = player.getAttackAnim(ageInTicks - Mth.floor(ageInTicks));
		if (swing > 0.0F) {
			if (swing < 0.3F) {
				armX = Mth.lerp(swing / 0.3F, ready, -2.9F);
			} else if (swing < 0.6F) {
				armX = Mth.lerp((swing - 0.3F) / 0.3F, -2.9F, -0.6F);
			} else {
				armX = Mth.lerp((swing - 0.6F) / 0.4F, -0.6F, ready);
			}
		}

		arm.xRot = armX;
		// Angle the arm slightly toward the middle of the body.
		arm.yRot = rightHanded ? -0.15F : 0.15F;
	}
}
