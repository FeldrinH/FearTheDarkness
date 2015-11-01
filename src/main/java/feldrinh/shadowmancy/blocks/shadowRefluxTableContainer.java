package feldrinh.shadowmancy.blocks;

import org.apache.logging.log4j.Level;

import feldrinh.shadowmancy.utility.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class shadowRefluxTableContainer extends Container
{
	private shadowRefluxTableEntity shadowTable; 
	private InventoryPlayer playerInventory;
	
	private class shadowSlot extends Slot
	{
		public shadowSlot(IInventory inventory, int int1, int int2, int int3) {
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
	
	public shadowRefluxTableContainer(shadowRefluxTableEntity shadowTable, EntityPlayer player)
	{
		this.shadowTable = shadowTable;
		this.playerInventory = player.inventory;
		
		int i;
		int j;
		
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
        
        addSlotToContainer(new shadowSlot(shadowTable,0,80,38));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return shadowTable.isUseableByPlayer(player);
	}
}
