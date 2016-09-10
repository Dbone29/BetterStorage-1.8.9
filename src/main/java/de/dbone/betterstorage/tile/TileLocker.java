package de.dbone.betterstorage.tile;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.tile.entity.TileEntityLocker;
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
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TileLocker extends TileContainerBetterStorage {
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	public TileLocker() {
		super(Material.wood);
		
		setHardness(2.5f);
		setStepSound(soundTypeWood);
		//setBlockBounds(1 / 16.0F, 1 / 16.0F, 1 / 16.0F, 15 / 16.0F, 15 / 16.0F, 15 / 16.0F);

		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		
		setHarvestLevel("axe", 0);
	}
	
	@Override
	public boolean isOpaqueCube() { return false; }
	/*@Override
	public boolean renderAsNormalBlock() { return false; }*/
	
	/*@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() { return ClientProxy.lockerRenderId; }*/
	
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
			case EAST:
				maxX -= 1.0F / 16;
				break;
			case WEST:
				minX += 1.0F / 16;
				break;
			case SOUTH:
				maxZ -= 1.0F / 16;
				break;
			case NORTH:
				minZ += 1.0F / 16;
				break;
			default: break;
		}
		setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, getFacingFromEntity(worldIn, pos, placer)), 2);
		((TileEntityLocker) worldIn.getTileEntity(pos)).setOrientation(getFacingFromEntity(worldIn, pos, placer));
		((TileEntityLocker) worldIn.getTileEntity(pos)).onBlockPlaced(placer, stack);
	}
	
	public EnumFacing getFacingFromEntity(World worldIn, BlockPos clickedBlock, EntityLivingBase entityIn)
    {
        if (MathHelper.abs((float)entityIn.posX - clickedBlock.getX()) < 2.0F && MathHelper.abs((float)entityIn.posZ - clickedBlock.getZ()) < 2.0F)
        {
            double d0 = entityIn.posY + entityIn.getEyeHeight();

            if (d0 - clickedBlock.getY() > 2.0D)
            {
                return EnumFacing.UP;
            }

            if (clickedBlock.getY() - d0 > 0.0D)
            {
                return EnumFacing.DOWN;
            }
        }

        return entityIn.getHorizontalFacing().getOpposite();
    } 
		
	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
	        byte b0 = 0;
	        int i = b0 | state.getValue(FACING).getIndex();
	       
	        return i;
	}
	@Override
	public IBlockState getStateFromMeta(int meta) { return this.getDefaultState(); }
	
	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] {FACING});
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityLocker();
	}
	
}
