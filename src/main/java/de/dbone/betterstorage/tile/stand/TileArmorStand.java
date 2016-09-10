package de.dbone.betterstorage.tile.stand;

import java.util.Random;

import de.dbone.betterstorage.proxy.ClientProxy;
import de.dbone.betterstorage.tile.TileContainerBetterStorage;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileArmorStand extends TileContainerBetterStorage {
	
	public TileArmorStand() {
		super(Material.rock);
		
		setHardness(2.5f);
		setBlockBounds(2 / 16.0F, 0, 2 / 16.0F, 14 / 16.0F, 2, 14 / 16.0F);
		
		setHarvestLevel("pickaxe", 0);
	}
	
	@Override
	public Class<? extends ItemBlock> getItemClass() { return ItemArmorStand.class; }
	
	/*@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon("stone_slab_top");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName() { return Constants.modId + ":armorStand"; }*/
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		int metadata = worldIn.getBlockState(pos).getBlock().getMetaFromState(worldIn.getBlockState(pos));
		if (metadata == 0) setBlockBounds(2 / 16.0F, 0, 2 / 16.0F, 14 / 16.0F, 2, 14 / 16.0F);
		else setBlockBounds(2 / 16.0F, -1, 2 / 16.0F, 14 / 16.0F, 1, 14 / 16.0F);
	}
	
	@Override
	public boolean isOpaqueCube() { return false; }
	//@Override
	public boolean renderAsNormalBlock() { return false; }
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() { return ClientProxy.armorStandRenderId; }
	
	//@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		return ((meta == 0) ? 1 : 0);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		// Set block above to armor stand with metadata 1.
		//worldIn.setBlock(pos.getX(), pos.getY() + 1, pos.getZ(), this, 1, SetBlockFlag.DEFAULT);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		int y = pos.getY();
		
		if (worldIn.isRemote) return true;
		if (worldIn.getBlockState(pos).getBlock().getMetaFromState(worldIn.getBlockState(pos)) > 0) { y -= 1; hitY += 1; }
		return super.onBlockActivated(worldIn, new BlockPos(pos.getX(), y, pos.getZ()), state, playerIn, side, hitX, hitY, hitZ);
	}
	
	/*@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		if (world.getBlockMetadata(x, y, z) > 0) { y -= 1; }
		return super.getPickBlock(target, world, x, y, z);
	}
	
	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		return world.setBlockToAir(x, y, z);
	}
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		if (meta > 0) return;
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		int metadata = world.getBlockMetadata(x, y, z);
		int targetY = y + ((metadata == 0) ? 1 : -1);
		int targetMeta = ((metadata == 0) ? 1 : 0);
		if ((world.getBlock(x, targetY, z) == this) &&
		    (world.getBlockMetadata(x, targetY, z) == targetMeta)) return;
		world.setBlockToAir(x, y, z);
		if (metadata == 0)
			dropBlockAsItem(world, x, y, z, metadata, 0);
	}*/
	
	@Override
	public boolean hasComparatorInputOverride() { return true; }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return ((meta == 0) ? new TileEntityArmorStand() : null);
	}
	
}
