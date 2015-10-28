package com.feldrinh.shadowmancy.api;

import net.minecraft.item.ItemStack;

public interface IRefluxRecipe
{
	public float getShadowSpeed();
	
	public float getProgressSpeed();
	
	public boolean baseMatches(ItemStack base);
	
	public boolean resultMatches(ItemStack result);
}
