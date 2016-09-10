package de.dbone.betterstorage.content;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.addon.Addon;
import de.dbone.betterstorage.entity.EntityCluckington;
//import de.dbone.betterstorage.entity.EntityFrienderman;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public final class BetterStorageEntities {
	
	private BetterStorageEntities() {  }
	
	public static void register() {
		
		//EntityRegistry.registerModEntity(EntityFrienderman.class, "Frienderman", 1, BetterStorage.instance, 64, 4, true);
		EntityRegistry.registerModEntity(EntityCluckington.class, "Cluckington", 2, BetterStorage.instance, 64, 4, true);
		
		Addon.registerEntitesAll();
		
	}
	
}
