package feldrinh.shadowmancy;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import feldrinh.shadowmancy.blocks.RefluxTableEntity;
import feldrinh.shadowmancy.blocks.RefluxTableRenderer;
//import feldrinh.shadowmancy.items.ShadowlurkerRenderHandler;
import feldrinh.shadowmancy.utility.LogHelper;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{
	@Override
	public void postInit()
	{
		RefluxTableRenderer shadowRefluxRenderer = new RefluxTableRenderer();
		FMLCommonHandler.instance().bus().register(shadowRefluxRenderer);
		ClientRegistry.bindTileEntitySpecialRenderer(RefluxTableEntity.class, shadowRefluxRenderer);
        //MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Shadowmancy.ShadowRefluxTable), );
		//tEST
        //MinecraftForge.EVENT_BUS.register(new shadowlurkerRenderHandler());
        
		LogHelper.log(Level.INFO, "Clientside postInit completed. #CombinedClientRules");
	}
}
