package feldrinh.shadowmancy.blocks;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import feldrinh.shadowmancy.utility.LogHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class shadowRefluxTableRenderer extends TileEntitySpecialRenderer
{
	private ResourceLocation texture = new ResourceLocation("shadowmancy:textures/blocks/shadowRefluxTable.png");
	private shadowRefluxTableModel model = new shadowRefluxTableModel();
	
	@SubscribeEvent
	public void incrementAnimation(ClientTickEvent event)
	{
		if (event.phase == Phase.START)
		{
			model.incrementCoreAnimation();
		}
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTickTime) 
	{
		GL11.glPushMatrix();
			GL11.glTranslated(x+0.5F, y+1.5F, z+0.5F);
			GL11.glRotatef(180, 0F, 0F, 1F);
			this.bindTexture(texture);
			
			model.render(0.0625F, partialTickTime);
			
			GL11.glRotatef(180, 0F, 0F, 1F);
			shadowRefluxTableEntity shadowTable = (shadowRefluxTableEntity)tileEntity;
			if(shadowTable.tableEntity != null)
			{
				shadowTable.tableEntity.hoverStart = 0.0F;
				RenderManager.instance.renderEntityWithPosYaw(shadowTable.tableEntity, 0.0D, -1.0D, 0.0D, 0.0F, partialTickTime);
			}
		GL11.glPopMatrix();
	}
}
