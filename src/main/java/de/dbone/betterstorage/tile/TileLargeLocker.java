package de.dbone.betterstorage.tile;

import de.dbone.betterstorage.attachment.IHasAttachments;
import de.dbone.betterstorage.misc.Constants;
import de.dbone.betterstorage.tile.entity.TileEntityContainer;
import de.dbone.betterstorage.tile.entity.TileEntityLocker;
import de.dbone.betterstorage.utils.MiscUtils;
import de.dbone.betterstorage.utils.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileLargeLocker extends BlockContainer {
	
	private String name = "largeLocker";

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final PropertyBool MIRROR = PropertyBool.create("mirror");
	
	public TileLargeLocker() {
		super(Material.wood);
		

		setUnlocalizedName(Constants.modId + "." + getTileName());
		GameRegistry.registerBlock(this, null, getTileName());
		
		setHardness(2.5f);
		setStepSound(soundTypeWood);
		setBlockBounds(1 / 16.0F, 1 / 16.0F, 1 / 16.0F, 15 / 16.0F, 15 / 16.0F, 15 / 16.0F);

		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(MIRROR, false));
		
		setHarvestLevel("axe", 0);
	}
	
	/** Returns the name of this tile, for example "craftingStation". */
	public String getTileName() {
		return ((name != null) ? name : (name = MiscUtils.getName(this)));
	}
	
	@Override
	public int getRenderType() {
		return 3;
	}
	
	
	
	
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
        TileEntity te = worldIn.getTileEntity(pos);
        return ((te != null) ? te.receiveClientEvent(eventID, eventParam) : false);
	}

	// Pass actions to TileEntityContainer
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {		

		return getContainer(worldIn, pos).onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
	}
	
	@Override
	public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		if (!getContainer(world, pos).onBlockBreak(player)) return false;
		return super.removedByPlayer(world, pos, player, willHarvest);
	}
	
	/*	
	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
		TileEntityContainer container = getContainer(world, x, y, z);
		if (container != null) container.onBlockDestroyed();
	}*/
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
		TileEntityContainer container = getContainer(world, pos);
		if (container instanceof IHasAttachments) {
			ItemStack pick = ((IHasAttachments)container).getAttachments().pick(target);
			if (pick != null) return pick;
		}
		ItemStack pick = super.getPickBlock(target, world, pos);
		return container.onPickBlock(pick, target);
	}
	
	@Override
	public int getComparatorInputOverride(World worldIn, BlockPos pos) {
		return TileEntityContainer.getContainerComparatorSignalStrength(worldIn, pos.getX(), pos.getY(), pos.getZ());
	}
	
	private TileEntityContainer getContainer(World world, BlockPos pos) {
		return WorldUtils.get(world, pos, TileEntityContainer.class);
	}
	
	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		TileEntityContainer container = getContainer(worldIn, pos);
		if (container != null) container.onNeighborUpdate(neighborBlock);
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
		
		worldIn.setBlockState(pos, state.withProperty(FACING, oriantation).withProperty(MIRROR, mirror), 2);
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
		if(state.getValue(MIRROR))	result = result + 4;
		
		return result;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) { return this.getDefaultState(); }
	
	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] {FACING, MIRROR});
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityLocker();
	}

}
