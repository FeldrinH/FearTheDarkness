package feldrinh.fearthedarkness;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class ShadowcloakEnchantment extends Enchantment
{
	protected ShadowcloakEnchantment(int maxLevel)
	{
		super(Enchantment.Rarity.RARE, EnumEnchantmentType.ARMOR, new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET});

		this.name = "shadowcloak";
		this.maxLevel = maxLevel;
	}
	
	public static ShadowcloakEnchantment self;
	
	private final int maxLevel;
	
	@Override
	public int getMaxLevel()
	{
		return maxLevel;
	}
	
	@Override
	public int getMinEnchantability(int level)
    {
        return 4 + level * 9;
    }

	@Override
    public int getMaxEnchantability(int level)
    {
        return 21 + level * 12;
    }
	
	public static boolean hasShadowcloak(ItemStack[] armor)
	{
		return self != null && self.checkShadowcloak(armor);
	}
	
	public static boolean hasDeepShadowcloak(ItemStack[] armor)
	{
		return self != null && self.checkDeepShadowcloak(armor);
	}
	
	//Has any level of Shadowcloak
	private boolean checkShadowcloak(ItemStack[] armor)
	{
		return EnchantmentHelper.getEnchantmentLevel(this, armor[0]) > 0 ||
			EnchantmentHelper.getEnchantmentLevel(this, armor[1]) > 0 ||
			EnchantmentHelper.getEnchantmentLevel(this, armor[2]) > 0 ||
			EnchantmentHelper.getEnchantmentLevel(this, armor[3]) > 0;
	}
	
	//Has maximum level of Shadowcloak
	private boolean checkDeepShadowcloak(ItemStack[] armor)
	{
		return EnchantmentHelper.getEnchantmentLevel(this, armor[0]) >= maxLevel ||
				EnchantmentHelper.getEnchantmentLevel(this, armor[1]) >= maxLevel ||
				EnchantmentHelper.getEnchantmentLevel(this, armor[2]) >= maxLevel ||
				EnchantmentHelper.getEnchantmentLevel(this, armor[3]) >= maxLevel;
	}
}
