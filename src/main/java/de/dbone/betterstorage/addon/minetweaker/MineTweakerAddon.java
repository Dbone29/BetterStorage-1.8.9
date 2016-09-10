package de.dbone.betterstorage.addon.minetweaker;

import minetweaker.MineTweakerAPI;
import de.dbone.betterstorage.addon.Addon;

public class MineTweakerAddon extends Addon {
	
	public MineTweakerAddon() {
		super("MineTweaker3");
	}
	
	@Override
	public void setupConfig() {
		MineTweakerAPI.registerClass(MTCraftingStation.class);
	}
	
}
