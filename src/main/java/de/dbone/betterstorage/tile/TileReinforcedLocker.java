package de.dbone.betterstorage.tile;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.item.tile.ItemLockable;
import de.dbone.betterstorage.tile.entity.TileEntityLocker;
import de.dbone.betterstorage.tile.entity.TileEntityReinforcedLocker;
import de.dbone.betterstorage.utils.GuiHandler;
import de.dbone.betterstorage.utils.WorldUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TileReinforcedLocker extends TileLockable {
	
	public TileReinforcedLocker(Material material) {
		super(material);
		
		setHardness(8.0F);
		setResistance(20.0F);
		setStepSound(soundTypeWood);
		setBlockBounds(1 / 16.0F, 1 / 16.0F, 1 / 16.0F, 15 / 16.0F, 15 / 16.0F, 15 / 16.0F);
		
		setHarvestLevel("axe", 2);
	}
	public TileReinforcedLocker() {
		this(Material.wood);
	}
	
	@Override
	public Class<? extends ItemBlock> getItemClass() { return ItemLockable.class; }
	
	@Override
	public boolean isOpaqueCube() { return false; }
	
	@Override
	public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side) {
		TileEntityLocker locker = WorldUtils.get(world, pos, TileEntityLocker.class);
		return ((locker == null) || (locker.getOrientation() != side));
	}
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		super.setBlockBoundsBasedOnState(worldIn, pos);
		
		float minX = 0, minY = 0, minZ = 0;
		float maxX = 1, maxY = 1, maxZ = 1;
		switch (WorldUtils.get(worldIn, pos, TileEntityLocker.class).getOrientation()) {
			case EAST: maxX -= 1.0F / 16; break;
			case WEST: minX += 1.0F / 16; break;
			case SOUTH: maxZ -= 1.0F / 16; break;
			case NORTH: minZ += 1.0F / 16; break;
			default: break;
		}
		setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityReinforcedLocker();
	}		
}
