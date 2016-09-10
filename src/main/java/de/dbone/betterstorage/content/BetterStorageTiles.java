package de.dbone.betterstorage.content;

import de.dbone.betterstorage.addon.Addon;
import de.dbone.betterstorage.config.GlobalConfig;
import de.dbone.betterstorage.tile.TileBackpack;
import de.dbone.betterstorage.tile.TileCardboardBox;
import de.dbone.betterstorage.tile.TileCraftingStation;
import de.dbone.betterstorage.tile.TileEnderBackpack;
import de.dbone.betterstorage.tile.TileFlintBlock;
import de.dbone.betterstorage.tile.TileLockableDoor;
import de.dbone.betterstorage.tile.TileLocker;
import de.dbone.betterstorage.tile.TilePresent;
import de.dbone.betterstorage.tile.TileReinforcedChest;
import de.dbone.betterstorage.tile.TileReinforcedLocker;
import de.dbone.betterstorage.tile.crate.TileCrate;
import de.dbone.betterstorage.tile.stand.TileArmorStand;
import de.dbone.betterstorage.utils.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;

public final class BetterStorageTiles {
	
	public static TileCrate crate;
	public static TileReinforcedChest reinforcedChest;
	public static TileLocker locker;
	public static TileArmorStand armorStand;
	public static TileBackpack backpack;
	public static TileEnderBackpack enderBackpack;
	public static TileCardboardBox cardboardBox;
	public static TileReinforcedLocker reinforcedLocker;
	public static TileCraftingStation craftingStation;
	public static TileFlintBlock flintBlock;
	public static TileLockableDoor lockableDoor;
	public static TilePresent present;
	
	private BetterStorageTiles() {  }
	
	public static void initialize() {
		
		crate            = MiscUtils.conditionalNew(TileCrate.class, GlobalConfig.crateEnabled);
		reinforcedChest  = MiscUtils.conditionalNew(TileReinforcedChest.class, GlobalConfig.reinforcedChestEnabled);
		locker           = MiscUtils.conditionalNew(TileLocker.class, GlobalConfig.lockerEnabled);
		armorStand       = MiscUtils.conditionalNew(TileArmorStand.class, GlobalConfig.armorStandEnabled);
		backpack         = MiscUtils.conditionalNew(TileBackpack.class, GlobalConfig.backpackEnabled);
		enderBackpack    = MiscUtils.conditionalNew(TileEnderBackpack.class, GlobalConfig.enderBackpackEnabled);
		cardboardBox     = MiscUtils.conditionalNew(TileCardboardBox.class, GlobalConfig.cardboardBoxEnabled);
		reinforcedLocker = MiscUtils.conditionalNew(TileReinforcedLocker.class, GlobalConfig.reinforcedLockerEnabled);
		craftingStation  = MiscUtils.conditionalNew(TileCraftingStation.class, GlobalConfig.craftingStationEnabled);
		flintBlock       = MiscUtils.conditionalNew(TileFlintBlock.class, GlobalConfig.flintBlockEnabled);
		lockableDoor     = MiscUtils.conditionalNew(TileLockableDoor.class, GlobalConfig.lockableDoorEnabled);
		present          = MiscUtils.conditionalNew(TilePresent.class, GlobalConfig.presentEnabled);
		
		if(!MinecraftServer.getServer().isDedicatedServer())
			registerItemModelMeshers();
		
		Addon.initializeTilesAll();		
	}

	public static void registerItemModelMeshers() {
		if(crate != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(Item.getItemFromBlock(crate), 0, new ModelResourceLocation("betterstorage:" + crate.getTileName(), "inventory"));
		if(reinforcedChest != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(Item.getItemFromBlock(reinforcedChest), 0, new ModelResourceLocation("betterstorage:" + reinforcedChest.getTileName(), "inventory"));
		if(locker != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(Item.getItemFromBlock(locker), 0, new ModelResourceLocation("betterstorage:" + locker.getTileName(), "inventory"));
		if(armorStand != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(Item.getItemFromBlock(armorStand), 0, new ModelResourceLocation("betterstorage:" + armorStand.getTileName(), "inventory"));
		if(cardboardBox != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(Item.getItemFromBlock(cardboardBox), 0, new ModelResourceLocation("betterstorage:" + cardboardBox.getTileName(), "inventory"));
		if(flintBlock != null) {
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(Item.getItemFromBlock(flintBlock), 0, new ModelResourceLocation("betterstorage:" + flintBlock.getTileName(), "inventory"));
		}
		if(craftingStation != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(Item.getItemFromBlock(craftingStation), 0, new ModelResourceLocation("betterstorage:" + craftingStation.getTileName(), "inventory"));
	}
	
}
