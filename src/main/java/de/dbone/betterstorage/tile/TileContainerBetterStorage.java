package de.dbone.betterstorage.tile;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.attachment.IHasAttachments;
import de.dbone.betterstorage.misc.Constants;
import de.dbone.betterstorage.tile.entity.TileEntityContainer;
import de.dbone.betterstorage.utils.MiscUtils;
import de.dbone.betterstorage.utils.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
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
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class TileContainerBetterStorage extends BlockContainer {
	
	private String name;
	
	public TileContainerBetterStorage(Material material) {
		
		super(material);
		isBlockContainer = true;
		
		setCreativeTab(BetterStorage.creativeTab);
		setUnlocalizedName(Constants.modId + "." + getTileName());
		registerBlock();		
	}
	
	/** Returns the name of this tile, for example "craftingStation". */
	public String getTileName() {
		return ((name != null) ? name : (name = MiscUtils.getName(this)));
	}
	
	/** Returns the item class used for this block.*/
	protected Class<? extends ItemBlock> getItemClass() { return ItemBlock.class; }
	
	/** Registers the block in the GameRegistry. */
	protected void registerBlock() {
		Class<? extends Item> itemClass = getItemClass();
		
		if (itemClass != null) {
			GameRegistry.registerBlock(this, (Class<? extends ItemBlock>)itemClass, getTileName());
		} else {
			GameRegistry.registerBlock(this, null, getTileName());
		}
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
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		getContainer(worldIn, pos).onBlockPlaced(placer, stack);
	}
	
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
}
