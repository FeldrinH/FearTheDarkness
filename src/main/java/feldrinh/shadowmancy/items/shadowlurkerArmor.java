package feldrinh.shadowmancy.items;

import org.apache.logging.log4j.Level;

import feldrinh.shadowmancy.Shadowmancy;
import feldrinh.shadowmancy.utility.LogHelper;
import javafx.scene.control.Tab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

public class shadowlurkerArmor extends ItemArmor
{
	public shadowlurkerArmor(int type)
	{
		super(Shadowmancy.shadowCloth, 0, type);
		setCreativeTab(CreativeTabs.tabCombat);
	}
	
	@Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
		if(checkLocalArmor(player)
		&& world.getSavedLightValue(EnumSkyBlock.Block,MathHelper.floor_double(player.posX),MathHelper.floor_double(player.posY)+1,MathHelper.floor_double(player.posZ)) == 0
		&& player.getBrightness(1.0F) < 0.084F)
		{
			updateInvis(player);
		}
    }
	
	private void updateInvis(EntityPlayer player)
	{
		if (!player.isPotionActive(Potion.invisibility) || player.getActivePotionEffect(Potion.invisibility).getDuration() < 10)
		{
			player.addPotionEffect(new PotionEffect(Potion.invisibility.id, 50, 0, true));
		}
	}
	
	private static boolean checkLocalArmor(EntityPlayer player)
	{
		return
		player.inventory.armorInventory[2] != null
		&& player.inventory.armorInventory[1] != null
		&& player.inventory.armorInventory[0] != null
		&& player.inventory.armorInventory[2].getItem() instanceof shadowlurkerArmor
		&& player.inventory.armorInventory[1].getItem() instanceof shadowlurkerArmor
		&& player.inventory.armorInventory[0].getItem() instanceof shadowlurkerArmor;
	}
	
	public static boolean checkArmor(EntityPlayer player)
	{
		return
		player.inventory.armorInventory[3] != null
		&& player.inventory.armorInventory[2] != null
		&& player.inventory.armorInventory[1] != null
		&& player.inventory.armorInventory[0] != null
		&& player.inventory.armorInventory[3].getItem() instanceof shadowlurkerArmor
		&& player.inventory.armorInventory[2].getItem() instanceof shadowlurkerArmor
		&& player.inventory.armorInventory[1].getItem() instanceof shadowlurkerArmor
		&& player.inventory.armorInventory[0].getItem() instanceof shadowlurkerArmor;	
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return "shadowmancy:textures/armor/shadowlurker_" + (((EntityLivingBase)entity).isInvisible() ? "invis" : (this.armorType == 2 ? "2" : "1")) + ".png";
	}
}
