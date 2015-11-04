package feldrinh.shadowmancy.blocks;

import org.apache.logging.log4j.Level;

import feldrinh.shadowmancy.utility.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class RefluxTableContainer extends Container
{
	private RefluxTableEntity shadowTable; 
	private InventoryPlayer playerInventory;
	
	private class shadowSlot extends Slot
	{
		public shadowSlot(IInventory inventory, int int1, int int2, int int3)
		{
			super(inventory, int1, int2, int3);
		}
		
		/*@Override
		public void onSlotChanged()
		{
			super.onSlotChanged();
			shadowTable.refreshItem();
			//LogHelper.log(Level.INFO, shadowTable.getWorldObj().isRemote);
		}*/
	}
	
	public RefluxTableContainer(RefluxTableEntity shadowTable, EntityPlayer player)
	{
		this.shadowTable = shadowTable;
		this.playerInventory = player.inventory;
		
		int i;
		int j;
		
		addSlotToContainer(new shadowSlot(shadowTable,0,80,38));
		
        for (i = 0; i < 3; ++i)
        {
            for (j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNum)
	{
		Slot slot = (Slot)inventorySlots.get(slotNum);
		if (!slot.getHasStack())
		{
			LogHelper.log(Level.INFO, "No Item");
			return null;
		}
		ItemStack item = slot.getStack();
		ItemStack itemCopy = item.copy();
		
		if (slotNum == 0)
		{
			if(!mergeItemStack(item,1,37,false))
			{
				LogHelper.log(Level.INFO, "Fail Add");
				return null;
			}
			else
			{
				LogHelper.log(Level.INFO, "Add");
				if (item.stackSize == 0)
				{
					LogHelper.log(Level.INFO, "Cleared Empty Stack");
					slot.putStack(null);
				}
				else
					slot.onSlotChanged();
			}
		}
		else
		{
			LogHelper.log(Level.INFO, "Swap");
			if (((Slot)inventorySlots.get(0)).getHasStack())
			{
				LogHelper.log(Level.INFO, "SwapBoth");
				slot.putStack(((Slot)inventorySlots.get(0)).getStack());
			}
			else
			{
				LogHelper.log(Level.INFO, "SwapEmpty");
				slot.putStack(null);
			}
			((Slot)inventorySlots.get(0)).putStack(item);
            /*if (item.stackSize == itemCopy.stackSize)
            {
            	LogHelper.log(Level.INFO, item.toString() + " " + itemCopy.toString());
                return null;
            }*/
		}
		return itemCopy;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return shadowTable.isUseableByPlayer(player);
	}
}
