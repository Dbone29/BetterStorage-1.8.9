package de.dbone.betterstorage.tile;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.item.tile.ItemLockable;
import de.dbone.betterstorage.tile.entity.TileEntityReinforcedChest;
import de.dbone.betterstorage.utils.GuiHandler;
import de.dbone.betterstorage.utils.WorldUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TileReinforcedChest extends TileLockable {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	public TileReinforcedChest(Material material) {
		super(material);
		
		setHardness(8.0F);
		setResistance(20.0F);
		setStepSound(soundTypeWood);

		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		
		setHarvestLevel("axe", 2);
	}
	public TileReinforcedChest() {
		this(Material.wood);
	}
	
	@Override
	public Class<? extends ItemBlock> getItemClass() { return ItemLockable.class; }
	
	@Override
	public boolean isOpaqueCube() { return false; }
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		final TileEntityReinforcedChest chest = WorldUtils.get(worldIn, pos, TileEntityReinforcedChest.class);
		if (chest != null && chest.isConnected()) {
			switch(chest.getConnected()) {
			case NORTH:	setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F); break;
			case SOUTH:	setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F); break;
			case WEST:	setBlockBounds(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F); break;
			case EAST:	setBlockBounds(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F); break;
			default:	setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
			}
		} else setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
		((TileEntityReinforcedChest) worldIn.getTileEntity(pos)).onBlockPlaced(placer, stack);
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
		
	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
	    return state.getValue(FACING).getIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState();
	}
	
	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] {FACING});
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityReinforcedChest();
	}
	
}
