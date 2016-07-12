package feldrinh.shadowmancy;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import feldrinh.shadowmancy.items.ShadowlurkerArmor;
import feldrinh.shadowmancy.utility.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;

import static cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

import java.util.Arrays;

import org.apache.logging.log4j.Level;

public class FtdTickHandler
{
    public static final DamageSource darkness = (new DamageSource("darkness")).setDamageBypassesArmor().setDamageIsAbsolute();
    public static final DamageSource deepDarkness = (new DamageSource("deepDarkness")).setDamageBypassesArmor().setDamageIsAbsolute();

    public FtdTickHandler()
    {
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event)
    {   
        if (event.side == Side.SERVER && event.phase == Phase.START && event.player.hurtResistantTime <= 10)
        {
        	float light = event.player.getBrightness(1.0F);

        	//LogHelper.log(Level.INFO, event.player.worldObj.getBlockLightValue(MathHelper.floor_double(event.player.posX),MathHelper.floor_double(event.player.posY)+1,MathHelper.floor_double(event.player.posZ)));
        	
        	//LogHelper.log(Level.INFO, Arrays.toString(event.player.worldObj.provider.lightBrightnessTable));
        	
    		//LogHelper.log(Level.INFO,event.player.worldObj.getSavedLightValue(EnumSkyBlock.Block,MathHelper.floor_double(event.player.posX),MathHelper.floor_double(event.player.posY)+1,MathHelper.floor_double(event.player.posZ)));
    		
        	if (light == 0.0F && !ShadowlurkerArmor.checkArmor(event.player))
        	{
        		event.player.attackEntityFrom(deepDarkness, 5);
        	}
        	else if (light < 0.18F && !ShadowlurkerArmor.checkArmor(event.player))
        	{
        		event.player.attackEntityFrom(darkness, 2);
        	}
        }
    }
}
