package de.dbone.betterstorage.tile;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.tile.entity.TileEntityCraftingStation;
import de.dbone.betterstorage.utils.GuiHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
//import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class TileCraftingStation extends TileContainerBetterStorage {
	
	public TileCraftingStation() {
		super(Material.iron);
		
		setHardness(1.5f);
		setStepSound(soundTypeStone);
	}
	
	@Override
	public boolean isNormalCube() { return false; }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCraftingStation();
	}
	
}
