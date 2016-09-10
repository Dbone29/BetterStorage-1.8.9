package de.dbone.betterstorage.tile;

import java.util.Random;

import de.dbone.betterstorage.item.tile.ItemPresent;
import de.dbone.betterstorage.proxy.ClientProxy;
import de.dbone.betterstorage.tile.entity.TileEntityPresent;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TilePresent extends TileContainerBetterStorage {
	
	public TilePresent() {
		super(Material.cloth);
		setCreativeTab(null);
		
		setHardness(0.75f);
		setStepSound(soundTypeCloth);
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}
	
	@Override
	public Class<? extends ItemBlock> getItemClass() { return ItemPresent.class; }
	
	@Override
	public boolean isOpaqueCube() { return false; }
	/*@Override
	public boolean renderAsNormalBlock() { return false; }*/
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() { return ClientProxy.presentRenderId; }
	
	@Override
	public int quantityDropped(Random rand) { return 0; }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityPresent();
	}
	
}
