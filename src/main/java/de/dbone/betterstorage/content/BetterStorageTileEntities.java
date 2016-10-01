package de.dbone.betterstorage.content;

import de.dbone.betterstorage.addon.Addon;
import de.dbone.betterstorage.misc.Constants;
import de.dbone.betterstorage.tile.crate.TileEntityCrate;
import de.dbone.betterstorage.tile.entity.TileEntityBackpack;
import de.dbone.betterstorage.tile.entity.TileEntityCardboardBox;
import de.dbone.betterstorage.tile.entity.TileEntityCraftingStation;
import de.dbone.betterstorage.tile.entity.TileEntityLockableDoor;
import de.dbone.betterstorage.tile.entity.TileEntityLocker;
import de.dbone.betterstorage.tile.entity.TileEntityPresent;
import de.dbone.betterstorage.tile.entity.TileEntityReinforcedChest;
import de.dbone.betterstorage.tile.entity.TileEntityReinforcedLocker;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class BetterStorageTileEntities {
	
	private BetterStorageTileEntities() {  }
	
	public static void register() {
		
		GameRegistry.registerTileEntity(TileEntityCrate.class, Constants.containerCrate);
		GameRegistry.registerTileEntity(TileEntityReinforcedChest.class, Constants.containerReinforcedChest);
		GameRegistry.registerTileEntity(TileEntityLocker.class, Constants.containerLocker);
		GameRegistry.registerTileEntity(TileEntityBackpack.class, Constants.containerBackpack);
		GameRegistry.registerTileEntity(TileEntityCardboardBox.class, Constants.containerCardboardBox);
		GameRegistry.registerTileEntity(TileEntityReinforcedLocker.class, Constants.containerReinforcedLocker);
		GameRegistry.registerTileEntity(TileEntityCraftingStation.class, Constants.containerCraftingStation);
		GameRegistry.registerTileEntity(TileEntityPresent.class, Constants.containerPresent);
		GameRegistry.registerTileEntity(TileEntityLockableDoor.class, Constants.lockableDoor);

		Addon.registerTileEntitesAll();
		
	}
	
}
