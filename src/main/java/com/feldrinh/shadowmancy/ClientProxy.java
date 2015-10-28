package com.feldrinh.shadowmancy;

import org.apache.logging.log4j.Level;

import com.feldrinh.shadowmancy.blocks.shadowRefluxTableEntity;
import com.feldrinh.shadowmancy.blocks.shadowRefluxTableRenderer;
import com.feldrinh.shadowmancy.items.shadowlurkerRenderHandler;
import com.feldrinh.shadowmancy.utility.LogHelper;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{
	@Override
	public void postInit()
	{
		shadowRefluxTableRenderer shadowRefluxRenderer = new shadowRefluxTableRenderer();
		FMLCommonHandler.instance().bus().register(shadowRefluxRenderer);
		ClientRegistry.bindTileEntitySpecialRenderer(shadowRefluxTableEntity.class, shadowRefluxRenderer);
        //MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Shadowmancy.ShadowRefluxTable), );
		//tEST
        //MinecraftForge.EVENT_BUS.register(new shadowlurkerRenderHandler());
        
		LogHelper.log(Level.INFO, "Clientside postInit completed. #CombinedClientRules");
	}
}
