package de.dbone.betterstorage.tile.entity;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.config.GlobalConfig;
import de.dbone.betterstorage.container.ContainerBetterStorage;
import de.dbone.betterstorage.container.ContainerCraftingStation;
import de.dbone.betterstorage.inventory.InventoryCraftingStation;
import de.dbone.betterstorage.inventory.InventoryTileEntity;
import de.dbone.betterstorage.misc.Constants;
import de.dbone.betterstorage.utils.GuiHandler;
import de.dbone.betterstorage.utils.NbtUtils;
import de.dbone.betterstorage.utils.WorldUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class TileEntityCraftingStation extends TileEntityContainer implements IInventory, ISidedInventory {
	
	public ItemStack[] crafting;
	public ItemStack[] output;
	
	private InventoryCraftingStation stationInventory;
	
	@Override
	protected int getSizeContents() { return 18; }
	
	@Override
	public String getName() { return Constants.containerCraftingStation; }
	
	@Override
	public InventoryTileEntity makePlayerInventory() {
		// Workaround because instance variables get initialized AFTER the
		// parent constructor. This gets called IN the parent constructor.
		crafting = new ItemStack[9];
		output = new ItemStack[9];
		stationInventory = new InventoryCraftingStation(this);
		return new InventoryTileEntity(this, stationInventory);
	}
	
	@Override
	public ContainerBetterStorage createContainer(EntityPlayer player) {
		return new ContainerCraftingStation(player, getPlayerInventory());
	}
	
	@Override
	public void updateEntity() { stationInventory.update(); }
	
	@Override
	public void dropContents() {
		for (ItemStack stack : crafting)
			WorldUtils.dropStackFromBlock(worldObj, pos.getX(), pos.getY(), pos.getZ(), stack);
		if (stationInventory.outputIsReal)
			for (ItemStack stack : output)
				WorldUtils.dropStackFromBlock(worldObj, pos.getX(), pos.getY(), pos.getZ(), stack);
		super.dropContents();
	}
	
	@Override
	protected boolean acceptsRedstoneSignal() { return true; }
	@Override
	protected boolean requiresStrongSignal() { return true; }
	
	// IInventory implementation
	@Override
	public boolean hasCustomName() { return !shouldLocalizeTitle(); }
	@Override
	public int getInventoryStackLimit() { return 64; }
	@Override
	public int getSizeInventory() { return (getPlayerInventory().getSizeInventory() - 9); }
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return getPlayerInventory().getStackInSlot(slot + 9); }
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		getPlayerInventory().setInventorySlotContents(slot + 9, stack); }
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		return getPlayerInventory().decrStackSize(slot + 9, amount); }
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return getPlayerInventory().isItemValidForSlot(slot + 9, stack); }
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return getPlayerInventory().isUseableByPlayer(player); }

	@Override
	public void markDirty() { stationInventory.markDirty(); }

	@Override
	public void openInventory(EntityPlayer player) { }

	@Override
	public void closeInventory(EntityPlayer player) { }
	
	// ISidedInventory implementation
	
	private static int[] slotsAny = { 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26 };
	private static int[] slotsBottom = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
	

	//TODO Fix Comment
	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return true;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return (GlobalConfig.enableStationAutoCraftingSetting.getValue() && stationInventory.canTake(null));
	}
	
	//@Override
	public int[] getAccessibleSlotsFromSide(int side) { return ((side == 0) ? slotsBottom : slotsAny); }
	//@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) { return (side != 0); }
	//@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return ((side != 0) || (GlobalConfig.enableStationAutoCraftingSetting.getValue() && stationInventory.canTake(null)));
	}
	
	// Reading from / writing to NBT
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NbtUtils.readItems(crafting, compound.getTagList("Crafting", NBT.TAG_COMPOUND));
		if (compound.hasKey("Output"))
			NbtUtils.readItems(output, compound.getTagList("Output", NBT.TAG_COMPOUND));
		stationInventory.progress = compound.getInteger("progress");
		stationInventory.outputIsReal = compound.hasKey("Output");
		// Update the inventory, causes ghost output to be initialized.
		stationInventory.inputChanged();
	}
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("Crafting", NbtUtils.writeItems(crafting));
		if (stationInventory.outputIsReal)
			compound.setTag("Output", NbtUtils.writeItems(output));
		compound.setInteger("progress", stationInventory.progress);
	}
	
	@Override
	public void openGui(EntityPlayer playerIn, World worldIn, BlockPos pos) {
		playerIn.openGui(BetterStorage.instance, GuiHandler.GUI_CRAFTING_STATION, worldIn, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public IChatComponent getDisplayName() {
		return new ChatComponentText(getName());
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		return null;
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
		return stationInventory.getFieldCount();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}	
}
