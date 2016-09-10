package de.dbone.betterstorage.tile;

import java.util.Random;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.item.tile.ItemCardboardBox;
import de.dbone.betterstorage.tile.entity.TileEntityCardboardBox;
import de.dbone.betterstorage.utils.GuiHandler;
import de.dbone.betterstorage.utils.WorldUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TileCardboardBox extends TileContainerBetterStorage {
	
	public TileCardboardBox() {
		super(Material.wood);
		
		setHardness(0.8f);
		setStepSound(soundTypeWood);
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}
	
	@Override
	public Class<? extends ItemBlock> getItemClass() { return ItemCardboardBox.class; }
	
	@Override
	public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
		TileEntityCardboardBox box = WorldUtils.get(worldIn, pos, TileEntityCardboardBox.class);
		return (((box != null) && (box.color >= 0)) ? box.color : 0x705030);
	}
	
	@Override
	public boolean isOpaqueCube() { return false; }
	
	@Override
	public int quantityDropped(Random random) {
		return 0;
	}
	
	/*@Override
	public boolean renderAsNormalBlock() { return false; }*/
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		playerIn.openGui(BetterStorage.instance, GuiHandler.GUI_CARDBOARD_BOX, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCardboardBox();
	}
	
}
