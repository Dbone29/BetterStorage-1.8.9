package de.dbone.betterstorage.tile.entity;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.config.GlobalConfig;
import de.dbone.betterstorage.misc.Constants;
import de.dbone.betterstorage.utils.GuiHandler;
import de.dbone.betterstorage.utils.WorldUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityReinforcedChest extends TileEntityLockable {
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return WorldUtils.getAABB(this, 0, 0, 0, 1, 1, 1);
	}
	
	@SideOnly(Side.CLIENT)
	public ResourceLocation getResource() {
		return getMaterial().getChestResource(isConnected());
	}
	
	@Override
	public void setAttachmentPosition() {
		double x = (!isConnected() ? 8 :
		            (((getOrientation() == EnumFacing.WEST) ||
		              (getOrientation() == EnumFacing.SOUTH)) ? 0 : 16));
		lockAttachment.setBox(x, 6.5, 0.5, 7, 7, 1);
	}
	
	// TileEntityContainer stuff
	
	@Override
	public int getColumns() { return BetterStorage.globalConfig.getInteger(GlobalConfig.reinforcedColumns); }
	
	// TileEntityConnactable stuff
	
	private static EnumFacing[] neighbors = { EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.WEST, EnumFacing.SOUTH };
	
	@Override
	protected String getConnectableName() { return Constants.containerReinforcedChest; }
	
	@Override
	public EnumFacing[] getPossibleNeighbors() { return neighbors; }
	
	@Override
	public boolean canConnect(TileEntityConnectable connectable) {
		return ((connectable instanceof TileEntityReinforcedChest) && super.canConnect(connectable) &&
		        (((pos.getX() != connectable.getPos().getX()) && ((getOrientation() == EnumFacing.SOUTH) ||
		                                             (getOrientation() == EnumFacing.NORTH))) ||
		         ((pos.getZ() != connectable.getPos().getZ()) && ((getOrientation() == EnumFacing.EAST) ||
		                                             (getOrientation() == EnumFacing.WEST)))));
	}
	
	@Override
	public void openGui(EntityPlayer playerIn, World worldIn, BlockPos pos) {
		if(((TileEntityReinforcedChest) worldIn.getTileEntity(pos)).isConnected())
			playerIn.openGui(BetterStorage.instance, GuiHandler.GUI_REINFORCED_CHEST_LARGE, worldIn, pos.getX(), pos.getY(), pos.getZ());
		else
			playerIn.openGui(BetterStorage.instance, GuiHandler.GUI_REINFORCED_CHEST, worldIn, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasCustomName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IChatComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
