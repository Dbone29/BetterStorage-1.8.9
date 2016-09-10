package de.dbone.betterstorage.network.packet;

import java.io.IOException;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.config.Config;
import de.dbone.betterstorage.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

/** Synchronizes some settings with the client. */
public class PacketSyncSetting extends AbstractPacket<PacketSyncSetting> {
	
	public NBTTagCompound data;
	
	public PacketSyncSetting() {  }
	public PacketSyncSetting(NBTTagCompound data) {
		this.data = data;
	}
	public PacketSyncSetting(Config config) {
		data = new NBTTagCompound();
		config.write(data);
	}
	
	@Override
	public void encode(PacketBuffer buffer) throws IOException {
		buffer.writeNBTTagCompoundToBuffer(data);
	}
	
	@Override
	public void decode(PacketBuffer buffer) throws IOException {
		data = buffer.readNBTTagCompoundFromBuffer();
	}
	
	@Override
	public void handle(EntityPlayer player) {
		BetterStorage.globalConfig.read(data);
	}
	
}
