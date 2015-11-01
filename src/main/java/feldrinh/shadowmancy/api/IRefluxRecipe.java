package feldrinh.shadowmancy.api;

import net.minecraft.item.ItemStack;

public interface IRefluxRecipe
{
	/**Shadow consumption per tick*/
	public float getShadowSpeed();
	
	/**Recipe progress per tick*/
	public float getProgressSpeed();
	
	/**Does the base of the recipe match?*/
	public boolean baseMatches(ItemStack base);
	
	/**Does the result of the recipe match?*/
	public boolean resultMatches(ItemStack result);
	
	/**Return a copy of the result ItemStack for output by the Shadow Reflux Table*/
	public ItemStack getNewResult(int curStackSize);
}
