package de.dbone.betterstorage.tile.entity;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.container.ContainerBetterStorage;
import de.dbone.betterstorage.misc.Constants;
import de.dbone.betterstorage.misc.Resources;
import de.dbone.betterstorage.utils.DirectionUtils;
import de.dbone.betterstorage.utils.GuiHandler;
import de.dbone.betterstorage.utils.WorldUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityLocker extends TileEntityLockable {
	
	private static final EnumFacing[] neighbors = { EnumFacing.DOWN, EnumFacing.UP };
	
	public InventoryBasic inventory;
	public boolean mirror = false;
	
	public TileEntityLocker() {
		inventory = new InventoryBasic("Locker", false, 27);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return WorldUtils.getAABB(this, 0, 0, 0, 0, 1, 0);
	}
	
	@SideOnly(Side.CLIENT)
	public ResourceLocation getResource() {
		return (isConnected() ? Resources.textureLockerLarge : Resources.textureLocker);
	}
	
	@Override
	public boolean canHaveLock() { return false; }
	@Override
	public boolean canHaveMaterial() { return false; }
	@Override
	public void setAttachmentPosition() {  }
	
	@Override
	public EnumFacing[] getPossibleNeighbors() { return neighbors; }
	
	@Override
	protected String getConnectableName() { return Constants.containerLocker; }
	
	@Override
	public boolean canConnect(TileEntityConnectable connectable) {
		if (!(connectable instanceof TileEntityLocker)) return false;
		TileEntityLocker locker = (TileEntityLocker)connectable;
		return (super.canConnect(connectable) && (mirror == locker.mirror));
	}
	
	@Override
	public void onBlockPlacedBeforeCheckingConnections(EntityLivingBase player, ItemStack stack) {
		super.onBlockPlacedBeforeCheckingConnections(player, stack);
		double angle = DirectionUtils.getRotation(getOrientation().getOpposite());
		double yaw = ((player.rotationYaw % 360) + 360) % 360;
		mirror = (DirectionUtils.angleDifference(angle, yaw) > 0);
		setAttachmentPosition();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {	
		if (getOrientation() != side) return true;
		return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
	}
	
	@Override
	public void openGui(EntityPlayer playerIn, World worldIn, BlockPos pos) {
		if(((TileEntityLocker) worldIn.getTileEntity(pos)).isConnected())
			playerIn.openGui(BetterStorage.instance, GuiHandler.GUI_LOCKER_LARGE, worldIn, pos.getX(), pos.getY(), pos.getZ());
		else
			playerIn.openGui(BetterStorage.instance, GuiHandler.GUI_LOCKER, worldIn, pos.getX(), pos.getY(), pos.getZ());
	}
	
	// TileEntity synchronization

	@Override
	public NBTTagCompound getDescriptionPacketData(NBTTagCompound compound) {
		compound = super.getDescriptionPacketData(compound);
		compound.setBoolean("mirror", mirror);
		return compound;
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
		NBTTagCompound compound = packet.getNbtCompound();
		mirror = compound.getBoolean("mirror");
		setAttachmentPosition();
	}
	
	// Reading from / writing to NBT
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		mirror = compound.getBoolean("mirror");
		setAttachmentPosition();
	}
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean("mirror", mirror);
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
