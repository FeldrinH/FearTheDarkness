package feldrinh.shadowmancy.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class refluxRecipeBasic implements IRefluxRecipe
{
	private final Item baseItem;
	private final ItemStack resultItemStack;
	/**The speed at which shadow is consumed (per tick).*/
	private final float shadowSpeed;
	/**The speed at which progress is accumulated (per tick) and */
	private final float progressSpeed;
	
	public refluxRecipeBasic(Item baseItem, ItemStack resultItemStack, float progressSpeed, float shadowSpeed)
	{
		this.baseItem = baseItem;
		this.resultItemStack = resultItemStack;
		this.progressSpeed = progressSpeed;
		this.shadowSpeed = shadowSpeed;
	}
	
	@Override
	public float getShadowSpeed()
	{
		return shadowSpeed;
	}
	
	@Override
	public float getProgressSpeed()
	{
		return progressSpeed;
	}
	
	@Override
	public boolean baseMatches(ItemStack base)
	{
		//Better MC/Forge infrastructure integration
		return (base.getItem() == baseItem && base.stackSize == 1);
	}
	
	@Override
	public boolean resultMatches(ItemStack result)
	{
		//More fuzziness?
		return ItemStack.areItemStacksEqual(resultItemStack, result);
	}
}
