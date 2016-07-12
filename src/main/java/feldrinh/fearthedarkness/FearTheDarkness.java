package feldrinh.fearthedarkness;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import feldrinh.fearthedarkness.ServerProxy;
import feldrinh.fearthedarkness.utility.LogHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;

@Mod(modid="fearthedarkness", name="FearTheDarkness", version="0.3.0")

public class FearTheDarkness
{
    @Mod.Instance("fearthedarkness")
    public static FearTheDarkness instance;

    @SidedProxy(clientSide="feldrinh.fearthedarkness.ClientProxy",serverSide="feldrinh.fearthedarkness.ServerProxy")
    public static CommonProxy proxy;
            
    /*@EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {    	
    }*/

    /*@EventHandler
    public void init(FMLInitializationEvent event)
    {
    }*/

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
		FMLCommonHandler.instance().bus().register(new FTDTickHandler());
        LogHelper.log(Level.INFO, "Fear for The Darkness rises...");
    }
}
