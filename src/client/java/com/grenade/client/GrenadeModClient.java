package com.grenade.client;

import com.grenade.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class GrenadeModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Draws the flying grenade using the item's model (the armed one).
		EntityRendererRegistry.register(ModEntities.GRENADE, ThrownItemRenderer::new);
	}
}
