package de.dbone.betterstorage.addon.thaumcraft;

import de.dbone.betterstorage.item.ItemBackpack;
import de.dbone.betterstorage.tile.TileBackpack;
import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileThaumcraftBackpack extends TileBackpack {
	
	/*@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon("wool_colored_purple");
	}*/
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityThaumcraftBackpack();
	}
	
	@Override
	public ItemBackpack getItemType() { return ThaumcraftAddon.itemThaumcraftBackpack; }
	
}
