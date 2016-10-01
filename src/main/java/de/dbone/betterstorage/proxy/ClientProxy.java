package de.dbone.betterstorage.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

import org.lwjgl.opengl.GL11;

import de.dbone.betterstorage.attachment.Attachment;
import de.dbone.betterstorage.attachment.Attachments;
import de.dbone.betterstorage.attachment.IHasAttachments;
import de.dbone.betterstorage.client.model.ModelBackpackArmor;
//import de.dbone.betterstorage.entity.EntityFrienderman;
import de.dbone.betterstorage.item.ItemBackpack;
import de.dbone.betterstorage.misc.Resources;
import de.dbone.betterstorage.misc.handlers.KeyBindingHandler;
import de.dbone.betterstorage.utils.RenderUtils;
import de.dbone.betterstorage.utils.WorldUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
	public static int reinforcedChestRenderId;
	public static int lockerRenderId;
	public static int armorStandRenderId;
	public static int backpackRenderId;
	public static int reinforcedLockerRenderId;
	public static int lockableDoorRenderId;
	public static int presentRenderId;
	
	@Override
	public void initialize() {
		super.initialize();		
		new KeyBindingHandler();
	}
	
	@SubscribeEvent
	public void drawBlockHighlight(DrawBlockHighlightEvent event) {
		
		EntityPlayer player = event.player;
		World world = player.worldObj;
		MovingObjectPosition target = WorldUtils.rayTrace(player, event.partialTicks);
		
		if ((target == null) || (target.typeOfHit != MovingObjectType.BLOCK)) return;
		int x = target.getBlockPos().getX();
		int y = target.getBlockPos().getY();
		int z = target.getBlockPos().getZ();
		
		AxisAlignedBB box = null;
		Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

		if (block == Blocks.iron_door)
			box = getIronDoorHightlightBox(player, world, x, y, z, target.hitVec, block);
		else if (tileEntity instanceof IHasAttachments)
			box = getAttachmentPointsHighlightBox(player, tileEntity, target);
		
		if (box == null) return;
		
		double xOff = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.partialTicks;
		double yOff = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.partialTicks;
		double zOff = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.partialTicks;
		box.offset(-xOff, -yOff, -zOff);
        
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(false);
		
		RenderGlobal.drawOutlinedBoundingBox(box, -1, 0, 0, 0);
		
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		
		event.setCanceled(true);
		
	}	
	
	private AxisAlignedBB getAttachmentPointsHighlightBox(EntityPlayer player, TileEntity tileEntity,
	                                                      MovingObjectPosition target) {
		Attachments attachments = ((IHasAttachments)tileEntity).getAttachments();
		Attachment attachment = attachments.get(target.subHit);
		if (attachment == null) return null;
		return attachment.getHighlightBox();
	}
	
	@SubscribeEvent
	public void onRenderPlayerSpecialsPre(RenderPlayerEvent.Specials.Pre event) {
		ItemStack backpack = ItemBackpack.getBackpackData(event.entityPlayer).backpack;
		if (backpack != null) {
			
			EntityPlayer player = event.entityPlayer;
			float partial = event.partialRenderTick;
			ItemBackpack backpackType = (ItemBackpack)backpack.getItem();
			int color = backpackType.getColor(backpack);
			ModelBackpackArmor model = (ModelBackpackArmor)backpackType.getArmorModel(player, backpack, 0);
			
			/*model.onGround = ReflectionUtils.invoke(
					RendererLivingEntity.class, event.renderer, "func_77040_d", "renderSwingProgress",
					EntityLivingBase.class, float.class, player, partial);*/
			model.setLivingAnimations(player, 0, 0, partial);
			
			RenderUtils.bindTexture(new ResourceLocation(backpackType.getArmorTexture(backpack, player, 0, null)));
			RenderUtils.setColorFromInt((color >= 0) ? color : 0xFFFFFF);
			model.render(player, 0, 0, 0, 0, 0, 0);
			
			if (color >= 0) {
				RenderUtils.bindTexture(new ResourceLocation(backpackType.getArmorTexture(backpack, player, 0, "overlay")));
				GL11.glColor3f(1.0F, 1.0F, 1.0F);
				model.render(player, 0, 0, 0, 0, 0, 0);
			}
			
			if (backpack.isItemEnchanted()) {
				float f9 = player.ticksExisted + partial;
				
				RenderUtils.bindTexture(Resources.enchantedEffect);

				GL11.glEnable(GL11.GL_BLEND);
				GL11.glColor4f(0.5F, 0.5F, 0.5F, 1.0F);
				GL11.glDepthFunc(GL11.GL_EQUAL);
				GL11.glDepthMask(false);
				for (int k = 0; k < 2; ++k) {
					GL11.glDisable(GL11.GL_LIGHTING);
					float f11 = 0.76F;
					GL11.glColor4f(0.5F * f11, 0.25F * f11, 0.8F * f11, 1.0F);
					GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
					GL11.glMatrixMode(GL11.GL_TEXTURE);
					GL11.glLoadIdentity();
					float f12 = f9 * (0.001F + k * 0.003F) * 20.0F;
					float f13 = 0.33333334F;
					GL11.glScalef(f13, f13, f13);
					GL11.glRotatef(30.0F - k * 60.0F, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(0.0F, f12, 0.0F);
					GL11.glMatrixMode(GL11.GL_MODELVIEW);
					model.render(player, 0, 0, 0, 0, 0, 0);
				}
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glDepthMask(true);
				GL11.glLoadIdentity();
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glDepthFunc(GL11.GL_LEQUAL);
			}
			
		} else backpack = ItemBackpack.getBackpack(event.entityPlayer);
		if (backpack != null) event.renderCape = false;
	}
	
}
