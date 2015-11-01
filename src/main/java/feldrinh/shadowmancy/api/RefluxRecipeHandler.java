package feldrinh.shadowmancy.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;

import net.minecraft.item.ItemStack;

public class RefluxRecipeHandler 
{
	private static final List<IRefluxRecipe> recipeList = new ArrayList<IRefluxRecipe>();
	
	public static IRefluxRecipe getRecipeFromItem(ItemStack baseItem)
	{
		for(IRefluxRecipe recipe : recipeList)
		{
			if (recipe.baseMatches(baseItem))
			{
				return recipe;
			}
		}
		return null;
	}
	
	public static IRefluxRecipe getRecipeToItem(ItemStack goalItem)
	{
		throw new NotImplementedException("Not yet necessary.");
	}
}
