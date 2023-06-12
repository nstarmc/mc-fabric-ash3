package de.miraisoft.ash2.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.miraisoft.ash2.AshCommands;
import de.miraisoft.ash2.DirectionEnum;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;

@Mixin(InGameHud.class)
public class InGameHudMixin {

	// private static final Logger logger = LogManager.getLogger();

	private static final int COLOR_X = 0xddffdd;
	private static final int COLOR_Y = 0xffffdd;
	private static final int COLOR_Z = 0xddddff;

	private static final int PADDING = 5;
	private static final int TEXT_POS_X = PADDING;
	private int textPosY = PADDING;

	@Inject(at = @At("TAIL"), method = "render")
	public void render(DrawContext drawContext, float tickDelta, CallbackInfo info) {
		MinecraftClient client = MinecraftClient.getInstance();
		Entity cameraEntity = client.getCameraEntity();

		textPosY = PADDING;

		if (!client.options.debugEnabled && AshCommands.config.showHud) {
			drawFps(drawContext, client, cameraEntity);
			drawCoordsAndDirection(drawContext, client, cameraEntity);
			drawLightLevel(drawContext, client, cameraEntity);
			drawBiome(drawContext, client, cameraEntity);
			drawTime(drawContext, client, cameraEntity);
		}
	}

	private void drawFps(DrawContext drawContext, MinecraftClient client, Entity cameraEntity) {
		if (!AshCommands.config.showFps)
			return;
		String ashString = String.format("%d fps",
				((MinecraftClientMixin) MinecraftClient.getInstance()).getCurrentFps());

		drawContext.drawTextWithShadow(client.textRenderer, ashString, TEXT_POS_X, textPosY, AshCommands.config.hudColor);

		textPosY += client.textRenderer.fontHeight + 1;
	}

	private void drawCoordsAndDirection(DrawContext drawContext, MinecraftClient client, Entity cameraEntity) {
		if ((!AshCommands.config.showCoords && !AshCommands.config.showDirection) || client.hasReducedDebugInfo())
			return;
		float degrees = MathHelper.wrapDegrees(cameraEntity.getYaw());
		DirectionEnum directionEnum = DirectionEnum.getByYawDegrees(degrees);

		// show coordinates

		if (AshCommands.config.showCoords) {
			BlockPos blockPos =  cameraEntity.getBlockPos();
			if (AshCommands.config.conciseCoords) {
				String direction = "";
				if (AshCommands.config.showDirection) {
					direction += directionEnum.name();
				}
				String coordsText = String.format("%d / %d / %d  %s", blockPos.getX(), blockPos.getY(), blockPos.getZ(),
						direction);
				drawContext.drawTextWithShadow(client.textRenderer, coordsText, TEXT_POS_X, textPosY,
						AshCommands.config.hudColor);
			} else {
				String xText = String.format("x: %d", blockPos.getX());
				String yText = String.format("y: %d", blockPos.getY());
				String zText = String.format("z: %d", blockPos.getZ());

				xText += directionEnum.getX();
				zText += directionEnum.getZ();

				int heightDiff = client.textRenderer.fontHeight + 1;
				drawContext.drawTextWithShadow(client.textRenderer, xText, TEXT_POS_X, textPosY, COLOR_X);
				textPosY += heightDiff;
				drawContext.drawTextWithShadow(client.textRenderer, yText, TEXT_POS_X, textPosY, COLOR_Y);
				textPosY += heightDiff;
				drawContext.drawTextWithShadow(client.textRenderer, zText, TEXT_POS_X, textPosY, COLOR_Z);
			}
			textPosY += client.textRenderer.fontHeight + 1;
		}

		// show direction

		if (!AshCommands.config.showDirection || AshCommands.config.conciseCoords)
			return;
		
		String ashString = "Direction: " + directionEnum.longName;

		drawContext.drawTextWithShadow(client.textRenderer, ashString, TEXT_POS_X, textPosY, AshCommands.config.hudColor);

		textPosY += client.textRenderer.fontHeight + 1;
	}

	private void drawLightLevel(DrawContext drawContext, MinecraftClient client, Entity cameraEntity) {
		if (!AshCommands.config.showLightLevel)
			return;

		BlockPos blockPos =  cameraEntity.getBlockPos();
		int lightLevel = client.world.getLightLevel(LightType.BLOCK, blockPos);
		String ashString = "Light Level: " + lightLevel;

		int color = AshCommands.config.hudColor;
		if (lightLevel == 0) {
			color = 0xffaaaa;
		}

		int textPosX = client.getWindow().getScaledWidth() - client.textRenderer.getWidth(ashString) - 5;

		drawContext.drawTextWithShadow(client.textRenderer, ashString, textPosX, PADDING, color);
	}

	private void drawBiome(DrawContext drawContext, MinecraftClient client, Entity cameraEntity) {
		if (!AshCommands.config.showBiome)
			return;

		BlockPos blockPos =  cameraEntity.getBlockPos();
		String biomeKey = client.world.getBiome(blockPos).getKey().get().getValue().toTranslationKey("biome");
		String ashString = "Biome: " + Text.translatable(biomeKey).getString();

		drawContext.drawTextWithShadow(client.textRenderer, ashString, TEXT_POS_X, textPosY, AshCommands.config.hudColor);

		textPosY += client.textRenderer.fontHeight + 1;
	}

	private void drawTime(DrawContext drawContext, MinecraftClient client, Entity cameraEntity) {
		if (!AshCommands.config.showTime)
			return;
		
		// tick 0 and 24000 is 6:00 am
		int hour = (int) ((client.world.getTimeOfDay() / 1000d) + 6) % 24;
		int minutes = (int) ((client.world.getTimeOfDay() / 1000d * 60) % 60);
		String ashString;
		if (minutes <= 9) {
			ashString = String.format("Time: %d:0%d", hour, minutes);
		} else {
			ashString = String.format("Time: %d:%d", hour, minutes);
		}

		drawContext.drawTextWithShadow(client.textRenderer, ashString, TEXT_POS_X, textPosY, AshCommands.config.hudColor);

		textPosY += client.textRenderer.fontHeight + 1;
	}

}