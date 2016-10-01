package de.dbone.betterstorage.tile;

import java.util.List;
import java.util.Random;

import de.dbone.betterstorage.api.BetterStorageEnchantment;
import de.dbone.betterstorage.attachment.Attachments;
import de.dbone.betterstorage.attachment.EnumAttachmentInteraction;
import de.dbone.betterstorage.attachment.IHasAttachments;
import de.dbone.betterstorage.tile.entity.TileEntityLockable;
import de.dbone.betterstorage.utils.WorldUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class TileLockable extends TileContainerBetterStorage {
	
	protected TileLockable(Material material) {
		super(material);
	}
	
	public boolean hasMaterial() { return true; }
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		if (!hasMaterial()) super.getSubBlocks(item, tab, list);
		else for (ContainerMaterial material : ContainerMaterial.getMaterials())
			list.add(material.setMaterial(new ItemStack(item, 1, 0)));
	}
	
	@Override
	public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		if (hasMaterial() && !player.capabilities.isCreativeMode)
			dropBlockAsItem(world, pos, world.getBlockState(pos), 0);//dropBlockAsItem(world, pos, WorldUtils.get(world, pos, TileEntityLockable.class).material.setMaterial(new ItemStack(this, 1, 0)));
		return super.removedByPlayer(world, pos, player, willHarvest);
	}
	
	@Override
	public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
		if (hasMaterial())
			//dropBlockAsItem(world, pos, WorldUtils.get(world, pos, TileEntityLockable.class).material.setMaterial(new ItemStack(this, 1, 0)));
		super.onBlockExploded(world, pos, explosion);
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return (hasMaterial() ? 0 : 1); 
	}
	
	@Override
	public float getBlockHardness(World worldIn, BlockPos pos) {
		TileEntityLockable lockable = WorldUtils.get(worldIn, pos, TileEntityLockable.class);
		if ((lockable != null) && (lockable.getLock() != null)) return -1;
		else return super.getBlockHardness(worldIn, pos);
	}
	
	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		float modifier = 1.0F;
		TileEntityLockable lockable = WorldUtils.get(world, pos, TileEntityLockable.class);
		if (lockable != null) {
			int persistance = BetterStorageEnchantment.getLevel(lockable.getLock(), "persistance");
			if (persistance > 0) modifier += Math.pow(2, persistance);
		}
		return super.getExplosionResistance(exploder) * modifier;
	}
	
	/*@Override
	public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
		return WorldUtils.get(worldIn, pos, IHasAttachments.class).getAttachments().rayTrace(worldIn, pos.getX(), pos.getY(), pos.getZ(), start, end);
	}*/
	
	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		// TODO: See if we can make a pull request to Forge to get PlayerInteractEvent to fire for left click on client.
		Attachments attachments = WorldUtils.get(worldIn, pos, IHasAttachments.class).getAttachments();
		boolean abort = attachments.interact(WorldUtils.rayTrace(playerIn, 1.0F), playerIn, EnumAttachmentInteraction.attack);
		// TODO: Abort block breaking? playerController.resetBlockBreaking doesn't seem to do the job.
	}
		
	@Override
	public boolean hasComparatorInputOverride() { return true; }
	
	// Trigger enchantment related
	
	@Override
	public boolean canProvidePower() { return true; }
	
	@Override
	public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
		return (WorldUtils.get(worldIn, pos, TileEntityLockable.class).isPowered() ? 15 : 0);
	}
	
	@Override
	public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
		return getWeakPower(worldIn, pos, state, side);
	}	
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		WorldUtils.get(worldIn, pos, TileEntityLockable.class).setPowered(false);
	}
}
