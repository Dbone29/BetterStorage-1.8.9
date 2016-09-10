package de.dbone.betterstorage.misc.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;

import org.lwjgl.input.Keyboard;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.config.GlobalConfig;
import de.dbone.betterstorage.item.ItemBackpack;
import de.dbone.betterstorage.misc.EquipmentSlot;
import de.dbone.betterstorage.network.packet.PacketBackpackOpen;
import de.dbone.betterstorage.network.packet.PacketDrinkingHelmetUse;

@SideOnly(Side.CLIENT)
public class KeyBindingHandler {
	
	public static final KeyBinding backpackOpen =
			new KeyBinding("key.betterstorage.backpackOpen", Keyboard.KEY_B, "key.categories.gameplay");
	public static final KeyBinding drinkingHelmet =
			new KeyBinding("key.betterstorage.drinkingHelmet", Keyboard.KEY_F, "key.categories.gameplay");
	
	private static final KeyBinding[] bindings = new KeyBinding[]{ backpackOpen, drinkingHelmet };
	
	public KeyBindingHandler() {
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
		
		ClientRegistry.registerKeyBinding(backpackOpen);
		ClientRegistry.registerKeyBinding(drinkingHelmet);
	}
	
	@SubscribeEvent
	public void onKey(KeyInputEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		if (!mc.inGameHasFocus || (player == null)) return;
		if (backpackOpen.isPressed() && (ItemBackpack.getBackpack(player) != null) &&
		    BetterStorage.globalConfig.getBoolean(GlobalConfig.enableBackpackOpen))
			BetterStorage.networkChannel.sendToServer(new PacketBackpackOpen());
		else if (drinkingHelmet.isPressed() && (player.getEquipmentInSlot(EquipmentSlot.HEAD) != null))
			BetterStorage.networkChannel.sendToServer(new PacketDrinkingHelmetUse());
	}
	
}
