package feldrinh.shadowmancy.blocks;

import org.apache.logging.log4j.Level;

import feldrinh.shadowmancy.Shadowmancy;
import feldrinh.shadowmancy.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class shadowRefluxTable extends BlockContainer 
{
	public shadowRefluxTable() 
	{
		super(Material.rock);
        setCreativeTab(CreativeTabs.tabMisc);
        setHardness(5.0F);
        setResistance(10.0F);
        setStepSound(soundTypeStone);
        setBlockName("shadowRefluxTable");
        setBlockTextureName("shadowmancy:shadowRefluxTableParticles");
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int int4, float float1, float float2, float float3)
	{
		if(!world.isRemote)
		{
			player.openGui(Shadowmancy.instance, 0, world, x, y, z);
		}
		return true;
	}
	
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	@Override
	public int getRenderType()
	{
		return -1;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) 
	{
		return new shadowRefluxTableEntity();
	}
}