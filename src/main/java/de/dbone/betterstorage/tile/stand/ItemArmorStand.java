package de.dbone.betterstorage.tile.stand;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemArmorStand extends ItemBlock {
	
	public ItemArmorStand(Block block) {
		super(block);
		setMaxStackSize(1);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		
		if (stack.stackSize == 0) return false;
		
		Block blockClicked = worldIn.getBlockState(pos).getBlock();
		
		// If the block clicked is air or snow,
		// don't change the target coordinates, but set the side to 1 (top).
		//if ((blockClicked == Blocks.snow_layer) &&
		//    ((worldIn.getBlockState(pos).getBlock().getMetaFromState(worldIn.getBlockState(pos)) & 7) < 1)) side = 1;
		// If the block clicked is not replaceable,
		// adjust the coordinates depending on the side clicked.
		//else if (!blockClicked.isReplaceable(worldIn, pos)) {
			/*switch (side) {
				case 0: y--; break;
				case 1: y++; break;
				case 2: z--; break;
				case 3: z++; break;
				case 4: x--; break;
				case 5: x++; break;
			}*/
		//}
		
		// Return false if there's not enough world height left.
		if (pos.getY() >= worldIn.getHeight() - 2) return false;
		
		Block blockTop = worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).getBlock();
		
		// Return false if the block above isn't replaceable.
		if (!blockTop.isReplaceable(worldIn, new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()))) return false;
		
		// Return false if the player can't edit any of the
		// two blocks the armor stand would occupy.
		if (!playerIn.canPlayerEdit(pos, side, stack) ||
			!playerIn.canPlayerEdit(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), side, stack)) return false;
		
		//Block block = field_150939_a;
		// Return false if there's an entity blocking the placement.
		//if (!worldIn.canPlaceEntityOnSide(block, x, y, z, false, side, playerIn, stack)) return false;
		
		// Actually place the block in the world,
		// play place sound and decrease stack size if successful.
		if (placeBlockAt(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ, null)) {
			//String sound = block.stepSound.func_150496_b();
			float volume = (block.stepSound.getVolume() + 1.0F) / 2.0F;
			//float pitch = block.stepSound.getPitch() * 0.8F;
			//worldIn.playSoundEffect(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5F, sound, volume, pitch);
			stack.stackSize--;
		}		
		
		return true;
	}	
}
