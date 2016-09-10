package de.dbone.betterstorage.tile.entity;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.config.GlobalConfig;
import de.dbone.betterstorage.misc.Constants;
import de.dbone.betterstorage.utils.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityReinforcedLocker extends TileEntityLocker {
	
	@Override
	@SideOnly(Side.CLIENT)
	public ResourceLocation getResource() {
		return getMaterial().getLockerResource(isConnected());
	}

	@Override
	public boolean canHaveLock() { return true; }
	@Override
	public boolean canHaveMaterial() { return true; }
	@Override
	public void setAttachmentPosition() {
		double x = (mirror ? 13.5 : 2.5);
		double y = (isConnected() ? 0 : 8);
		lockAttachment.setBox(x, y, 0.5, 5, 5, 1);
		lockAttachment.setScale(0.375F, 1.5F);
	}
	
	@Override
	public int getColumns() { return BetterStorage.globalConfig.getInteger(GlobalConfig.reinforcedColumns); }
	@Override
	protected String getConnectableName() { return Constants.containerReinforcedLocker; }
	
	@Override
	public void openGui(EntityPlayer playerIn, World worldIn, BlockPos pos) {
		if(((TileEntityReinforcedLocker) worldIn.getTileEntity(pos)).isConnected())
			playerIn.openGui(BetterStorage.instance, GuiHandler.GUI_REINFORCED_LOCKER_LARGE, worldIn, pos.getX(), pos.getY(), pos.getZ());
		else
			playerIn.openGui(BetterStorage.instance, GuiHandler.GUI_REINFORCED_LOCKER, worldIn, pos.getX(), pos.getY(), pos.getZ());
	}
}
