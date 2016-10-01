package de.dbone.betterstorage.tile;

import de.dbone.betterstorage.tile.entity.TileEntityLocker;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileInvisibleBlock extends BlockContainer {

	protected TileInvisibleBlock(Material materialIn) {
		super(materialIn);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityLocker();
	}

}
