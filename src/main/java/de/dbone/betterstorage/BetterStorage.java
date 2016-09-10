package de.dbone.betterstorage;

import net.minecraft.creativetab.CreativeTabs;

import org.apache.logging.log4j.Logger;

import de.dbone.betterstorage.addon.Addon;
import de.dbone.betterstorage.config.Config;
import de.dbone.betterstorage.config.GlobalConfig;
import de.dbone.betterstorage.content.BetterStorageEntities;
import de.dbone.betterstorage.content.BetterStorageItems;
import de.dbone.betterstorage.content.BetterStorageTileEntities;
import de.dbone.betterstorage.content.BetterStorageTiles;
import de.dbone.betterstorage.item.EnchantmentBetterStorage;
import de.dbone.betterstorage.misc.Constants;
import de.dbone.betterstorage.misc.CreativeTabBetterStorage;
import de.dbone.betterstorage.misc.DungeonLoot;
import de.dbone.betterstorage.misc.Recipes;
import de.dbone.betterstorage.network.ChannelHandler;
import de.dbone.betterstorage.proxy.CommonProxy;
import de.dbone.betterstorage.utils.GuiHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = Constants.modId,
     name = Constants.modName,
     dependencies = "required-after:Forge; after:Thaumcraft; after:NotEnoughItems;",
     guiFactory = "de.dbone.betterstorage.client.gui.BetterStorageGuiFactory")
public class BetterStorage {
	
	@Instance(Constants.modId)
	public static BetterStorage instance;
	
	@SidedProxy(serverSide = Constants.commonProxy,
	            clientSide = Constants.clientProxy)
	public static CommonProxy proxy;
	
	public static ChannelHandler networkChannel;	
	public static Logger log;	
	public static CreativeTabs creativeTab;	
	public static Config globalConfig;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		networkChannel = new ChannelHandler();
		log = event.getModLog();
		creativeTab = new CreativeTabBetterStorage();
		
		Addon.initialize();
		
		globalConfig = new GlobalConfig(event.getSuggestedConfigurationFile());
		Addon.setupConfigsAll();
		globalConfig.load();
		globalConfig.save();		
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		BetterStorageItems.initialize();
		BetterStorageTiles.initialize();
		
		EnchantmentBetterStorage.initialize();
		
		BetterStorageTileEntities.register();		
		BetterStorageEntities.register();
		DungeonLoot.add();
		
		Recipes.add();
		proxy.initialize();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		Addon.postInitializeAll();
	}
}
