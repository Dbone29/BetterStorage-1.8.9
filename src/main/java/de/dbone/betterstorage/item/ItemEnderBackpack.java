package de.dbone.betterstorage.item;

import de.dbone.betterstorage.content.BetterStorageTiles;
import de.dbone.betterstorage.inventory.InventoryWrapper;
import de.dbone.betterstorage.misc.Constants;
import de.dbone.betterstorage.misc.PropertiesBackpack;
import de.dbone.betterstorage.misc.Resources;
import de.dbone.betterstorage.tile.TileBackpack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ItemEnderBackpack extends ItemBackpack {
	
	public ItemEnderBackpack() {
		super(ItemBackpack.material);
		setMaxDamage(0);
	}
	
	@Override
	public String getBackpackName() { return Constants.containerEnderBackpack; }
	
	@Override
	public int getBackpackRows() { return 3; }
	
	@Override
	protected int getDefaultColor() { return -1; }
	
	@Override
	public TileBackpack getBlockType() { return BetterStorageTiles.enderBackpack; }
	
	@Override
	protected IInventory getBackpackItemsInternal(EntityLivingBase carrier, EntityPlayer player) {
		return new InventoryEnderBackpackEquipped(player.getInventoryEnderChest());
	}
	
	@Override
	public boolean containsItems(PropertiesBackpack backpackData) { return false; }
	@Override
	protected String getAdditionalInfo(ItemStack stack, EntityPlayer player) { return "backpack.bound"; }
	
	// Item stuff
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return Resources.textureEnderBackpack.toString();
	}
	
	class InventoryEnderBackpackEquipped extends InventoryWrapper {
		public InventoryEnderBackpackEquipped(IInventory base) { super(base); }
		//@Override public String getInventoryName() { return getBackpackName(); }
	}
	
}
