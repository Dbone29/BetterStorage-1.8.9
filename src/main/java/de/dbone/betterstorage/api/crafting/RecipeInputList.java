package de.dbone.betterstorage.api.crafting;

import java.util.List;

import de.dbone.betterstorage.api.BetterStorageUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RecipeInputList extends RecipeInputBase {
	
	private final List<ItemStack> items;
	
	public RecipeInputList(List<ItemStack> items) {
		this.items = items;
	}
	
	@Override
	public int getAmount() { return 1; }
	
	@Override
	public boolean matches(ItemStack stack) {
		for (ItemStack item : items)
			if (BetterStorageUtils.wildcardMatch(item, stack))
				return true;
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public List<ItemStack> getPossibleMatches() { return items; }
	
}
