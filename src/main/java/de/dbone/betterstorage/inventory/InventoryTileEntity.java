package de.dbone.betterstorage.inventory;

import de.dbone.betterstorage.tile.entity.TileEntityContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;

/** An inventory that is connected to a TileEntityInventoy. */
public class InventoryTileEntity extends InventoryBetterStorage {
	
	public final TileEntityContainer mainTileEntity;
	public final TileEntityContainer[] tileEntities;
	public final IInventory inventory;
	public final int columns, rows;
	
	public InventoryTileEntity(TileEntityContainer mainTileEntity, TileEntityContainer[] tileEntities, IInventory inventory) {
		this.mainTileEntity = mainTileEntity;
		this.tileEntities = tileEntities;
		this.inventory = inventory;
		
		columns = mainTileEntity.getColumns();
		rows = inventory.getSizeInventory() / columns;
	}
	public InventoryTileEntity(TileEntityContainer mainTileEntity, TileEntityContainer... tileEntities) {
		this(mainTileEntity, tileEntities, new InventoryStacks(getAllContents(tileEntities)));
	}
	public InventoryTileEntity(TileEntityContainer tileEntity, IInventory inventory) {
		this(tileEntity, new TileEntityContainer[]{ tileEntity }, inventory);
	}
	public InventoryTileEntity(TileEntityContainer tileEntity) {
		this(tileEntity, new InventoryStacks(tileEntity.contents));
	}
	
	private static ItemStack[][] getAllContents(TileEntityContainer... tileEntities) {
		ItemStack[][] allContents = new ItemStack[tileEntities.length][];
		for (int i = 0; i < tileEntities.length; i++)
			allContents[i] = tileEntities[i].contents;
		return allContents;
	}
	
	/*@Override
	public String getInventoryName() { return mainTileEntity.getContainerTitle(); }*/

	@Override
	public boolean hasCustomName() {
		return !mainTileEntity.shouldLocalizeTitle();
	}
	
	@Override
	public int getSizeInventory() { return inventory.getSizeInventory(); }
	@Override
	public ItemStack getStackInSlot(int slot) { return inventory.getStackInSlot(slot); }
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory.setInventorySlotContents(slot, stack);
		markDirty();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return inventory.isItemValidForSlot(slot, stack);
	}
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		for (TileEntityContainer tileEntity : tileEntities)
			if (!tileEntity.canPlayerUseContainer(player))
				return false;
		return true;
	}
	
	@Override
	public void openInventory(EntityPlayer player) {
		mainTileEntity.onContainerOpened();		
	}
	@Override
	public void closeInventory(EntityPlayer player) {
		mainTileEntity.onContainerClosed();		
	}
	
	@Override
	public void markDirty() {
		inventory.markDirty();
		for (TileEntityContainer te : tileEntities)
			te.markDirtySuper();
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
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IChatComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
