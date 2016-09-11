package de.dbone.betterstorage.addon.thaumcraft;

import de.dbone.betterstorage.BetterStorage;
import de.dbone.betterstorage.tile.TileReinforcedChest;
import de.dbone.betterstorage.utils.GuiHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileThaumiumChest extends TileReinforcedChest {
	
	public TileThaumiumChest() {
		super(Material.iron);
		
		setHardness(12.0f);
		setResistance(35.0f);
		setStepSound(soundTypeMetal);
		
		setHarvestLevel("pickaxe", 2);
	}
	
	@Override
	public boolean hasMaterial() { return false; }
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if(((TileEntityThaumiumChest) worldIn.getTileEntity(pos)).isConnected())
			playerIn.openGui(BetterStorage.instance, GuiHandler.GUI_THAUMIUM_CHEST_LARGE, worldIn, pos.getX(), pos.getY(), pos.getZ());
		else
			playerIn.openGui(BetterStorage.instance, GuiHandler.GUI_THAUMIUM_CHEST, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityThaumiumChest();
	}
	
}
