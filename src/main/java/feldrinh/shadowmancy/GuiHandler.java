package feldrinh.shadowmancy;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.network.IGuiHandler;
import feldrinh.shadowmancy.blocks.RefluxTableContainer;
import feldrinh.shadowmancy.blocks.RefluxTableEntity;
import feldrinh.shadowmancy.blocks.RefluxTableGui;
import feldrinh.shadowmancy.utility.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		//LogHelper.log(Level.INFO, tileEntity);
		if(tileEntity instanceof RefluxTableEntity)
		{
			return new RefluxTableContainer((RefluxTableEntity)tileEntity,player);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		//LogHelper.log(Level.INFO, tileEntity);
		if(tileEntity instanceof RefluxTableEntity)
		{
			return new RefluxTableGui((RefluxTableEntity)tileEntity, player);
		}
		return null;
	}
}
