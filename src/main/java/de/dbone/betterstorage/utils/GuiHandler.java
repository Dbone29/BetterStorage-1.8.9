package de.dbone.betterstorage.utils;

import de.dbone.betterstorage.addon.thaumcraft.GuiThaumiumChest;
import de.dbone.betterstorage.client.gui.GuiBetterStorage;
import de.dbone.betterstorage.client.gui.GuiCraftingStation;
import de.dbone.betterstorage.client.gui.GuiCrate;
import de.dbone.betterstorage.container.ContainerBetterStorage;
import de.dbone.betterstorage.container.ContainerCraftingStation;
import de.dbone.betterstorage.container.ContainerKeyring;
import de.dbone.betterstorage.inventory.InventoryBackpackEquipped;
import de.dbone.betterstorage.inventory.InventoryCardboardBox;
import de.dbone.betterstorage.inventory.InventoryCraftingStation;
import de.dbone.betterstorage.inventory.InventoryTileEntity;
import de.dbone.betterstorage.inventory.InventoryWrapper;
import de.dbone.betterstorage.item.ItemBackpack;
import de.dbone.betterstorage.misc.Constants;
import de.dbone.betterstorage.tile.entity.TileEntityBackpack;
import de.dbone.betterstorage.tile.entity.TileEntityContainer;
import de.dbone.betterstorage.tile.entity.TileEntityCraftingStation;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	public static final int GUI_CRAFTING_STATION		= 0;
	public static final int GUI_KEYRING					= 1;
	public static final int GUI_CARDBOARD_BOX			= 2;
	public static final int GUI_LOCKER					= 3;
	public static final int GUI_LOCKER_LARGE			= 4;
	public static final int GUI_REINFORCED_LOCKER		= 5;
	public static final int GUI_REINFORCED_LOCKER_LARGE	= 6;
	public static final int GUI_REINFORCED_CHEST		= 7;
	public static final int GUI_REINFORCED_CHEST_LARGE	= 8;
	public static final int GUI_THAUMIUM_CHEST			= 9;
	public static final int GUI_THAUMIUM_CHEST_LARGE	= 10;
	public static final int GUI_CRATE_0					= 11;
	public static final int GUI_CRATE_1					= 12;
	public static final int GUI_CRATE_2					= 13;
	public static final int GUI_BACKPACK				= 14;
	public static final int GUI_ENDER_BACKPACK			= 15;
	public static final int GUI_THAUMCRAFT_BACKPACK		= 16;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
		case GUI_CRAFTING_STATION:
			return new ContainerCraftingStation(player, new InventoryCraftingStation((TileEntityCraftingStation) world.getTileEntity(new BlockPos(x, y, z))));
		case GUI_KEYRING:
			int protectedSlot = player.inventory.currentItem;
			return new ContainerKeyring(player, "Keyring", protectedSlot);
		case GUI_CARDBOARD_BOX:
			return ((TileEntityContainer) world.getTileEntity(new BlockPos(x, y, z))).createContainer(player);
		case GUI_LOCKER:
		case GUI_LOCKER_LARGE:
		case GUI_REINFORCED_LOCKER:
		case GUI_REINFORCED_LOCKER_LARGE:
		case GUI_REINFORCED_CHEST:
		case GUI_REINFORCED_CHEST_LARGE:
		case GUI_THAUMIUM_CHEST:
		case GUI_THAUMIUM_CHEST_LARGE:
			return new ContainerBetterStorage(player, ((TileEntityContainer) world.getTileEntity(new BlockPos(x, y, z))).getPlayerInventory());
		case GUI_CRATE_0:
		case GUI_CRATE_1:
		case GUI_CRATE_2:
		case GUI_BACKPACK:
		case GUI_ENDER_BACKPACK:
			TileEntityBackpack enderBackpack = WorldUtils.get(world, new BlockPos(x, y, z), TileEntityBackpack.class);
			IInventory enderInventory = new InventoryTileEntity(enderBackpack, player.getInventoryEnderChest());
			return new ContainerBetterStorage(player, enderInventory, 9, 3);
		case GUI_THAUMCRAFT_BACKPACK:
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
		case GUI_CRAFTING_STATION:
			return new GuiCraftingStation(player, "Crafting Station", true);
		case GUI_KEYRING:
			return new GuiBetterStorage(new ContainerKeyring(player, "Keyring", 9));
		case GUI_CARDBOARD_BOX:
			return new GuiBetterStorage(player, 9, 1, new InventoryWrapper(
					new InventoryCardboardBox(new ItemStack[9]), "Cardboard Box", true));
		case GUI_LOCKER:
			return new GuiBetterStorage(player, 9, 3, "Locker", true);
		case GUI_LOCKER_LARGE:
			return new GuiBetterStorage(player, 9, 6, "Large Locker", true);
		case GUI_REINFORCED_LOCKER:
			return new GuiBetterStorage(player, 13, 3, "Reinforced Locker", true);
		case GUI_REINFORCED_LOCKER_LARGE:
			return new GuiBetterStorage(player, 13, 6, "Large Reinforced Locker", true);
		case GUI_REINFORCED_CHEST:
			return new GuiBetterStorage(player, 13, 3, "Reinforced Chest", true);
		case GUI_REINFORCED_CHEST_LARGE:
			return new GuiBetterStorage(player, 13, 6, "Large Reinforced Chest", true);
		case GUI_THAUMIUM_CHEST:
			return new GuiThaumiumChest(player, 17, 3, "Thaumium Chest", true);
		case GUI_THAUMIUM_CHEST_LARGE:
			return new GuiThaumiumChest(player, 17, 6, "Large Thaumium Chest", true);
		case GUI_CRATE_0:
			return new GuiCrate(player, 2, "Crate", true);
		case GUI_CRATE_1:
			return new GuiCrate(player, 4, "Crate", true);
		case GUI_CRATE_2:
			return new GuiCrate(player, 6, "Crate", true);
		case GUI_BACKPACK:			
			return new GuiBetterStorage(player, 9, 4, "BackPack", true);
		case GUI_ENDER_BACKPACK:
			TileEntityBackpack backpack = WorldUtils.get(world, new BlockPos(x, y, z), TileEntityBackpack.class);
			return new GuiBetterStorage(player, 9, 3, backpack.getCustomTitle(), true);
		case GUI_THAUMCRAFT_BACKPACK:
		default:
			return null;
		}
	}

}
