package de.dbone.betterstorage.tile;

import java.util.Random;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.config.GlobalConfig;
import de.dbone.betterstorage.content.BetterStorageItems;
import de.dbone.betterstorage.item.ItemBackpack;
import de.dbone.betterstorage.proxy.ClientProxy;
import de.dbone.betterstorage.tile.entity.TileEntityBackpack;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileBackpack extends TileContainerBetterStorage {
	
	public TileBackpack() {
		super(Material.cloth);
		
		setHardness(1.5f);
		setStepSound(soundTypeCloth);
		float w = getBoundsWidth() / 16.0F;
		float h = getBoundsHeight() / 16.0F;
		setBlockBounds(0.5F - w / 2, 0.0F, 0.5F - w / 2, 0.5F + w / 2, h, 0.5F + w / 2);
	}
	
	public int getBoundsWidth() { return 12; }
	public int getBoundsHeight() { return 13; }
	public int getBoundsDepth() { return 10; }
	
	@Override
	public Class<? extends ItemBlock> getItemClass() { return null; }

	public ItemBackpack getItemType() { return BetterStorageItems.itemBackpack; }
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
		float w = getBoundsWidth() / 16.0F;
		float h = getBoundsHeight() / 16.0F;
		float d = getBoundsDepth() / 16.0F;
		/*EnumFacing orientation = EnumFacing.getOrientation(worldIn.getBlockState(pos).getBlock());//meta?
		if ((orientation == EnumFacing.NORTH) || (orientation == EnumFacing.SOUTH))
			setBlockBounds(0.5F - w / 2, 0.0F, 0.5F - d / 2, 0.5F + w / 2, h, 0.5F + d / 2);
		else if ((orientation == EnumFacing.WEST) || (orientation == EnumFacing.EAST))
			setBlockBounds(0.5F - d / 2, 0.0F, 0.5F - w / 2, 0.5F + d / 2, h, 0.5F + w / 2);
		else setBlockBounds(0.5F - w / 2, 0.0F, 0.5F - w / 2, 0.5F + w / 2, h, 0.5F + w / 2);*/
	}
	
	@Override
	public boolean isOpaqueCube() { return false; }
	/*@Override
	public boolean renderAsNormalBlock() { return false; }*/
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() { return ClientProxy.backpackRenderId; }
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 0;
	}
	
	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer playerIn, World worldIn, BlockPos pos) {
		float hardness = super.getPlayerRelativeBlockHardness(playerIn, worldIn, pos);
		boolean sneaking = playerIn.isSneaking();
		boolean canEquip = ItemBackpack.canEquipBackpack(playerIn);
		boolean stoppedSneaking = localPlayerStoppedSneaking(playerIn);
		return ((stoppedSneaking || (sneaking && !canEquip)) ? -1.0F : (hardness * (sneaking ? 4 : 1)));
	}
	
	boolean lastSneaking = false;
	private boolean localPlayerStoppedSneaking(EntityPlayer player) {
		if (!player.worldObj.isRemote || (player != Minecraft.getMinecraft().thePlayer)) return false;
		boolean stoppedSneaking = (!player.isSneaking() && lastSneaking);
		lastSneaking = player.isSneaking();
		return stoppedSneaking;
	}
	
	private long lastHelpMessage = System.currentTimeMillis();
	
	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		if (worldIn.isRemote && playerIn.isSneaking() && !ItemBackpack.canEquipBackpack(playerIn) &&
			    BetterStorage.globalConfig.getBoolean(GlobalConfig.enableHelpTooltips) &&
			    (System.currentTimeMillis() > lastHelpMessage + 10 * 1000)) {
				boolean backpack = (ItemBackpack.getBackpack(playerIn) != null);
				playerIn.addChatMessage(new ChatComponentTranslation("tile.betterstorage.backpack.cantEquip." + (backpack ? "backpack" : "chestplate")));
				lastHelpMessage = System.currentTimeMillis();
			}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBackpack();
	}	
}
