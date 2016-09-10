package de.dbone.betterstorage.tile;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.container.ContainerBetterStorage;
import de.dbone.betterstorage.content.BetterStorageItems;
import de.dbone.betterstorage.inventory.InventoryTileEntity;
import de.dbone.betterstorage.item.ItemBackpack;
import de.dbone.betterstorage.misc.Constants;
import de.dbone.betterstorage.network.packet.PacketBackpackTeleport;
import de.dbone.betterstorage.tile.entity.TileEntityBackpack;
import de.dbone.betterstorage.utils.GuiHandler;
import de.dbone.betterstorage.utils.RandomUtils;
import de.dbone.betterstorage.utils.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class TileEnderBackpack extends TileBackpack {
	
	public TileEnderBackpack() {
		setHardness(3.0f);
	}
	
	@Override
	public ItemBackpack getItemType() { return BetterStorageItems.itemEnderBackpack; }
	
	public static boolean teleportRandomly(World world, double sourceX, double sourceY, double sourceZ, boolean canFloat, ItemStack stack) {
		
		int x = (int)sourceX + RandomUtils.getInt(-12, 12 + 1);
		int y = (int)sourceY + RandomUtils.getInt(-8, 8 + 1);
		int z = (int)sourceZ + RandomUtils.getInt(-12, 12 + 1);
		y = Math.max(1, Math.min(world.getHeight() - 1, y));
		
		//if (!world.blockExists(x, y, z)) return false;
		Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
		if (!block.isReplaceable(world,new BlockPos(x, y, z))) return false;
		if (!canFloat && !world.isSideSolid(new BlockPos(x, y - 1, z), EnumFacing.UP)) return false;
		
		BetterStorage.networkChannel.sendToAllAround(
				new PacketBackpackTeleport(sourceX, sourceY, sourceZ, x, y, z),
				world, sourceX + 0.5, sourceY + 0.5, sourceZ + 0.5, 256);
		
		world.playSoundEffect(sourceX + 0.5, sourceY + 0.5, sourceZ + 0.5,
		                      "mob.endermen.portal", 1.0F, 1.0F);
		world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5,
		                      "mob.endermen.portal", 1.0F, 1.0F);
		
		//world.setBlock(x, y, z, ((ItemBackpack)stack.getItem()).getBlockType(), RandomUtils.getInt(2, 6), 3);
		TileEntityBackpack newBackpack = WorldUtils.get(world, new BlockPos(x, y, z), TileEntityBackpack.class);
		newBackpack.stack = stack;
		
		return true;
		
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			playerIn.openGui(BetterStorage.instance, GuiHandler.GUI_ENDER_BACKPACK, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityBackpack();
	}
}
