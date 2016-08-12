package feldrinh.fearthedarkness;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class ShadowcloakEnchantment extends Enchantment
{
	protected ShadowcloakEnchantment(int id, int maxLevel)
	{
		super(id, 2, EnumEnchantmentType.armor);
		
		this.name = "shadowcloak";
		this.maxLevel = maxLevel;
		addToBookList(this);
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
		return EnchantmentHelper.getEnchantmentLevel(effectId, armor[0]) > 0 ||
			EnchantmentHelper.getEnchantmentLevel(effectId, armor[1]) > 0 ||
			EnchantmentHelper.getEnchantmentLevel(effectId, armor[2]) > 0 ||
			EnchantmentHelper.getEnchantmentLevel(effectId, armor[3]) > 0;
	}
	
	//Has maximum level of Shadowcloak
	private boolean checkDeepShadowcloak(ItemStack[] armor)
	{
		return EnchantmentHelper.getEnchantmentLevel(effectId, armor[0]) >= maxLevel ||
				EnchantmentHelper.getEnchantmentLevel(effectId, armor[1]) >= maxLevel ||
				EnchantmentHelper.getEnchantmentLevel(effectId, armor[2]) > maxLevel ||
				EnchantmentHelper.getEnchantmentLevel(effectId, armor[3]) > maxLevel;
	}
}
