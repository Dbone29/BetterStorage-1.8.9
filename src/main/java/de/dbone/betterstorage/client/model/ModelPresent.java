package de.dbone.betterstorage.client.model;

import de.dbone.betterstorage.misc.Resources;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.client.model.AdvancedModelLoader;
//import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelPresent extends ModelBase {
	
	//private IModelCustom model;
	
	protected ResourceLocation modelPath() { return Resources.modelPresent; }
	
	public ModelPresent() {
		//model = AdvancedModelLoader.loadModel(modelPath());
	}
	
	public void render(int layer) {
		//model.renderPart("layer" + layer);
	}
	
}
