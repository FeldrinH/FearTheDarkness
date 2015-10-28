package com.feldrinh.shadowmancy.items;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Level;

import com.feldrinh.shadowmancy.shadowEventHandler;
import com.feldrinh.shadowmancy.utility.LogHelper;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class lifePoolPendant extends Item implements IBauble
{	
	public lifePoolPendant()
	{
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabCombat);
		setUnlocalizedName("lifePoolPendant");
		setTextureName("shadowmancy:lifePoolPendant");
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.AMULET;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player)
	{
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player)
	{
		//shadowEventHandler.lifePoolMembers.add(player);
		shadowEventHandler.recalcPoolMembers();
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
	{
		//shadowEventHandler.lifePoolMembers.remove(player);
		shadowEventHandler.recalcPoolMembers();
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}
}
