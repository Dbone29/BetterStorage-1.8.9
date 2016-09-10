package de.dbone.betterstorage.tile;

import net.minecraft.block.material.Material;

public class TileFlintBlock extends TileBetterStorage {
	
	public TileFlintBlock() {
		super(Material.rock);
		
		setHardness(3.0F);
		setResistance(6.0F);
		setStepSound(soundTypeStone);
	}
	
	/*@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon(Constants.modId + ":" + getTileName());
	}*/
	
}
