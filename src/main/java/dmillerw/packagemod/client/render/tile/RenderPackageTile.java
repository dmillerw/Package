package dmillerw.packagemod.client.render.tile;

import dmillerw.packagemod.block.tile.TilePackage;
import dmillerw.packagemod.lib.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

/**
 * @author dmillerw
 */
public class RenderPackageTile extends TileEntitySpecialRenderer {

	private static final String FLAP_LEFT = "BoxFull___Fold_1";
	private static final String FLAG_RIGHT = "BoxFull___Fold_2";

	public static final ResourceLocation TEXTURE = new ResourceLocation(ModInfo.RESOURCE_PREFIX + "textures/models/package.png");
	public static final ResourceLocation TEXTURE_TAPED = new ResourceLocation(ModInfo.RESOURCE_PREFIX + "textures/models/package_taped.png");

	private IModelCustom modelPackage;

	public RenderPackageTile() {
		modelPackage = AdvancedModelLoader.loadModel("/assets/package/models/package.obj");
	}

	public void renderPackageAt(TilePackage tile, double x, double y, double z, float partial) {
		GL11.glPushMatrix();

		GL11.glTranslated(x - 0.5, y - 0.09, z - 0.375);

		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);

		modelPackage.renderAllExcept(FLAG_RIGHT, FLAP_LEFT);

		// Flap rotations
		float rotation = 0F;

		// Left flap
		GL11.glPushMatrix();

		GL11.glTranslated(0.5, 1, 0);
		GL11.glRotated(-rotation, 0, 0, 1);
		GL11.glTranslated(-0.5, -1, -0);

		// Fixes flap being slightly offset
		GL11.glTranslated(0, 0, 0.0055);

		modelPackage.renderOnly(FLAP_LEFT);
		GL11.glPopMatrix();

//		// Right flap
		GL11.glPushMatrix();

		GL11.glTranslated(-0.5, 1, 0);
		GL11.glRotated(rotation, 0, 0, 1);
		GL11.glTranslated(0.5, -1, -0);

		modelPackage.renderOnly(FLAG_RIGHT);
		GL11.glPopMatrix();

		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		renderPackageAt((TilePackage) tileentity, d0, d1, d2, f);
	}

}
