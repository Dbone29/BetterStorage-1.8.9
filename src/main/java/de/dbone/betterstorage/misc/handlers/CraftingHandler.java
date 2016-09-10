package de.dbone.betterstorage.misc.handlers;

import de.dbone.betterstorage.content.BetterStorageItems;
import de.dbone.betterstorage.item.locking.ItemKey;
import de.dbone.betterstorage.utils.InventoryUtils;
import net.minecraft.init.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

/** Handles key and lock crafting. */
public class CraftingHandler {
	
	public CraftingHandler() {
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
	}
	
	@SubscribeEvent
	public void onItemCrafted(ItemCraftedEvent event) {
		// If item crafted is a key ...
		if (event.crafting.getItem() instanceof ItemKey) {
			
			// See if a key was modified by checking if no gold was used in the recipe.
			boolean modifyKey = !InventoryUtils.hasItem(event.craftMatrix, Items.gold_ingot);
			
			// If it is, remove it from the crafting matrix.
			if (modifyKey) {
				int keyIndex = InventoryUtils.findItemSlot(event.craftMatrix, BetterStorageItems.key);
				event.craftMatrix.setInventorySlotContents(keyIndex, null);
			}
			
		}
	}
	
}
