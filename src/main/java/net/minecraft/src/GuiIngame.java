package net.minecraft.src;

import java.awt.Color;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

// Spout Start
import org.lwjgl.opengl.GL11;
import org.spoutcraft.client.SpoutClient;
import org.spoutcraft.client.chunkcache.ChunkNetCache;
import org.spoutcraft.client.config.Configuration;
import org.spoutcraft.client.gui.minimap.ZanMinimap;
import org.spoutcraft.api.Spoutcraft;
import org.spoutcraft.api.gui.ChatTextBox;
import org.spoutcraft.api.gui.InGameHUD;
import org.spoutcraft.api.gui.ServerPlayerList;
import org.spoutcraft.api.player.ChatMessage;
// Spout End


public class GuiIngame extends Gui {
	private static final RenderItem itemRenderer = new RenderItem();
	public static final Random rand = new Random(); // Spout private -> public static final
	private final Minecraft mc;

	/** ChatGUI instance that retains all previous chat data */
	private final GuiNewChat persistantChatGUI;
	private int updateCounter = 0;

	/** The string specifying which record music is playing */
	private String recordPlaying = "";

	/** How many ticks the record playing message will be displayed */
	private int recordPlayingUpFor = 0;
	private boolean recordIsPlaying = false;

	/** Previous frame vignette brightness (slowly changes by 1% each frame) */
	public float prevVignetteBrightness = 1.0F;

	public GuiIngame(Minecraft par1Minecraft) {
		this.mc = par1Minecraft;
		this.persistantChatGUI = new GuiNewChat(par1Minecraft);
	}

	/**
	 * Render the ingame overlay with quick icon bar, ...
	 */
	// Spout Start
	// TODO Rewrite again, it's in a horrible state, i'm surprised it works...
	// Most of function rewritten
	public void renderGameOverlay(float par1, boolean par2, int par3, int par4) {

		InGameHUD mainScreen = SpoutClient.getInstance().getActivePlayer().getMainScreen();

		ScaledResolution scaledRes = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
		int screenWidth = scaledRes.getScaledWidth();
		int screenHeight = scaledRes.getScaledHeight();
		FontRenderer font = this.mc.fontRenderer;
		this.mc.entityRenderer.setupOverlayRendering();
		GL11.glEnable(GL11.GL_BLEND);

		if (Minecraft.isFancyGraphicsEnabled()) {
			this.renderVignette(this.mc.thePlayer.getBrightness(par1), var6, var7);
		} else {
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}

		ItemStack helmet = this.mc.thePlayer.inventory.armorItemInSlot(3);
		if(this.mc.gameSettings.thirdPersonView == 0 && helmet != null && helmet.itemID == Block.pumpkin.blockID) {
			this.renderPumpkinBlur(screenWidth, screenHeight);
		}

		if(!this.mc.thePlayer.isPotionActive(Potion.confusion)) {
			float var10 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * f;
			if(var10 > 0.0F) {
				this.renderPortalOverlay(var10, screenWidth, screenHeight);
			}
		}
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glBindTexture(3553 /* GL_TEXTURE_2D */, this.mc.renderEngine.getTexture("/gui/gui.png"));
		InventoryPlayer var11 = this.mc.thePlayer.inventory;
		this.zLevel = -90.0F;
		this.drawTexturedModalRect(screenWidth / 2 - 91, screenHeight - 22, 0, 0, 182, 22);
		this.drawTexturedModalRect(screenWidth / 2 - 91 - 1 + var11.currentItem * 20, screenHeight - 22 - 1, 0, 22, 24, 22);
		GL11.glBindTexture(3553 /* GL_TEXTURE_2D */, this.mc.renderEngine.getTexture("/gui/icons.png"));
		GL11.glEnable(3042 /* GL_BLEND */);
		GL11.glBlendFunc(775, 769);
		this.drawTexturedModalRect(screenWidth / 2 - 7, screenHeight / 2 - 7, 0, 0, 16, 16);
		GL11.glDisable(3042 /* GL_BLEND */);

		GuiIngame.rand.setSeed((long) (this.updateCounter * 312871));
		int var15;
		int var17;

		this.renderBossHealth();

		//better safe than sorry
		SpoutClient.enableSandbox();
		
		//Toggle visibility if needed
		if(needsUpdate && mainScreen.getHealthBar().isVisible() == mc.playerController.isInCreativeMode()) {
			mainScreen.toggleSurvivalHUD(!mc.playerController.isInCreativeMode());
		}
		needsUpdate = false;

		// Hunger Bar Begin
		mainScreen.getHungerBar().render();
		// Hunger Bar End

		// Armor Bar Begin
		mainScreen.getArmorBar().render();
		// Armor Bar End

		// Health Bar Begin
		mainScreen.getHealthBar().render();
		// Health Bar End

		// Bubble Bar Begin
		mainScreen.getBubbleBar().render();
		// Bubble Bar End

		// Exp Bar Begin
		mainScreen.getExpBar().render();
		// Exp Bar End

		SpoutClient.disableSandbox();
		
		map.onRenderTick();

			GL11.glDisable(GL11.GL_BLEND);
			this.mc.mcProfiler.startSection("actionBar");
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			RenderHelper.enableGUIStandardItemLighting();

			for (var18 = 0; var18 < 9; ++var18) {
				var19 = var6 / 2 - 90 + var18 * 20 + 2;
				var20 = var7 - 16 - 3;
				this.renderInventorySlot(var18, var19, var20, par1);
			}

			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			this.mc.mcProfiler.endSection();
		}

		float var33;

		if (this.mc.thePlayer.getSleepTimer() > 0) {
			GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
			GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
			var15 = this.mc.thePlayer.getSleepTimer();
			float var26 = (float)var15 / 100.0F;
			if(var26 > 1.0F) {
				var26 = 1.0F - (float)(var15 - 100) / 10.0F;
			}

			var17 = (int)(220.0F * var26) << 24 | 1052704;
			this.drawRect(0, 0, screenWidth, screenHeight, var17);
			GL11.glEnable(3008 /*GL_ALPHA_TEST*/);
			GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
		}

		SpoutClient.enableSandbox();
		mainScreen.render();
		SpoutClient.disableSandbox();

		if (this.mc.gameSettings.showDebugInfo) {
			this.mc.mcProfiler.startSection("debug");
			GL11.glPushMatrix();
			var8.drawStringWithShadow("Minecraft 1.4 (" + this.mc.debug + ")", 2, 2, 16777215);
			var8.drawStringWithShadow(this.mc.debugInfoRenders(), 2, 12, 16777215);
			var8.drawStringWithShadow(this.mc.getEntityDebug(), 2, 22, 16777215);
			var8.drawStringWithShadow(this.mc.debugInfoEntities(), 2, 32, 16777215);
			var8.drawStringWithShadow(this.mc.getWorldProviderName(), 2, 42, 16777215);
			long var41 = Runtime.getRuntime().maxMemory();
			long var34 = Runtime.getRuntime().totalMemory();
			long var42 = Runtime.getRuntime().freeMemory();
			long var43 = var34 - var42;
			String var45 = "Used memory: " + var43 * 100L / var41 + "% (" + var43 / 1024L / 1024L + "MB) of " + var41 / 1024L / 1024L + "MB";
			this.drawString(var8, var45, var6 - var8.getStringWidth(var45) - 2, 2, 14737632);
			var45 = "Allocated memory: " + var34 * 100L / var41 + "% (" + var34 / 1024L / 1024L + "MB)";
			this.drawString(var8, var45, var6 - var8.getStringWidth(var45) - 2, 12, 14737632);
			var47 = MathHelper.floor_double(this.mc.thePlayer.posX);
			var22 = MathHelper.floor_double(this.mc.thePlayer.posY);
			var23 = MathHelper.floor_double(this.mc.thePlayer.posZ);
			this.drawString(var8, String.format("x: %.5f (%d) // c: %d (%d)", new Object[] {Double.valueOf(this.mc.thePlayer.posX), Integer.valueOf(var47), Integer.valueOf(var47 >> 4), Integer.valueOf(var47 & 15)}), 2, 64, 14737632);
			this.drawString(var8, String.format("y: %.3f (feet pos, %.3f eyes pos)", new Object[] {Double.valueOf(this.mc.thePlayer.boundingBox.minY), Double.valueOf(this.mc.thePlayer.posY)}), 2, 72, 14737632);
			this.drawString(var8, String.format("z: %.5f (%d) // c: %d (%d)", new Object[] {Double.valueOf(this.mc.thePlayer.posZ), Integer.valueOf(var23), Integer.valueOf(var23 >> 4), Integer.valueOf(var23 & 15)}), 2, 80, 14737632);
			var24 = MathHelper.floor_double((double)(this.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			this.drawString(var8, "f: " + var24 + " (" + Direction.field_82373_c[var24] + ") / " + MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw), 2, 88, 14737632);

			if (this.mc.theWorld != null && this.mc.theWorld.blockExists(var47, var22, var23)) {
				Chunk var53 = this.mc.theWorld.getChunkFromBlockCoords(var47, var23);
				this.drawString(var8, "lc: " + (var53.getTopFilledSegment() + 15) + " b: " + var53.getBiomeGenForWorldCoords(var47 & 15, var23 & 15, this.mc.theWorld.getWorldChunkManager()).biomeName + " bl: " + var53.getSavedLightValue(EnumSkyBlock.Block, var47 & 15, var22, var23 & 15) + " sl: " + var53.getSavedLightValue(EnumSkyBlock.Sky, var47 & 15, var22, var23 & 15) + " rl: " + var53.getBlockLightValue(var47 & 15, var22, var23 & 15, 0), 2, 96, 14737632);
			}

			this.drawString(var8, String.format("ws: %.3f, fs: %.3f, g: %b, fl: %d", new Object[] {Float.valueOf(this.mc.thePlayer.capabilities.getWalkSpeed()), Float.valueOf(this.mc.thePlayer.capabilities.getFlySpeed()), Boolean.valueOf(this.mc.thePlayer.onGround), Integer.valueOf(this.mc.theWorld.getHeightValue(var47, var23))}), 2, 104, 14737632);
			GL11.glPopMatrix();
			this.mc.mcProfiler.endSection();
		}

		if (this.recordPlayingUpFor > 0) {
			this.mc.mcProfiler.startSection("overlayMessage");
			var33 = (float)this.recordPlayingUpFor - par1;
			var12 = (int)(var33 * 256.0F / 20.0F);

			if (var12 > 255) {
				var12 = 255;
			}

			if (var12 > 0) {
				GL11.glPushMatrix();
				GL11.glTranslatef((float)(var6 / 2), (float)(var7 - 48), 0.0F);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				var13 = 16777215;

				if (this.recordIsPlaying) {
					var13 = Color.HSBtoRGB(var33 / 50.0F, 0.7F, 0.6F) & 16777215;
				}

				var8.drawString(this.recordPlaying, -font.getStringWidth(this.recordPlaying) / 2, -4, var13 + (var12 << 24));
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
			}

			this.mc.mcProfiler.endSection();
		}

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, (float)(var7 - 48), 0.0F);
		this.mc.mcProfiler.startSection("chat");
		this.persistantChatGUI.drawChat(this.updateCounter);
		this.mc.mcProfiler.endSection();
		GL11.glPopMatrix();

		if (this.mc.gameSettings.keyBindPlayerList.pressed && (!this.mc.isIntegratedServerRunning() || this.mc.thePlayer.sendQueue.playerInfoList.size() > 1)) {
			this.mc.mcProfiler.startSection("playerList");
			NetClientHandler var37 = this.mc.thePlayer.sendQueue;
			List var39 = var37.playerInfoList;
			var13 = var37.currentServerMaxPlayers;
			var40 = var13;

			for (var38 = 1; var40 > 20; var40 = (var13 + var38 - 1) / var38) {
				++var38;
			}

			var16 = 300 / var38;

			if (var16 > 150) {
				var16 = 150;
			}

			var17 = (var6 - var38 * var16) / 2;
			byte var44 = 10;
			drawRect(var17 - 1, var44 - 1, var17 + var16 * var38, var44 + 9 * var40, Integer.MIN_VALUE);

			for (var19 = 0; var19 < var13; ++var19) {
				var20 = var17 + var19 % var38 * var16;
				var47 = var44 + var19 / var38 * 9;
				drawRect(var20, var47, var20 + var16 - 1, var47 + 8, 553648127);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glEnable(GL11.GL_ALPHA_TEST);

				if (var19 < var39.size()) {
					GuiPlayerInfo var46 = (GuiPlayerInfo)var39.get(var19);
					var8.drawStringWithShadow(var46.name, var20, var47, 16777215);
					this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture("/gui/icons.png"));
					byte var50 = 0;
					boolean var48 = false;
					byte var49;

					if (var46.responseTime < 0) {
						var49 = 5;
					} else if (var46.responseTime < 150) {
						var49 = 0;
					} else if (var46.responseTime < 300) {
						var49 = 1;
					} else if (var46.responseTime < 600) {
						var49 = 2;
					} else if (var46.responseTime < 1000) {
						var49 = 3;
					} else {
						var49 = 4;
					}

					this.zLevel += 100.0F;
					this.drawTexturedModalRect(var20 + var16 - 12, var47, 0 + var50 * 10, 176 + var49 * 8, 10, 8);
					this.zLevel -= 100.0F;
				}
			}
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
	}

	/**
	 * Renders dragon's (boss) health on the HUD
	 */
	private void renderBossHealth() {
		if (BossStatus.field_82827_c != null && BossStatus.field_82826_b > 0) {
			--BossStatus.field_82826_b;
			FontRenderer var1 = this.mc.fontRenderer;
			ScaledResolution var2 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
			int var3 = var2.getScaledWidth();
			short var4 = 182;
			int var5 = var3 / 2 - var4 / 2;
			int var6 = (int)(BossStatus.field_82828_a * (float)(var4 + 1));
			byte var7 = 12;
			this.drawTexturedModalRect(var5, var7, 0, 74, var4, 5);
			this.drawTexturedModalRect(var5, var7, 0, 74, var4, 5);

			if (var6 > 0) {
				this.drawTexturedModalRect(var5, var7, 0, 79, var6, 5);
			}

			String var8 = BossStatus.field_82827_c;
			var1.drawStringWithShadow(var8, var3 / 2 - var1.getStringWidth(var8) / 2, var7 - 10, 16777215);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/icons.png"));
		}
	}

	private void renderPumpkinBlur(int par1, int par2) {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("%blur%/misc/pumpkinblur.png"));
		Tessellator var3 = Tessellator.instance;
		var3.startDrawingQuads();
		var3.addVertexWithUV(0.0D, (double)par2, -90.0D, 0.0D, 1.0D);
		var3.addVertexWithUV((double)par1, (double)par2, -90.0D, 1.0D, 1.0D);
		var3.addVertexWithUV((double)par1, 0.0D, -90.0D, 1.0D, 0.0D);
		var3.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		var3.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Renders the vignette. Args: vignetteBrightness, width, height
	 */
	private void renderVignette(float par1, int par2, int par3) {
		par1 = 1.0F - par1;

		if (par1 < 0.0F) {
			par1 = 0.0F;
		}

		if (par1 > 1.0F) {
			par1 = 1.0F;
		}

		this.prevVignetteBrightness = (float)((double)this.prevVignetteBrightness + (double)(par1 - this.prevVignetteBrightness) * 0.01D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_ZERO, GL11.GL_ONE_MINUS_SRC_COLOR);
		GL11.glColor4f(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("%blur%/misc/vignette.png"));
		Tessellator var4 = Tessellator.instance;
		var4.startDrawingQuads();
		var4.addVertexWithUV(0.0D, (double)par3, -90.0D, 0.0D, 1.0D);
		var4.addVertexWithUV((double)par2, (double)par3, -90.0D, 1.0D, 1.0D);
		var4.addVertexWithUV((double)par2, 0.0D, -90.0D, 1.0D, 0.0D);
		var4.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		var4.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	/**
	 * Renders the portal overlay. Args: portalStrength, width, height
	 */
	private void renderPortalOverlay(float par1, int par2, int par3) {
		if (par1 < 1.0F) {
			par1 *= par1;
			par1 *= par1;
			par1 = par1 * 0.8F + 0.2F;
		}

		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, par1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/terrain.png"));
		float var4 = (float)(Block.portal.blockIndexInTexture % 16) / 16.0F;
		float var5 = (float)(Block.portal.blockIndexInTexture / 16) / 16.0F;
		float var6 = (float)(Block.portal.blockIndexInTexture % 16 + 1) / 16.0F;
		float var7 = (float)(Block.portal.blockIndexInTexture / 16 + 1) / 16.0F;
		Tessellator var8 = Tessellator.instance;
		var8.startDrawingQuads();
		var8.addVertexWithUV(0.0D, (double)par3, -90.0D, (double)var4, (double)var7);
		var8.addVertexWithUV((double)par2, (double)par3, -90.0D, (double)var6, (double)var7);
		var8.addVertexWithUV((double)par2, 0.0D, -90.0D, (double)var6, (double)var5);
		var8.addVertexWithUV(0.0D, 0.0D, -90.0D, (double)var4, (double)var5);
		var8.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Renders the specified item of the inventory slot at the specified location. Args: slot, x, y, partialTick
	 */
	private void renderInventorySlot(int par1, int par2, int par3, float par4) {
		ItemStack var5 = this.mc.thePlayer.inventory.mainInventory[par1];

		if (var5 != null) {
			float var6 = (float)var5.animationsToGo - par4;

			if (var6 > 0.0F) {
				GL11.glPushMatrix();
				float var7 = 1.0F + var6 / 5.0F;
				GL11.glTranslatef((float)(par2 + 8), (float)(par3 + 12), 0.0F);
				GL11.glScalef(1.0F / var7, (var7 + 1.0F) / 2.0F, 1.0F);
				GL11.glTranslatef((float)(-(par2 + 8)), (float)(-(par3 + 12)), 0.0F);
			}

			itemRenderer.func_82406_b(this.mc.fontRenderer, this.mc.renderEngine, var5, par2, par3);

			if (var6 > 0.0F) {
				GL11.glPopMatrix();
			}

			itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var5, par2, par3);
		}
	}

	/**
	 * The update tick for the ingame UI
	 */
	public void updateTick() {
		if (this.recordPlayingUpFor > 0) {
			--this.recordPlayingUpFor;
		}

		++this.updateCounter;
	}

	public void setRecordPlayingMessage(String par1Str) {
		this.recordPlaying = "Now playing: " + par1Str;
		this.recordPlayingUpFor = 60;
		this.recordIsPlaying = true;
	}

	/**
	 * returns a pointer to the persistant Chat GUI, containing all previous chat messages and such
	 */
	public GuiNewChat getChatGUI() {
		return this.persistantChatGUI;
	}

	public int getUpdateCounter() {
		return this.updateCounter;
	}
}
