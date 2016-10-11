package de.dbone.betterstorage.content;

import de.dbone.betterstorage.addon.Addon;
import de.dbone.betterstorage.config.GlobalConfig;
import de.dbone.betterstorage.tile.TileBackpack;
import de.dbone.betterstorage.tile.TileCardboardBox;
import de.dbone.betterstorage.tile.TileCraftingStation;
import de.dbone.betterstorage.tile.TileEnderBackpack;
import de.dbone.betterstorage.tile.TileFlintBlock;
import de.dbone.betterstorage.tile.TileLargeLocker;
import de.dbone.betterstorage.tile.TileLockableDoor;
import de.dbone.betterstorage.tile.TileLocker;
import de.dbone.betterstorage.tile.TilePresent;
import de.dbone.betterstorage.tile.TileReinforcedChest;
import de.dbone.betterstorage.tile.TileReinforcedLocker;
import de.dbone.betterstorage.tile.crate.TileCrate;
import de.dbone.betterstorage.tile.reinforced.TileReinforcedGoldChest;
import de.dbone.betterstorage.tile.reinforced.TileReinforcedIronChest;
import de.dbone.betterstorage.utils.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;

public final class BetterStorageTiles {
	
	public static TileCrate crate;
	public static TileReinforcedChest reinforcedChest;
	public static TileReinforcedIronChest reinforcedIronChest;
	public static TileReinforcedGoldChest reinforcedGoldChest;
	public static TileLocker locker;
	public static TileLargeLocker largeLocker;
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
		
		crate            	= MiscUtils.conditionalNew(TileCrate.class, GlobalConfig.crateEnabled);
		reinforcedChest  	= MiscUtils.conditionalNew(TileReinforcedChest.class, GlobalConfig.reinforcedChestEnabled);
		reinforcedIronChest	= MiscUtils.conditionalNew(TileReinforcedIronChest.class, GlobalConfig.reinforcedChestEnabled);
		reinforcedGoldChest	= MiscUtils.conditionalNew(TileReinforcedGoldChest.class, GlobalConfig.reinforcedChestEnabled);
		locker           	= MiscUtils.conditionalNew(TileLocker.class, GlobalConfig.lockerEnabled);
		largeLocker			= MiscUtils.conditionalNew(TileLargeLocker.class, GlobalConfig.lockerEnabled);
		backpack         	= MiscUtils.conditionalNew(TileBackpack.class, GlobalConfig.backpackEnabled);
		enderBackpack    	= MiscUtils.conditionalNew(TileEnderBackpack.class, GlobalConfig.enderBackpackEnabled);
		cardboardBox     	= MiscUtils.conditionalNew(TileCardboardBox.class, GlobalConfig.cardboardBoxEnabled);
		reinforcedLocker 	= MiscUtils.conditionalNew(TileReinforcedLocker.class, GlobalConfig.reinforcedLockerEnabled);
		craftingStation  	= MiscUtils.conditionalNew(TileCraftingStation.class, GlobalConfig.craftingStationEnabled);
		flintBlock       	= MiscUtils.conditionalNew(TileFlintBlock.class, GlobalConfig.flintBlockEnabled);
		lockableDoor     	= MiscUtils.conditionalNew(TileLockableDoor.class, GlobalConfig.lockableDoorEnabled);
		present          	= MiscUtils.conditionalNew(TilePresent.class, GlobalConfig.presentEnabled);
		
		if(!MinecraftServer.getServer().isDedicatedServer())
			registerItemModelMeshers();
		
		Addon.initializeTilesAll();		
	}

	public static void registerItemModelMeshers() {
		final ItemModelMesher itemMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		
		if(crate != null)
			itemMesher.register(Item.getItemFromBlock(crate), 0, new ModelResourceLocation("betterstorage:" + crate.getTileName(), "inventory"));
		if(reinforcedChest != null)
			itemMesher.register(Item.getItemFromBlock(reinforcedChest), 0, new ModelResourceLocation("betterstorage:" + reinforcedChest.getTileName(), "inventory"));
		if(reinforcedChest != null)
			itemMesher.register(Item.getItemFromBlock(reinforcedIronChest), 0, new ModelResourceLocation("betterstorage:reinforcedIronChest", "inventory"));
		if(reinforcedGoldChest != null)
			itemMesher.register(Item.getItemFromBlock(reinforcedGoldChest), 0, new ModelResourceLocation("betterstorage:reinforcedGoldChest", "inventory"));
		if(locker != null) {
			itemMesher.register(Item.getItemFromBlock(locker), 0, new ModelResourceLocation("betterstorage:" + locker.getTileName(), "inventory"));
			itemMesher.register(Item.getItemFromBlock(largeLocker), 0, new ModelResourceLocation("betterstorage:" + largeLocker.getTileName(), "inventory"));
		}
		if(backpack != null)
			itemMesher.register(Item.getItemFromBlock(backpack), 0, new ModelResourceLocation("betterstorage:" + backpack.getTileName(), "inventory"));
		if(cardboardBox != null)
			itemMesher.register(Item.getItemFromBlock(cardboardBox), 0, new ModelResourceLocation("betterstorage:" + cardboardBox.getTileName(), "inventory"));		
		if(reinforcedLocker != null)
			itemMesher.register(Item.getItemFromBlock(reinforcedLocker), 0, new ModelResourceLocation("betterstorage:" + reinforcedLocker.getTileName(), "inventory"));
		if(craftingStation != null)
			itemMesher.register(Item.getItemFromBlock(craftingStation), 0, new ModelResourceLocation("betterstorage:" + craftingStation.getTileName(), "inventory"));
		if(flintBlock != null)
			itemMesher.register(Item.getItemFromBlock(flintBlock), 0, new ModelResourceLocation("betterstorage:" + flintBlock.getTileName(), "inventory"));
	}
	
}
