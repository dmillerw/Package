package dmillerw.packagemod.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

import java.util.Random;

/**
 * @author dmillerw
 */
public class ModelPixel extends ModelBase {

	public ModelRenderer pixel;

	public Random rand;

	public ModelPixel() {
		rand = new Random();

		textureWidth = 64;
		textureHeight = 32;

		pixel = new ModelRenderer(this, 0, 0);
		pixel.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
		pixel.setRotationPoint(0F, 0F, 0F);
		pixel.setTextureSize(64, 32);
		pixel.mirror = true;
		setRotation(pixel, 0F, 0F, 0F);
	}

	public void render() {
		pixel.render(0.0625F);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
