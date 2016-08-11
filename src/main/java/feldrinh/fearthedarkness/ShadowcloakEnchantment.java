package feldrinh.fearthedarkness;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class ShadowcloakEnchantment extends Enchantment
{
	protected ShadowcloakEnchantment(int id, int weight, int maxLevel)
	{
		super(id, weight, EnumEnchantmentType.armor);
		this.maxLevel = maxLevel;
	}
	
	private final int maxLevel;
	
	@Override
	public int getMaxLevel()
	{
		return maxLevel;
	}
}
