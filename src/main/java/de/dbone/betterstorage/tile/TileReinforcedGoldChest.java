package de.dbone.betterstorage.tile;

import de.dbone.betterstorage.item.tile.ItemLockable;
import de.dbone.betterstorage.tile.entity.TileEntityReinforcedChest;
import de.dbone.betterstorage.utils.WorldUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TileReinforcedGoldChest extends TileReinforcedChest {
	
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
