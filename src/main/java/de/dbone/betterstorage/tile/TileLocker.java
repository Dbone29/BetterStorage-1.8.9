package de.dbone.betterstorage.tile;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.tile.entity.TileEntityLocker;
import de.dbone.betterstorage.tile.entity.TileEntityReinforcedChest;
import de.dbone.betterstorage.utils.GuiHandler;
import de.dbone.betterstorage.utils.WorldUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TileLocker extends TileContainerBetterStorage {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final PropertyBool LARGE = PropertyBool.create("large");
	public static final PropertyBool MIRROR = PropertyBool.create("mirror");
	
	public TileLocker() {
		super(Material.wood);
		
		setHardness(2.5f);
		setStepSound(soundTypeWood);
		setBlockBounds(1 / 16.0F, 1 / 16.0F, 1 / 16.0F, 15 / 16.0F, 15 / 16.0F, 15 / 16.0F);

		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(MIRROR, false).withProperty(LARGE, false));
		
		setHarvestLevel("axe", 0);
	}
	
	@Override
	public boolean isOpaqueCube() { return false; }
	
	@Override
	public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side) {
		TileEntityLocker locker = WorldUtils.get(world, pos, TileEntityLocker.class);
		return ((locker == null) || (locker.getOrientation() != side));
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		if(WorldUtils.get(worldIn, pos, TileEntityLocker.class) == null) return;
		float minX = 0, minY = 0, minZ = 0;
		float maxX = 1, maxY = 1, maxZ = 1;
		switch (WorldUtils.get(worldIn, pos, TileEntityLocker.class).getOrientation()) {
			case EAST:	maxX -= 1.0F / 16; break;
			case WEST:	minX += 1.0F / 16; break;
			case SOUTH:	maxZ -= 1.0F / 16; break;
			case NORTH:	minZ += 1.0F / 16; break;
			default:
		}
		setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		final EnumFacing oriantation = placer.getHorizontalFacing().getOpposite();
		boolean mirror;
		
		switch(oriantation) {
		case NORTH:	mirror = placer.posX > (pos.getX() + 0.5); break;
		case SOUTH:	mirror = placer.posX < (pos.getX() + 0.5); break;
		case EAST:	mirror = placer.posZ > (pos.getZ() + 0.5); break;
		case WEST:	mirror = placer.posZ < (pos.getZ() + 0.5); break;
		default:	mirror = false;
		}
		
		worldIn.setBlockState(pos, state.withProperty(FACING, oriantation).withProperty(MIRROR, mirror).withProperty(LARGE, false), 2);
		final TileEntityLocker entity = ((TileEntityLocker) worldIn.getTileEntity(pos));
		entity.setOrientation(oriantation);
		entity.mirror = mirror;
		entity.onBlockPlaced(placer, stack);
	}
		
	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int result = state.getValue(FACING).getIndex() - 2;
		
		if(result < 0)				result = 0;
		if(state.getValue(LARGE))	result = result + 8;		
		if(state.getValue(MIRROR))	result = result + 4;
		
		return result;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) { return this.getDefaultState(); }
	
	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] {FACING, LARGE, MIRROR});
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityLocker();
	}
	
}
