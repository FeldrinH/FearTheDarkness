package com.feldrinh.shadowmancy.blocks;

import java.util.Random;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class shadowRefluxTableGui extends GuiContainer
{
	private static final ResourceLocation guiTexture = new ResourceLocation("shadowmancy:textures/blocks/shadowRefluxTableGUI.png");
	private shadowRefluxTableEntity shadowTable;
	private Random random;
	
	public shadowRefluxTableGui(shadowRefluxTableEntity shadowTable, EntityPlayer player)
	{
		super(new shadowRefluxTableContainer(shadowTable, player));
		this.shadowTable = shadowTable;
		this.random = new Random();
		xSize = 176;
		ySize = 166;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int int1, int int2)
	{
		this.fontRendererObj.drawString(this.shadowTable.hasCustomInventoryName() ? this.shadowTable.getInventoryName() : I18n.format(this.shadowTable.getInventoryName(), new Object[0]), 8, 6, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float float1, int int1, int int2)
	{
		this.mc.getTextureManager().bindTexture(guiTexture);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
