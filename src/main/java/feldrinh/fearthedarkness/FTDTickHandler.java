package feldrinh.fearthedarkness;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
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
	public void onWorldTick(WorldTickEvent e)
	{
		if (e.side == Side.SERVER && e.phase == Phase.START)
		{
			FTDConfig conf = FTDConfig.getConfigOrDefault(e.world.provider.dimensionId);

			for (Object playerObj : e.world.playerEntities)
			{
				EntityPlayer player = (EntityPlayer)playerObj;

				int light = getLightLevel(player);

				if (light <= conf.deepLightLevel)
				{
					if (player.ticksExisted % conf.deepCooldown == 0)
					{
						player.hurtResistantTime = 0;
						if (player.attackEntityFrom(FTDConfig.deepDarkness, conf.getDeepDamage(light)) && FTDConfig.supressRedFlash)
						{
							player.hurtTime = 0;
						}
					}
				}
				else if (light <= conf.lightLevel)
				{
					if (player.ticksExisted % conf.cooldown == 0)
					{
						player.hurtResistantTime = 0;
						if (player.attackEntityFrom(FTDConfig.darkness, conf.getDamage(light)) && FTDConfig.supressRedFlash)
						{
							player.hurtTime = 0;
						}
					}
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
