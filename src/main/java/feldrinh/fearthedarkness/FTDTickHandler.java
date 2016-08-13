package feldrinh.fearthedarkness;

import feldrinh.fearthedarkness.utility.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Arrays;

import org.apache.logging.log4j.Level;

public class FTDTickHandler
{
	@SubscribeEvent
	public void onWorldTick(WorldTickEvent e)
	{
		if (e.side == Side.SERVER && e.phase == Phase.START)
		{
			FTDConfig conf = FTDConfig.getConfigOrDefault(e.world.provider.getDimensionId());

			for (Object playerObj : e.world.playerEntities)
			{
				EntityPlayer player = (EntityPlayer)playerObj;

				int light = getLightLevel(player);

				if (light <= conf.deepLightLevel)
				{
					if (player.ticksExisted % conf.deepCooldown == 0 && !ShadowcloakEnchantment.hasDeepShadowcloak(player.inventory.armorInventory))
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
					if (player.ticksExisted % conf.cooldown == 0 && !ShadowcloakEnchantment.hasShadowcloak(player.inventory.armorInventory))
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
		BlockPos blockpos = new BlockPos(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ);
        return player.worldObj.isBlockLoaded(blockpos) ? player.worldObj.getLightFromNeighbors(blockpos) : 0;
	}
}
