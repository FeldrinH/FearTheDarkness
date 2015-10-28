package com.feldrinh.shadowmancy;

import org.apache.logging.log4j.Level;

import com.feldrinh.shadowmancy.blocks.shadowRefluxTableContainer;
import com.feldrinh.shadowmancy.blocks.shadowRefluxTableEntity;
import com.feldrinh.shadowmancy.blocks.shadowRefluxTableGui;
import com.feldrinh.shadowmancy.utility.LogHelper;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class guiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		//LogHelper.log(Level.INFO, tileEntity);
		if(tileEntity instanceof shadowRefluxTableEntity)
		{
			return new shadowRefluxTableContainer((shadowRefluxTableEntity)tileEntity,player);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		//LogHelper.log(Level.INFO, tileEntity);
		if(tileEntity instanceof shadowRefluxTableEntity)
		{
			return new shadowRefluxTableGui((shadowRefluxTableEntity)tileEntity, player);
		}
		return null;
	}
}
