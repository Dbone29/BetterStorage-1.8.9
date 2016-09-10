package de.dbone.betterstorage.addon.thaumcraft;

import de.dbone.betterstorage.misc.Constants;
import de.dbone.betterstorage.tile.entity.TileEntityBackpack;

public class TileEntityThaumcraftBackpack extends TileEntityBackpack {
	
	@Override
	public String getName() { return Constants.containerThaumcraftBackpack; }
	
	@Override
	public int getColumns() { return 13; }
	
}
