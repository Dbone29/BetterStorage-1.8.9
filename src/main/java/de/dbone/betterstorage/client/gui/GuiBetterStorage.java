package de.dbone.betterstorage.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import de.dbone.betterstorage.addon.thaumcraft.ThaumcraftResources;
import de.dbone.betterstorage.container.ContainerBetterStorage;
import de.dbone.betterstorage.misc.Resources;
import de.dbone.betterstorage.utils.RenderUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBetterStorage extends GuiContainer {
	
	public final ContainerBetterStorage container;
	public final String title;
	
	private final int columns;
	private final int rows;
	
	public int getColumns() { return columns; }
	public int getRows() { return rows; }
	
	public GuiBetterStorage(ContainerBetterStorage container) {
		super(container);
		
		this.container = container;
		IInventory inv = container.inventory;
		title = (inv.hasCustomName() ? inv.getName() : StatCollector.translateToLocal(inv.getName()));
		columns = container.getColumns();
		rows = container.getRows();
		
		xSize = 14 + columns * 18;
		ySize = container.getHeight();
		
		container.setUpdateGui(this);
	}
	public GuiBetterStorage(EntityPlayer player, int columns, int rows, IInventory inventory) {
		this(new ContainerBetterStorage(player, inventory, columns, rows));
	}
	public GuiBetterStorage(EntityPlayer player, int columns, int rows, String title, boolean localized) {
		this(player, columns, rows, new InventoryBasic(title, localized, columns * rows));
	}
	public GuiBetterStorage(EntityPlayer player, int columns, int rows, String title) {
		this(player, columns, rows, title, false);
	}
	
	protected ResourceLocation getResource() {
		if (columns <= 9) return new ResourceLocation("textures/gui/container/generic_54.png");
		else return Resources.containerReinforcedChest;
	}
	
	protected int getHeight() { return 223; }
	
	protected int getTextureWidth() { return 256; }
	protected int getTextureHeight() { return 256; }
	
	public void update(int par1, int par2) {  }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRendererObj.drawString(title, 8, 6, 0x404040);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8 + (xSize - 176) / 2, ySize - 94, 0x404040);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1, 1, 1, 1);
		mc.renderEngine.bindTexture(getResource());
		RenderUtils.bindTexture(getResource());
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		int w = getTextureWidth();
		int h = getTextureHeight() / 2;
		int m = 107;
		int m1 = ySize - m;
		int m2 = getHeight() - m;
		
		if(getResource() == ThaumcraftResources.thaumiumChestContainer) {
			w = w / 2;
			x = x / 2;
			GL11.glScalef(2, 1, 1);
			if(rows <= 3)
				drawTexturedModalRect(x, y, 0, 0, w, h / 2 + 4);
			else
				drawTexturedModalRect(x, y, 0, 0, w, h);
		} else if(getResource() == Resources.containerReinforcedChest && rows <= 3)
			drawTexturedModalRect(x, y, 0, 0, w, h / 2);
		else if(getResource() == Resources.containerCraftingStation)
			drawTexturedModalRect(x, y, 0, 0, xSize, h);			
		else
			drawTexturedModalRect(x, y, 0, 0, w, h);
		drawTexturedModalRect(x, y + m1, 0, m2, w, h);
		
		if(getResource() == ThaumcraftResources.thaumiumChestContainer)
			GL11.glScalef((float) 0.5, 1, 1);
	}
	
}
