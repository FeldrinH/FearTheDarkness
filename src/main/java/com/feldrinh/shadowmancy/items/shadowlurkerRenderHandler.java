package com.feldrinh.shadowmancy.items;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;

import com.feldrinh.shadowmancy.utility.LogHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class shadowlurkerRenderHandler
{
	@SubscribeEvent
	public void multiplyShadowlurker(RenderPlayerEvent.Pre event)
	{
		event.setCanceled(true);
	}
	
	//@SubscribeEvent
	public void demultiplyShadowlurker(RenderPlayerEvent.Post event)
	{

	}

}
