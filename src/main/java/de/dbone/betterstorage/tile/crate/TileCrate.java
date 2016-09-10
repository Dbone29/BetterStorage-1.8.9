package de.dbone.betterstorage.tile.crate;

import de.dbone.betterstorage.item.tile.ItemTileBetterStorage;
import de.dbone.betterstorage.misc.ConnectedTexture;
import de.dbone.betterstorage.tile.TileContainerBetterStorage;
import de.dbone.betterstorage.utils.WorldUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import vazkii.botania.api.mana.ILaputaImmobile;
import net.minecraftforge.fml.common.Optional.Interface;

@Interface(modid = "Botania", iface = "vazkii.botania.api.mana.ILaputaImmobile", striprefs = true)
public class TileCrate extends TileContainerBetterStorage implements ILaputaImmobile {
	
	private ConnectedTexture texture = new ConnectedTextureCrate();
	
	public TileCrate() {
		super(Material.wood);
		
		setHardness(2.0f);
		setStepSound(soundTypeWood);
		
		setHarvestLevel("axe", 0);
	}
	
	@Override
	public Class<? extends ItemBlock> getItemClass() { return ItemTileBetterStorage.class; }
	
	public void onBlockPlacedExtended(World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ,
	                                  EntityLivingBase entity, ItemStack stack) {
		TileEntityCrate crate = WorldUtils.get(world, pos, TileEntityCrate.class);
		if (stack.hasDisplayName())
			crate.setCustomTitle(stack.getDisplayName());
		crate.attemptConnect(side.getOpposite());
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) return true;
		WorldUtils.get(worldIn, pos, TileEntityCrate.class).openGui(playerIn, worldIn, pos);
		return true;
	}
	
	@Override
	public boolean hasComparatorInputOverride() { return true; }
	
	private class ConnectedTextureCrate extends ConnectedTexture {
		@Override
		public boolean canConnect(IBlockAccess world, int x, int y, int z, EnumFacing side, EnumFacing connected) {
			if (world.getBlockState(new BlockPos(x, y, z)).getBlock() != TileCrate.this) return false;
			int offX = x + connected.getFrontOffsetX();
			int offY = y + connected.getFrontOffsetY();
			int offZ = z + connected.getFrontOffsetZ();
			
			if (offY <= 0) return false;
			
			TileEntityCrate connectedCrate = WorldUtils.get(world, new BlockPos(offX, offY, offZ), TileEntityCrate.class);
			if (connectedCrate == null) return false;
			TileEntityCrate crate = WorldUtils.get(world, new BlockPos(x, y, z), TileEntityCrate.class);
			return (crate.getID() == connectedCrate.getID() && !crate.equals(connectedCrate));
		}
	}

	@Override
	public boolean canMove(World world, BlockPos pos) {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCrate();
	}
}
