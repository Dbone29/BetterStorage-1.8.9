package de.dbone.betterstorage.tile.stand;

import de.dbone.betterstorage.api.stand.BetterStorageArmorStand;
import de.dbone.betterstorage.api.stand.ClientArmorStandPlayer;
import de.dbone.betterstorage.api.stand.IArmorStand;
import de.dbone.betterstorage.api.stand.IArmorStandRenderHandler;
import de.dbone.betterstorage.misc.EquipmentSlot;
import net.minecraft.tileentity.TileEntity;

public class VanillaArmorStandRenderHandler implements IArmorStandRenderHandler {
	
	@Override
	public <T extends TileEntity & IArmorStand> void onPreRender(T armorStand, ClientArmorStandPlayer player) {
		player.setCurrentItemOrArmor(EquipmentSlot.HEAD,  armorStand.getItem(BetterStorageArmorStand.helmet));
		player.setCurrentItemOrArmor(EquipmentSlot.CHEST, armorStand.getItem(BetterStorageArmorStand.chestplate));
		player.setCurrentItemOrArmor(EquipmentSlot.LEGS,  armorStand.getItem(BetterStorageArmorStand.leggins));
		player.setCurrentItemOrArmor(EquipmentSlot.FEET,  armorStand.getItem(BetterStorageArmorStand.boots));
	}
	
	@Override
	public <T extends TileEntity & IArmorStand> void onPostRender(T armorStand, ClientArmorStandPlayer player) {  }
	
}
