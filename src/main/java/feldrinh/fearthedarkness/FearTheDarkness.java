package feldrinh.fearthedarkness;

import org.apache.logging.log4j.Level;

import feldrinh.fearthedarkness.utility.LogHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Arrays;

@Mod(modid = "FearTheDarkness", name = "Fear The Darkness", version = "0.7.0")

public class FearTheDarkness
{
	@Mod.Instance("fearthedarkness")
	public static FearTheDarkness instance;

	/*@SidedProxy(clientSide = "feldrinh.fearthedarkness.ClientProxy", serverSide = "feldrinh.fearthedarkness.ServerProxy")
	public static CommonProxy proxy;*/

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		FTDConfig.loadConfig(event.getSuggestedConfigurationFile());
	}

	/*@EventHandler
	public void init(FMLInitializationEvent event)
	{
	}*/

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new FTDTickHandler());
		LogHelper.log(Level.INFO, "Fear for the Darkness rises...");
	}
}
