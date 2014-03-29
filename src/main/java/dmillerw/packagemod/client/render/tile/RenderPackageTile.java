package dmillerw.packagemod.client.render.tile;

import dmillerw.packagemod.block.tile.TilePackage;
import dmillerw.packagemod.client.texture.TextureHelper;
import dmillerw.packagemod.client.texture.TextureSection;
import dmillerw.packagemod.lib.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
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

	private static final String BOX = "BoxFull___box_1";
	private static final String FLAP_LEFT = "BoxFull___Fold_1";
	private static final String FLAP_RIGHT = "BoxFull___Fold_2";
	private static final String FLAP_FLAT = "BoxFull___Top";

	private static final int TEXTURE_WIDTH = 128;
	private static final int TEXTURE_HEIGHT = 128;

	public static final ResourceLocation TEXTURE = new ResourceLocation(ModInfo.RESOURCE_PREFIX + "textures/models/package.png");
	public static final ResourceLocation TEXTURE_TAPED = new ResourceLocation(ModInfo.RESOURCE_PREFIX + "textures/models/package_taped.png");

	private IModelCustom modelPackage;

	public RenderPackageTile() {
		modelPackage = AdvancedModelLoader.loadModel("/assets/package/models/package.obj");
	}

	public void renderPackageAt(TilePackage tile, double x, double y, double z, float partial) {
		GL11.glPushMatrix();

		GL11.glTranslated(x + 0.5, y, z + 0.5);

		Minecraft.getMinecraft().renderEngine.bindTexture(tile.taped ? tile.tapeTick == tile.tapeTickMax ? TEXTURE_TAPED : TEXTURE : TEXTURE);

		modelPackage.renderOnly(BOX);

		if (tile.rotationTick > 0) {
			// "dramatic" shadow
			GL11.glDisable(GL11.GL_TEXTURE_2D);

			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_BLEND);

			GL11.glColor4f(0, 0, 0, 1F - (1F * (tile.rotation / TilePackage.ROTATION_MAX)));

			GL11.glTranslated(0, -0.01F, 0);
			modelPackage.renderOnly(FLAP_FLAT);
			GL11.glTranslated(0, 0.01F, 0);

			GL11.glColor4f(1, 1, 1, 1);

			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_TEXTURE_2D);

			// Left flap
			GL11.glPushMatrix();

			GL11.glTranslated(0.5, 1, 0);
			GL11.glRotated(-tile.rotation, 0, 0, 1);
			GL11.glTranslated(-0.5, -1, -0);

			// Fixes flap being slightly offset
			GL11.glTranslated(0, 0, 0.0055);

			modelPackage.renderOnly(FLAP_LEFT);
			GL11.glPopMatrix();

			// Right flap
			GL11.glPushMatrix();

			GL11.glTranslated(-0.5, 1, 0);
			GL11.glRotated(tile.rotation, 0, 0, 1);
			GL11.glTranslated(0.5, -1, -0);

			modelPackage.renderOnly(FLAP_RIGHT);
			GL11.glPopMatrix();
		} else {
//			modelPackage.renderOnly(FLAP_FLAT);

			if (tile.taped) {
				Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_TAPED);

				float progress = (float) tile.tapeTick / (float) tile.tapeTickMax;

				Tessellator t = Tessellator.instance;

				t.startDrawingQuads();
				t.setNormal(0, 1, 0);

				// X determines flap
				// Z is modified by tape progress

				// First flap (left on texture sheet)
				TextureSection leftFlap = TextureHelper.getSection(TEXTURE_WIDTH, TEXTURE_HEIGHT, 32, 0, 32, 16);
				t.addVertexWithUV(-0.5, 1.001,  0.5 * progress, leftFlap.getInterpolatedU(progress), leftFlap.getMaxV());
				t.addVertexWithUV( 0,   1.001,  0.5 * progress, leftFlap.getInterpolatedU(progress), leftFlap.getMinV());
				t.addVertexWithUV( 0,   1.001, -0.5, leftFlap.getMinU(), leftFlap.getMinV());
				t.addVertexWithUV(-0.5, 1.001, -0.5, leftFlap.getMinU(), leftFlap.getMaxV());

				// Second flap (right on texture sheet)
				TextureSection rightFlap = TextureHelper.getSection(TEXTURE_WIDTH, TEXTURE_HEIGHT, 64, 0, 32, 16);
				t.addVertexWithUV(0,   1.001,  0.5 * progress, rightFlap.getInterpolatedU(progress), rightFlap.getMaxV());
				t.addVertexWithUV(0.5, 1.001,  0.5 * progress, rightFlap.getInterpolatedU(progress), rightFlap.getMinV());
				t.addVertexWithUV(0.5, 1.001, -0.5, rightFlap.getMinU(), rightFlap.getMinV());
				t.addVertexWithUV(0,   1.001, -0.5, rightFlap.getMinU(), rightFlap.getMaxV());
				t.draw();


			}
		}

		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		renderPackageAt((TilePackage) tileentity, d0, d1, d2, f);
	}

}
