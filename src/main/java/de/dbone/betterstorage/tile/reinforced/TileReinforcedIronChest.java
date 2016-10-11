package de.dbone.betterstorage.tile.reinforced;

import de.dbone.betterstorage.tile.TileReinforcedChest;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;

public class TileReinforcedIronChest extends TileReinforcedChest {
	
	public TileReinforcedIronChest(Material material) {
		super(material);
		
		setHardness(8.0F);
		setResistance(20.0F);
		setStepSound(soundTypeWood);
		
		setUnlocalizedName("Reinforced Iron Chest");

		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		
		setHarvestLevel("axe", 2);
	}
	public TileReinforcedIronChest() {
		this(Material.wood);
	}
	
	@Override
	public boolean hasMaterial() {
		return false;
	}
}