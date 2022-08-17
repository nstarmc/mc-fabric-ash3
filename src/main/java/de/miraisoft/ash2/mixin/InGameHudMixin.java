package de.miraisoft.ash2.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.miraisoft.ash2.AshCommands;
import de.miraisoft.ash2.DirectionEnum;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
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

	private static final float PADDING = 5;
	private static final float TEXT_POS_X = PADDING;
	private float textPosY = PADDING;

	@Inject(at = @At("TAIL"), method = "render")
	public void render(MatrixStack matrixStack, float tickDelta, CallbackInfo info) {
		MinecraftClient client = MinecraftClient.getInstance();
		Entity cameraEntity = client.getCameraEntity();

		textPosY = PADDING;

		if (!client.options.debugEnabled && AshCommands.config.showHud) {
			drawFps(matrixStack, client, cameraEntity);
			drawCoords(matrixStack, client, cameraEntity);
			drawDirection(matrixStack, client, cameraEntity);
			drawLightLevel(matrixStack, client, cameraEntity);
			drawBiome(matrixStack, client, cameraEntity);
		}
	}

	private void drawFps(MatrixStack matrixStack, MinecraftClient client, Entity cameraEntity) {
		if (!AshCommands.config.showFps)
			return;
		matrixStack.push();
		String ashString = String.format("%d fps",
				((MinecraftClientMixin) MinecraftClient.getInstance()).getCurrentFps());

		client.textRenderer.drawWithShadow(matrixStack, ashString, TEXT_POS_X, textPosY, AshCommands.config.hudColor);
		matrixStack.pop();

		textPosY += client.textRenderer.fontHeight + 1;
	}

	private void drawCoords(MatrixStack matrixStack, MinecraftClient client, Entity cameraEntity) {
		if (!AshCommands.config.showCoords || client.hasReducedDebugInfo())
			return;
		matrixStack.push();
		BlockPos blockPos = new BlockPos(cameraEntity.getX(), cameraEntity.getBoundingBox().getMin(Direction.Axis.Y),
				cameraEntity.getZ());
		if (AshCommands.config.conciseCoords) {
			String direction = "";
			if (AshCommands.config.showDirection) {
				float degrees = MathHelper.wrapDegrees(cameraEntity.getYaw());
				DirectionEnum directionEnum = DirectionEnum.getByYawDegrees(degrees);
				direction += directionEnum.name();
			}
			String coordsText = String.format("%d / %d / %d  %s", blockPos.getX(), blockPos.getY(), blockPos.getZ(), direction);
			client.textRenderer.drawWithShadow(matrixStack, coordsText, TEXT_POS_X, textPosY,
					AshCommands.config.hudColor);
		} else {
			String xText = String.format("x: %d", blockPos.getX());
			String yText = String.format("y: %d", blockPos.getY());
			String zText = String.format("z: %d", blockPos.getZ());

			Direction direction = cameraEntity.getHorizontalFacing();
			if (Direction.EAST.equals(direction)) {
				xText += " +";
			} else if (Direction.WEST.equals(direction)) {
				xText += " -";
			} else if (Direction.SOUTH.equals(direction)) {
				zText += " +";
			} else if (Direction.NORTH.equals(direction)) {
				zText += " -";
			}

			int heightDiff = client.textRenderer.fontHeight + 1;
			client.textRenderer.drawWithShadow(matrixStack, xText, TEXT_POS_X, textPosY, COLOR_X);
			textPosY += heightDiff;
			client.textRenderer.drawWithShadow(matrixStack, yText, TEXT_POS_X, textPosY, COLOR_Y);
			textPosY += heightDiff;
			client.textRenderer.drawWithShadow(matrixStack, zText, TEXT_POS_X, textPosY, COLOR_Z);
		}
		matrixStack.pop();

		textPosY += client.textRenderer.fontHeight + 1;
	}

	private void drawDirection(MatrixStack matrixStack, MinecraftClient client, Entity cameraEntity) {
		if (!AshCommands.config.showDirection || AshCommands.config.conciseCoords)
			return;
		matrixStack.push();
		float degrees = MathHelper.wrapDegrees(cameraEntity.getYaw());
		DirectionEnum directionEnum = DirectionEnum.getByYawDegrees(degrees);
		String ashString = "Direction: " + directionEnum.longName;

		client.textRenderer.drawWithShadow(matrixStack, ashString, TEXT_POS_X, textPosY, AshCommands.config.hudColor);
		matrixStack.pop();

		textPosY += client.textRenderer.fontHeight + 1;
	}

	private void drawLightLevel(MatrixStack matrixStack, MinecraftClient client, Entity cameraEntity) {
		if (!AshCommands.config.showLightLevel)
			return;
		matrixStack.push();

		BlockPos blockPos = new BlockPos(cameraEntity.getX(), cameraEntity.getBoundingBox().getMin(Direction.Axis.Y),
				cameraEntity.getZ());
		int lightLevel = client.world.getLightLevel(LightType.BLOCK, blockPos);
		String ashString = "Light Level: " + lightLevel;

		int color = AshCommands.config.hudColor;
		if (lightLevel == 0) {
			color = 0xffaaaa;
		}

		float textPosX = client.getWindow().getScaledWidth() - client.textRenderer.getWidth(ashString) - 5;

		client.textRenderer.drawWithShadow(matrixStack, ashString, textPosX, PADDING, color);
		matrixStack.pop();
	}

	private void drawBiome(MatrixStack matrixStack, MinecraftClient client, Entity cameraEntity) {
		if (!AshCommands.config.showBiome)
			return;
		matrixStack.push();

		BlockPos blockPos = new BlockPos(cameraEntity.getX(), cameraEntity.getBoundingBox().getMin(Direction.Axis.Y),
				cameraEntity.getZ());
		String biomeKey = client.world.getBiome(blockPos).getKey().get().getValue().toTranslationKey("biome");
		String ashString = "Biome: " + Text.translatable(biomeKey).getString();

		client.textRenderer.drawWithShadow(matrixStack, ashString, TEXT_POS_X, textPosY, AshCommands.config.hudColor);
		matrixStack.pop();

		textPosY += client.textRenderer.fontHeight + 1;
	}

}