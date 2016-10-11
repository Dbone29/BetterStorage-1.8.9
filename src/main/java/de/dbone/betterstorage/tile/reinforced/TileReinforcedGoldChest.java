package de.dbone.betterstorage.tile.reinforced;

import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;

public class TileReinforcedGoldChest extends TileReinforcedIronChest {
	
	public TileReinforcedGoldChest(Material material) {
		super(material);
		
		setHardness(8.0F);
		setResistance(20.0F);
		setStepSound(soundTypeWood);
		
		setUnlocalizedName("Reinforced Gold Chest");

		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		
		setHarvestLevel("axe", 2);
	}
	public TileReinforcedGoldChest() {
		this(Material.wood);
	}
	
	@Override
	public boolean hasMaterial() {
		return false;
	}
}
