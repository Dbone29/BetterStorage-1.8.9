package de.dbone.betterstorage.item;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.misc.Constants;
import de.dbone.betterstorage.utils.MiscUtils;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class ItemArmorBetterStorage extends ItemArmor {
	
	private String name;
	
	public ItemArmorBetterStorage(ArmorMaterial material, int renderSlot, int slot) {
		
		super(material, renderSlot, slot);
				
		setCreativeTab(BetterStorage.creativeTab);

		setUnlocalizedName(Constants.modId + "." + getItemName());
		GameRegistry.registerItem(this, getItemName());
	}

	/** Returns the name of this item, for example "drinkingHelmet". */
	public String getItemName() {
		return ((name != null) ? name : (name = MiscUtils.getName(this)));
	}
}
