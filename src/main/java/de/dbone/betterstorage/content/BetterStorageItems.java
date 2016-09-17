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
		if(key != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(key, 0, new ModelResourceLocation("betterstorage:" + key.getItemName(), "inventory"));
		if(lock != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(lock, 0, new ModelResourceLocation("betterstorage:" + lock.getItemName(), "inventory"));
		if(keyring != null) {
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(keyring, 0, new ModelResourceLocation("betterstorage:" + keyring.getItemName(), "inventory"));
			//Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
			//	.register(keyring, 1, new ModelResourceLocation("betterstorage:" + keyring.getItemName() + "_1", "inventory"));
		}
		if (cardboardSheet != null) {
			OreDictionary.registerOre("sheetCardboard", cardboardSheet);
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(cardboardSheet, 0, new ModelResourceLocation("betterstorage:" + cardboardSheet.getItemName(), "inventory"));
		}
		if(masterKey != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(masterKey, 0, new ModelResourceLocation("betterstorage:" + masterKey.getItemName(), "inventory"));
		if(drinkingHelmet != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(drinkingHelmet, 0, new ModelResourceLocation("betterstorage:" + drinkingHelmet.getItemName(), "inventory"));
		if(slimeBucket != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(slimeBucket, 0, new ModelResourceLocation("betterstorage:" + slimeBucket.getItemName(), "inventory"));
		if(presentBook != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(presentBook, 0, new ModelResourceLocation("betterstorage:presentBook", "inventory"));
		
		
		
		if(cardboardHelmet != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(cardboardHelmet, 0, new ModelResourceLocation("betterstorage:" + cardboardHelmet.getItemName(), "inventory"));
		if(cardboardChestplate != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(cardboardChestplate, 0, new ModelResourceLocation("betterstorage:" + cardboardChestplate.getItemName(), "inventory"));
		if(cardboardLeggings != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(cardboardLeggings, 0, new ModelResourceLocation("betterstorage:" + cardboardLeggings.getItemName(), "inventory"));
		if(cardboardBoots != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(cardboardBoots, 0, new ModelResourceLocation("betterstorage:" + cardboardBoots.getItemName(), "inventory"));
		
		
		

		if(cardboardSword != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(cardboardSword, 0, new ModelResourceLocation("betterstorage:" + cardboardSword.getItemName(), "inventory"));
		if(cardboardPickaxe != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(cardboardPickaxe, 0, new ModelResourceLocation("betterstorage:" + cardboardPickaxe.getItemName(), "inventory"));
		if(cardboardShovel != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(cardboardShovel, 0, new ModelResourceLocation("betterstorage:" + cardboardShovel.getItemName(), "inventory"));
		if(cardboardAxe != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(cardboardAxe, 0, new ModelResourceLocation("betterstorage:" + cardboardAxe.getItemName(), "inventory"));
		if(cardboardHoe != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
				.register(cardboardHoe, 0, new ModelResourceLocation("betterstorage:" + cardboardHoe.getItemName(), "inventory"));
	}
	
	private static ItemCardboardArmor conditionalNewArmor(String configName, int armorType) {
		if (!BetterStorage.globalConfig.getBoolean(configName)) return null;
		return new ItemCardboardArmor(armorType);
	}
	
}
