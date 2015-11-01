package feldrinh.shadowmancy;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import cpw.mods.fml.relauncher.Side;
import feldrinh.shadowmancy.items.lifePoolPendant;
import feldrinh.shadowmancy.items.shadowlurkerArmor;
import feldrinh.shadowmancy.utility.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;

import static cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import java.util.ArrayList;
import org.apache.logging.log4j.Level;

import baubles.api.BaublesApi;

public class shadowEventHandler
{
	public shadowEventHandler(lifePoolPendant lifePoolItem)
	{
		lifePool = lifePoolItem;
		lifePoolMembers = new ArrayList<EntityLivingBase>();
	}

	//Life Pool Pendant
	public static final DamageSource lifePoolEmpty = (new DamageSource("lifePoolEmpty").setDamageBypassesArmor().setDamageIsAbsolute());

	public static ArrayList<EntityLivingBase> lifePoolMembers;
	private static lifePoolPendant lifePool;

	//public static boolean scheduledRecalc;

	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onServerTick(ServerTickEvent event)
	{
		if (event.phase == Phase.END)
		{
			recalcLifePool();
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onPlayerAttack(LivingAttackEvent event)
	{
		if (event.source.damageType == "drainShademail" && event.entityLiving instanceof EntityPlayer && hasLifePool((EntityPlayer)event.entityLiving) && (event.entityLiving.getHealth() - event.ammount <= 0.0F))
		{
			event.entityLiving.setHealth(0.01F);
			event.setCanceled(true);
		}
	}

	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void onPlayerHurt(LivingHurtEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer && event.source != lifePoolEmpty && hasLifePool((EntityPlayer)event.entityLiving) && (event.entityLiving.getHealth() - event.ammount <= 0.0F))
		{
			event.entityLiving.setHealth(0.01F);
			event.setCanceled(true);
		}
	}
	
	private static boolean hasLifePool(EntityPlayer player)
	{
		return BaublesApi.getBaubles(player).getStackInSlot(0) != null && BaublesApi.getBaubles(player).getStackInSlot(0).getItem() == lifePool;
	}
	
	private static void recalcLifePool()
	{		
		if (lifePoolMembers.isEmpty())
		{
			return;
		}
		if (lifePoolMembers.size() == 1)
		{
			LogHelper.log(Level.INFO, "1 member!");
			if (lifePoolMembers.get(0).getHealth() < 0.02F)
			{
				lifePoolMembers.get(0).attackEntityFrom(lifePoolEmpty,100);
			}
			return;
		}
		
		double lifePoolTotal = 0.0D;
		float lifePoolPlayer;
		for (EntityLivingBase player : lifePoolMembers)
		{
			if(player.getHealth() >= 0.02F)
			{
				lifePoolTotal += player.getHealth();
			}
		}
		lifePoolPlayer = (float) (lifePoolTotal/lifePoolMembers.size());
		if (lifePoolPlayer < 0.1F)
		{
			for(EntityLivingBase player : lifePoolMembers)
			{
				player.attackEntityFrom(lifePoolEmpty,100);
			}
			return;
		}
		for (EntityLivingBase player : lifePoolMembers)
		{
			if (lifePoolPlayer != player.getHealth())
			{
				player.setHealth(lifePoolPlayer);
			}
		}
	}

	public static void recalcPoolMembers()
	{
		lifePoolMembers.clear();
		EntityPlayer player;
		for (Object playerObj : MinecraftServer.getServer().getConfigurationManager().playerEntityList)
		{
			player = (EntityPlayer)playerObj;
			if (hasLifePool(player))
			{
				lifePoolMembers.add(player);
			}
		}
	}
	
	//Shadowlurker Robes
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onPlayerTargeted(LivingSetAttackTargetEvent event)
	{
		//if(event.target != null)
		//{
		
		//LogHelper.log(Level.INFO, event.entityLiving.getDistanceSqToEntity(event.target));
		
		if(event.target instanceof EntityPlayer 
		&& event.target.worldObj.getSavedLightValue(EnumSkyBlock.Block,MathHelper.floor_double(event.target.posX),MathHelper.floor_double(event.target.posY)+1,MathHelper.floor_double(event.target.posZ)) == 0
		&& event.target.getBrightness(1.0F) < 0.084F
		&& shadowlurkerArmor.checkArmor((EntityPlayer)event.target) 
		&& event.entityLiving.func_94060_bK() != event.target
		&& event.entityLiving.getDistanceSqToEntity(event.target) > 6.0D)
		{
			event.entityLiving.setRevengeTarget(null);
			if(event.entityLiving instanceof EntityLiving)
			{
				((EntityLiving)event.entityLiving).setAttackTarget(null);
			}
		}
		//}
	}
}