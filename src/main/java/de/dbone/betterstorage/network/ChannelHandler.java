package de.dbone.betterstorage.network;

import java.util.List;

import de.dbone.betterstorage.misc.Constants;
import de.dbone.betterstorage.network.packet.PacketBackpackHasItems;
import de.dbone.betterstorage.network.packet.PacketBackpackIsOpen;
import de.dbone.betterstorage.network.packet.PacketBackpackOpen;
import de.dbone.betterstorage.network.packet.PacketBackpackStack;
import de.dbone.betterstorage.network.packet.PacketBackpackTeleport;
import de.dbone.betterstorage.network.packet.PacketDrinkingHelmetUse;
import de.dbone.betterstorage.network.packet.PacketLockHit;
import de.dbone.betterstorage.network.packet.PacketPresentOpen;
import de.dbone.betterstorage.network.packet.PacketSyncSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ChannelHandler extends SimpleNetworkWrapper {
	
	public ChannelHandler() {
		super(Constants.modId);
		//register(0, Side.CLIENT, PacketOpenGui.class);
		register(0, Side.CLIENT, PacketBackpackTeleport.class);
		register(1, Side.CLIENT, PacketBackpackHasItems.class);
		register(2, Side.CLIENT, PacketBackpackIsOpen.class);
		register(3, Side.SERVER, PacketBackpackOpen.class);
		register(4, Side.CLIENT, PacketBackpackStack.class);
		register(5, Side.SERVER, PacketDrinkingHelmetUse.class);
		register(6, Side.SERVER, PacketLockHit.class);
		register(7, Side.CLIENT, PacketSyncSetting.class);
		register(8, Side.CLIENT, PacketPresentOpen.class);
	}
	
	public <T extends IMessage & IMessageHandler<T, IMessage>> void register(int id, Side receivingSide, Class<T> messageClass) {
		registerMessage(messageClass, messageClass, id, receivingSide);
	}
	
	// Sending packets
	
	public void sendTo(IMessage message, EntityPlayer player) {
		sendTo(message, (EntityPlayerMP)player);
	}
	
	public void sendToAllAround(IMessage message, World world, double x, double y, double z, double distance) {
		sendToAllAround(message, new NetworkRegistry.TargetPoint(world.provider.getDimensionId(), x, y, z, distance));
	}
	
	public void sendToAllAround(IMessage message, World world, double x, double y, double z,
	                            double distance, EntityPlayer except) {
		for (EntityPlayer player : world.playerEntities) {
			if (player == except) continue;
			double dx = x - player.posX;
			double dy = y - player.posY;
			double dz = z - player.posZ;
            if ((dx * dx + dy * dy + dz * dz) < (distance * distance))
            	sendTo(message, player);
		}
	}
	
	/** Sends a packet to everyone tracking an entity. */
	public void sendToAllTracking(IMessage message, Entity entity) {
		((WorldServer)entity.worldObj).getEntityTracker().func_151248_b(entity, getPacketFrom(message));
	}
	
	/** Sends a packet to everyone tracking an entity,
	 *  including the entity itself if it's a player. */
	public void sendToAndAllTracking(IMessage message, Entity entity) {
		sendToAllTracking(message, entity);
		if (entity instanceof EntityPlayer)
			sendTo(message, (EntityPlayer)entity);
	}
	
}
