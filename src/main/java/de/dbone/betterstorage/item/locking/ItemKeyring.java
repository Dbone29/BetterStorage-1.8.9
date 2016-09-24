package de.dbone.betterstorage.item.locking;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.api.IContainerItem;
import de.dbone.betterstorage.api.lock.IKey;
import de.dbone.betterstorage.item.ItemBetterStorage;
import de.dbone.betterstorage.utils.GuiHandler;
import de.dbone.betterstorage.utils.StackUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemKeyring extends ItemBetterStorage implements IKey, IContainerItem {
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (world.isRemote || !player.isSneaking()) return stack;
		
		final BlockPos pos = player.getPosition();		
		player.openGui(BetterStorage.instance, GuiHandler.GUI_KEYRING, world, pos.getX(), pos.getY(), pos.getZ());
		
		return stack;
	}
	
	@Override
	public void setDamage(ItemStack stack, int damage) {
		//return icons[Math.min(damage, icons.length - 1)];
		super.setDamage(stack, damage);
	}
	
	// IKey implementation
	
	@Override
	public boolean isNormalKey() { return false; }
	
	@Override
	public boolean unlock(ItemStack keyring, ItemStack lock, boolean useAbility) {
		
		// Goes through all the keys in the keyring,
		// returns if any of the keys fit in the lock.
		
		ItemStack[] items = StackUtils.getStackContents(keyring, 9);
		for (ItemStack key : items) {
			if (key == null) continue;
			IKey keyType = (IKey)key.getItem();
			if (keyType.unlock(key, lock, false))
				return true;
		}
		
		return false;
		
	}
	
	@Override
	public boolean canApplyEnchantment(ItemStack key, Enchantment enchantment) { return false; }
	
	// IContainerItem implementation
	
	@Override
	public ItemStack[] getContainerItemContents(ItemStack container) {
		return StackUtils.getStackContents(container, 9);
	}
	
	@Override
	public boolean canBeStoredInContainerItem(ItemStack item) { return true; }
	
}
