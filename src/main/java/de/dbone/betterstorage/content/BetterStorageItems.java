package de.dbone.betterstorage.content;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.addon.Addon;
import de.dbone.betterstorage.config.GlobalConfig;
import de.dbone.betterstorage.item.ItemBackpack;
import de.dbone.betterstorage.item.ItemBucketSlime;
import de.dbone.betterstorage.item.ItemDrinkingHelmet;
import de.dbone.betterstorage.item.ItemEnderBackpack;
import de.dbone.betterstorage.item.ItemPresentBook;
import de.dbone.betterstorage.item.cardboard.ItemCardboardArmor;
import de.dbone.betterstorage.item.cardboard.ItemCardboardAxe;
import de.dbone.betterstorage.item.cardboard.ItemCardboardHoe;
import de.dbone.betterstorage.item.cardboard.ItemCardboardPickaxe;
import de.dbone.betterstorage.item.cardboard.ItemCardboardSheet;
import de.dbone.betterstorage.item.cardboard.ItemCardboardShovel;
import de.dbone.betterstorage.item.cardboard.ItemCardboardSword;
import de.dbone.betterstorage.item.locking.ItemKey;
import de.dbone.betterstorage.item.locking.ItemKeyring;
import de.dbone.betterstorage.item.locking.ItemLock;
import de.dbone.betterstorage.item.locking.ItemMasterKey;
import de.dbone.betterstorage.utils.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.oredict.OreDictionary;

public final class BetterStorageItems {
	
	public static ItemKey key;
	public static ItemLock lock;
	public static ItemKeyring keyring;
	public static ItemCardboardSheet cardboardSheet;
	public static ItemMasterKey masterKey;
	public static ItemDrinkingHelmet drinkingHelmet;
	public static ItemBucketSlime slimeBucket;
	public static ItemPresentBook presentBook;
	
	public static ItemBackpack itemBackpack;
	public static ItemEnderBackpack itemEnderBackpack;
	
	public static ItemCardboardArmor cardboardHelmet;
	public static ItemCardboardArmor cardboardChestplate;
	public static ItemCardboardArmor cardboardLeggings;
	public static ItemCardboardArmor cardboardBoots;
	
	public static ItemCardboardSword cardboardSword;
	public static ItemCardboardPickaxe cardboardPickaxe;
	public static ItemCardboardShovel cardboardShovel;
	public static ItemCardboardAxe cardboardAxe;
	public static ItemCardboardHoe cardboardHoe;
	
	public static boolean anyCardboardItemsEnabled;
	
	private BetterStorageItems() {  }
	
	public static void initialize() {
		
		key            = MiscUtils.conditionalNew(ItemKey.class, GlobalConfig.keyEnabled);
		lock           = MiscUtils.conditionalNew(ItemLock.class, GlobalConfig.lockEnabled);
		keyring        = MiscUtils.conditionalNew(ItemKeyring.class, GlobalConfig.keyringEnabled);
		cardboardSheet = MiscUtils.conditionalNew(ItemCardboardSheet.class, GlobalConfig.cardboardSheetEnabled);
		masterKey      = MiscUtils.conditionalNew(ItemMasterKey.class, GlobalConfig.masterKeyEnabled);
		drinkingHelmet = MiscUtils.conditionalNew(ItemDrinkingHelmet.class, GlobalConfig.drinkingHelmetEnabled);
		slimeBucket    = MiscUtils.conditionalNew(ItemBucketSlime.class, GlobalConfig.slimeBucketEnabled);
		presentBook    = new ItemPresentBook();
		
		itemBackpack        = MiscUtils.conditionalNew(ItemBackpack.class, GlobalConfig.backpackEnabled);
		itemEnderBackpack   = MiscUtils.conditionalNew(ItemEnderBackpack.class, GlobalConfig.enderBackpackEnabled);
		
		cardboardHelmet     = conditionalNewArmor(GlobalConfig.cardboardHelmetEnabled, 0);
		cardboardChestplate = conditionalNewArmor(GlobalConfig.cardboardChestplateEnabled, 1);
		cardboardLeggings   = conditionalNewArmor(GlobalConfig.cardboardLeggingsEnabled, 2);
		cardboardBoots      = conditionalNewArmor(GlobalConfig.cardboardBootsEnabled, 3);
		
		cardboardSword = MiscUtils.conditionalNew(ItemCardboardSword.class, GlobalConfig.cardboardSwordEnabled);
		cardboardPickaxe = MiscUtils.conditionalNew(ItemCardboardPickaxe.class, GlobalConfig.cardboardPickaxeEnabled);
		cardboardShovel = MiscUtils.conditionalNew(ItemCardboardShovel.class, GlobalConfig.cardboardShovelEnabled);
		cardboardAxe = MiscUtils.conditionalNew(ItemCardboardAxe.class, GlobalConfig.cardboardAxeEnabled);
		cardboardHoe = MiscUtils.conditionalNew(ItemCardboardHoe.class, GlobalConfig.cardboardHoeEnabled);
		
		anyCardboardItemsEnabled = ((BetterStorageItems.cardboardHelmet != null) ||
		                            (BetterStorageItems.cardboardChestplate != null) ||
		                            (BetterStorageItems.cardboardLeggings != null) ||
		                            (BetterStorageItems.cardboardBoots != null) ||
		                            (BetterStorageItems.cardboardSword != null) ||
		                            (BetterStorageItems.cardboardPickaxe != null) ||
		                            (BetterStorageItems.cardboardAxe != null) ||
		                            (BetterStorageItems.cardboardShovel != null) ||
		                            (BetterStorageItems.cardboardHoe != null));
		
		if(!MinecraftServer.getServer().isDedicatedServer())
			registerItemModelMeshers();
		
		Addon.initializeItemsAll();
		
	}

	private static void registerItemModelMeshers() {
		
		final ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		
		if(key != null)
			mesher.register(key, 0, new ModelResourceLocation(key.getRegistryName(), "inventory"));
		if(lock != null)
			mesher.register(lock, 0, new ModelResourceLocation(lock.getRegistryName(), "inventory"));
		if(keyring != null) {
			mesher.register(keyring, 0, new ModelResourceLocation(keyring.getRegistryName(), "inventory"));
			mesher.register(keyring, 1, new ModelResourceLocation(keyring.getRegistryName() + "One", "inventory"));
			mesher.register(keyring, 2, new ModelResourceLocation(key.getRegistryName(), "inventory"));
			mesher.register(keyring, 3, new ModelResourceLocation(masterKey.getRegistryName(), "inventory"));
		}
		if (cardboardSheet != null) {
			OreDictionary.registerOre("sheetCardboard", cardboardSheet);
			mesher.register(cardboardSheet, 0, new ModelResourceLocation(cardboardSheet.getRegistryName(), "inventory"));
		}
		if(masterKey != null)
			mesher.register(masterKey, 0, new ModelResourceLocation(masterKey.getRegistryName(), "inventory"));
		if(drinkingHelmet != null)
			mesher.register(drinkingHelmet, 0, new ModelResourceLocation(drinkingHelmet.getRegistryName(), "inventory"));
		if(slimeBucket != null)
			mesher.register(slimeBucket, 0, new ModelResourceLocation(slimeBucket.getRegistryName(), "inventory"));
		if(presentBook != null)
			mesher.register(presentBook, 0, new ModelResourceLocation("betterstorage:presentBook", "inventory"));
		
		
		
		if(cardboardHelmet != null)
			mesher.register(cardboardHelmet, 0, new ModelResourceLocation(cardboardHelmet.getRegistryName(), "inventory"));
		if(cardboardChestplate != null)
			mesher.register(cardboardChestplate, 0, new ModelResourceLocation(cardboardChestplate.getRegistryName(), "inventory"));
		if(cardboardLeggings != null)
			mesher.register(cardboardLeggings, 0, new ModelResourceLocation(cardboardLeggings.getRegistryName(), "inventory"));
		if(cardboardBoots != null)
			mesher.register(cardboardBoots, 0, new ModelResourceLocation(cardboardBoots.getRegistryName(), "inventory"));
		
		
		

		if(cardboardSword != null)
			mesher.register(cardboardSword, 0, new ModelResourceLocation(cardboardSword.getRegistryName(), "inventory"));
		if(cardboardPickaxe != null)
			mesher.register(cardboardPickaxe, 0, new ModelResourceLocation(cardboardPickaxe.getRegistryName(), "inventory"));
		if(cardboardShovel != null)
			mesher.register(cardboardShovel, 0, new ModelResourceLocation(cardboardShovel.getRegistryName(), "inventory"));
		if(cardboardAxe != null)
			mesher.register(cardboardAxe, 0, new ModelResourceLocation(cardboardAxe.getRegistryName(), "inventory"));
		if(cardboardHoe != null)
			mesher.register(cardboardHoe, 0, new ModelResourceLocation(cardboardHoe.getRegistryName(), "inventory"));
	}
	
	private static ItemCardboardArmor conditionalNewArmor(String configName, int armorType) {
		if (!BetterStorage.globalConfig.getBoolean(configName)) return null;
		return new ItemCardboardArmor(armorType);
	}
	
}
