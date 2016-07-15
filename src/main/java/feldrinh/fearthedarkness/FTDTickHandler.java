package feldrinh.fearthedarkness;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import feldrinh.fearthedarkness.utility.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;

import static cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

import java.util.Arrays;

import org.apache.logging.log4j.Level;

public class FTDTickHandler
{
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{
		if (event.side == Side.SERVER && event.phase == Phase.START && event.player.hurtResistantTime < 1)
		{
			//LogHelper.log(Level.INFO, event.player.worldObj.getBlockLightValue(MathHelper.floor_double(event.player.posX),MathHelper.floor_double(event.player.posY)+1,MathHelper.floor_double(event.player.posZ)));

			//LogHelper.log(Level.INFO, Arrays.toString(event.player.worldObj.provider.lightBrightnessTable));

			//LogHelper.log(Level.INFO,event.player.worldObj.getSavedLightValue(EnumSkyBlock.Block,MathHelper.floor_double(event.player.posX),MathHelper.floor_double(event.player.posY)+1,MathHelper.floor_double(event.player.posZ)));

			//            0    1            2            3            4           5           6           7           8           9           10          11          12          13          14          15
			//Overworld: [0.0, 0.017543858, 0.037037037, 0.058823526, 0.08333333, 0.11111113, 0.14285712, 0.1794872,  0.22222225, 0.2727273,  0.33333334, 0.40740743, 0.50000006, 0.61904764, 0.77777773, 1.0]
			//Nether:    [0.1, 0.11578947,  0.13333333,  0.15294117,  0.175,      0.20000002, 0.22857141, 0.26153848, 0.3,        0.34545457, 0.4,        0.46666667, 0.5500001,  0.6571429,  0.79999995, 1.0]

			FTDConfig conf = FTDConfig.getConfigOrDefault(event.player.dimension);
			
			int light = getLightLevel(event.player);
			if (light <= conf.deepLightLevel)
			{
				if (event.player.attackEntityFrom(FTDConfig.deepDarkness, conf.deepDamage))
				{
					event.player.hurtTime = 0;
					event.player.hurtResistantTime = conf.deepCooldown; //Probably not the best solution
				}
			}
			else if (light <= conf.lightLevel)
			{
				if (event.player.attackEntityFrom(FTDConfig.darkness, conf.damage))
				{
					event.player.hurtTime = 0;
					event.player.hurtResistantTime = conf.cooldown; //Probably not the best solution
				}
			}
		}
	}

	public static int getLightLevel(EntityPlayer player)
	{
		int i = MathHelper.floor_double(player.posX);
		int j = MathHelper.floor_double(player.posZ);

		if (player.worldObj.blockExists(i, 0, j))
		{
			double d = (player.boundingBox.maxY - player.boundingBox.minY) * 0.66D;
			int k = MathHelper.floor_double(player.posY - (double) player.yOffset + d);
			return player.worldObj.getBlockLightValue(i, k, j);
		}
		else
		{
			return 0;
		}
	}
}
