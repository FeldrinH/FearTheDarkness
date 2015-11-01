package feldrinh.shadowmancy.blocks;

import org.apache.logging.log4j.Level;

import feldrinh.shadowmancy.Shadowmancy;
import feldrinh.shadowmancy.api.IRefluxRecipe;
import feldrinh.shadowmancy.api.refluxRecipeHandler;
import feldrinh.shadowmancy.utility.LogHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import scala.Int;

public class shadowRefluxTableEntity extends TileEntity implements ISidedInventory 
{
	ItemStack tableItem;
	EntityItem tableEntity;
	private IRefluxRecipe curRecipe;
	private float progress;
	private float progressGoal;
	private float darkLevel;
	static final float maxDarkLevel = 100.0F;
	private static final float darkSpeed = 0.01F;
	boolean isActive;
	
	public void refreshItem()
	{
		if(this.getWorldObj().isRemote)
		{
			if(tableItem != null)
			{
				tableEntity = new EntityItem(worldObj, xCoord, yCoord, zCoord, tableItem);
			}
			else
			{
				tableEntity = null;
			}
		}
	}
	
	public void updateItem()
	{
		if(!this.getWorldObj().isRemote)
		{
			curRecipe = refluxRecipeHandler.getRecipeFromItem(tableItem);
			if(curRecipe != null)
			{
				progressGoal = tableItem.stackSize;
				progress = 0.0f;
			}
			this.getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	@Override
	public void updateEntity()
	{
		if(this.getWorldObj().isRemote && tableEntity != null)
		{
			++tableEntity.age;
		}
		else if(!this.getWorldObj().isRemote && curRecipe != null)
		{
			progress += curRecipe.getProgressSpeed();
			darkLevel -= curRecipe.getShadowSpeed();
		}
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		return new S35PacketUpdateTileEntity(xCoord,yCoord,zCoord,1,writeSyncToNBT(new NBTTagCompound()));
	}
	
	@Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		readSyncFromNBT(pkt.func_148857_g());
		refreshItem();
		//LogHelper.log(Level.INFO, "#Packet");
    }
	
	@Override
	public void writeToNBT(NBTTagCompound nbtTag)
	{
		super.writeToNBT(nbtTag);
		writeSyncToNBT(nbtTag);
		//LogHelper.log(Level.INFO, tableItem);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTag)
	{
		super.readFromNBT(nbtTag);
		readSyncFromNBT(nbtTag);
		//LogHelper.log(Level.INFO, tableItem);
	}
	
	public NBTTagCompound writeSyncToNBT(NBTTagCompound nbtTag)
	{
		if (tableItem != null)
		{
			nbtTag.setTag("shadowItem", tableItem.writeToNBT(new NBTTagCompound()));
		}
		return nbtTag;
	}
	
	public void readSyncFromNBT(NBTTagCompound nbtTag)
	{
		if (nbtTag.hasKey("shadowItem"))
		{
			tableItem = ItemStack.loadItemStackFromNBT(nbtTag.getCompoundTag("shadowItem"));
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1.0, yCoord+1.0, zCoord+1.0);
	}
	
	@Override
	public int getSizeInventory()
	{
		return 1;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		return tableItem;
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount) 
	{
        if (tableItem != null)
        {
            ItemStack itemstack;

            if (this.tableItem.stackSize <= amount)
            {
                itemstack = this.tableItem;
                this.tableItem = null;
                this.markDirty();
                updateItem();
                return itemstack;
            }
            else
            {
                itemstack = this.tableItem.splitStack(amount);
                
                if (this.tableItem.stackSize == 0)
                {
                    this.tableItem = null;
                }
                
                this.markDirty();
                updateItem();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) 
	{
		return tableItem;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack item) 
	{
		this.tableItem = item;
		updateItem();
	}

	@Override
	public String getInventoryName()
	{
		return "container.shadowRefluxTable";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory()
	{
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item)
	{
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		// TODO Auto-generated method stub
		return false;
	}
}
