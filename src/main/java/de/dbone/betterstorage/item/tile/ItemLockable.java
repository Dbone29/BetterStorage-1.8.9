package de.dbone.betterstorage.item.tile;

import de.dbone.betterstorage.misc.Constants;
import de.dbone.betterstorage.tile.ContainerMaterial;
import de.dbone.betterstorage.tile.TileLockable;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemLockable extends ItemBlock {
	
	public ItemLockable(Block block) {
		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	@Override
	public int getMetadata(int damage) { return damage; }
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (!((TileLockable)Block.getBlockFromItem(stack.getItem())).hasMaterial())
			return super.getItemStackDisplayName(stack);
		
		ContainerMaterial material = ContainerMaterial.getMaterial(stack, ContainerMaterial.iron);
		
		String name = StatCollector.translateToLocal(getUnlocalizedName(stack) + ".name.full");
		String materialName = StatCollector.translateToLocal("material." + Constants.modId + "." + material.name);
		return name.replace("%MATERIAL%", materialName);
	}
	
}
